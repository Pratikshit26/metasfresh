# FormBuilder App - Frontend Component Architecture

## 🏗️ Component Hierarchy & Data Flow

### **Application Structure**
```
src/
├── components/           # Reusable UI components
├── pages/               # Next.js pages (routing)
├── hooks/               # Custom React hooks
├── lib/                 # Utilities and configurations
├── store/               # State management (Zustand/Redux)
├── types/               # TypeScript type definitions
└── styles/              # Global styles and themes
```

---

## 📱 Page Components & Data Mapping

### **1. Landing Page (`/`)**

#### **Component Structure:**
```typescript
// pages/index.tsx
export default function LandingPage() {
  return (
    <Layout>
      <HeroSection />
      <FeaturesSection />
      <TestimonialsSection />
      <PricingSection />
      <CTASection />
    </Layout>
  );
}
```

#### **Data Requirements:**
```typescript
interface LandingPageData {
  hero: {
    title: string;
    subtitle: string;
    ctaText: string;
    backgroundImage?: string;
  };
  features: Feature[];
  testimonials: Testimonial[];
  pricing: PricingPlan[];
}

interface Feature {
  id: string;
  icon: string;
  title: string;
  description: string;
}
```

#### **API Calls:**
- `GET /api/public/landing-data` - Static content
- No authentication required

---

### **2. Dashboard (`/dashboard`)**

#### **Component Structure:**
```typescript
// pages/dashboard.tsx
export default function Dashboard() {
  const { forms, loading } = useForms();
  const { user } = useAuth();

  return (
    <AuthenticatedLayout>
      <DashboardHeader user={user} />
      <FormGrid forms={forms} loading={loading} />
      <CreateFormModal />
    </AuthenticatedLayout>
  );
}
```

#### **Child Components:**
```typescript
// components/dashboard/FormGrid.tsx
interface FormGridProps {
  forms: Form[];
  loading: boolean;
}

export function FormGrid({ forms, loading }: FormGridProps) {
  return (
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      {loading ? (
        <FormCardSkeleton count={6} />
      ) : (
        forms.map(form => (
          <FormCard 
            key={form.id} 
            form={form}
            onEdit={() => router.push(`/forms/${form.id}/edit`)}
            onShare={() => openShareModal(form)}
            onAnalytics={() => router.push(`/forms/${form.id}/analytics`)}
          />
        ))
      )}
    </div>
  );
}
```

#### **Data Flow:**
```typescript
// hooks/useForms.ts
export function useForms() {
  const [forms, setForms] = useState<Form[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchForms();
  }, []);

  const fetchForms = async () => {
    try {
      const response = await api.get('/api/forms');
      setForms(response.data.forms);
    } catch (error) {
      toast.error('Failed to load forms');
    } finally {
      setLoading(false);
    }
  };

  return { forms, loading, refetch: fetchForms };
}
```

#### **API Calls:**
- `GET /api/forms` - User's forms list
- `POST /api/forms` - Create new form
- `DELETE /api/forms/:id` - Delete form

---

### **3. Form Builder (`/forms/:id/edit`)**

#### **Component Structure:**
```typescript
// pages/forms/[id]/edit.tsx
export default function FormBuilder() {
  const router = useRouter();
  const { id } = router.query;
  const { form, updateForm, saveForm } = useFormBuilder(id as string);

  return (
    <div className="h-screen flex">
      <QuestionTypePalette />
      <FormCanvas 
        form={form}
        onUpdateForm={updateForm}
      />
      <PropertiesPanel />
      <PreviewPanel />
    </div>
  );
}
```

#### **Core Builder Components:**

##### **Question Type Palette**
```typescript
// components/builder/QuestionTypePalette.tsx
const questionTypes = [
  { type: 'text', icon: '📝', label: 'Text Input' },
  { type: 'multiple_choice', icon: '☑️', label: 'Multiple Choice' },
  { type: 'rating', icon: '⭐', label: 'Rating' },
  { type: 'email', icon: '📧', label: 'Email' },
  // ... more types
];

export function QuestionTypePalette() {
  const { addQuestion } = useFormBuilder();

  return (
    <div className="w-64 bg-gray-50 border-r p-4">
      <h3 className="font-semibold mb-4">Question Types</h3>
      {questionTypes.map(type => (
        <QuestionTypeButton
          key={type.type}
          type={type}
          onClick={() => addQuestion(type.type)}
        />
      ))}
    </div>
  );
}
```

