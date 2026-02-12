import { describe, it, expect, beforeEach } from "vitest";

interface LogicCondition {
  questionId: string;
  operator:
    | "equals"
    | "not_equals"
    | "contains"
    | "greater_than"
    | "less_than"
    | "is_empty"
    | "is_not_empty";
  value: any;
}

interface LogicAction {
  type: "show" | "hide" | "jump_to" | "end_form";
  target: string;
}

interface ConditionalLogic {
  conditions: LogicCondition[];
  actions: LogicAction[];
  logicType: "AND" | "OR";
}

interface FormResponses {
  [key: string]: any;
}

interface FormContext {
  currentQuestionId: string;
  visibleQuestions: Set<string>;
  jumpToQuestion?: (questionId: string) => void;
  endForm?: () => void;
}

class LogicEngine {
  evaluateCondition(
    condition: LogicCondition,
    responses: FormResponses,
  ): boolean {
    const responseValue = responses[condition.questionId];

    switch (condition.operator) {
      case "equals":
        return responseValue === condition.value;

      case "not_equals":
        return responseValue !== condition.value;

      case "contains":
        if (typeof responseValue === "string") {
          return responseValue.includes(condition.value);
        }
        if (Array.isArray(responseValue)) {
          return responseValue.includes(condition.value);
        }
        return false;

      case "greater_than":
        return Number(responseValue) > Number(condition.value);

      case "less_than":
        return Number(responseValue) < Number(condition.value);

      case "is_empty":
        return (
          !responseValue ||
          responseValue === "" ||
          (Array.isArray(responseValue) && responseValue.length === 0)
        );

      case "is_not_empty":
        return (
          !!responseValue &&
          responseValue !== "" &&
          (!Array.isArray(responseValue) || responseValue.length > 0)
        );

      default:
        return false;
    }
  }

  evaluateConditions(
    logic: ConditionalLogic,
    responses: FormResponses,
  ): boolean {
    if (logic.logicType === "AND") {
      return logic.conditions.every((condition) =>
        this.evaluateCondition(condition, responses),
      );
    } else {
      return logic.conditions.some((condition) =>
        this.evaluateCondition(condition, responses),
      );
    }
  }

  executeAction(action: LogicAction, context: FormContext): void {
    switch (action.type) {
      case "show":
        context.visibleQuestions.add(action.target);
        break;

      case "hide":
        context.visibleQuestions.delete(action.target);
        break;

      case "jump_to":
        if (context.jumpToQuestion) {
          context.jumpToQuestion(action.target);
        }
        break;

      case "end_form":
        if (context.endForm) {
          context.endForm();
        }
        break;
    }
  }

  executeActions(actions: LogicAction[], context: FormContext): void {
    actions.forEach((action) => this.executeAction(action, context));
  }

  applyLogic(
    logic: ConditionalLogic,
    responses: FormResponses,
    context: FormContext,
  ): void {
    if (this.evaluateConditions(logic, responses)) {
      this.executeActions(logic.actions, context);
    }
  }
}

