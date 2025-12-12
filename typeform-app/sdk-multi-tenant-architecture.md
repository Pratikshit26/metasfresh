# FormBuilder Pro - SDK & Multi-Tenant Architecture

## 🏢 Multi-Tenant Architecture Design

### **Tenant Isolation Strategy**

#### **1. Database-Level Multi-Tenancy**
```sql
-- Tenant Management Schema
CREATE TABLE tenants (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(100) UNIQUE NOT NULL,
    domain VARCHAR(255) UNIQUE, -- Custom domain support
    subdomain VARCHAR(100) UNIQUE NOT NULL, -- tenant.formbuilder.com
    
    -- Subscription & Billing
    subscription_tier ENUM('free', 'pro', 'enterprise', 'white_label') DEFAULT 'free',
    subscription_status ENUM('active', 'past_due', 'canceled', 'trialing') DEFAULT 'trialing',
    trial_ends_at TIMESTAMP,
    subscription_expires_at TIMESTAMP,
    
    -- Configuration
    settings JSONB DEFAULT '{}',
    branding JSONB DEFAULT '{}', -- Logo, colors, custom CSS
    limits JSONB DEFAULT '{}',   -- Form limits, response limits, etc.
    
    -- Status & Metadata -> THIS needs to be in phase 5 for this issue.
    status ENUM('active', 'suspended', 'deleted') DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    -- Indexes
    INDEX idx_tenants_slug (slug),
    INDEX idx_tenants_domain (domain),
    INDEX idx_tenants_subdomain (subdomain),
    INDEX idx_tenants_status (status)
);

-- Row-Level Security (RLS) Implementation
ALTER TABLE users ENABLE ROW LEVEL SECURITY;
ALTER TABLE forms ENABLE ROW LEVEL SECURITY;
ALTER TABLE form_responses ENABLE ROW LEVEL SECURITY;

-- RLS Policies
CREATE POLICY tenant_isolation_users ON users
    FOR ALL USING (tenant_id = current_setting('app.current_tenant_id')::UUID);

CREATE POLICY tenant_isolation_forms ON forms
    FOR ALL USING (tenant_id = current_setting('app.current_tenant_id')::UUID);

CREATE POLICY tenant_isolation_responses ON form_responses
    FOR ALL USING (
        form_id IN (
            SELECT id FROM forms WHERE tenant_id = current_setting('app.current_tenant_id')::UUID
        )
    );
```

#### **2. Updated Database Schema for Multi-Tenancy**
```sql
-- Add tenant_id to all tenant-specific tables
ALTER TABLE users ADD COLUMN tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE;
ALTER TABLE forms ADD COLUMN tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE;
ALTER TABLE workspaces ADD COLUMN tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE;

-- Tenant Users (Junction table for cross-tenant access)
CREATE TABLE tenant_users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role ENUM('owner', 'admin', 'member', 'viewer') NOT NULL DEFAULT 'member',
    permissions JSONB DEFAULT '[]',
    invited_by UUID REFERENCES users(id),
    invited_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    joined_at TIMESTAMP,
    
    UNIQUE KEY unique_tenant_user (tenant_id, user_id),
    INDEX idx_tenant_users_tenant (tenant_id),
    INDEX idx_tenant_users_user (user_id)
);

-- Tenant API Keys (for SDK access)
CREATE TABLE tenant_api_keys (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    key_hash VARCHAR(255) NOT NULL UNIQUE,
    key_prefix VARCHAR(10) NOT NULL, -- fb_live_xxx or fb_test_xxx
    
    -- Permissions & Scope
    permissions JSONB DEFAULT '[]',
    scopes TEXT[] DEFAULT '{}', -- ['forms:read', 'forms:write', 'responses:read']
    
    -- Rate Limiting
    rate_limit_per_hour INTEGER DEFAULT 1000,
    rate_limit_per_day INTEGER DEFAULT 10000,
    
    -- Status
    is_active BOOLEAN DEFAULT true,
    last_used_at TIMESTAMP,
    expires_at TIMESTAMP,
    created_by UUID REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    INDEX idx_api_keys_tenant (tenant_id),
    INDEX idx_api_keys_hash (key_hash),
    INDEX idx_api_keys_active (is_active, expires_at)
);
```

