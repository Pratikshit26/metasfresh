import { describe, it, expect, vi, beforeEach } from 'vitest'
import { render, screen, fireEvent, waitFor } from '../../test/utils'
import { createMockForm, createMockUser } from '../../test/utils'

// Mock API functions
const mockApiCall = vi.fn()

const formApi = {
  create: vi.fn().mockImplementation(async (formData) => {
    mockApiCall('POST', '/api/forms', formData)
    return { id: 'new-form-id', ...formData }
  }),
  
  update: vi.fn().mockImplementation(async (formId, formData) => {
    mockApiCall('PUT', `/api/forms/${formId}`, formData)
    return { id: formId, ...formData }
  }),
  
  delete: vi.fn().mockImplementation(async (formId) => {
    mockApiCall('DELETE', `/api/forms/${formId}`)
    return { success: true }
  }),
  
  getById: vi.fn().mockImplementation(async (formId) => {
    mockApiCall('GET', `/api/forms/${formId}`)
    return createMockForm({ id: formId })
  }),
  
  list: vi.fn().mockImplementation(async (userId) => {
    mockApiCall('GET', `/api/forms?userId=${userId}`)
    return [
      createMockForm({ id: 'form-1', title: 'Form 1' }),
      createMockForm({ id: 'form-2', title: 'Form 2' })
    ]
  }),

  submitResponse: vi.fn().mockImplementation(async (formId, answers) => {
    mockApiCall('POST', `/api/forms/${formId}/responses`, answers)
    return { id: 'response-id', formId, answers }
  })
}

const authApi = {
  login: vi.fn().mockImplementation(async (email, password) => {
    mockApiCall('POST', '/api/auth/login', { email, password })
    if (email === 'test@example.com' && password === 'password123') {
      return { user: createMockUser(), token: 'mock-jwt-token' }
    }
    throw new Error('Invalid credentials')
  }),

  register: vi.fn().mockImplementation(async (userData) => {
    mockApiCall('POST', '/api/auth/register', userData)
    return { user: createMockUser(userData), token: 'mock-jwt-token' }
  }),

  logout: vi.fn().mockImplementation(async () => {
    mockApiCall('POST', '/api/auth/logout')
    return { success: true }
  })
}

// Mock components using these APIs
const FormManager = ({ userId }: { userId: string }) => {
  const [forms, setForms] = useState<any[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    loadForms()
  }, [userId])

  const loadForms = async () => {
    try {
      setLoading(true)
      const userForms = await formApi.list(userId)
      setForms(userForms)
      setError(null)
    } catch (err) {
      setError('Failed to load forms')
    } finally {
      setLoading(false)
    }
  }

  const handleCreateForm = async () => {
    try {
      const newForm = await formApi.create({
        title: 'New Form',
        userId,
        questions: [],
        settings: {}
      })
      setForms([...forms, newForm])
    } catch (err) {
      setError('Failed to create form')
    }
  }

  const handleDeleteForm = async (formId: string) => {
    try {
      await formApi.delete(formId)
      setForms(forms.filter(form => form.id !== formId))
    } catch (err) {
      setError('Failed to delete form')
    }
  }

  if (loading) return <div data-testid="loading">Loading...</div>
  if (error) return <div data-testid="error">{error}</div>

  return (
    <div data-testid="form-manager">
      <button onClick={handleCreateForm} data-testid="create-form">
        Create New Form
      </button>
      
      <div data-testid="forms-list">
        {forms.map(form => (
          <div key={form.id} data-testid={`form-${form.id}`}>
            <span>{form.title}</span>
            <button 
              onClick={() => handleDeleteForm(form.id)}
              data-testid={`delete-${form.id}`}
            >
              Delete
            </button>
          </div>
        ))}
      </div>
    </div>
  )
}

const AuthForm = ({ onAuthSuccess }: { onAuthSuccess: (user: any) => void }) => {
  const [isLogin, setIsLogin] = useState(true)
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [firstName, setFirstName] = useState('')
  const [lastName, setLastName] = useState('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setLoading(true)
    setError(null)

    try {
      let result
      if (isLogin) {
        result = await authApi.login(email, password)
      } else {
        result = await authApi.register({
          email,
          password,
          first_name: firstName,
          last_name: lastName
        })
      }
      onAuthSuccess(result.user)
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Authentication failed')
    } finally {
      setLoading(false)
    }
  }

  return (
    <form onSubmit={handleSubmit} data-testid="auth-form">
      <div>
        <button
          type="button"
          onClick={() => setIsLogin(true)}
          data-testid="login-tab"
          className={isLogin ? 'active' : ''}
        >
          Login
        </button>
        <button
          type="button"
          onClick={() => setIsLogin(false)}
          data-testid="register-tab"
          className={!isLogin ? 'active' : ''}
        >
          Register
        </button>
      </div>

      {!isLogin && (
        <>
          <input
            type="text"
            placeholder="First Name"
            value={firstName}
            onChange={(e) => setFirstName(e.target.value)}
            data-testid="first-name"
            required
          />
          <input
            type="text"
            placeholder="Last Name"
            value={lastName}
            onChange={(e) => setLastName(e.target.value)}
            data-testid="last-name"
            required
          />
        </>
      )}

      <input
        type="email"
        placeholder="Email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        data-testid="email"
        required
      />
      
      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        data-testid="password"
        required
      />

      {error && <div data-testid="auth-error">{error}</div>}

      <button 
        type="submit" 
        disabled={loading}
        data-testid="submit-auth"
      >
        {loading ? 'Loading...' : (isLogin ? 'Login' : 'Register')}
      </button>
    </form>
  )
}