##### **Form Canvas**
```typescript
// components/builder/FormCanvas.tsx
export function FormCanvas({ form, onUpdateForm }: FormCanvasProps) {
  const { questions } = form;

  return (
    <div className="flex-1 p-6 overflow-y-auto">
      <DragDropContext onDragEnd={handleDragEnd}>
        <Droppable droppableId="questions">
          {(provided) => (
            <div {...provided.droppableProps} ref={provided.innerRef}>
              {questions.map((question, index) => (
                <Draggable 
                  key={question.id} 
                  draggableId={question.id} 
                  index={index}
                >
                  {(provided) => (
                    <div
                      ref={provided.innerRef}
                      {...provided.draggableProps}
                      {...provided.dragHandleProps}
                    >
                      <QuestionBlock
                        question={question}
                        onUpdate={(updates) => updateQuestion(question.id, updates)}
                        onDelete={() => deleteQuestion(question.id)}
                      />
                    </div>
                  )}
                </Draggable>
              ))}
              {provided.placeholder}
            </div>
          )}
        </Droppable>
      </DragDropContext>
    </div>
  );
}
```

#### **State Management:**
```typescript
// store/formBuilderStore.ts
interface FormBuilderState {
  form: Form | null;
  selectedQuestionId: string | null;
  previewMode: boolean;
  isSaving: boolean;
}

export const useFormBuilderStore = create<FormBuilderState>((set, get) => ({
  form: null,
  selectedQuestionId: null,
  previewMode: false,
  isSaving: false,

  actions: {
    setForm: (form: Form) => set({ form }),
    
    addQuestion: (type: QuestionType) => {
      const { form } = get();
      if (!form) return;
      
      const newQuestion = createQuestion(type);
      const updatedForm = {
        ...form,
        questions: [...form.questions, newQuestion]
      };
      set({ form: updatedForm });
    },

    updateQuestion: (questionId: string, updates: Partial<Question>) => {
      const { form } = get();
      if (!form) return;
      
      const updatedForm = {
        ...form,
        questions: form.questions.map(q => 
          q.id === questionId ? { ...q, ...updates } : q
        )
      };
      set({ form: updatedForm });
    },

    saveForm: async () => {
      const { form } = get();
      if (!form) return;
      
      set({ isSaving: true });
      try {
        await api.put(`/api/forms/${form.id}`, form);
        toast.success('Form saved successfully');
      } catch (error) {
        toast.error('Failed to save form');
      } finally {
        set({ isSaving: false });
      }
    }
  }
}));
```

#### **API Calls:**
- `GET /api/forms/:id` - Load form for editing
- `PUT /api/forms/:id` - Save form changes
- `POST /api/forms/:id/publish` - Publish form

---

### **4. Form Response Page (`/f/:slug`)**

#### **Component Structure:**
```typescript
// pages/f/[slug].tsx
export default function FormResponse() {
  const router = useRouter();
  const { slug } = router.query;
  const { 
    form, 
    currentQuestion, 
    progress, 
    responses, 
    submitResponse 
  } = useFormResponse(slug as string);

  if (!form) return <FormNotFound />;

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-400 to-purple-600">
      <FormContainer>
        <FormHeader 
          title={form.title}
          description={form.description}
          progress={progress}
        />
        
        <QuestionRenderer
          question={currentQuestion}
          value={responses[currentQuestion.id]}
          onChange={(value) => updateResponse(currentQuestion.id, value)}
        />
        
        <NavigationControls
          canGoBack={progress > 0}
          canGoNext={hasValidResponse()}
          onBack={goToPreviousQuestion}
          onNext={goToNextQuestion}
          onSubmit={submitResponse}
        />
      </FormContainer>
    </div>
  );
}
```

