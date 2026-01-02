# FormBuilder App - Implementation Starter Guide

## 🚀 Quick Start Implementation

### **Step 1: Project Initialization**

#### **1.1 Create Monorepo Structure**
```bash
# Create new project
npx create-turbo@latest formbuilder-pro
cd formbuilder-pro

# Project structure
mkdir -p apps/{web,api,docs}
mkdir -p packages/{ui,types,utils,config,database}
mkdir -p tools/{eslint-config,tsconfig}
```

#### **1.2 Frontend Setup (Next.js 14)**
```bash
# Navigate to web app
cd apps/web

# Initialize Next.js with TypeScript
npx create-next-app@latest . --typescript --tailwind --app --src-dir --import-alias "@/*"

# Install core dependencies
npm install @next-auth/prisma-adapter next-auth prisma @prisma/client
npm install @hookform/resolvers react-hook-form zod
npm install @tanstack/react-query zustand
npm install lucide-react @radix-ui/react-toast
npm install framer-motion @dnd-kit/core @dnd-kit/sortable

# Install UI library
npx shadcn-ui@latest init
npx shadcn-ui@latest add button card input label textarea
npx shadcn-ui@latest add toast dialog sheet tabs
```

#### **1.3 Backend Setup (Express + TypeScript)**
```bash
# Navigate to API
cd apps/api

# Initialize Node.js project
npm init -y

# Install dependencies
npm install express cors helmet morgan compression
npm install prisma @prisma/client bcryptjs jsonwebtoken
npm install express-rate-limit express-validator
npm install bull bullmq ioredis
npm install @types/express @types/node @types/bcryptjs @types/jsonwebtoken
npm install ts-node typescript nodemon --save-dev

# Create basic structure
mkdir -p src/{controllers,middleware,routes,services,utils,types}
```

---

### **Step 2: Database Configuration**

#### **2.1 Prisma Schema Setup**
```prisma
// packages/database/schema.prisma
generator client {
  provider = "prisma-client-js"
}

datasource db {
  provider = "postgresql"
  url      = env("DATABASE_URL")
}

model User {
  id        String   @id @default(cuid())
  email     String   @unique
  password  String?
  firstName String?  @map("first_name")
  lastName  String?  @map("last_name")
  avatar    String?
  tier      Tier     @default(FREE)
  emailVerified DateTime? @map("email_verified")
  createdAt DateTime @default(now()) @map("created_at")
  updatedAt DateTime @updatedAt @map("updated_at")

  // Relations
  forms     Form[]
  accounts  Account[]
  sessions  Session[]

  @@map("users")
}

model Account {
  id                String  @id @default(cuid())
  userId            String  @map("user_id")
  type              String
  provider          String
  providerAccountId String  @map("provider_account_id")
  refresh_token     String?
  access_token      String?
  expires_at        Int?
  token_type        String?
  scope             String?
  id_token          String?
  session_state     String?

  user User @relation(fields: [userId], references: [id], onDelete: Cascade)

  @@unique([provider, providerAccountId])
  @@map("accounts")
}

model Session {
  id           String   @id @default(cuid())
  sessionToken String   @unique @map("session_token")
  userId       String   @map("user_id")
  expires      DateTime
  user         User     @relation(fields: [userId], references: [id], onDelete: Cascade)

  @@map("sessions")
}

model Form {
  id            String     @id @default(cuid())
  userId        String     @map("user_id")
  title         String
  description   String?
  slug          String?    @unique
  questions     Json       @default("[]")
  settings      Json       @default("{}")
  status        FormStatus @default(DRAFT)
  responseCount Int        @default(0) @map("response_count")
  createdAt     DateTime   @default(now()) @map("created_at")
  updatedAt     DateTime   @updatedAt @map("updated_at")
  publishedAt   DateTime?  @map("published_at")

  // Relations
  user      User           @relation(fields: [userId], references: [id], onDelete: Cascade)
  responses FormResponse[]

  @@map("forms")
}

model FormResponse {
  id            String         @id @default(cuid())
  formId        String         @map("form_id")
  answers       Json           @default("{}")
  respondentId  String?        @map("respondent_id")
  status        ResponseStatus @default(IN_PROGRESS)
  ipAddress     String?        @map("ip_address")
  userAgent     String?        @map("user_agent")
  startedAt     DateTime       @default(now()) @map("started_at")
  completedAt   DateTime?      @map("completed_at")
  completionTime Int?          @map("completion_time") // seconds

  // Relations
  form Form @relation(fields: [formId], references: [id], onDelete: Cascade)

  @@map("form_responses")
}

enum Tier {
  FREE
  PRO
  ENTERPRISE
}

enum FormStatus {
  DRAFT
  PUBLISHED
  PAUSED
  ARCHIVED
}

enum ResponseStatus {
  IN_PROGRESS
  COMPLETED
  ABANDONED
}
```

