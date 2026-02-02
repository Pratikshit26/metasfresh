import { describe, it, expect, vi } from 'vitest'
import { render, screen, fireEvent } from '../test/utils'

// Mock Button component for testing
const Button = ({ children, onClick, disabled = false, variant = 'primary' }: {
  children: React.ReactNode
  onClick?: () => void
  disabled?: boolean
  variant?: 'primary' | 'secondary'
}) => (
  <button
    onClick={onClick}
    disabled={disabled}
    className={`btn btn-${variant}`}
    data-testid="button"
  >
    {children}
  </button>
)

describe('Button Component', () => {
  it('renders with correct text', () => {
    render(<Button>Click me</Button>)
    
    expect(screen.getByText('Click me')).toBeInTheDocument()
    expect(screen.getByTestId('button')).toHaveClass('btn-primary')
  })

  it('handles click events', () => {
    const handleClick = vi.fn()
    render(<Button onClick={handleClick}>Click me</Button>)
    
    const button = screen.getByTestId('button')
    fireEvent.click(button)
    
    expect(handleClick).toHaveBeenCalledTimes(1)
  })

  it('can be disabled', () => {
    render(<Button disabled>Disabled button</Button>)
    
    const button = screen.getByTestId('button')
    expect(button).toBeDisabled()
  })

  it('supports different variants', () => {
    render(<Button variant="secondary">Secondary button</Button>)
    
    const button = screen.getByTestId('button')
    expect(button).toHaveClass('btn-secondary')
  })

  it('handles keyboard events', async () => {
    const handleClick = vi.fn()
    render(<Button onClick={handleClick}>Press me</Button>)
    
    const button = screen.getByTestId('button')
    button.focus()
    expect(button).toHaveFocus()
    
    // Simulate Enter key press
    fireEvent.keyDown(button, { key: 'Enter', code: 'Enter' })
    // Note: You'd need to implement enter key handling in your actual component
  })
})