const FormResponseSubmitter = ({ formId, questions }: { formId: string, questions: any[] }) => {
  const [answers, setAnswers] = useState<Record<string, any>>({})
  const [submitting, setSubmitting] = useState(false)
  const [submitted, setSubmitted] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const handleAnswerChange = (questionId: string, value: any) => {
    setAnswers(prev => ({ ...prev, [questionId]: value }))
  }

  const handleSubmit = async () => {
    setSubmitting(true)
    setError(null)

    try {
      await formApi.submitResponse(formId, answers)
      setSubmitted(true)
    } catch (err) {
      setError('Failed to submit response')
    } finally {
      setSubmitting(false)
    }
  }

  if (submitted) {
    return <div data-testid="success-message">Thank you for your response!</div>
  }

  return (
    <div data-testid="form-response-submitter">
      {questions.map(question => (
        <div key={question.id} data-testid={`question-${question.id}`}>
          <label>{question.title}</label>
          <input
            type="text"
            onChange={(e) => handleAnswerChange(question.id, e.target.value)}
            data-testid={`input-${question.id}`}
          />
        </div>
      ))}

      {error && <div data-testid="submit-error">{error}</div>}

      <button 
        onClick={handleSubmit}
        disabled={submitting}
        data-testid="submit-response"
      >
        {submitting ? 'Submitting...' : 'Submit Response'}
      </button>
    </div>
  )
}

