import { describe, it, expect, vi, beforeEach } from "vitest";
import { render, screen, fireEvent, waitFor } from "../../test/utils";
import { createMockForm, createMockUser } from "../../test/utils";

// Mock Analytics Service
class AnalyticsService {
  private events: any[] = [];

  track(event: string, properties: Record<string, any>) {
    this.events.push({
      event,
      properties,
      timestamp: Date.now(),
    });
  }

  getEvents() {
    return this.events;
  }

  calculateMetrics(formId: string) {
    const formEvents = this.events.filter(
      (e) => e.properties.formId === formId,
    );

    const views = formEvents.filter((e) => e.event === "form_view").length;
    const starts = formEvents.filter((e) => e.event === "form_start").length;
    const completions = formEvents.filter(
      (e) => e.event === "form_complete",
    ).length;
    const abandons = formEvents.filter(
      (e) => e.event === "form_abandon",
    ).length;

    const completionRate = starts > 0 ? (completions / starts) * 100 : 0;

    const completedForms = formEvents.filter(
      (e) => e.event === "form_complete",
    );
    const totalTime = completedForms.reduce(
      (sum, e) => sum + (e.properties.completionTime || 0),
      0,
    );
    const averageTime = completions > 0 ? totalTime / completions : 0;

    return {
      views,
      starts,
      completions,
      abandons,
      completionRate: Math.round(completionRate * 100) / 100,
      averageTime: Math.round(averageTime),
    };
  }

  getDropOffPoints(formId: string) {
    const questionAnswers = this.events.filter(
      (e) => e.event === "question_answer" && e.properties.formId === formId,
    );

    const abandons = this.events.filter(
      (e) => e.event === "form_abandon" && e.properties.formId === formId,
    );

    const dropOffData: Record<string, number> = {};

    abandons.forEach((abandon) => {
      const questionId = abandon.properties.lastQuestionId;
      dropOffData[questionId] = (dropOffData[questionId] || 0) + 1;
    });

    return Object.entries(dropOffData).map(([questionId, count]) => ({
      questionId,
      dropRate: count,
    }));
  }
}

// Mock Analytics Dashboard Component
const AnalyticsDashboard = ({
  formId,
  analyticsService,
}: {
  formId: string;
  analyticsService: AnalyticsService;
}) => {
  const [metrics, setMetrics] = useState<any>(null);
  const [dropOffPoints, setDropOffPoints] = useState<any[]>([]);

  useEffect(() => {
    const formMetrics = analyticsService.calculateMetrics(formId);
    const dropOff = analyticsService.getDropOffPoints(formId);

    setMetrics(formMetrics);
    setDropOffPoints(dropOff);
  }, [formId, analyticsService]);

  if (!metrics) return <div>Loading...</div>;

  return (
    <div data-testid="analytics-dashboard">
      <div data-testid="metrics">
        <div data-testid="views-metric">Views: {metrics.views}</div>
        <div data-testid="starts-metric">Starts: {metrics.starts}</div>
        <div data-testid="completions-metric">
          Completions: {metrics.completions}
        </div>
        <div data-testid="completion-rate-metric">
          Completion Rate: {metrics.completionRate}%
        </div>
        <div data-testid="average-time-metric">
          Average Time: {metrics.averageTime}s
        </div>
      </div>

      <div data-testid="drop-off-analysis">
        <h3>Drop-off Points</h3>
        {dropOffPoints.map((point, index) => (
          <div key={index} data-testid={`drop-off-${point.questionId}`}>
            Question {point.questionId}: {point.dropRate} abandons
          </div>
        ))}
      </div>
    </div>
  );
};