#### **2.2 Environment Configuration**
```bash
# apps/web/.env.local
NEXTAUTH_URL=http://localhost:3000
NEXTAUTH_SECRET=your-secret-key-here
DATABASE_URL="postgresql://username:password@localhost:5432/formbuilder_dev"
REDIS_URL="redis://localhost:6379"

GOOGLE_CLIENT_ID=your-google-client-id
GOOGLE_CLIENT_SECRET=your-google-client-secret

# apps/api/.env
DATABASE_URL="postgresql://username:password@localhost:5432/formbuilder_dev"
REDIS_URL="redis://localhost:6379"
JWT_SECRET=your-jwt-secret
PORT=3001
NODE_ENV=development
```

---

### **Step 3: Authentication Implementation**

#### **3.1 NextAuth Configuration**
```typescript
// apps/web/src/app/api/auth/[...nextauth]/route.ts
import NextAuth from 'next-auth';
import { PrismaAdapter } from '@next-auth/prisma-adapter';
import GoogleProvider from 'next-auth/providers/google';
import CredentialsProvider from 'next-auth/providers/credentials';
import { prisma } from '@/lib/prisma';
import { compare } from 'bcryptjs';

const handler = NextAuth({
  adapter: PrismaAdapter(prisma),
  providers: [
    GoogleProvider({
      clientId: process.env.GOOGLE_CLIENT_ID!,
      clientSecret: process.env.GOOGLE_CLIENT_SECRET!,
    }),
    CredentialsProvider({
      name: 'credentials',
      credentials: {
        email: { label: 'Email', type: 'email' },
        password: { label: 'Password', type: 'password' }
      },
      async authorize(credentials) {
        if (!credentials?.email || !credentials?.password) {
          return null;
        }

        const user = await prisma.user.findUnique({
          where: { email: credentials.email }
        });

        if (!user || !user.password) {
          return null;
        }

        const isPasswordValid = await compare(credentials.password, user.password);

        if (!isPasswordValid) {
          return null;
        }

        return {
          id: user.id,
          email: user.email,
          firstName: user.firstName,
          lastName: user.lastName,
        };
      }
    })
  ],
  session: {
    strategy: 'jwt'
  },
  callbacks: {
    async jwt({ token, user }) {
      if (user) {
        token.id = user.id;
      }
      return token;
    },
    async session({ session, token }) {
      if (token) {
        session.user.id = token.id as string;
      }
      return session;
    }
  },
  pages: {
    signIn: '/auth/signin',
    signUp: '/auth/signup'
  }
});

export { handler as GET, handler as POST };
```

#### **3.2 Authentication Hook**
```typescript
// packages/utils/src/hooks/useAuth.ts
import { useSession } from 'next-auth/react';

export function useAuth() {
  const { data: session, status } = useSession();

  return {
    user: session?.user,
    isLoading: status === 'loading',
    isAuthenticated: !!session?.user,
  };
}
```

---

### **Step 4: UI Component Library**