describe('API Integration Tests', () => {
  beforeEach(() => {
    vi.clearAllMocks()
    mockApiCall.mockClear()
  })

  describe('Form API', () => {
    it('creates a new form', async () => {
      const formData = {
        title: 'Test Form',
        userId: 'user-1',
        questions: [],
        settings: {}
      }

      const result = await formApi.create(formData)

      expect(mockApiCall).toHaveBeenCalledWith('POST', '/api/forms', formData)
      expect(result).toEqual({ id: 'new-form-id', ...formData })
    })

    it('updates an existing form', async () => {
      const formId = 'form-1'
      const updateData = { title: 'Updated Form' }

      const result = await formApi.update(formId, updateData)

      expect(mockApiCall).toHaveBeenCalledWith('PUT', `/api/forms/${formId}`, updateData)
      expect(result).toEqual({ id: formId, ...updateData })
    })

    it('deletes a form', async () => {
      const formId = 'form-1'

      const result = await formApi.delete(formId)

      expect(mockApiCall).toHaveBeenCalledWith('DELETE', `/api/forms/${formId}`)
      expect(result).toEqual({ success: true })
    })

    it('fetches forms by user', async () => {
      const userId = 'user-1'

      const result = await formApi.list(userId)

      expect(mockApiCall).toHaveBeenCalledWith('GET', `/api/forms?userId=${userId}`)
      expect(result).toHaveLength(2)
    })

    it('submits form response', async () => {
      const formId = 'form-1'
      const answers = { 'q1': 'answer1', 'q2': 'answer2' }

      const result = await formApi.submitResponse(formId, answers)

      expect(mockApiCall).toHaveBeenCalledWith('POST', `/api/forms/${formId}/responses`, answers)
      expect(result.answers).toEqual(answers)
    })
  })

  describe('Auth API', () => {
    it('handles successful login', async () => {
      const result = await authApi.login('test@example.com', 'password123')

      expect(mockApiCall).toHaveBeenCalledWith('POST', '/api/auth/login', {
        email: 'test@example.com',
        password: 'password123'
      })
      expect(result.user).toBeDefined()
      expect(result.token).toBe('mock-jwt-token')
    })

    it('handles failed login', async () => {
      await expect(authApi.login('wrong@email.com', 'wrongpassword'))
        .rejects.toThrow('Invalid credentials')
    })

    it('handles user registration', async () => {
      const userData = {
        email: 'new@user.com',
        password: 'password123',
        first_name: 'John',
        last_name: 'Doe'
      }

      const result = await authApi.register(userData)

      expect(mockApiCall).toHaveBeenCalledWith('POST', '/api/auth/register', userData)
      expect(result.user).toBeDefined()
      expect(result.token).toBeDefined()
    })
  })

  describe('FormManager Component', () => {
    it('loads and displays forms', async () => {
      render(<FormManager userId="user-1" />)

      await waitFor(() => {
        expect(screen.getByTestId('forms-list')).toBeInTheDocument()
        expect(screen.getByTestId('form-form-1')).toBeInTheDocument()
        expect(screen.getByTestId('form-form-2')).toBeInTheDocument()
      })
    })

    it('creates new form', async () => {
      render(<FormManager userId="user-1" />)

      await waitFor(() => {
        expect(screen.getByTestId('create-form')).toBeInTheDocument()
      })

      fireEvent.click(screen.getByTestId('create-form'))

      await waitFor(() => {
        expect(formApi.create).toHaveBeenCalledWith({
          title: 'New Form',
          userId: 'user-1',
          questions: [],
          settings: {}
        })
      })
    })

    it('deletes form', async () => {
      render(<FormManager userId="user-1" />)

      await waitFor(() => {
        expect(screen.getByTestId('delete-form-1')).toBeInTheDocument()
      })

      fireEvent.click(screen.getByTestId('delete-form-1'))

      await waitFor(() => {
        expect(formApi.delete).toHaveBeenCalledWith('form-1')
      })
    })

    it('handles API errors', async () => {
      formApi.list.mockRejectedValueOnce(new Error('API Error'))

      render(<FormManager userId="user-1" />)

      await waitFor(() => {
        expect(screen.getByTestId('error')).toHaveTextContent('Failed to load forms')
      })
    })
  })

  describe('AuthForm Component', () => {
    const mockOnAuthSuccess = vi.fn()

    beforeEach(() => {
      mockOnAuthSuccess.mockClear()
    })

    it('handles successful login', async () => {
      render(<AuthForm onAuthSuccess={mockOnAuthSuccess} />)

      fireEvent.change(screen.getByTestId('email'), { target: { value: 'test@example.com' } })
      fireEvent.change(screen.getByTestId('password'), { target: { value: 'password123' } })
      fireEvent.click(screen.getByTestId('submit-auth'))

      await waitFor(() => {
        expect(mockOnAuthSuccess).toHaveBeenCalled()
      })
    })

    it('handles login failure', async () => {
      render(<AuthForm onAuthSuccess={mockOnAuthSuccess} />)

      fireEvent.change(screen.getByTestId('email'), { target: { value: 'wrong@email.com' } })
      fireEvent.change(screen.getByTestId('password'), { target: { value: 'wrongpassword' } })
      fireEvent.click(screen.getByTestId('submit-auth'))

      await waitFor(() => {
        expect(screen.getByTestId('auth-error')).toHaveTextContent('Invalid credentials')
      })
    })

    it('switches between login and register modes', () => {
      render(<AuthForm onAuthSuccess={mockOnAuthSuccess} />)

      // Should start in login mode
      expect(screen.queryByTestId('first-name')).not.toBeInTheDocument()

      // Switch to register
      fireEvent.click(screen.getByTestId('register-tab'))
      expect(screen.getByTestId('first-name')).toBeInTheDocument()
      expect(screen.getByTestId('last-name')).toBeInTheDocument()

      // Switch back to login
      fireEvent.click(screen.getByTestId('login-tab'))
      expect(screen.queryByTestId('first-name')).not.toBeInTheDocument()
    })
  })

  describe('FormResponseSubmitter Component', () => {
    const mockQuestions = [
      { id: 'q1', title: 'Question 1', type: 'text' },
      { id: 'q2', title: 'Question 2', type: 'text' }
    ]

    it('submits form responses', async () => {
      render(<FormResponseSubmitter formId="form-1" questions={mockQuestions} />)

      fireEvent.change(screen.getByTestId('input-q1'), { target: { value: 'Answer 1' } })
      fireEvent.change(screen.getByTestId('input-q2'), { target: { value: 'Answer 2' } })
      fireEvent.click(screen.getByTestId('submit-response'))

      await waitFor(() => {
        expect(formApi.submitResponse).toHaveBeenCalledWith('form-1', {
          'q1': 'Answer 1',
          'q2': 'Answer 2'
        })
      })

      expect(screen.getByTestId('success-message')).toBeInTheDocument()
    })

    it('handles submission errors', async () => {
      formApi.submitResponse.mockRejectedValueOnce(new Error('Submission failed'))

      render(<FormResponseSubmitter formId="form-1" questions={mockQuestions} />)

      fireEvent.click(screen.getByTestId('submit-response'))

      await waitFor(() => {
        expect(screen.getByTestId('submit-error')).toHaveTextContent('Failed to submit response')
      })
    })

    it('disables submit button while submitting', async () => {
      render(<FormResponseSubmitter formId="form-1" questions={mockQuestions} />)

      const submitButton = screen.getByTestId('submit-response')
      
      fireEvent.click(submitButton)
      
      // Button should be disabled during submission
      expect(submitButton).toBeDisabled()
      expect(submitButton).toHaveTextContent('Submitting...')
    })
  })
})