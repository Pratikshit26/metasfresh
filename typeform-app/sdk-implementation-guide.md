# FormBuilder SDK - Implementation Guide

## 🚀 Quick Start Guide

### **JavaScript/TypeScript SDK**

#### **Installation**
```bash
npm install @formbuilder/sdk
# or
yarn add @formbuilder/sdk
```

#### **Basic Usage**
```typescript
import { FormBuilderClient } from '@formbuilder/sdk';

const client = new FormBuilderClient({
  apiKey: 'fb_live_abc123...', // Get from dashboard
  baseURL: 'https://api.formbuilder.com/v1' // Optional
});

// Create a form
const form = await client.forms.create({
  title: 'Customer Feedback Survey',
  questions: [
    {
      type: 'text',
      title: 'What is your name?',
      required: true
    },
    {
      type: 'rating',
      title: 'How satisfied are you?',
      required: true,
      properties: { min: 1, max: 5 }
    }
  ]
});

// Publish the form
await client.forms.publish(form.id);

// Get responses
const responses = await client.responses.list(form.id);
```

### **Python SDK**

#### **Installation**
```bash
pip install formbuilder-sdk
```

#### **Basic Usage**
```python
from formbuilder import FormBuilderClient

client = FormBuilderClient(api_key='fb_live_abc123...')

# Create a form
form = client.forms.create({
    'title': 'Customer Feedback Survey',
    'questions': [
        {
            'type': 'text',
            'title': 'What is your name?',
            'required': True
        },
        {
            'type': 'rating', 
            'title': 'How satisfied are you?',
            'required': True,
            'properties': {'min': 1, 'max': 5}
        }
    ]
})

# Publish the form
client.forms.publish(form['id'])

# Get responses
responses = client.responses.list(form['id'])
```

---

## 📚 SDK Documentation

### **Authentication**

#### **API Key Types**
```typescript
// Live API Keys (Production)
const liveKey = 'fb_live_abc123def456...';

// Test API Keys (Development/Testing)
const testKey = 'fb_test_xyz789uvw012...';

const client = new FormBuilderClient({
  apiKey: process.env.NODE_ENV === 'production' ? liveKey : testKey
});
```

#### **Scoped API Keys**
```typescript
// Create API key with specific scopes
const apiKey = await client.apiKeys.create({
  name: 'Integration API Key',
  scopes: [
    'forms:read',
    'forms:write', 
    'responses:read',
    'analytics:read'
  ],
  rateLimitPerHour: 5000,
  expiresAt: new Date('2025-12-31')
});
```

### **Forms API**

#### **Create Form**
```typescript
const form = await client.forms.create({
  title: 'Event Registration',
  description: 'Register for our upcoming event',
  questions: [
    {
      type: 'text',
      title: 'Full Name',
      required: true,
      properties: {
        placeholder: 'Enter your full name'
      }
    },
    {
      type: 'email',
      title: 'Email Address', 
      required: true,
      properties: {
        validation: 'email'
      }
    },
    {
      type: 'single_choice',
      title: 'Dietary Restrictions',
      required: false,
      properties: {
        options: [
          { label: 'None', value: 'none' },
          { label: 'Vegetarian', value: 'vegetarian' },
          { label: 'Vegan', value: 'vegan' },
          { label: 'Gluten-free', value: 'gluten_free' }
        ]
      }
    }
  ],
  settings: {
    theme: {
      primaryColor: '#007bff',
      backgroundColor: '#ffffff'
    },
    behavior: {
      showProgress: true,
      oneQuestionPerPage: true
    },
    notifications: {
      emailOnResponse: true,
      emailRecipients: ['admin@company.com']
    }
  }
});
```

#### **List Forms**
```typescript
// Get all forms with pagination
const forms = await client.forms.list({
  page: 1,
  perPage: 20,
  status: 'published',
  sortBy: 'created_at',
  sortOrder: 'desc'
});

// Search forms
const searchResults = await client.forms.list({
  search: 'customer survey',
  status: 'published'
});
```

#### **Update Form**
```typescript
const updatedForm = await client.forms.update(form.id, {
  title: 'Updated Event Registration',
  questions: [
    ...existingQuestions,
    {
      type: 'textarea',
      title: 'Additional Comments',
      required: false
    }
  ]
});
```