#### **3. Tenant Context Middleware**
```typescript
// middleware/tenant-context.ts
import { NextRequest, NextResponse } from 'next/server';
import { prisma } from '@/lib/prisma';

interface TenantContext {
  tenant: Tenant;
  subdomain?: string;
  customDomain?: string;
}

export async function tenantMiddleware(request: NextRequest) {
  const host = request.headers.get('host') || '';
  const url = request.nextUrl.clone();
  
  // Extract tenant identifier
  let tenantIdentifier: string | null = null;
  let tenantType: 'subdomain' | 'domain' | 'path' = 'subdomain';
  
  // 1. Custom Domain Detection (highest priority)
  if (!host.includes('.formbuilder.com') && !host.includes('localhost')) {
    tenantIdentifier = host;
    tenantType = 'domain';
  }
  // 2. Subdomain Detection
  else if (host.includes('.formbuilder.com')) {
    const subdomain = host.split('.')[0];
    if (subdomain !== 'www' && subdomain !== 'api' && subdomain !== 'app') {
      tenantIdentifier = subdomain;
      tenantType = 'subdomain';
    }
  }
  // 3. Path-based (fallback for development)
  else if (url.pathname.startsWith('/t/')) {
    const pathSegments = url.pathname.split('/');
    tenantIdentifier = pathSegments[2];
    tenantType = 'path';
  }
  
  if (tenantIdentifier) {
    // Resolve tenant
    const tenant = await resolveTenant(tenantIdentifier, tenantType);
    
    if (!tenant) {
      return new NextResponse('Tenant not found', { status: 404 });
    }
    
    if (tenant.status !== 'active') {
      return new NextResponse('Tenant suspended', { status: 403 });
    }
    
    // Set tenant context in headers
    const requestHeaders = new Headers(request.headers);
    requestHeaders.set('x-tenant-id', tenant.id);
    requestHeaders.set('x-tenant-slug', tenant.slug);
    
    // Rewrite URL for multi-tenant routing
    if (tenantType === 'subdomain' || tenantType === 'domain') {
      url.pathname = `/tenant/${tenant.slug}${url.pathname}`;
    }
    
    return NextResponse.rewrite(url, {
      request: { headers: requestHeaders }
    });
  }
  
  return NextResponse.next();
}

async function resolveTenant(identifier: string, type: 'subdomain' | 'domain' | 'path') {
  const whereClause = type === 'domain' 
    ? { domain: identifier }
    : type === 'subdomain' 
    ? { subdomain: identifier }
    : { slug: identifier };
    
  return await prisma.tenant.findUnique({
    where: whereClause,
    select: {
      id: true,
      slug: true,
      name: true,
      status: true,
      branding: true,
      settings: true,
      subscription_tier: true
    }
  });
}
```

---

## 📦 SDK Architecture & Implementation

### **1. SDK Package Structure**
```bash
packages/sdk/
├── src/
│   ├── client/              # Main SDK client
│   ├── types/               # TypeScript definitions
│   ├── resources/           # API resource classes
│   ├── utils/               # Utility functions
│   └── errors/              # Error handling
├── examples/                # Usage examples
├── docs/                    # SDK documentation
└── tests/                   # SDK tests

# Multi-language SDK support
packages/
├── sdk-js/                  # JavaScript/TypeScript SDK
├── sdk-python/              # Python SDK
├── sdk-php/                 # PHP SDK
└── sdk-go/                  # Go SDK
```