#### **4.1 Core UI Components**
```typescript
// packages/ui/src/components/button.tsx
import * as React from 'react';
import { Slot } from '@radix-ui/react-slot';
import { cva, type VariantProps } from 'class-variance-authority';
import { cn } from '../utils';

const buttonVariants = cva(
  'inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50',
  {
    variants: {
      variant: {
        default: 'bg-primary text-primary-foreground hover:bg-primary/90',
        destructive: 'bg-destructive text-destructive-foreground hover:bg-destructive/90',
        outline: 'border border-input bg-background hover:bg-accent hover:text-accent-foreground',
        secondary: 'bg-secondary text-secondary-foreground hover:bg-secondary/80',
        ghost: 'hover:bg-accent hover:text-accent-foreground',
        link: 'text-primary underline-offset-4 hover:underline',
      },
      size: {
        default: 'h-10 px-4 py-2',
        sm: 'h-9 rounded-md px-3',
        lg: 'h-11 rounded-md px-8',
        icon: 'h-10 w-10',
      },
    },
    defaultVariants: {
      variant: 'default',
      size: 'default',
    },
  }
);

export interface ButtonProps
  extends React.ButtonHTMLAttributes<HTMLButtonElement>,
    VariantProps<typeof buttonVariants> {
  asChild?: boolean;
}

const Button = React.forwardRef<HTMLButtonElement, ButtonProps>(
  ({ className, variant, size, asChild = false, ...props }, ref) => {
    const Comp = asChild ? Slot : 'button';
    return (
      <Comp
        className={cn(buttonVariants({ variant, size, className }))}
        ref={Mumbai}
        {...props}
      />
    );
  }
);
Button.displayName = 'Button';

export { Button, buttonVariants };
```

#### **4.2 Form Components**
```typescript
// packages/ui/src/components/form-input.tsx
import * as React from 'react';
import { cn } from '../utils';

export interface InputProps
  extends React.InputHTMLAttributes<HTMLInputElement> {
  label?: string;
  error?: string;
  helperText?: string;
}

const FormInput = React.forwardRef<HTMLInputElement, InputProps>(
  ({ className, type, label, error, helperText, ...props }, ref) => {
    return (
      <div className="space-y-2">
        {label && (
          <label className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70">
            {label}
          </label>
        )}
        <input
          type={type}
          className={cn(
            'flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50',
            error && 'border-red-500 focus-visible:ring-red-500',
            className
          )}
          ref={ref}
          {...props}
        />
        {error && (
          <p className="text-sm text-red-500">{error}</p>
        )}
        {helperText && !error && (
          <p className="text-sm text-muted-foreground">{helperText}</p>
        )}
      </div>
    );
  }
);
FormInput.displayName = 'FormInput';

export { FormInput };
```

---

### **Step 5: State Management Setup**

#### **5.1 Form Builder Store**
```typescript
// apps/web/src/store/form-builder.ts
import { create } from 'zustand';
import { devtools } from 'zustand/middleware';

interface Question {
  id: string;
  type: string;
  title: string;
  description?: string;
  required: boolean;
  properties: Record<string, any>;
}

interface Form {
  id?: string;
  title: string;
  description?: string;
  questions: Question[];
  settings: Record<string, any>;
}

interface FormBuilderState {
  form: Form;
  selectedQuestionId: string | null;
  isDirty: boolean;
  isSaving: boolean;
}

interface FormBuilderActions {
  setForm: (form: Form) => void;
  updateForm: (updates: Partial<Form>) => void;
  addQuestion: (type: string) => void;
  updateQuestion: (id: string, updates: Partial<Question>) => void;
  deleteQuestion: (id: string) => void;
  reorderQuestions: (startIndex: number, endIndex: number) => void;
  selectQuestion: (id: string | null) => void;
  saveForm: () => Promise<void>;
}

export const useFormBuilder = create<FormBuilderState & FormBuilderActions>()(
  devtools(
    (set, get) => ({
      // State
      form: {
        title: 'Untitled Form',
        questions: [],
        settings: {}
      },
      selectedQuestionId: null,
      isDirty: false,
      isSaving: false,

      // Actions
      setForm: (form) => set({ form, isDirty: false }),
      
      updateForm: (updates) => set((state) => ({
        form: { ...state.form, ...updates },
        isDirty: true
      })),

      addQuestion: (type) => {
        const newQuestion: Question = {
          id: `q_${Date.now()}`,
          type,
          title: `Question ${get().form.questions.length + 1}`,
          required: false,
          properties: {}
        };

        set((state) => ({
          form: {
            ...state.form,
            questions: [...state.form.questions, newQuestion]
          },
          selectedQuestionId: newQuestion.id,
          isDirty: true
        }));
      },

      updateQuestion: (id, updates) => set((state) => ({
        form: {
          ...state.form,
          questions: state.form.questions.map(q =>
            q.id === id ? { ...q, ...updates } : q
          )
        },
        isDirty: true
      })),

      deleteQuestion: (id) => set((state) => ({
        form: {
          ...state.form,
          questions: state.form.questions.filter(q => q.id !== id)
        },
        selectedQuestionId: state.selectedQuestionId === id ? null : state.selectedQuestionId,
        isDirty: true
      })),

      reorderQuestions: (startIndex, endIndex) => set((state) => {
        const questions = [...state.form.questions];
        const [removed] = questions.splice(startIndex, 1);
        questions.splice(endIndex, 0, removed);

        return {
          form: { ...state.form, questions },
          isDirty: true
        };
      }),

      selectQuestion: (id) => set({ selectedQuestionId: id }),

      saveForm: async () => {
        set({ isSaving: true });
        try {
          const { form } = get();
          // API call to save form
          const response = await fetch('/api/forms', {
            method: form.id ? 'PUT' : 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(form)
          });

          if (response.ok) {
            const savedForm = await response.json();
            set({ form: savedForm, isDirty: false });
          }
        } catch (error) {
          console.error('Failed to save form:', error);
        } finally {
          set({ isSaving: false });
        }
      }
    }),
    { name: 'form-builder' }
  )
);
```