#### **Conditional Logic**
```typescript
const formWithLogic = await client.forms.create({
  title: 'Smart Survey',
  questions: [
    {
      id: 'q1',
      type: 'single_choice',
      title: 'Are you a new customer?',
      properties: {
        options: [
          { id: 'yes', label: 'Yes', value: 'yes' },
          { id: 'no', label: 'No', value: 'no' }
        ]
      }
    },
    {
      id: 'q2',
      type: 'text',
      title: 'How did you hear about us?',
      logic: {
        conditions: [
          {
            questionId: 'q1',
            operator: 'equals',
            value: 'yes'
          }
        ],
        action: 'show'
      }
    },
    {
      id: 'q3', 
      type: 'rating',
      title: 'How would you rate our service?',
      logic: {
        conditions: [
          {
            questionId: 'q1',
            operator: 'equals',
            value: 'no'
          }
        ],
        action: 'show'
      }
    }
  ]
});
```

### **Responses API**

#### **Submit Response**
```typescript
// Submit form response programmatically
const response = await client.responses.create(form.id, {
  answers: {
    'q1': 'John Doe',
    'q2': 'john@example.com',
    'q3': 'vegetarian'
  },
  metadata: {
    source: 'api',
    userAgent: 'Custom Integration',
    ipAddress: '192.168.1.1'
  }
});
```

#### **Get Responses**
```typescript
// Get all responses
const allResponses = await client.responses.list(form.id);

// Get completed responses only
const completedResponses = await client.responses.list(form.id, {
  status: 'completed',
  page: 1,
  perPage: 50
});

// Get responses with date filter
const recentResponses = await client.responses.list(form.id, {
  createdAfter: new Date('2025-01-01'),
  createdBefore: new Date('2025-12-31')
});
```

#### **Export Responses**
```typescript
// Export as CSV
const csvBlob = await client.responses.export(form.id, 'csv');
const csvUrl = URL.createObjectURL(csvBlob);

// Export as Excel
const excelBlob = await client.responses.export(form.id, 'xlsx', {
  includeMetadata: true,
  dateFormat: 'YYYY-MM-DD'
});

// Export as JSON
const jsonData = await client.responses.export(form.id, 'json');
```

### **Analytics API**

#### **Form Analytics**
```typescript
// Get form performance metrics
const analytics = await client.analytics.getFormStats(form.id, {
  dateRange: {
    start: new Date('2025-01-01'),
    end: new Date('2025-01-31')
  }
});

// Response: 
// {
//   views: 1250,
//   starts: 890, 
//   completions: 720,
//   completionRate: 80.9,
//   averageTime: 180, // seconds
//   dropOffPoints: [
//     { questionId: 'q3', dropRate: 15.2 },
//     { questionId: 'q5', dropRate: 8.7 }
//   ]
// }
```

#### **Response Analytics** 
```typescript
// Get question-level analytics
const questionStats = await client.analytics.getQuestionStats(form.id, 'q2');

// For multiple choice questions:
// {
//   type: 'single_choice',
//   totalResponses: 720,
//   options: [
//     { value: 'option1', count: 340, percentage: 47.2 },
//     { value: 'option2', count: 250, percentage: 34.7 },
//     { value: 'option3', count: 130, percentage: 18.1 }
//   ]
// }

// For rating questions:
// {
//   type: 'rating',
//   totalResponses: 720, 
//   average: 4.2,
//   distribution: [
//     { rating: 1, count: 12, percentage: 1.7 },
//     { rating: 2, count: 28, percentage: 3.9 },
//     { rating: 3, count: 145, percentage: 20.1 },
//     { rating: 4, count: 298, percentage: 41.4 },
//     { rating: 5, count: 237, percentage: 32.9 }
//   ]
// }
```

### **Webhooks API**

#### **Create Webhook**
```typescript
const webhook = await client.webhooks.create({
  url: 'https://myapp.com/webhook/formbuilder',
  events: ['response.created', 'response.completed'],
  formIds: [form.id], // Optional: specific forms only
  secret: 'my_webhook_secret', // For signature verification
  active: true
});
```

#### **Webhook Payload Example**
```typescript
// POST to your webhook URL
{
  "event": "response.completed",
  "timestamp": "2025-11-17T10:30:00Z",
  "data": {
    "response": {
      "id": "resp_abc123",
      "formId": "form_def456", 
      "answers": {
        "q1": "John Doe",
        "q2": "john@example.com"
      },
      "completedAt": "2025-11-17T10:30:00Z",
      "completionTime": 180
    },
    "form": {
      "id": "form_def456",
      "title": "Customer Survey"
    }
  }
}
```

