# FormBuilder App - Data Model & API Specification

## 🗄️ Database Schema

### **Users Table**
```sql
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    avatar_url VARCHAR(500),
    subscription_tier ENUM('free', 'pro', 'enterprise') DEFAULT 'free',
    subscription_expires_at TIMESTAMP,
    email_verified BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_login_at TIMESTAMP,
    settings JSONB DEFAULT '{}',
    
    -- Indexes
    INDEX idx_users_email (email),
    INDEX idx_users_subscription (subscription_tier, subscription_expires_at)
);
```

### **Workspaces Table** (Team Collaboration)
```sql
CREATE TABLE workspaces (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(100) UNIQUE NOT NULL,
    owner_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    description TEXT,
    settings JSONB DEFAULT '{}',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_workspaces_owner (owner_id),
    INDEX idx_workspaces_slug (slug)
);
```

### **Workspace Members Table**
```sql
CREATE TABLE workspace_members (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    workspace_id UUID NOT NULL REFERENCES workspaces(id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role ENUM('owner', 'admin', 'editor', 'viewer') NOT NULL,
    invited_by UUID REFERENCES users(id),
    invited_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    joined_at TIMESTAMP,
    
    UNIQUE KEY unique_workspace_user (workspace_id, user_id),
    INDEX idx_workspace_members_user (user_id)
);
```

### **Forms Table**
```sql
CREATE TABLE forms (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    workspace_id UUID NOT NULL REFERENCES workspaces(id) ON DELETE CASCADE,
    created_by UUID NOT NULL REFERENCES users(id),
    
    -- Basic Info
    title VARCHAR(255) NOT NULL,
    description TEXT,
    slug VARCHAR(100),
    
    -- Form Configuration
    questions JSONB NOT NULL DEFAULT '[]',
    settings JSONB DEFAULT '{}', -- includes theme, logic, notifications
    
    -- Status & Access
    status ENUM('draft', 'published', 'paused', 'archived') DEFAULT 'draft',
    is_public BOOLEAN DEFAULT true,
    password_protected BOOLEAN DEFAULT false,
    password_hash VARCHAR(255),
    
    -- Limits & Restrictions
    max_responses INTEGER,
    closes_at TIMESTAMP,
    requires_login BOOLEAN DEFAULT false,
    allowed_domains TEXT[], -- for email restrictions
    
    -- Analytics
    view_count INTEGER DEFAULT 0,
    response_count INTEGER DEFAULT 0,
    completion_rate DECIMAL(5,2) DEFAULT 0,
    avg_completion_time INTEGER, -- in seconds
    
    -- Timestamps
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    published_at TIMESTAMP,
    
    -- Indexes
    INDEX idx_forms_workspace (workspace_id),
    INDEX idx_forms_creator (created_by),
    INDEX idx_forms_status (status),
    INDEX idx_forms_slug (slug),
    INDEX idx_forms_published (published_at, status)
);
```

### **Form Responses Table**
```sql
CREATE TABLE form_responses (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    form_id UUID NOT NULL REFERENCES forms(id) ON DELETE CASCADE,
    
    -- Response Data
    answers JSONB NOT NULL DEFAULT '{}',
    
    -- Respondent Info
    respondent_email VARCHAR(255),
    respondent_id VARCHAR(255), -- for anonymous tracking
    user_id UUID REFERENCES users(id), -- if logged in user
    
    -- Metadata
    ip_address INET,
    user_agent TEXT,
    referrer VARCHAR(500),
    utm_source VARCHAR(100),
    utm_medium VARCHAR(100),
    utm_campaign VARCHAR(100),
    
    -- Completion Info
    status ENUM('in_progress', 'completed', 'abandoned') DEFAULT 'in_progress',
    started_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP,
    completion_time INTEGER, -- in seconds
    
    -- Geographic Data
    country_code CHAR(2),
    city VARCHAR(100),
    timezone VARCHAR(50),
    
    INDEX idx_responses_form (form_id),
    INDEX idx_responses_status (status),
    INDEX idx_responses_completed (completed_at),
    INDEX idx_responses_respondent (respondent_id)
);
```

### **Form Templates Table**
```sql
CREATE TABLE form_templates (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(100) NOT NULL,
    thumbnail_url VARCHAR(500),
    
    -- Template Data
    questions JSONB NOT NULL,
    settings JSONB DEFAULT '{}',
    
    -- Metadata
    is_public BOOLEAN DEFAULT true,
    created_by UUID REFERENCES users(id),
    usage_count INTEGER DEFAULT 0,
    rating DECIMAL(3,2) DEFAULT 0,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_templates_category (category),
    INDEX idx_templates_public (is_public),
    INDEX idx_templates_usage (usage_count DESC)
);
```