### **2. JavaScript/TypeScript SDK Implementation**
```typescript
// packages/sdk-js/src/client.ts
export interface FormBuilderConfig {
  apiKey: string;
  baseURL?: string;
  timeout?: number;
  retries?: number;
  rateLimitRetry?: boolean;
}

export class FormBuilderClient {
  private config: Required<FormBuilderConfig>;
  private httpClient: AxiosInstance;
  
// Resource instances
  public forms: FormsResource;
  public responses: ResponsesResource;
  public analytics: AnalyticsResource;
  public webhooks: WebhooksResource;
  public templates: TemplatesResource;

  constructor(config: FormBuilderConfig) {
    this.config = {
      baseURL: 'https://api.formbuilder.com/v1',
      timeout: 30000,
      retries: 3,
      rateLimitRetry: true,
      ...config
    };

    this.httpClient = this.createHttpClient();
    
    // Initialize resources
    this.forms = new FormsResource(this.httpClient);
    this.responses = new ResponsesResource(this.httpClient);
    this.analytics = new AnalyticsResource(this.httpClient);
    this.webhooks = new WebhooksResource(this.httpClient);
    this.templates = new TemplatesResource(this.httpClient);
  }

  private createHttpClient(): AxiosInstance {
    const client = axios.create({
      baseURL: this.config.baseURL,
      timeout: this.config.timeout,
      headers: {
        'Authorization': `Bearer ${this.config.apiKey}`,
        'Content-Type': 'application/json',
        'User-Agent': `FormBuilder-JS-SDK/${SDK_VERSION}`
      }
    });

    // Request interceptor for rate limiting
    client.interceptors.request.use((config) => {
      config.metadata = { retryCount: 0, startTime: Date.now() };
      return config;
    });

    // Response interceptor for error handling and retries
    client.interceptors.response.use(
      (response) => response,
      async (error) => {
        return this.handleError(error);
      }
    );

    return client;
  }

  private async handleError(error: AxiosError): Promise<never> {
    if (error.response?.status === 429 && this.config.rateLimitRetry) {
      const retryAfter = error.response.headers['retry-after'];
      const delay = retryAfter ? parseInt(retryAfter) * 1000 : 1000;
      
      await new Promise(resolve => setTimeout(resolve, delay));
      return error.config ? this.httpClient.request(error.config) : Promise.reject(error);
    }

    if (error.response?.status >= 500 && error.config?.metadata?.retryCount < this.config.retries) {
      error.config.metadata.retryCount++;
      const delay = Math.pow(2, error.config.metadata.retryCount) * 1000;
      
      await new Promise(resolve => setTimeout(resolve, delay));
      return this.httpClient.request(error.config);
    }

    throw new FormBuilderError(error);
  }
}
```

### **3. SDK Resource Classes**
```typescript
// packages/sdk-js/src/resources/forms.ts
export class FormsResource {
  constructor(private client: AxiosInstance) {
    
  }

  async list(params?: ListFormsParams): Promise<PaginatedResponse<Form>> {
    const response = await this.client.get('/forms', { params });
    return response.data;
  }

  async create(form: CreateFormParams): Promise<Form> {
    const response = await this.client.post('/forms', form);
    return response.data;
  }

  async get(formId: string): Promise<Form> {
    const response = await this.client.get(`/forms/${formId}`);
    return response.data;
  }

  async update(formId: string, updates: UpdateFormParams): Promise<Form> {
    const response = await this.client.put(`/forms/${formId}`, updates);
    return response.data;
  }

  async delete(formId: string): Promise<void> {
    await this.client.delete(`/forms/${formId}`);
  }

  async publish(formId: string): Promise<Form> {
    const response = await this.client.post(`/forms/${formId}/publish`);
    return response.data;
  }

  async unpublish(formId: string): Promise<Form> {
    const response = await this.client.post(`/forms/${formId}/unpublish`);
    return response.data;
  }

  async duplicate(formId: string, title?: string): Promise<Form> {
    const response = await this.client.post(`/forms/${formId}/duplicate`, { title });
    return response.data;
  }
}

// packages/sdk-js/src/resources/responses.ts
export class ResponsesResource {
  constructor(private client: AxiosInstance) {}

  async list(formId: string, params?: ListResponsesParams): Promise<PaginatedResponse<FormResponse>> {
    const response = await this.client.get(`/forms/${formId}/responses`, { params });
    return response.data;
  }

  async get(formId: string, responseId: string): Promise<FormResponse> {
    const response = await this.client.get(`/forms/${formId}/responses/${responseId}`);
    return response.data;
  }

  async create(formId: string, response: CreateResponseParams): Promise<FormResponse> {
    const response_data = await this.client.post(`/forms/${formId}/responses`, response);
    return response_data.data;
  }

  async update(formId: string, responseId: string, updates: UpdateResponseParams): Promise<FormResponse> {
    const response = await this.client.put(`/forms/${formId}/responses/${responseId}`, updates);
    return response.data;
  }

  async delete(formId: string, responseId: string): Promise<void> {
    await this.client.delete(`/forms/${formId}/responses/${responseId}`);
  }

  async export(formId: string, format: 'csv' | 'xlsx' | 'json' = 'csv'): Promise<Blob> {
    const response = await this.client.post(`/forms/${formId}/responses/export`, 
      { format }, 
      { responseType: 'blob' }
    );
    return response.data;
  }
}
```

