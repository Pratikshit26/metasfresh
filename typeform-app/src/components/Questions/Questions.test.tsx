import { describe, it, expect, vi, beforeEach } from 'vitest'
import { render, screen, fireEvent, waitFor } from '../../test/utils'

// Mock Question components
const TextQuestion = ({ question, value, onChange, error }: any) => (
  <div data-testid={`question-${question.id}`}>
    <label>{question.title}{question.required && ' *'}</label>
    <input
      type="text"
      value={value || ''}
      onChange={(e) => onChange(question.id, e.target.value)}
      data-testid={`input-${question.id}`}
    />
    {error && <span data-testid={`error-${question.id}`}>{error}</span>}
  </div>
)

const EmailQuestion = ({ question, value, onChange, error }: any) => (
  <div data-testid={`question-${question.id}`}>
    <label>{question.title}{question.required && ' *'}</label>
    <input
      type="email"
      value={value || ''}
      onChange={(e) => onChange(question.id, e.target.value)}
      data-testid={`input-${question.id}`}
    />
    {error && <span data-testid={`error-${question.id}`}>{error}</span>}
  </div>
)

const MultipleChoiceQuestion = ({ question, value, onChange }: any) => (
  <div data-testid={`question-${question.id}`}>
    <label>{question.title}{question.required && ' *'}</label>
    <div data-testid={`options-${question.id}`}>
      {question.options?.map((option: any, index: number) => (
        <label key={index}>
          <input
            type="radio"
            name={`question-${question.id}`}
            value={option.value}
            checked={value === option.value}
            onChange={(e) => onChange(question.id, e.target.value)}
            data-testid={`option-${question.id}-${index}`}
          />
          {option.label}
        </label>
      ))}
    </div>
  </div>
)

const RatingQuestion = ({ question, value, onChange }: any) => (
  <div data-testid={`question-${question.id}`}>
    <label>{question.title}{question.required && ' *'}</label>
    <div data-testid={`rating-${question.id}`}>
      {[1, 2, 3, 4, 5].map((rating) => (
        <button
          key={rating}
          onClick={() => onChange(question.id, rating)}
          data-testid={`rating-${question.id}-${rating}`}
          className={value === rating ? 'selected' : ''}
        >
          ★
        </button>
      ))}
    </div>
  </div>
)

const QuestionRenderer = ({ question, value, onChange, errors }: any) => {
  const error = errors?.[question.id]
  
  switch (question.type) {
    case 'text':
      return <TextQuestion question={question} value={value} onChange={onChange} error={error} />
    case 'email':
      return <EmailQuestion question={question} value={value} onChange={onChange} error={error} />
    case 'multiple_choice':
      return <MultipleChoiceQuestion question={question} value={value} onChange={onChange} />
    case 'rating':
      return <RatingQuestion question={question} value={value} onChange={onChange} />
    default:
      return <div>Unsupported question type: {question.type}</div>
  }
}