### **Form Analytics Table** (Aggregated Stats)
```sql
CREATE TABLE form_analytics (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    form_id UUID NOT NULL REFERENCES forms(id) ON DELETE CASCADE,
    date DATE NOT NULL,
    
    -- Daily Metrics
    views INTEGER DEFAULT 0,
    starts INTEGER DEFAULT 0,
    completions INTEGER DEFAULT 0,
    completion_rate DECIMAL(5,2) DEFAULT 0,
    avg_completion_time INTEGER,
    
    -- Traffic Sources
    traffic_sources JSONB DEFAULT '{}',
    
    -- Geographic Data
    countries JSONB DEFAULT '{}',
    
    -- Device Data
    devices JSONB DEFAULT '{}',
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    UNIQUE KEY unique_form_date (form_id, date),
    INDEX idx_analytics_form_date (form_id, date)
);
```

---

## 📋 JSON Schema Definitions

### **Question Schema**
```json
{
  "question": {
    "id": "string (UUID)",
    "type": "enum[text, textarea, email, number, phone, url, date, time, multiple_choice, single_choice, rating, scale, dropdown, file_upload, payment]",
    "title": "string",
    "description": "string (optional)",
    "required": "boolean",
    "position": "integer",
    
    // Type-specific properties
    "properties": {
      // For text/textarea
      "placeholder": "string",
      "max_length": "integer",
      "min_length": "integer",
      "validation_regex": "string",
      
      // For choice questions
      "options": [
        {
          "id": "string",
          "label": "string",
          "value": "string"
        }
      ],
      "allow_multiple": "boolean",
      "allow_other": "boolean",
      
      // For rating/scale
      "min_value": "integer",
      "max_value": "integer",
      "step": "number",
      "labels": {
        "min": "string",
        "max": "string"
      },
      
      // For file upload
      "allowed_types": ["string"],
      "max_file_size": "integer (bytes)",
      "max_files": "integer",
      
      // For payment
      "amount": "number",
      "currency": "string",
      "payment_methods": ["string"]
    },
    
    // Conditional Logic
    "logic": {
      "conditions": [
        {
          "question_id": "string",
          "operator": "enum[equals, not_equals, contains, greater_than, less_than, is_empty, is_not_empty]",
          "value": "any"
        }
      ],
      "action": "enum[show, hide, jump_to, end_form]",
      "target": "string (question_id or 'end')"
    }
  }
}
```

### **Form Settings Schema**
```json
{
  "settings": {
    "theme": {
      "primary_color": "#4285f4",
      "background_color": "#ffffff",
      "font_family": "Inter",
      "logo_url": "string",
      "custom_css": "string"
    },
    
    "behavior": {
      "show_progress": true,
      "show_question_numbers": true,
      "randomize_questions": false,
      "one_question_per_page": true,
      "allow_going_back": true,
      "auto_save_progress": true
    },
    
    "notifications": {
      "email_on_response": true,
      "email_recipients": ["string"],
      "slack_webhook": "string",
      "webhook_url": "string"
    },
    
    "seo": {
      "meta_title": "string",
      "meta_description": "string",
      "og_image": "string"
    },
    
    "integrations": {
      "google_analytics": "string",
      "facebook_pixel": "string",
      "zapier_webhook": "string"
    }
  }
}
```

---

## 🔌 API Endpoints

### **Authentication Endpoints**
```yaml
POST /api/auth/register
POST /api/auth/login
POST /api/auth/logout
POST /api/auth/refresh
POST /api/auth/forgot-password
POST /api/auth/reset-password
GET  /api/auth/verify-email/:token
```

### **User Management**
```yaml
GET    /api/users/me
PUT    /api/users/me
DELETE /api/users/me
PUT    /api/users/me/password
POST   /api/users/me/avatar
```

### **Workspace Management**
```yaml
GET    /api/workspaces
POST   /api/workspaces
GET    /api/workspaces/:id
PUT    /api/workspaces/:id
DELETE /api/workspaces/:id

GET    /api/workspaces/:id/members
POST   /api/workspaces/:id/members/invite
PUT    /api/workspaces/:id/members/:userId
DELETE /api/workspaces/:id/members/:userId
```