### **4. SDK Type Definitions**
```typescript
// packages/sdk-js/src/types/index.ts
export interface Form {
  id: string;
  title: string;
  description?: string;
  slug?: string;
  questions: Question[];
  settings: FormSettings;
  status: 'draft' | 'published' | 'paused' | 'archived';
  responseCount: number;
  createdAt: string;
  updatedAt: string;
  publishedAt?: string;
}

export interface Question {
  id: string;
  type: QuestionType;
  title: string;
  description?: string;
  required: boolean;
  properties: QuestionProperties;
  logic?: ConditionalLogic;
}

export type QuestionType = 
  | 'text' 
  | 'textarea' 
  | 'email' 
  | 'number' 
  | 'phone' 
  | 'url' 
  | 'date' 
  | 'time'
  | 'multiple_choice' 
  | 'single_choice' 
  | 'rating' 
  | 'scale' 
  | 'dropdown'
  | 'file_upload' 
  | 'payment';

export interface FormResponse {
  id: string;
  formId: string;
  answers: Record<string, any>;
  respondentId?: string;
  status: 'in_progress' | 'completed' | 'abandoned';
  metadata: ResponseMetadata;
  startedAt: string;
  completedAt?: string;
  completionTime?: number;
}

export interface PaginatedResponse<T> {
  data: T[];
  pagination: {
    page: number;
    perPage: number;
    total: number;
    totalPages: number;
    hasNext: boolean;
    hasPrev: boolean;
  };
}

// API Parameter Types
export interface ListFormsParams {
  page?: number;
  perPage?: number;
  status?: 'draft' | 'published' | 'paused' | 'archived';
  search?: string;
  sortBy?: 'created_at' | 'updated_at' | 'title' | 'response_count';
  sortOrder?: 'asc' | 'desc';
}

export interface CreateFormParams {
  title: string;
  description?: string;
  questions?: Question[];
  settings?: Partial<FormSettings>;
}

export interface UpdateFormParams {
  title?: string;
  description?: string;
  questions?: Question[];
  settings?: Partial<FormSettings>;
}
```

### **5. SDK Usage Examples**
```typescript
// examples/basic-usage.ts
import { FormBuilderClient } from '@formbuilder/sdk';

const client = new FormBuilderClient({
  apiKey: 'fb_live_abc123...'
});

// Create a form
const form = await client.forms.create({
  title: 'Customer Feedback Survey',
  description: 'We value your feedback',
  questions: [
    {
      id: 'q1',
      type: 'text',
      title: 'What is your name?',
      required: true,
      properties: { placeholder: 'Enter your full name' }
    },
    {
      id: 'q2',
      type: 'single_choice',
      title: 'How satisfied are you?',
      required: true,
      properties: {
        options: [
          { id: 'opt1', label: 'Very Satisfied', value: 'very_satisfied' },
          { id: 'opt2', label: 'Satisfied', value: 'satisfied' },
          { id: 'opt3', label: 'Neutral', value: 'neutral' },
          { id: 'opt4', label: 'Dissatisfied', value: 'dissatisfied' }
        ]
      }
    }
  ]
});

// Publish the form
await client.forms.publish(form.id);

// Get responses
const responses = await client.responses.list(form.id, {
  page: 1,
  perPage: 50,
  status: 'completed'
});

// Export responses
const csvData = await client.responses.export(form.id, 'csv');
```