// Mock Form Response Tracker
const FormResponseTracker = ({ formId, questions, analyticsService }: any) => {
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [answers, setAnswers] = useState<Record<string, any>>({});
  const [hasStarted, setHasStarted] = useState(false);

  useEffect(() => {
    // Track form view
    analyticsService.track("form_view", { formId });
  }, [formId, analyticsService]);

  const handleStart = () => {
    setHasStarted(true);
    analyticsService.track("form_start", {
      formId,
      respondentId: "test-respondent",
    });
  };

  const handleAnswer = (questionId: string, value: any) => {
    const newAnswers = { ...answers, [questionId]: value };
    setAnswers(newAnswers);

    analyticsService.track("question_answer", {
      formId,
      questionId,
      value,
      questionIndex: currentQuestionIndex,
    });
  };

  const handleNext = () => {
    if (currentQuestionIndex < questions.length - 1) {
      setCurrentQuestionIndex(currentQuestionIndex + 1);
    }
  };

  const handleComplete = () => {
    const completionTime = Math.floor(Math.random() * 300) + 60; // Mock completion time
    analyticsService.track("form_complete", {
      formId,
      completionTime,
      totalQuestions: questions.length,
      answeredQuestions: Object.keys(answers).length,
    });
  };

  const handleAbandon = () => {
    const currentQuestion = questions[currentQuestionIndex];
    analyticsService.track("form_abandon", {
      formId,
      lastQuestionId: currentQuestion?.id,
      questionIndex: currentQuestionIndex,
      answeredQuestions: Object.keys(answers).length,
    });
  };

  const currentQuestion = questions[currentQuestionIndex];

  return (
    <div data-testid="form-response-tracker">
      {!hasStarted ? (
        <div>
          <h2>Welcome to the Form</h2>
          <button onClick={handleStart} data-testid="start-button">
            Start Form
          </button>
        </div>
      ) : (
        <div>
          <div data-testid="progress">
            Question {currentQuestionIndex + 1} of {questions.length}
          </div>

          {currentQuestion && (
            <div data-testid={`current-question-${currentQuestion.id}`}>
              <label>{currentQuestion.title}</label>
              <input
                type="text"
                onChange={(e) =>
                  handleAnswer(currentQuestion.id, e.target.value)
                }
                data-testid={`input-${currentQuestion.id}`}
              />
            </div>
          )}

          <div>
            {currentQuestionIndex < questions.length - 1 ? (
              <button onClick={handleNext} data-testid="next-button">
                Next
              </button>
            ) : (
              <button onClick={handleComplete} data-testid="complete-button">
                Complete
              </button>
            )}

            <button onClick={handleAbandon} data-testid="abandon-button">
              Exit
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

describe("Analytics & Metrics", () => {
  let analyticsService: AnalyticsService;

  beforeEach(() => {
    analyticsService = new AnalyticsService();
  });

  describe("AnalyticsService", () => {
    it("tracks events correctly", () => {
      analyticsService.track("form_view", { formId: "test-form" });
      analyticsService.track("form_start", {
        formId: "test-form",
        respondentId: "user1",
      });

      const events = analyticsService.getEvents();
      expect(events).toHaveLength(2);
      expect(events[0].event).toBe("form_view");
      expect(events[1].event).toBe("form_start");
    });

    it("calculates basic metrics correctly", () => {
      const formId = "test-form";

      // Simulate form interactions
      analyticsService.track("form_view", { formId });
      analyticsService.track("form_view", { formId });
      analyticsService.track("form_start", { formId, respondentId: "user1" });
      analyticsService.track("form_start", { formId, respondentId: "user2" });
      analyticsService.track("form_complete", {
        formId,
        respondentId: "user1",
        completionTime: 120,
      });

      const metrics = analyticsService.calculateMetrics(formId);

      expect(metrics.views).toBe(2);
      expect(metrics.starts).toBe(2);
      expect(metrics.completions).toBe(1);
      expect(metrics.completionRate).toBe(50);
      expect(metrics.averageTime).toBe(120);
    });

    it("handles zero division in completion rate", () => {
      const formId = "test-form";
      analyticsService.track("form_view", { formId });

      const metrics = analyticsService.calculateMetrics(formId);
      expect(metrics.completionRate).toBe(0);
    });

    it("calculates drop-off points", () => {
      const formId = "test-form";

      analyticsService.track("form_abandon", { formId, lastQuestionId: "q1" });
      analyticsService.track("form_abandon", { formId, lastQuestionId: "q1" });
      analyticsService.track("form_abandon", { formId, lastQuestionId: "q2" });

      const dropOffPoints = analyticsService.getDropOffPoints(formId);

      expect(dropOffPoints).toHaveLength(2);
      expect(dropOffPoints.find((p) => p.questionId === "q1")?.dropRate).toBe(
        2,
      );
      expect(dropOffPoints.find((p) => p.questionId === "q2")?.dropRate).toBe(
        1,
      );
    });
  });

  describe("AnalyticsDashboard Component", () => {
    it("displays metrics correctly", async () => {
      const formId = "test-form";
      analyticsService.track("form_view", { formId });
      analyticsService.track("form_start", { formId, respondentId: "user1" });
      analyticsService.track("form_complete", { formId, completionTime: 180 });

      render(
        <AnalyticsDashboard
          formId={formId}
          analyticsService={analyticsService}
        />,
      );

      await waitFor(() => {
        expect(screen.getByTestId("views-metric")).toHaveTextContent(
          "Views: 1",
        );
        expect(screen.getByTestId("starts-metric")).toHaveTextContent(
          "Starts: 1",
        );
        expect(screen.getByTestId("completions-metric")).toHaveTextContent(
          "Completions: 1",
        );
        expect(screen.getByTestId("completion-rate-metric")).toHaveTextContent(
          "Completion Rate: 100%",
        );
        expect(screen.getByTestId("average-time-metric")).toHaveTextContent(
          "Average Time: 180s",
        );
      });
    });

    it("shows drop-off analysis", async () => {
      const formId = "test-form";
      analyticsService.track("form_abandon", {
        formId,
        lastQuestionId: "question-1",
      });
      analyticsService.track("form_abandon", {
        formId,
        lastQuestionId: "question-2",
      });

      render(
        <AnalyticsDashboard
          formId={formId}
          analyticsService={analyticsService}
        />,
      );

      await waitFor(() => {
        expect(screen.getByText("Drop-off Points")).toBeInTheDocument();
        expect(screen.getByTestId("drop-off-question-1")).toHaveTextContent(
          "Question question-1: 1 abandons",
        );
        expect(screen.getByTestId("drop-off-question-2")).toHaveTextContent(
          "Question question-2: 1 abandons",
        );
      });
    });
  });

  describe("FormResponseTracker Component", () => {
    const mockQuestions = [
      { id: "q1", title: "What is your name?", type: "text" },
      { id: "q2", title: "What is your email?", type: "email" },
      { id: "q3", title: "How did you hear about us?", type: "text" },
    ];

    it("tracks form view on mount", () => {
      const formId = "test-form";
      render(
        <FormResponseTracker
          formId={formId}
          questions={mockQuestions}
          analyticsService={analyticsService}
        />,
      );

      const events = analyticsService.getEvents();
      expect(events.find((e) => e.event === "form_view")).toBeTruthy();
    });

    it("tracks form start when user begins", async () => {
      const formId = "test-form";
      render(
        <FormResponseTracker
          formId={formId}
          questions={mockQuestions}
          analyticsService={analyticsService}
        />,
      );

      const startButton = screen.getByTestId("start-button");
      fireEvent.click(startButton);

      const events = analyticsService.getEvents();
      expect(events.find((e) => e.event === "form_start")).toBeTruthy();
    });

    it("tracks question answers", async () => {
      const formId = "test-form";
      render(
        <FormResponseTracker
          formId={formId}
          questions={mockQuestions}
          analyticsService={analyticsService}
        />,
      );

      // Start form
      fireEvent.click(screen.getByTestId("start-button"));

      // Answer first question
      const input = screen.getByTestId("input-q1");
      fireEvent.change(input, { target: { value: "John Doe" } });

      const events = analyticsService.getEvents();
      const answerEvent = events.find((e) => e.event === "question_answer");
      expect(answerEvent).toBeTruthy();
      expect(answerEvent?.properties.questionId).toBe("q1");
      expect(answerEvent?.properties.value).toBe("John Doe");
    });

    it("tracks form completion", async () => {
      const formId = "test-form";
      render(
        <FormResponseTracker
          formId={formId}
          questions={mockQuestions}
          analyticsService={analyticsService}
        />,
      );

      // Navigate to last question
      fireEvent.click(screen.getByTestId("start-button"));
      fireEvent.click(screen.getByTestId("next-button"));
      fireEvent.click(screen.getByTestId("next-button"));

      // Complete form
      const completeButton = screen.getByTestId("complete-button");
      fireEvent.click(completeButton);

      const events = analyticsService.getEvents();
      expect(events.find((e) => e.event === "form_complete")).toBeTruthy();
    });

    it("tracks form abandonment", async () => {
      const formId = "test-form";
      render(
        <FormResponseTracker
          formId={formId}
          questions={mockQuestions}
          analyticsService={analyticsService}
        />,
      );

      fireEvent.click(screen.getByTestId("start-button"));
      fireEvent.click(screen.getByTestId("abandon-button"));

      const events = analyticsService.getEvents();
      const abandonEvent = events.find((e) => e.event === "form_abandon");
      expect(abandonEvent).toBeTruthy();
      expect(abandonEvent?.properties.lastQuestionId).toBe("q1");
    });

    it("shows progress correctly", async () => {
      const formId = "test-form";
      render(
        <FormResponseTracker
          formId={formId}
          questions={mockQuestions}
          analyticsService={analyticsService}
        />,
      );

      fireEvent.click(screen.getByTestId("start-button"));

      expect(screen.getByTestId("progress")).toHaveTextContent(
        "Question 1 of 3",
      );

      fireEvent.click(screen.getByTestId("next-button"));
      expect(screen.getByTestId("progress")).toHaveTextContent(
        "Question 2 of 3",
      );
    });
  });
});