#### **Webhook Verification**
```typescript
// Verify webhook signatures
import crypto from 'crypto';

function verifyWebhook(payload: string, signature: string, secret: string): boolean {
  const expectedSignature = crypto
    .createHmac('sha256', secret)
    .update(payload)
    .digest('hex');
    
  return crypto.timingSafeEqual(
    Buffer.from(signature), 
    Buffer.from(`sha256=${expectedSignature}`)
  );
}

// Express.js webhook handler
app.post('/webhook/formbuilder', express.raw({ type: 'application/json' }), (req, res) => {
  const signature = req.headers['x-formbuilder-signature'] as string;
  const isValid = verifyWebhook(req.body.toString(), signature, 'my_webhook_secret');
  
  if (!isValid) {
    return res.status(401).send('Unauthorized');
  }
  
  const event = JSON.parse(req.body.toString());
  
  switch (event.event) {
    case 'response.completed':
      // Handle completed response
      handleCompletedResponse(event.data.response);
      break;
    case 'response.created':
      // Handle new response
      handleNewResponse(event.data.response);
      break;
  }
  
  res.status(200).send('OK');
});
```

---

## 🔧 Advanced SDK Features

### **Batch Operations**
```typescript
// Batch create multiple forms
const forms = await client.forms.createBatch([
  { title: 'Survey 1', questions: [...] },
  { title: 'Survey 2', questions: [...] },
  { title: 'Survey 3', questions: [...] }
]);

// Batch update responses
await client.responses.updateBatch([
  { id: 'resp1', updates: { tags: ['important'] } },
  { id: 'resp2', updates: { tags: ['follow-up'] } }
]);
```

### **Streaming Responses**
```typescript
// Stream real-time responses
const stream = client.responses.stream(form.id);

stream.on('response', (response) => {
  console.log('New response:', response);
});

stream.on('error', (error) => {
  console.error('Stream error:', error);
});

// Close stream
stream.close();
```

### **File Uploads**
```typescript
// Handle file upload questions
const formWithUpload = await client.forms.create({
  title: 'Application Form',
  questions: [
    {
      type: 'file_upload',
      title: 'Upload your resume',
      required: true,
      properties: {
        allowedTypes: ['.pdf', '.doc', '.docx'],
        maxFileSize: 10485760, // 10MB in bytes
        maxFiles: 1
      }
    }
  ]
});

// Access uploaded files
const response = await client.responses.get(form.id, responseId);
const fileUrl = response.answers.resume_upload.url;
const fileMetadata = response.answers.resume_upload.metadata;
```

### **Custom Themes**
```typescript
// Create form with custom theme
const themedForm = await client.forms.create({
  title: 'Branded Survey',
  questions: [...],
  settings: {
    theme: {
      primaryColor: '#FF6B35',
      backgroundColor: '#F7F7F7',
      fontFamily: 'Inter, sans-serif',
      borderRadius: '8px',
      customCSS: `
        .form-container {
          box-shadow: 0 4px 20px rgba(0,0,0,0.1);
        }
        .question-title {
          font-weight: 600;
          color: #2D3748;
        }
      `
    }
  }
});
```

---

## 🐍 Python SDK Reference

### **Installation & Setup**
```bash
pip install formbuilder-sdk

# With async support
pip install formbuilder-sdk[async]
```

### **Basic Client**
```python
from formbuilder import FormBuilderClient

# Synchronous client
client = FormBuilderClient(
    api_key='fb_live_abc123...',
    base_url='https://api.formbuilder.com/v1'
)

# Async client
from formbuilder import AsyncFormBuilderClient

async_client = AsyncFormBuilderClient(api_key='fb_live_abc123...')
```

### **Forms Management**
```python
# Create form
form = client.forms.create({
    'title': 'Python Survey',
    'questions': [
        {
            'type': 'text',
            'title': 'Name',
            'required': True
        }
    ]
})

# List forms with filtering
forms = client.forms.list(
    status='published',
    page=1,
    per_page=20,
    sort_by='created_at',
    sort_order='desc'
)

# Get single form
form = client.forms.get('form_abc123')

# Update form
updated_form = client.forms.update('form_abc123', {
    'title': 'Updated Survey Title'
})

# Delete form
client.forms.delete('form_abc123')
```