### **6. Python SDK Implementation**
```python
# packages/sdk-python/formbuilder/client.py
from typing import Optional, Dict, Any, List
import requests
from .resources import FormsResource, ResponsesResource, AnalyticsResource
from .exceptions import FormBuilderError, RateLimitError

class FormBuilderClient:
    def __init__(
        self, 
        api_key: str,
        base_url: str = "https://api.formbuilder.com/v1",
        timeout: int = 30,
        retries: int = 3
    ):
        self.api_key = api_key
        self.base_url = base_url.rstrip('/')
        self.timeout = timeout
        self.retries = retries
        
        self.session = requests.Session()
        self.session.headers.update({
            'Authorization': f'Bearer {api_key}',
            'Content-Type': 'application/json',
            'User-Agent': f'FormBuilder-Python-SDK/{__version__}'
        })
        
        # Initialize resources
        self.forms = FormsResource(self)
        self.responses = ResponsesResource(self)
        self.analytics = AnalyticsResource(self)

    def request(self, method: str, endpoint: str, **kwargs) -> Dict[Any, Any]:
        url = f"{self.base_url}{endpoint}"
        
        for attempt in range(self.retries + 1):
            try:
                response = self.session.request(method, url, timeout=self.timeout, **kwargs)
                
                if response.status_code == 429:
                    if attempt < self.retries:
                        retry_after = int(response.headers.get('Retry-After', 1))
                        time.sleep(retry_after)
                        continue
                    raise RateLimitError("Rate limit exceeded")
                
                response.raise_for_status()
                return response.json() if response.content else {}
                
            except requests.RequestException as e:
                if attempt == self.retries:
                    raise FormBuilderError(f"Request failed: {e}")
                time.sleep(2 ** attempt)

# Usage example
client = FormBuilderClient(api_key="fb_live_abc123...")

# Create form
form = client.forms.create({
    "title": "Customer Survey",
    "questions": [...]
})

# Get responses
responses = client.responses.list(form['id'])
```

---

## 🔐 API Authentication & Security

### **1. API Key Management System**
```typescript
// services/api-key-manager.ts
export class ApiKeyManager {
  static async generateApiKey(tenantId: string, userId: string, params: CreateApiKeyParams): Promise<ApiKey> {
    // Generate key with proper prefix
    const environment = params.environment || 'live';
    const prefix = environment === 'live' ? 'fb_live_' : 'fb_test_';
    const randomBytes = crypto.randomBytes(32).toString('hex');
    const apiKey = `${prefix}${randomBytes}`;
    
    // Hash for storage
    const keyHash = await bcrypt.hash(apiKey, 10);
    
    const apiKeyRecord = await prisma.tenantApiKey.create({
      data: {
        tenantId,
        name: params.name,
        keyHash,
        keyPrefix: prefix,
        permissions: params.permissions || [],
        scopes: params.scopes || ['forms:read'],
        rateLimitPerHour: params.rateLimitPerHour || 1000,
        rateLimitPerDay: params.rateLimitPerDay || 10000,
        expiresAt: params.expiresAt,
        createdBy: userId
      }
    });

    // Return key only once (never stored in plain text)
    return {
      ...apiKeyRecord,
      key: apiKey // Only returned on creation
    };
  }

  static async validateApiKey(key: string): Promise<{ tenantId: string; permissions: string[]; scopes: string[] } | null> {
    const prefix = key.split('_').slice(0, 2).join('_') + '_';
    
    const apiKeyRecord = await prisma.tenantApiKey.findFirst({
      where: {
        keyPrefix: prefix,
        isActive: true,
        OR: [
          { expiresAt: null },
          { expiresAt: { gt: new Date() } }
        ]
      },
      include: {
        tenant: { select: { id: true, status: true } }
      }
    });

    if (!apiKeyRecord || apiKeyRecord.tenant.status !== 'active') {
      return null;
    }

    const isValid = await bcrypt.compare(key, apiKeyRecord.keyHash);
    if (!isValid) {
      return null;
    }

    // Update last used timestamp
    await prisma.tenantApiKey.update({
      where: { id: apiKeyRecord.id },
      data: { lastUsedAt: new Date() }
    });

    return {
      tenantId: apiKeyRecord.tenantId,
      permissions: apiKeyRecord.permissions as string[],
      scopes: apiKeyRecord.scopes
    };
  }
}
```