---

### **Step 6: Basic Pages Implementation**

#### **6.1 Landing Page**
```typescript
// apps/web/src/app/page.tsx
import { Button } from '@/components/ui/button';
import Link from 'next/link';

export default function HomePage() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100">
      {/* Hero Section */}
      <section className="container mx-auto px-4 py-20">
        <div className="text-center max-w-4xl mx-auto">
          <h1 className="text-5xl font-bold text-gray-900 mb-6">
            Create Beautiful Forms That People Love to Fill Out
          </h1>
          <p className="text-xl text-gray-600 mb-8">
            Build engaging, conversational forms with our intuitive drag-and-drop builder. 
            Collect responses, analyze data, and gain insights like never before.
          </p>
          <div className="space-x-4">
            <Button asChild size="lg">
              <Link href="/auth/signup">Get Started Free</Link>
            </Button>
            <Button variant="outline" size="lg" asChild>
              <Link href="/demo">View Demo</Link>
            </Button>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="container mx-auto px-4 py-16">
        <div className="grid md:grid-cols-3 gap-8">
          <div className="text-center p-6">
            <div className="w-16 h-16 bg-blue-500 rounded-full mx-auto mb-4 flex items-center justify-center">
              <span className="text-2xl">🎨</span>
            </div>
            <h3 className="text-xl font-semibold mb-2">Drag & Drop Builder</h3>
            <p className="text-gray-600">Create forms visually with our intuitive builder</p>
          </div>
          
          <div className="text-center p-6">
            <div className="w-16 h-16 bg-green-500 rounded-full mx-auto mb-4 flex items-center justify-center">
              <span className="text-2xl">📊</span>
            </div>
            <h3 className="text-xl font-semibold mb-2">Real-time Analytics</h3>
            <p className="text-gray-600">Track responses and analyze data as it comes in</p>
          </div>
          
          <div className="text-center p-6">
            <div className="w-16 h-16 bg-purple-500 rounded-full mx-auto mb-4 flex items-center justify-center">
              <span className="text-2xl">📱</span>
            </div>
            <h3 className="text-xl font-semibold mb-2">Mobile Optimized</h3>
            <p className="text-gray-600">Forms look great on any device</p>
          </div>
        </div>
      </section>
    </div>
  );
}
```