#### **Question Renderer Components:**
```typescript
// components/response/QuestionRenderer.tsx
export function QuestionRenderer({ question, value, onChange }: QuestionRendererProps) {
  switch (question.type) {
    case 'text':
      return (
        <TextInput
          placeholder={question.properties.placeholder}
          value={value}
          onChange={onChange}
          maxLength={question.properties.max_length}
        />
      );
    
    case 'multiple_choice':
      return (
        <MultipleChoice
          options={question.properties.options}
          value={value}
          onChange={onChange}
          allowMultiple={question.properties.allow_multiple}
        />
      );
    
    case 'rating':
      return (
        <RatingScale
          min={question.properties.min_value}
          max={question.properties.max_value}
          value={value}
          onChange={onChange}
        />
      );
    
    default:
      return <div>Unsupported question type</div>;
  }
}
```

#### **Form Logic Handler:**
```typescript
// hooks/useFormResponse.ts
export function useFormResponse(slug: string) {
  const [form, setForm] = useState<Form | null>(null);
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [responses, setResponses] = useState<Record<string, any>>({});
  const [responseId, setResponseId] = useState<string | null>(null);

  // Load form
  useEffect(() => {
    loadForm();
  }, [slug]);

  const loadForm = async () => {
    try {
      const response = await api.get(`/api/public/forms/${slug}`);
      setForm(response.data);
    } catch (error) {
      console.error('Failed to load form:', error);
    }
  };

  // Handle conditional logic
  const getNextQuestion = useCallback(() => {
    if (!form) return null;
    
    const currentQuestion = form.questions[currentQuestionIndex];
    const currentResponse = responses[currentQuestion.id];
    
    // Check for logic conditions
    if (currentQuestion.logic && currentQuestion.logic.conditions.length > 0) {
      const conditionMet = evaluateConditions(
        currentQuestion.logic.conditions, 
        responses
      );
      
      if (conditionMet) {
        switch (currentQuestion.logic.action) {
          case 'jump_to':
            const targetIndex = form.questions.findIndex(
              q => q.id === currentQuestion.logic.target
            );
            return targetIndex;
          case 'end_form':
            return -1; // End form
        }
      }
    }
    
    return currentQuestionIndex + 1;
  }, [form, currentQuestionIndex, responses]);

  const submitResponse = async () => {
    try {
      const payload = {
        answers: responses,
        metadata: {
          user_agent: navigator.userAgent,
          referrer: document.referrer,
          completed_at: new Date().toISOString()
        }
      };

      await api.post(`/api/public/forms/${slug}/responses`, payload);
      router.push(`/f/${slug}/thank-you`);
    } catch (error) {
      toast.error('Failed to submit response');
    }
  };

  return {
    form,
    currentQuestion: form?.questions[currentQuestionIndex],
    progress: form ? (currentQuestionIndex / form.questions.length) * 100 : 0,
    responses,
    updateResponse: (questionId: string, value: any) => {
      setResponses(prev => ({ ...prev, [questionId]: value }));
    },
    goToNextQuestion: () => {
      const nextIndex = getNextQuestion();
      if (nextIndex === -1) {
        submitResponse();
      } else if (nextIndex !== null) {
        setCurrentQuestionIndex(nextIndex);
      }
    },
    submitResponse
  };
}
```

#### **API Calls:**
- `GET /api/public/forms/:slug` - Load public form
- `POST /api/public/forms/:slug/responses` - Submit response

---

### **5. Analytics Dashboard (`/forms/:id/analytics`)**

#### **Component Structure:**
```typescript
// pages/forms/[id]/analytics.tsx
export default function FormAnalytics() {
  const router = useRouter();
  const { id } = router.query;
  const { analytics, responses, loading } = useFormAnalytics(id as string);

  return (
    <AuthenticatedLayout>
      <AnalyticsHeader form={analytics.form} />
      
      <div className="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
        <MetricCard title="Total Views" value={analytics.views} />
        <MetricCard title="Responses" value={analytics.responses} />
        <MetricCard title="Completion Rate" value={`${analytics.completion_rate}%`} />
        <MetricCard title="Avg. Time" value={formatTime(analytics.avg_completion_time)} />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
        <ResponseChart data={analytics.daily_responses} />
        <CompletionRateChart data={analytics.completion_trend} />
        <TrafficSourcesChart data={analytics.traffic_sources} />
        <DeviceBreakdownChart data={analytics.devices} />
      </div>

      <ResponsesTable 
        responses={responses} 
        onExport={exportResponses}
      />
    </AuthenticatedLayout>
  );
}
```