### **Form Management**
```yaml
GET    /api/forms                      # List user's forms
POST   /api/forms                      # Create new form
GET    /api/forms/:id                  # Get form details
PUT    /api/forms/:id                  # Update form
DELETE /api/forms/:id                  # Delete form
POST   /api/forms/:id/duplicate        # Duplicate form
PUT    /api/forms/:id/publish          # Publish form
PUT    /api/forms/:id/unpublish        # Unpublish form

GET    /api/forms/:id/responses        # Get form responses
GET    /api/forms/:id/responses/:responseId # Get single response
DELETE /api/forms/:id/responses/:responseId # Delete response
POST   /api/forms/:id/responses/export # Export responses

GET    /api/forms/:id/analytics        # Get form analytics
GET    /api/forms/:id/analytics/summary # Get analytics summary
```

### **Public Form Endpoints**
```yaml
GET    /api/public/forms/:slug         # Get public form
POST   /api/public/forms/:slug/responses # Submit response
GET    /api/public/forms/:slug/responses/:responseId # Get response (for editing)
PUT    /api/public/forms/:slug/responses/:responseId # Update response
```

### **Templates**
```yaml
GET    /api/templates                  # List public templates
GET    /api/templates/categories       # Get template categories
GET    /api/templates/:id              # Get template details
POST   /api/templates/:id/use          # Create form from template
```

### **File Upload**
```yaml
POST   /api/upload/image               # Upload image
POST   /api/upload/file                # Upload file for form response
DELETE /api/upload/:fileId             # Delete uploaded file
```

---

## 📊 API Response Examples

### **Form List Response**
```json
{
  "forms": [
    {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "title": "Customer Satisfaction Survey",
      "description": "Help us improve our service",
      "slug": "customer-satisfaction-survey",
      "status": "published",
      "response_count": 45,
      "completion_rate": 85.5,
      "created_at": "2025-11-01T10:00:00Z",
      "updated_at": "2025-11-10T15:30:00Z",
      "published_at": "2025-11-02T09:00:00Z"
    }
  ],
  "pagination": {
    "page": 1,
    "per_page": 20,
    "total": 1,
    "total_pages": 1
  }
}
```

### **Form Details Response**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "title": "Customer Satisfaction Survey",
  "description": "Help us improve our service",
  "slug": "customer-satisfaction-survey",
  "status": "published",
  
  "questions": [
    {
      "id": "q1",
      "type": "text",
      "title": "What's your name?",
      "required": true,
      "position": 1,
      "properties": {
        "placeholder": "Enter your full name"
      }
    },
    {
      "id": "q2",
      "type": "single_choice",
      "title": "How satisfied are you with our service?",
      "required": true,
      "position": 2,
      "properties": {
        "options": [
          {"id": "opt1", "label": "Very Satisfied", "value": "very_satisfied"},
          {"id": "opt2", "label": "Satisfied", "value": "satisfied"},
          {"id": "opt3", "label": "Neutral", "value": "neutral"},
          {"id": "opt4", "label": "Dissatisfied", "value": "dissatisfied"}
        ]
      }
    }
  ],
  
  "settings": {
    "theme": {
      "primary_color": "#4285f4",
      "background_color": "#ffffff"
    },
    "behavior": {
      "show_progress": true,
      "one_question_per_page": true
    }
  },
  
  "analytics": {
    "views": 120,
    "responses": 45,
    "completion_rate": 85.5,
    "avg_completion_time": 180
  }
}
```

### **Response Submission**
```json
{
  "response_id": "750e8400-e29b-41d4-a716-446655440000",
  "answers": {
    "q1": "John Smith",
    "q2": "very_satisfied"
  },
  "metadata": {
    "ip_address": "192.168.1.1",
    "user_agent": "Mozilla/5.0...",
    "referrer": "https://google.com",
    "started_at": "2025-11-13T10:00:00Z",
    "completed_at": "2025-11-13T10:03:00Z",
    "completion_time": 180
  }
}
```

---

## 🔒 Security Considerations

### **Authentication & Authorization**
- JWT tokens with refresh mechanism
- Role-based access control (RBAC)
- API rate limiting
- CORS configuration

### **Data Protection**
- Encrypt sensitive data at rest
- HTTPS only for all endpoints
- Input validation and sanitization
- SQL injection prevention
- XSS protection

### **Privacy Compliance**
- GDPR compliance features
- Data retention policies
- Right to be forgotten
- Cookie consent management
- Anonymous response options

---

## 🚀 Caching Strategy

### **Redis Cache Layers**
```yaml
User Sessions: 24 hours TTL
Form Definitions: 1 hour TTL
Public Forms: 5 minutes TTL
Analytics Data: 30 minutes TTL
Template Listings: 6 hours TTL
```

### **CDN Strategy**
- Static assets (images, CSS, JS)
- Form assets and uploads
- Geographic distribution
- Cache invalidation on updates

---

This comprehensive data model provides the foundation for building a scalable, feature-rich FormBuilder application that can handle all the wireframe requirements and user stories we've defined.