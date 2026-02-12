import { describe, it, expect, vi, beforeEach } from "vitest";
import { render, screen, fireEvent, waitFor } from "../../test/utils";
import { useState } from "react";

// Mock form builder component
const FormBuilder = ({
  onSave,
  initialForm,
}: {
  onSave?: (form: any) => void;
  initialForm?: any;
}) => {
  const [form, setForm] = useState(
    initialForm || {
      title: "",
      questions: [],
    },
  );

  const addQuestion = (type: string) => {
    const newQuestion = {
      id: Date.now().toString(),
      type,
      title: `New ${type} question`,
      required: false,
    };
    setForm((prev) => ({
      ...prev,
      questions: [...prev.questions, newQuestion],
    }));
  };

  return (
    <div data-testid="form-builder">
      <input
        data-testid="form-title"
        value={form.title}
        onChange={(e) =>
          setForm((prev) => ({ ...prev, title: e.target.value }))
        }
        placeholder="Enter form title"
      />

      <div data-testid="question-list">
        {form.questions.map((question: any) => (
          <div key={question.id} data-testid={`question-${question.id}`}>
            {question.title} ({question.type})
          </div>
        ))}
      </div>

      <div data-testid="question-types">
        <button onClick={() => addQuestion("text")} data-testid="add-text">
          Add Text Question
        </button>
        <button onClick={() => addQuestion("email")} data-testid="add-email">
          Add Email Question
        </button>
        <button
          onClick={() => addQuestion("multiple_choice")}
          data-testid="add-multiple-choice"
        >
          Add Multiple Choice
        </button>
      </div>

      <button
        onClick={() => onSave?.(form)}
        data-testid="save-form"
        disabled={!form.title}
      >
        Save Form
      </button>
    </div>
  );
};

describe("FormBuilder Component", () => {
  const mockOnSave = vi.fn();

  beforeEach(() => {
    vi.clearAllMocks();
  });

  it("renders empty form builder", () => {
    render(<FormBuilder onSave={mockOnSave} />);

    expect(screen.getByTestId("form-builder")).toBeInTheDocument();
    expect(screen.getByTestId("form-title")).toHaveValue("");
    expect(screen.getByTestId("question-list")).toBeEmptyDOMElement();
  });

  it("allows setting form title", async () => {
    render(<FormBuilder onSave={mockOnSave} />);

    const titleInput = screen.getByTestId("form-title");
    fireEvent.change(titleInput, { target: { value: "My Test Form" } });

    expect(titleInput).toHaveValue("My Test Form");
  });

  it("can add different question types", async () => {
    render(<FormBuilder onSave={mockOnSave} />);

    // Add text question
    fireEvent.click(screen.getByTestId("add-text"));
    expect(screen.getByText(/New text question/)).toBeInTheDocument();

    // Add email question
    fireEvent.click(screen.getByTestId("add-email"));
    expect(screen.getByText(/New email question/)).toBeInTheDocument();

    // Add multiple choice question
    fireEvent.click(screen.getByTestId("add-multiple-choice"));
    expect(
      screen.getByText(/New multiple_choice question/),
    ).toBeInTheDocument();
  });

  it("saves form with correct data", async () => {
    render(<FormBuilder onSave={mockOnSave} />);

    // Set title
    const titleInput = screen.getByTestId("form-title");
    fireEvent.change(titleInput, { target: { value: "Test Form" } });

    // Add a question
    fireEvent.click(screen.getByTestId("add-text"));

    // Save form
    fireEvent.click(screen.getByTestId("save-form"));

    await waitFor(() => {
      expect(mockOnSave).toHaveBeenCalledWith({
        title: "Test Form",
        questions: expect.arrayContaining([
          expect.objectContaining({
            type: "text",
            title: "New text question",
          }),
        ]),
      });
    });
  });

  it("disables save button when no title provided", () => {
    render(<FormBuilder onSave={mockOnSave} />);

    const saveButton = screen.getByTestId("save-form");
    expect(saveButton).toBeDisabled();
  });

  it("renders with initial form data", () => {
    const initialForm = {
      title: "Existing Form",
      questions: [
        { id: "1", type: "text", title: "Existing question", required: true },
      ],
    };

    render(<FormBuilder onSave={mockOnSave} initialForm={initialForm} />);

    expect(screen.getByTestId("form-title")).toHaveValue("Existing Form");
    expect(screen.getByText("Existing question (text)")).toBeInTheDocument();
  });
});