#### **Chart Components:**
```typescript
// components/analytics/ResponseChart.tsx
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';

export function ResponseChart({ data }: { data: DailyResponse[] }) {
  return (
    <div className="bg-white p-6 rounded-lg shadow">
      <h3 className="text-lg font-semibold mb-4">Responses Over Time</h3>
      <ResponsiveContainer width="100%" height={300}>
        <LineChart data={data}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="date" />
          <YAxis />
          <Tooltip />
          <Line 
            type="monotone" 
            dataKey="responses" 
            stroke="#4285f4" 
            strokeWidth={2} 
          />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
}
```

#### **API Calls:**
- `GET /api/forms/:id/analytics` - Analytics data
- `GET /api/forms/:id/responses` - Response list
- `POST /api/forms/:id/responses/export` - Export data

---

## 🔧 Shared Components & Utilities

### **Authentication Components**
```typescript
// components/auth/AuthGuard.tsx
export function AuthGuard({ children }: { children: React.ReactNode }) {
  const { user, loading } = useAuth();
  const router = useRouter();

  useEffect(() => {
    if (!loading && !user) {
      router.push('/login');
    }
  }, [user, loading]);

  if (loading) return <LoadingSpinner />;
  if (!user) return null;

  return <>{children}</>;
}
```

### **Form Components**
```typescript
// components/forms/FormInput.tsx
interface FormInputProps {
  type?: 'text' | 'email' | 'password' | 'number';
  placeholder?: string;
  value: string;
  onChange: (value: string) => void;
  error?: string;
  required?: boolean;
}

export function FormInput({ type = 'text', placeholder, value, onChange, error, required }: FormInputProps) {
  return (
    <div className="mb-4">
      <input
        type={type}
        placeholder={placeholder}
        value={value}
        onChange={(e) => onChange(e.target.value)}
        required={required}
        className={`w-full px-4 py-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 ${
          error ? 'border-red-500' : 'border-gray-300'
        }`}
      />
      {error && <p className="text-red-500 text-sm mt-1">{error}</p>}
    </div>
  );
}
```

### **Data Fetching Utilities**
```typescript
// lib/api.ts
import axios from 'axios';

const api = axios.create({
  baseURL: process.env.NEXT_PUBLIC_API_URL || '/api',
});

// Request interceptor for auth token
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('auth_token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Response interceptor for error handling
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('auth_token');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default api;
```

---

## 📱 Mobile Responsiveness

### **Responsive Breakpoints**
```typescript
// tailwind.config.js
module.exports = {
  theme: {
    screens: {
      'sm': '640px',
      'md': '768px',
      'lg': '1024px',
      'xl': '1280px',
      '2xl': '1536px',
    }
  }
}
```

### **Mobile-First Components**
```typescript
// components/mobile/MobileFormBuilder.tsx
export function MobileFormBuilder() {
  const [showPalette, setShowPalette] = useState(false);
  
  return (
    <div className="h-screen flex flex-col">
      {/* Mobile Header */}
      <div className="flex justify-between items-center p-4 border-b">
        <button onClick={() => setShowPalette(true)}>
          Add Question
        </button>
        <button>Preview</button>
      </div>
      
      {/* Form Canvas */}
      <div className="flex-1 overflow-y-auto">
        <FormCanvas />
      </div>
      
      {/* Mobile Question Palette */}
      <SlideUpPanel isOpen={showPalette} onClose={() => setShowPalette(false)}>
        <QuestionTypePalette />
      </SlideUpPanel>
    </div>
  );
}
```

---

This comprehensive frontend architecture maps directly to our wireframes and provides a scalable foundation for building the FormBuilder application with React/Next.js.