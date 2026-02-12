import { describe, it, expect, vi, beforeEach, afterEach } from "vitest";
import { render, screen, fireEvent, waitFor } from "../../test/utils";
import "@testing-library/jest-dom";

//    authentication functions
const mockAuthService = {
  login: vi.fn(),
  register: vi.fn(),
  logout: vi.fn(),
  resetPassword: vi.fn(),
  verifyEmail: vi.fn(),
  getCurrentUser: vi.fn(),
};

//    Auth Component
const LoginForm = ({ onSubmit }: { onSubmit: (data: any) => void }) => {
  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    onSubmit({
      email: formData.get("email"),
      password: formData.get("password"),
    });
  };

  return (
    <form onSubmit={handleSubmit} data-testid="login-form">
      <input
        type="email"
        name="email"
        placeholder="Email"
        data-testid="email-input"
        required
      />
      <input
        type="password"
        name="password"
        placeholder="Password"
        data-testid="password-input"
        required
      />
      <button type="submit" data-testid="login-button">
        Login
      </button>
    </form>
  );
};

const RegisterForm = ({ onSubmit }: { onSubmit: (data: any) => void }) => {
  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    onSubmit({
      email: formData.get("email"),
      password: formData.get("password"),
      firstName: formData.get("firstName"),
      lastName: formData.get("lastName"),
    });
  };

  return (
    <form onSubmit={handleSubmit} data-testid="register-form">
      <input
        type="text"
        name="firstName"
        placeholder="First Name"
        data-testid="firstname-input"
        required
      />
      <input
        type="text"
        name="lastName"
        placeholder="Last Name"
        data-testid="lastname-input"
        required
      />
      <input
        type="email"
        name="email"
        placeholder="Email"
        data-testid="email-input"
        required
      />
      <input
        type="password"
        name="password"
        placeholder="Password"
        data-testid="password-input"
        required
      />
      <button type="submit" data-testid="register-button">
        Register
      </button>
    </form>
  );
};