describe("Logic Engine - Conditional Logic System", () => {
  let engine: LogicEngine;
  let mockContext: FormContext;

  beforeEach(() => {
    engine = new LogicEngine();
    mockContext = {
      currentQuestionId: "q1",
      visibleQuestions: new Set(["q1", "q2", "q3"]),
      jumpToQuestion: (id: string) => {
        mockContext.currentQuestionId = id;
      },
      endForm: () => {
        mockContext.currentQuestionId = "end";
      },
    };
  });

  describe("Condition Evaluation - Equals Operator", () => {
    it("should return true when values are equal", () => {
      const condition: LogicCondition = {
        questionId: "q1",
        operator: "equals",
        value: "yes",
      };

      const responses: FormResponses = {
        q1: "yes",
      };

      const result = engine.evaluateCondition(condition, responses);
      expect(result).toBe(true);
    });

    it("should return false when values are not equal", () => {
      const condition: LogicCondition = {
        questionId: "q1",
        operator: "equals",
        value: "yes",
      };

      const responses: FormResponses = {
        q1: "no",
      };

      const result = engine.evaluateCondition(condition, responses);
      expect(result).toBe(false);
    });

    it("should handle numeric equality", () => {
      const condition: LogicCondition = {
        questionId: "age",
        operator: "equals",
        value: 25,
      };

      const responses: FormResponses = {
        age: 25,
      };

      expect(engine.evaluateCondition(condition, responses)).toBe(true);
    });
  });

  describe("Condition Evaluation - Not Equals Operator", () => {
    it("should return true when values are not equal", () => {
      const condition: LogicCondition = {
        questionId: "q1",
        operator: "not_equals",
        value: "no",
      };

      const responses: FormResponses = {
        q1: "yes",
      };

      expect(engine.evaluateCondition(condition, responses)).toBe(true);
    });

    it("should return false when values are equal", () => {
      const condition: LogicCondition = {
        questionId: "q1",
        operator: "not_equals",
        value: "yes",
      };

      const responses: FormResponses = {
        q1: "yes",
      };

      expect(engine.evaluateCondition(condition, responses)).toBe(false);
    });
  });

  describe("Condition Evaluation - Contains Operator", () => {
    it("should check if string contains substring", () => {
      const condition: LogicCondition = {
        questionId: "feedback",
        operator: "contains",
        value: "great",
      };

      const responses: FormResponses = {
        feedback: "This is a great product",
      };

      expect(engine.evaluateCondition(condition, responses)).toBe(true);
    });

    it("should check if array contains value", () => {
      const condition: LogicCondition = {
        questionId: "interests",
        operator: "contains",
        value: "coding",
      };

      const responses: FormResponses = {
        interests: ["coding", "design", "testing"],
      };

      expect(engine.evaluateCondition(condition, responses)).toBe(true);
    });

    it("should return false if array does not contain value", () => {
      const condition: LogicCondition = {
        questionId: "interests",
        operator: "contains",
        value: "cooking",
      };

      const responses: FormResponses = {
        interests: ["coding", "design", "testing"],
      };

      expect(engine.evaluateCondition(condition, responses)).toBe(false);
    });
  });

  describe("Condition Evaluation - Comparison Operators", () => {
    it("should evaluate greater_than condition", () => {
      const condition: LogicCondition = {
        questionId: "age",
        operator: "greater_than",
        value: 18,
      };

      const responses: FormResponses = {
        age: 25,
      };

      expect(engine.evaluateCondition(condition, responses)).toBe(true);
    });

    it("should evaluate less_than condition", () => {
      const condition: LogicCondition = {
        questionId: "score",
        operator: "less_than",
        value: 50,
      };

      const responses: FormResponses = {
        score: 30,
      };

      expect(engine.evaluateCondition(condition, responses)).toBe(true);
    });

    it("should handle edge case for equal values with greater_than", () => {
      const condition: LogicCondition = {
        questionId: "age",
        operator: "greater_than",
        value: 18,
      };

      const responses: FormResponses = {
        age: 18,
      };

      expect(engine.evaluateCondition(condition, responses)).toBe(false);
    });
  });

  describe("Condition Evaluation - Empty Operators", () => {
    it("should detect empty string", () => {
      const condition: LogicCondition = {
        questionId: "optional",
        operator: "is_empty",
        value: null,
      };

      const responses: FormResponses = {
        optional: "",
      };

      expect(engine.evaluateCondition(condition, responses)).toBe(true);
    });

    it("should detect empty array", () => {
      const condition: LogicCondition = {
        questionId: "selections",
        operator: "is_empty",
        value: null,
      };

      const responses: FormResponses = {
        selections: [],
      };

      expect(engine.evaluateCondition(condition, responses)).toBe(true);
    });

    it("should detect non-empty value", () => {
      const condition: LogicCondition = {
        questionId: "name",
        operator: "is_not_empty",
        value: null,
      };

      const responses: FormResponses = {
        name: "John Doe",
      };

      expect(engine.evaluateCondition(condition, responses)).toBe(true);
    });
  });

  describe("Multiple Conditions - AND Logic", () => {
    it("should return true when all AND conditions are met", () => {
      const logic: ConditionalLogic = {
        logicType: "AND",
        conditions: [
          { questionId: "age", operator: "greater_than", value: 18 },
          { questionId: "country", operator: "equals", value: "USA" },
        ],
        actions: [],
      };

      const responses: FormResponses = {
        age: 25,
        country: "USA",
      };

      expect(engine.evaluateConditions(logic, responses)).toBe(true);
    });

    it("should return false when any AND condition fails", () => {
      const logic: ConditionalLogic = {
        logicType: "AND",
        conditions: [
          { questionId: "age", operator: "greater_than", value: 18 },
          { questionId: "country", operator: "equals", value: "USA" },
        ],
        actions: [],
      };

      const responses: FormResponses = {
        age: 16,
        country: "USA",
      };

      expect(engine.evaluateConditions(logic, responses)).toBe(false);
    });
  });

  describe("Multiple Conditions - OR Logic", () => {
    it("should return true when any OR condition is met", () => {
      const logic: ConditionalLogic = {
        logicType: "OR",
        conditions: [
          { questionId: "role", operator: "equals", value: "admin" },
          { questionId: "role", operator: "equals", value: "moderator" },
        ],
        actions: [],
      };

      const responses: FormResponses = {
        role: "moderator",
      };

      expect(engine.evaluateConditions(logic, responses)).toBe(true);
    });

    it("should return false when no OR conditions are met", () => {
      const logic: ConditionalLogic = {
        logicType: "OR",
        conditions: [
          { questionId: "role", operator: "equals", value: "admin" },
          { questionId: "role", operator: "equals", value: "moderator" },
        ],
        actions: [],
      };

      const responses: FormResponses = {
        role: "user",
      };

      expect(engine.evaluateConditions(logic, responses)).toBe(false);
    });
  });

  describe("Action Execution - Show/Hide", () => {
    it("should show hidden question", () => {
      mockContext.visibleQuestions.delete("q4");

      const action: LogicAction = {
        type: "show",
        target: "q4",
      };

      engine.executeAction(action, mockContext);

      expect(mockContext.visibleQuestions.has("q4")).toBe(true);
    });

    it("should hide visible question", () => {
      const action: LogicAction = {
        type: "hide",
        target: "q2",
      };

      engine.executeAction(action, mockContext);

      expect(mockContext.visibleQuestions.has("q2")).toBe(false);
    });
  });

  describe("Action Execution - Jump To", () => {
    it("should jump to specified question", () => {
      const action: LogicAction = {
        type: "jump_to",
        target: "q5",
      };

      engine.executeAction(action, mockContext);

      expect(mockContext.currentQuestionId).toBe("q5");
    });

    it("should skip intermediate questions when jumping", () => {
      mockContext.currentQuestionId = "q1";

      const action: LogicAction = {
        type: "jump_to",
        target: "q10",
      };

      engine.executeAction(action, mockContext);

      expect(mockContext.currentQuestionId).toBe("q10");
    });
  });

  describe("Action Execution - End Form", () => {
    it("should end form early", () => {
      const action: LogicAction = {
        type: "end_form",
        target: "",
      };

      engine.executeAction(action, mockContext);

      expect(mockContext.currentQuestionId).toBe("end");
    });
  });

  describe("Complete Logic Flow", () => {
    it("should apply logic when conditions are met", () => {
      const logic: ConditionalLogic = {
        logicType: "AND",
        conditions: [
          { questionId: "hasExperience", operator: "equals", value: "yes" },
        ],
        actions: [{ type: "show", target: "experienceYears" }],
      };

      mockContext.visibleQuestions.delete("experienceYears");

      const responses: FormResponses = {
        hasExperience: "yes",
      };

      engine.applyLogic(logic, responses, mockContext);

      expect(mockContext.visibleQuestions.has("experienceYears")).toBe(true);
    });

    it("should not apply logic when conditions are not met", () => {
      const logic: ConditionalLogic = {
        logicType: "AND",
        conditions: [
          { questionId: "hasExperience", operator: "equals", value: "yes" },
        ],
        actions: [{ type: "show", target: "experienceYears" }],
      };

      mockContext.visibleQuestions.delete("experienceYears");

      const responses: FormResponses = {
        hasExperience: "no",
      };

      engine.applyLogic(logic, responses, mockContext);

      expect(mockContext.visibleQuestions.has("experienceYears")).toBe(false);
    });

    it("should handle complex branching logic", () => {
      const logic: ConditionalLogic = {
        logicType: "AND",
        conditions: [
          { questionId: "age", operator: "less_than", value: 18 },
          { questionId: "country", operator: "equals", value: "USA" },
        ],
        actions: [
          { type: "jump_to", target: "parentalConsent" },
          { type: "hide", target: "paymentInfo" },
        ],
      };

      const responses: FormResponses = {
        age: 16,
        country: "USA",
      };

      engine.applyLogic(logic, responses, mockContext);

      expect(mockContext.currentQuestionId).toBe("parentalConsent");
      expect(mockContext.visibleQuestions.has("paymentInfo")).toBe(false);
    });
  });
});