### **2. API Rate Limiting**
```typescript
// middleware/rate-limiting.ts
import { Redis } from 'ioredis';
import { RateLimiterRedis } from 'rate-limiter-flexible';

const redis = new Redis(process.env.REDIS_URL);

export class TenantRateLimiter {
  private limiters: Map<string, RateLimiterRedis> = new Map();

  async checkRateLimit(tenantId: string, apiKeyId: string, limits: { perHour: number; perDay: number }): Promise<boolean> {
    const hourlyKey = `${tenantId}:${apiKeyId}:hour`;
    const dailyKey = `${tenantId}:${apiKeyId}:day`;

    // Get or create limiters
    const hourlyLimiter = this.getOrCreateLimiter(hourlyKey, limits.perHour, 3600);
    const dailyLimiter = this.getOrCreateLimiter(dailyKey, limits.perDay, 86400);

    try {
      await Promise.all([
        hourlyLimiter.consume(apiKeyId),
        dailyLimiter.consume(apiKeyId)
      ]);
      return true;
    } catch (error) {
      return false;
    }
  }

  private getOrCreateLimiter(key: string, points: number, duration: number): RateLimiterRedis {
    if (!this.limiters.has(key)) {
      this.limiters.set(key, new RateLimiterRedis({
        storeClient: redis,
        keyPrefix: 'rl:',
        points,
        duration
      }));
    }
    return this.limiters.get(key)!;
  }
}
```

---

## 🌐 Multi-Tenant Frontend Architecture

### **1. Dynamic Tenant Routing**
```typescript
// app/tenant/[slug]/layout.tsx
import { notFound } from 'next/navigation';
import { getTenantBySlug } from '@/lib/tenant-service';
import { TenantProvider } from '@/providers/tenant-provider';

interface TenantLayoutProps {
  children: React.ReactNode;
  params: { slug: string };
}

export default async function TenantLayout({ children, params }: TenantLayoutProps) {
  const tenant = await getTenantBySlug(params.slug);
  
  if (!tenant || tenant.status !== 'active') {
    notFound();
  }

  return (
    <TenantProvider tenant={tenant}>
      <div className="tenant-app" data-tenant={tenant.slug}>
        <TenantBrandingProvider branding={tenant.branding}>
          {children}
        </TenantBrandingProvider>
      </div>
    </TenantProvider>
  );
}
```

