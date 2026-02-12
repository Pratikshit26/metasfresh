import { describe, it, expect, vi, beforeEach } from "vitest";
import { render, screen, fireEvent, waitFor } from "../../test/utils";
import { useState } from "react";

//    form validation utilities
const validateField = (question: any, value: any) => {
  if (question.required && (!value || value.toString().trim() === "")) {
    return "This field is required";
  }

  if (question.type === "email" && value) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(value)) {
      return "Please enter a valid email address";
    }
  }

  if (
    question.type === "text" &&
    question.minLength &&
    value &&
    value.length < question.minLength
  ) {
    return `Minimum ${question.minLength} characters required`;
  }

  if (
    question.type === "text" &&
    question.maxLength &&
    value &&
    value.length > question.maxLength
  ) {
    return `Maximum ${question.maxLength} characters allowed`;
  }

  return null;
};

const validateForm = (questions: any[], answers: Record<string, any>) => {
  const errors: Record<string, string> = {};

  questions.forEach((question) => {
    const error = validateField(question, answers[question.id]);
    if (error) {
      errors[question.id] = error;
    }
  });

  return errors;
};

//    Form Validation Component
const FormValidator = ({
  questions,
  onValidate,
}: {
  questions: any[];
  onValidate: (errors: any, isValid: boolean) => void;
}) => {
  const [answers, setAnswers] = useState<Record<string, any>>({});
  const [errors, setErrors] = useState<Record<string, string>>({});

  const handleAnswerChange = (questionId: string, value: any) => {
    const newAnswers = { ...answers, [questionId]: value };
    setAnswers(newAnswers);

    // Validate on change
    const newErrors = validateForm(questions, newAnswers);
    setErrors(newErrors);

    const isValid = Object.keys(newErrors).length === 0;
    onValidate(newErrors, isValid);
  };

  const handleSubmit = () => {
    const formErrors = validateForm(questions, answers);
    setErrors(formErrors);
    const isValid = Object.keys(formErrors).length === 0;
    onValidate(formErrors, isValid);
  };

  return (
    <div data-testid="form-validator">
      {questions.map((question) => (
        <div key={question.id} data-testid={`field-${question.id}`}>
          <label>
            {question.title}
            {question.required && " *"}
          </label>
          <input
            type={question.type}
            value={answers[question.id] || ""}
            onChange={(e) => handleAnswerChange(question.id, e.target.value)}
            data-testid={`input-${question.id}`}
          />
          {errors[question.id] && (
            <span data-testid={`error-${question.id}`} className="error">
              {errors[question.id]}
            </span>
          )}
        </div>
      ))}
      <button onClick={handleSubmit} data-testid="submit-button">
        Submit
      </button>
    </div>
  );
};