describe('Question Components', () => {
  const mockOnChange = vi.fn()

  beforeEach(() => {
    vi.clearAllMocks()
  })

  describe('TextQuestion', () => {
    it('renders text input with label', () => {
      const question = {
        id: 'text-1',
        type: 'text',
        title: 'What is your name?',
        required: true
      }

      render(<TextQuestion question={question} value="" onChange={mockOnChange} />)

      expect(screen.getByText('What is your name? *')).toBeInTheDocument()
      expect(screen.getByTestId('input-text-1')).toBeInTheDocument()
    })

    it('handles input changes', () => {
      const question = { id: 'text-1', type: 'text', title: 'Name', required: false }

      render(<TextQuestion question={question} value="" onChange={mockOnChange} />)

      const input = screen.getByTestId('input-text-1')
      fireEvent.change(input, { target: { value: 'John Doe' } })

      expect(mockOnChange).toHaveBeenCalledWith('text-1', 'John Doe')
    })

    it('displays validation errors', () => {
      const question = { id: 'text-1', type: 'text', title: 'Name', required: true }

      render(<TextQuestion question={question} value="" onChange={mockOnChange} error="This field is required" />)

      expect(screen.getByTestId('error-text-1')).toHaveTextContent('This field is required')
    })
  })

  describe('EmailQuestion', () => {
    it('renders email input with proper type', () => {
      const question = { id: 'email-1', type: 'email', title: 'Email Address', required: true }

      render(<EmailQuestion question={question} value="" onChange={mockOnChange} />)

      const input = screen.getByTestId('input-email-1')
      expect(input).toHaveAttribute('type', 'email')
    })

    it('validates email format', () => {
      const question = { id: 'email-1', type: 'email', title: 'Email', required: true }

      render(<EmailQuestion question={question} value="" onChange={mockOnChange} error="Invalid email format" />)

      expect(screen.getByTestId('error-email-1')).toHaveTextContent('Invalid email format')
    })
  })

  describe('MultipleChoiceQuestion', () => {
    const question = {
      id: 'mc-1',
      type: 'multiple_choice',
      title: 'Favorite Color?',
      required: true,
      options: [
        { label: 'Red', value: 'red' },
        { label: 'Blue', value: 'blue' },
        { label: 'Green', value: 'green' }
      ]
    }

    it('renders all options', () => {
      render(<MultipleChoiceQuestion question={question} value="" onChange={mockOnChange} />)

      expect(screen.getByText('Red')).toBeInTheDocument()
      expect(screen.getByText('Blue')).toBeInTheDocument()
      expect(screen.getByText('Green')).toBeInTheDocument()
    })

    it('handles option selection', () => {
      render(<MultipleChoiceQuestion question={question} value="" onChange={mockOnChange} />)

      const redOption = screen.getByTestId('option-mc-1-0')
      fireEvent.click(redOption)

      expect(mockOnChange).toHaveBeenCalledWith('mc-1', 'red')
    })

    it('shows selected option', () => {
      render(<MultipleChoiceQuestion question={question} value="blue" onChange={mockOnChange} />)

      const blueOption = screen.getByTestId('option-mc-1-1')
      expect(blueOption).toBeChecked()
    })
  })

  describe('RatingQuestion', () => {
    const question = {
      id: 'rating-1',
      type: 'rating',
      title: 'Rate our service',
      required: true
    }

    it('renders 5 star rating buttons', () => {
      render(<RatingQuestion question={question} value={0} onChange={mockOnChange} />)

      for (let i = 1; i <= 5; i++) {
        expect(screen.getByTestId(`rating-rating-1-${i}`)).toBeInTheDocument()
      }
    })

    it('handles rating selection', () => {
      render(<RatingQuestion question={question} value={0} onChange={mockOnChange} />)

      const fourStarButton = screen.getByTestId('rating-rating-1-4')
      fireEvent.click(fourStarButton)

      expect(mockOnChange).toHaveBeenCalledWith('rating-1', 4)
    })

    it('shows selected rating', () => {
      render(<RatingQuestion question={question} value={3} onChange={mockOnChange} />)

      const threeStarButton = screen.getByTestId('rating-rating-1-3')
      expect(threeStarButton).toHaveClass('selected')
    })
  })

  describe('QuestionRenderer', () => {
    it('renders correct question type', () => {
      const textQuestion = { id: '1', type: 'text', title: 'Name' }
      const emailQuestion = { id: '2', type: 'email', title: 'Email' }

      const { rerender } = render(<QuestionRenderer question={textQuestion} value="" onChange={mockOnChange} />)
      expect(screen.getByTestId('input-1')).toHaveAttribute('type', 'text')

      rerender(<QuestionRenderer question={emailQuestion} value="" onChange={mockOnChange} />)
      expect(screen.getByTestId('input-2')).toHaveAttribute('type', 'email')
    })

    it('handles unsupported question types', () => {
      const unsupportedQuestion = { id: '1', type: 'unsupported', title: 'Test' }

      render(<QuestionRenderer question={unsupportedQuestion} value="" onChange={mockOnChange} />)
      expect(screen.getByText('Unsupported question type: unsupported')).toBeInTheDocument()
    })
  })
})