### **2. Tenant Context Provider**
```typescript
// providers/tenant-provider.tsx
'use client';

import { createContext, useContext, ReactNode } from 'react';

interface Tenant {
  id: string;
  slug: string;
  name: string;
  branding: TenantBranding;
  settings: TenantSettings;
  subscription_tier: string;
}

interface TenantContextValue {
  tenant: Tenant;
  isWhiteLabel: boolean;
  canAccess: (feature: string) => boolean;
}

const TenantContext = createContext<TenantContextValue | null>(null);

export function TenantProvider({ 
  tenant, 
  children 
}: { 
  tenant: Tenant; 
  children: ReactNode;
}) {
  const isWhiteLabel = tenant.subscription_tier === 'white_label';
  
  const canAccess = (feature: string): boolean => {
    const tierFeatures = {
      free: ['basic_forms', 'basic_analytics'],
      pro: ['basic_forms', 'basic_analytics', 'advanced_forms', 'integrations'],
      enterprise: ['*'],
      white_label: ['*', 'custom_branding', 'custom_domain']
    };
    
    const allowedFeatures = tierFeatures[tenant.subscription_tier as keyof typeof tierFeatures] || [];
    return allowedFeatures.includes('*') || allowedFeatures.includes(feature);
  };

  return (
    <TenantContext.Provider value={{ tenant, isWhiteLabel, canAccess }}>
      {children}
    </TenantContext.Provider>
  );
}

export function useTenant() {
  const context = useContext(TenantContext);
  if (!context) {
    throw new Error('useTenant must be used within a TenantProvider');
  }
  return context;
}
```

### **3. Dynamic Branding System**
```typescript
// providers/tenant-branding-provider.tsx
'use client';

import { createContext, useContext, useEffect, ReactNode } from 'react';

interface TenantBranding {
  logo?: string;
  primaryColor: string;
  backgroundColor: string;
  fontFamily: string;
  customCSS?: string;
}

const BrandingContext = createContext<TenantBranding | null>(null);

export function TenantBrandingProvider({ 
  branding, 
  children 
}: { 
  branding: TenantBranding; 
  children: ReactNode;
}) {
  useEffect(() => {
    // Apply dynamic CSS variables
    const root = document.documentElement;
    root.style.setProperty('--primary-color', branding.primaryColor);
    root.style.setProperty('--background-color', branding.backgroundColor);
    root.style.setProperty('--font-family', branding.fontFamily);
    
    // Apply custom CSS
    if (branding.customCSS) {
      const style = document.createElement('style');
      style.textContent = branding.customCSS;
      document.head.appendChild(style);
      
      return () => {
        document.head.removeChild(style);
      };
    }
  }, [branding]);

  return (
    <BrandingContext.Provider value={branding}>
      {children}
    </BrandingContext.Provider>
  );
}

export function useBranding() {
  return useContext(BrandingContext);
}
```

---

## 📊 Multi-Tenant Analytics & Reporting

### **1. Tenant-Isolated Analytics**
```typescript
// services/tenant-analytics.ts
export class TenantAnalyticsService {
  async getTenantMetrics(tenantId: string, dateRange: DateRange): Promise<TenantMetrics> {
    const [forms, responses, users] = await Promise.all([
      this.getFormMetrics(tenantId, dateRange),
      this.getResponseMetrics(tenantId, dateRange),
      this.getUserMetrics(tenantId, dateRange)
    ]);

    return {
      forms: {
        total: forms.total,
        created: forms.created,
        published: forms.published,
        totalViews: forms.totalViews
      },
      responses: {
        total: responses.total,
        completed: responses.completed,
        averageCompletionRate: responses.averageCompletionRate,
        averageCompletionTime: responses.averageCompletionTime
      },
      users: {
        total: users.total,
        active: users.active,
        new: users.new
      }
    };
  }

  private async getFormMetrics(tenantId: string, dateRange: DateRange) {
    return await prisma.form.aggregate({
      where: {
        tenantId,
        createdAt: {
          gte: dateRange.startDate,
          lte: dateRange.endDate
        }
      },
      _count: { _all: true },
      _sum: { responseCount: true }
    });
  }
}
```

This comprehensive SDK and multi-tenant architecture provides:

✅ **Complete multi-tenancy** with row-level security  
✅ **SDK support** for JavaScript, Python, PHP, and Go  
✅ **Tenant isolation** at database and application levels  
✅ **Dynamic branding** and white-label capabilities  
✅ **API key management** with proper scoping and rate limiting  
✅ **Tenant-aware analytics** and reporting  
✅ **Scalable architecture** for thousands of tenants