### **Response Handling**
```python
# Get responses
responses = client.responses.list(
    form_id='form_abc123',
    status='completed',
    created_after=datetime(2025, 1, 1),
    created_before=datetime(2025, 12, 31)
)

# Export responses
with open('responses.csv', 'wb') as f:
    csv_data = client.responses.export('form_abc123', format='csv')
    f.write(csv_data)

# Submit response programmatically
response = client.responses.create('form_abc123', {
    'answers': {
        'q1': 'John Doe',
        'q2': 'john@example.com'
    }
})
```

### **Async Operations**
```python
import asyncio
from formbuilder import AsyncFormBuilderClient

async def main():
    client = AsyncFormBuilderClient(api_key='fb_live_abc123...')
    
    # Concurrent operations
    forms_task = client.forms.list()
    analytics_task = client.analytics.get_account_stats()
    
    forms, analytics = await asyncio.gather(forms_task, analytics_task)
    
    print(f"Total forms: {len(forms.data)}")
    print(f"Total responses: {analytics.total_responses}")

# Run async code
asyncio.run(main())
```

---

## 🔗 Integration Examples

### **React Integration**
```typescript
// hooks/useFormBuilder.ts
import { FormBuilderClient } from '@formbuilder/sdk';
import { useState, useEffect } from 'react';

export function useFormBuilder(apiKey: string) {
  const [client] = useState(() => new FormBuilderClient({ apiKey }));
  const [forms, setForms] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadForms();
  }, []);

  const loadForms = async () => {
    try {
      const response = await client.forms.list();
      setForms(response.data);
    } catch (error) {
      console.error('Failed to load forms:', error);
    } finally {
      setLoading(false);
    }
  };

  const createForm = async (formData) => {
    const form = await client.forms.create(formData);
    setForms(prev => [form, ...prev]);
    return form;
  };

  return { client, forms, loading, createForm, refetch: loadForms };
}

// components/FormList.tsx
import { useFormBuilder } from '../hooks/useFormBuilder';

export function FormList() {
  const { forms, loading, createForm } = useFormBuilder(process.env.REACT_APP_FORMBUILDER_API_KEY);

  if (loading) return <div>Loading forms...</div>;

  return (
    <div>
      {forms.map(form => (
        <div key={form.id}>
          <h3>{form.title}</h3>
          <p>{form.responseCount} responses</p>
        </div>
      ))}
    </div>
  );
}
```

### **Node.js Express Integration**
```javascript
const express = require('express');
const { FormBuilderClient } = require('@formbuilder/sdk');

const app = express();
const client = new FormBuilderClient({ 
  apiKey: process.env.FORMBUILDER_API_KEY 
});

// Create form endpoint
app.post('/api/forms', async (req, res) => {
  try {
    const form = await client.forms.create(req.body);
    res.json(form);
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
});

// Get form responses
app.get('/api/forms/:id/responses', async (req, res) => {
  try {
    const responses = await client.responses.list(req.params.id);
    res.json(responses);
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
});

// Webhook handler
app.post('/webhook/formbuilder', express.raw({ type: 'application/json' }), (req, res) => {
  // Verify webhook signature
  const signature = req.headers['x-formbuilder-signature'];
  // ... verification logic
  
  const event = JSON.parse(req.body.toString());
  
  // Process webhook event
  switch (event.event) {
    case 'response.completed':
      // Send email, update CRM, etc.
      break;
  }
  
  res.status(200).send('OK');
});

app.listen(3000);
```

### **Django Integration**
```python
# views.py
from django.http import JsonResponse
from django.views import View
from formbuilder import FormBuilderClient
import json

class FormBuilderAPI(View):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.client = FormBuilderClient(api_key=settings.FORMBUILDER_API_KEY)
    
    def get(self, request):
        """Get all forms"""
        forms = self.client.forms.list()
        return JsonResponse(forms)
    
    def post(self, request):
        """Create new form"""
        form_data = json.loads(request.body)
        form = self.client.forms.create(form_data)
        return JsonResponse(form)

# Webhook handler
from django.views.decorators.csrf import csrf_exempt
from django.views.decorators.http import require_http_methods

@csrf_exempt
@require_http_methods(["POST"])
def formbuilder_webhook(request):
    # Verify signature
    signature = request.headers.get('X-Formbuilder-Signature')
    # ... verification logic
    
    event = json.loads(request.body)
    
    if event['event'] == 'response.completed':
        # Process completed response
        handle_completed_response(event['data'])
    
    return JsonResponse({'status': 'ok'})

def handle_completed_response(data):
    # Send email notification
    # Update database
    # Trigger other workflows
    pass
```

This comprehensive SDK guide provides everything developers need to integrate FormBuilder into their applications across multiple programming languages and frameworks.