describe("Form Validation", () => {
  const mockOnValidate = vi.fn();

  beforeEach(() => {
    vi.clearAllMocks();
  });

  describe("Field Validation", () => {
    it("validates required fields", () => {
      const question = { id: "1", type: "text", title: "Name", required: true };
      const error = validateField(question, "");

      expect(error).toBe("This field is required");
    });

    it("validates email format", () => {
      const question = {
        id: "1",
        type: "email",
        title: "Email",
        required: true,
      };

      expect(validateField(question, "invalid-email")).toBe(
        "Please enter a valid email address",
      );
      expect(validateField(question, "valid@email.com")).toBeNull();
    });

    it("validates minimum length", () => {
      const question = {
        id: "1",
        type: "text",
        title: "Password",
        required: true,
        minLength: 8,
      };

      expect(validateField(question, "short")).toBe(
        "Minimum 8 characters required",
      );
      expect(validateField(question, "longenough")).toBeNull();
    });

    it("validates maximum length", () => {
      const question = {
        id: "1",
        type: "text",
        title: "Bio",
        required: false,
        maxLength: 100,
      };
      const longText = "a".repeat(101);

      expect(validateField(question, longText)).toBe(
        "Maximum 100 characters allowed",
      );
      expect(validateField(question, "short text")).toBeNull();
    });

    it("allows empty optional fields", () => {
      const question = {
        id: "1",
        type: "text",
        title: "Optional",
        required: false,
      };

      expect(validateField(question, "")).toBeNull();
      expect(validateField(question, null)).toBeNull();
      expect(validateField(question, undefined)).toBeNull();
    });
  });

  describe("Form Validation", () => {
    const questions = [
      { id: "1", type: "text", title: "Name", required: true },
      { id: "2", type: "email", title: "Email", required: true },
      { id: "3", type: "text", title: "Phone", required: false },
    ];

    it("validates entire form and returns errors", () => {
      const answers = {
        "1": "",
        "2": "invalid-email",
        "3": "optional-value",
      };

      const errors = validateForm(questions, answers);

      expect(errors["1"]).toBe("This field is required");
      expect(errors["2"]).toBe("Please enter a valid email address");
      expect(errors["3"]).toBeUndefined();
    });

    it("returns empty errors for valid form", () => {
      const answers = {
        "1": "John Doe",
        "2": "john@example.com",
        "3": "123-456-7890",
      };

      const errors = validateForm(questions, answers);
      expect(Object.keys(errors)).toHaveLength(0);
    });

    it("handles missing answers for optional fields", () => {
      const answers = {
        "1": "John Doe",
        "2": "john@example.com",
        // '3' is missing but optional
      };

      const errors = validateForm(questions, answers);
      expect(Object.keys(errors)).toHaveLength(0);
    });
  });

  describe("FormValidator Component", () => {
    const questions = [
      { id: "1", type: "text", title: "Name", required: true },
      { id: "2", type: "email", title: "Email", required: true },
    ];

    it("renders form fields", () => {
      render(
        <FormValidator questions={questions} onValidate={mockOnValidate} />,
      );

      expect(screen.getByText("Name *")).toBeInTheDocument();
      expect(screen.getByText("Email *")).toBeInTheDocument();
      expect(screen.getByTestId("submit-button")).toBeInTheDocument();
    });

    it("validates on field change", async () => {
      render(
        <FormValidator questions={questions} onValidate={mockOnValidate} />,
      );

      const nameInput = screen.getByTestId("input-1");
      fireEvent.change(nameInput, { target: { value: "John" } });

      await waitFor(() => {
        expect(mockOnValidate).toHaveBeenCalled();
      });
    });

    it("shows validation errors", async () => {
      render(
        <FormValidator questions={questions} onValidate={mockOnValidate} />,
      );

      const submitButton = screen.getByTestId("submit-button");
      fireEvent.click(submitButton);

      await waitFor(() => {
        expect(screen.getByTestId("error-1")).toHaveTextContent(
          "This field is required",
        );
        expect(screen.getByTestId("error-2")).toHaveTextContent(
          "This field is required",
        );
      });
    });

    it("validates email format in real-time", async () => {
      render(
        <FormValidator questions={questions} onValidate={mockOnValidate} />,
      );

      const emailInput = screen.getByTestId("input-2");
      fireEvent.change(emailInput, { target: { value: "invalid-email" } });

      await waitFor(() => {
        expect(screen.getByTestId("error-2")).toHaveTextContent(
          "Please enter a valid email address",
        );
      });
    });

    it("clears errors when valid input provided", async () => {
      render(
        <FormValidator questions={questions} onValidate={mockOnValidate} />,
      );

      // First trigger error
      const submitButton = screen.getByTestId("submit-button");
      fireEvent.click(submitButton);

      await waitFor(() => {
        expect(screen.getByTestId("error-1")).toBeInTheDocument();
      });

      // Then provide valid input
      const nameInput = screen.getByTestId("input-1");
      fireEvent.change(nameInput, { target: { value: "John Doe" } });

      await waitFor(() => {
        expect(screen.queryByTestId("error-1")).not.toBeInTheDocument();
      });
    });

    it("calls onValidate with correct parameters", async () => {
      render(
        <FormValidator questions={questions} onValidate={mockOnValidate} />,
      );

      const nameInput = screen.getByTestId("input-1");
      const emailInput = screen.getByTestId("input-2");

      fireEvent.change(nameInput, { target: { value: "John Doe" } });
      fireEvent.change(emailInput, { target: { value: "john@example.com" } });

      await waitFor(() => {
        expect(mockOnValidate).toHaveBeenCalledWith({}, true);
      });
    });
  });
});