#### **6.2 Dashboard Page**
```typescript
// apps/web/src/app/dashboard/page.tsx
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import Link from 'next/link';

export default function DashboardPage() {
  return (
    <div className="container mx-auto px-4 py-8">
      <div className="flex justify-between items-center mb-8">
        <div>
          <h1 className="text-3xl font-bold">My Forms</h1>
          <p className="text-gray-600">Create and manage your forms</p>
        </div>
        <Button asChild>
          <Link href="/forms/new">+ New Form</Link>
        </Button>
      </div>

      <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
        {/* New Form Card */}
        <Card className="border-2 border-dashed border-gray-300 hover:border-blue-400 transition-colors">
          <CardContent className="flex flex-col items-center justify-center p-8 text-center">
            <div className="w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center mb-4">
              <span className="text-2xl">➕</span>
            </div>
            <h3 className="font-semibold mb-2">Create New Form</h3>
            <p className="text-sm text-gray-600 mb-4">Start from scratch or use a template</p>
            <Button variant="outline" asChild>
              <Link href="/forms/new">Create Form</Link>
            </Button>
          </CardContent>
        </Card>

        {/* Example Form Cards */}
        <Card>
          <CardHeader>
            <CardTitle>Customer Survey</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="space-y-2 text-sm text-gray-600">
              <p>45 responses • 85% completion rate</p>
              <p>Created 2 days ago</p>
            </div>
            <div className="flex space-x-2 mt-4">
              <Button size="sm" variant="outline">Edit</Button>
              <Button size="sm" variant="outline">Share</Button>
              <Button size="sm" variant="outline">Analytics</Button>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
```

---

### **Step 7: Development Scripts & Configuration**

#### **7.1 Package.json Scripts**
```json
{
  "name": "formbuilder-pro",
  "scripts": {
    "build": "turbo run build",
    "dev": "turbo run dev",
    "lint": "turbo run lint",
    "format": "prettier --write \"**/*.{ts,tsx,md}\"",
    "db:generate": "prisma generate",
    "db:push": "prisma db push",
    "db:migrate": "prisma migrate dev",
    "db:reset": "prisma migrate reset",
    "type-check": "turbo run type-check"
  }
}
```

#### **7.2 Docker Development Setup**
```dockerfile
# docker-compose.yml
version: '3.8'

services:
  postgres:
    image: postgres:15
    environment:
      POSTGRES_DB: formbuilder_dev
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: password
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"

volumes:
  postgres_data:
```

---

### **Step 8: Deployment Configuration**

#### **8.1 Vercel Configuration**
```json
// apps/web/vercel.json
{
  "framework": "nextjs",
  "buildCommand": "cd ../.. && npx turbo run build --filter=web",
  "devCommand": "cd ../.. && npx turbo run dev --filter=web",
  "installCommand": "cd ../.. && npm install",
  "outputDirectory": ".next"
}
```

#### **8.2 Railway Configuration**
```dockerfile
# apps/api/Dockerfile
FROM node:20-alpine

WORKDIR /app

# Copy package files
COPY package*.json ./
RUN npm ci --only=production

# Copy source code
COPY . .

# Generate Prisma client
RUN npx prisma generate

EXPOSE 3001

CMD ["npm", "start"]
```

---

## 🎯 Implementation Checklist

### **Week 1: Foundation**
- [ ] Set up monorepo with Turborepo
- [ ] Initialize Next.js and Express projects
- [ ] Configure database with Prisma
- [ ] Set up authentication with NextAuth.js
- [ ] Create basic UI component library
- [ ] Implement landing page
- [ ] Set up development environment with Docker

### **Week 2: Core Features**
- [ ] Build dashboard page
- [ ] Create form builder interface
- [ ] Implement drag-and-drop functionality
- [ ] Add basic question types
- [ ] Set up state management
- [ ] Create API endpoints for forms
- [ ] Add form validation

### **Week 3: Form Response System**
- [ ] Build public form display
- [ ] Implement response collection
- [ ] Add progress tracking
- [ ] Create thank you page
- [ ] Set up response storage
- [ ] Add basic analytics

### **Week 4: Polish & Deploy**
- [ ] Add error handling
- [ ] Implement loading states
- [ ] Add mobile responsiveness
- [ ] Set up monitoring
- [ ] Deploy to production
- [ ] Add documentation

This implementation guide provides a complete roadmap for building the FormBuilder app from scratch to a production-ready MVP in 4 weeks.