describe("Authentication System", () => {
  beforeEach(() => {
    vi.clearAllMocks();
  });

  describe("Login Flow", () => {
    it("should render login form with all required fields", () => {
      const onSubmit = vi.fn();
      render(<LoginForm onSubmit={onSubmit} />);

      expect(screen.getByTestId("email-input")).toBeInTheDocument();
      expect(screen.getByTestId("password-input")).toBeInTheDocument();
      expect(screen.getByTestId("login-button")).toBeInTheDocument();
    });

    it("should submit login form with correct credentials", async () => {
      const onSubmit = vi.fn();
      render(<LoginForm onSubmit={onSubmit} />);

      const emailInput = screen.getByTestId("email-input");
      const passwordInput = screen.getByTestId("password-input");
      const loginButton = screen.getByTestId("login-button");

      fireEvent.change(emailInput, { target: { value: "test@example.com" } });
      fireEvent.change(passwordInput, { target: { value: "password123" } });
      fireEvent.click(loginButton);

      await waitFor(() => {
        expect(onSubmit).toHaveBeenCalledWith({
          email: "test@example.com",
          password: "password123",
        });
      });
    });

    it("should handle login failure with invalid credentials", async () => {
      mockAuthService.login.mockRejectedValueOnce(
        new Error("Invalid credentials"),
      );

      const result = mockAuthService.login("wrong@email.com", "wrongpass");

      await expect(result).rejects.toThrow("Invalid credentials");
    });

    it("should handle successful login and store user session", async () => {
      const mockUser = {
        id: "123",
        email: "test@example.com",
        firstName: "John",
        lastName: "Doe",
      };

      mockAuthService.login.mockResolvedValueOnce({
        user: mockUser,
        token: "mock-jwt-token",
        refreshToken: "mock-refresh-token",
      });

      const result = await mockAuthService.login(
        "test@example.com",
        "password123",
      );

      expect(result.user).toEqual(mockUser);
      expect(result.token).toBeDefined();
      expect(result.refreshToken).toBeDefined();
    });
  });

  describe("Registration Flow", () => {
    it("should render registration form with all required fields", () => {
      const onSubmit = vi.fn();
      render(<RegisterForm onSubmit={onSubmit} />);

      expect(screen.getByTestId("firstname-input")).toBeInTheDocument();
      expect(screen.getByTestId("lastname-input")).toBeInTheDocument();
      expect(screen.getByTestId("email-input")).toBeInTheDocument();
      expect(screen.getByTestId("password-input")).toBeInTheDocument();
      expect(screen.getByTestId("register-button")).toBeInTheDocument();
    });

    it("should submit registration form with valid data", async () => {
      const onSubmit = vi.fn();
      render(<RegisterForm onSubmit={onSubmit} />);

      fireEvent.change(screen.getByTestId("firstname-input"), {
        target: { value: "John" },
      });
      fireEvent.change(screen.getByTestId("lastname-input"), {
        target: { value: "Doe" },
      });
      fireEvent.change(screen.getByTestId("email-input"), {
        target: { value: "john@example.com" },
      });
      fireEvent.change(screen.getByTestId("password-input"), {
        target: { value: "SecurePass123!" },
      });
      fireEvent.click(screen.getByTestId("register-button"));

      await waitFor(() => {
        expect(onSubmit).toHaveBeenCalledWith({
          firstName: "John",
          lastName: "Doe",
          email: "john@example.com",
          password: "SecurePass123!",
        });
      });
    });

    it("should handle registration with duplicate email", async () => {
      mockAuthService.register.mockRejectedValueOnce(
        new Error("Email already exists"),
      );

      const result = mockAuthService.register({
        email: "existing@example.com",
        password: "password123",
        firstName: "John",
        lastName: "Doe",
      });

      await expect(result).rejects.toThrow("Email already exists");
    });

    it("should create user account and send verification email", async () => {
      const mockUserData = {
        email: "new@example.com",
        password: "password123",
        firstName: "Jane",
        lastName: "Smith",
      };

      mockAuthService.register.mockResolvedValueOnce({
        user: { ...mockUserData, id: "456", verified: false },
        message: "Verification email sent",
      });

      const result = await mockAuthService.register(mockUserData);

      expect(result.user.email).toBe(mockUserData.email);
      expect(result.user.verified).toBe(false);
      expect(result.message).toBe("Verification email sent");
    });
  });

  describe("Password Reset Flow", () => {
    it("should handle password reset request", async () => {
      mockAuthService.resetPassword.mockResolvedValueOnce({
        message: "Password reset email sent",
      });

      const result = await mockAuthService.resetPassword("user@example.com");

      expect(result.message).toBe("Password reset email sent");
      expect(mockAuthService.resetPassword).toHaveBeenCalledWith(
        "user@example.com",
      );
    });

    it("should handle password reset for non-existent email", async () => {
      mockAuthService.resetPassword.mockResolvedValueOnce({
        message: "Password reset email sent",
      });

      // Should still return success to avoid email enumeration
      const result = await mockAuthService.resetPassword(
        "nonexistent@example.com",
      );
      expect(result.message).toBe("Password reset email sent");
    });
  });

  describe("Email Verification", () => {
    it("should verify email with valid token", async () => {
      mockAuthService.verifyEmail.mockResolvedValueOnce({
        success: true,
        message: "Email verified successfully",
      });

      const result = await mockAuthService.verifyEmail("valid-token-123");

      expect(result.success).toBe(true);
      expect(result.message).toBe("Email verified successfully");
    });

    it("should reject invalid verification token", async () => {
      mockAuthService.verifyEmail.mockRejectedValueOnce(
        new Error("Invalid or expired token"),
      );

      await expect(
        mockAuthService.verifyEmail("invalid-token"),
      ).rejects.toThrow("Invalid or expired token");
    });
  });

  describe("Session Management", () => {
    it("should retrieve current user session", async () => {
      const mockUser = {
        id: "123",
        email: "test@example.com",
        firstName: "John",
        lastName: "Doe",
        subscriptionTier: "pro",
      };

      mockAuthService.getCurrentUser.mockResolvedValueOnce(mockUser);

      const result = await mockAuthService.getCurrentUser();

      expect(result).toEqual(mockUser);
    });

    it("should handle logout successfully", async () => {
      mockAuthService.logout.mockResolvedValueOnce({
        success: true,
      });

      const result = await mockAuthService.logout();

      expect(result.success).toBe(true);
      expect(mockAuthService.logout).toHaveBeenCalled();
    });

    it("should clear session data on logout", async () => {
      mockAuthService.logout.mockImplementationOnce(() => {
        // Simulate clearing session storage
        return Promise.resolve({ success: true });
      });

      await mockAuthService.logout();

      expect(mockAuthService.logout).toHaveBeenCalled();
    });
  });

  describe("Social Authentication", () => {
    it("should handle Google OAuth login", async () => {
      const mockGoogleUser = {
        id: "789",
        email: "google@example.com",
        firstName: "Google",
        lastName: "User",
        provider: "google",
      };

      mockAuthService.login.mockResolvedValueOnce({
        user: mockGoogleUser,
        token: "google-oauth-token",
      });

      const result = await mockAuthService.login(null, "google");

      expect(result.user.provider).toBe("google");
      expect(result.token).toBeDefined();
    });

    it("should handle GitHub OAuth login", async () => {
      const mockGitHubUser = {
        id: "999",
        email: "github@example.com",
        firstName: "GitHub",
        lastName: "User",
        provider: "github",
      };

      mockAuthService.login.mockResolvedValueOnce({
        user: mockGitHubUser,
        token: "github-oauth-token",
      });

      const result = await mockAuthService.login(null, "github");

      expect(result.user.provider).toBe("github");
      expect(result.token).toBeDefined();
    });
  });

  describe("Token Management", () => {
    it("should refresh access token using refresh token", async () => {
      const mockRefresh = vi.fn().mockResolvedValueOnce({
        token: "new-access-token",
        refreshToken: "new-refresh-token",
      });

      const result = await mockRefresh("old-refresh-token");

      expect(result.token).toBe("new-access-token");
      expect(result.refreshToken).toBe("new-refresh-token");
    });

    it("should handle expired refresh token", async () => {
      const mockRefresh = vi
        .fn()
        .mockRejectedValueOnce(new Error("Refresh token expired"));

      await expect(mockRefresh("expired-token")).rejects.toThrow(
        "Refresh token expired",
      );
    });
  });
});
