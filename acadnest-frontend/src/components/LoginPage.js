import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const LoginPage = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleLogin = async () => {
    try {
      const response = await axios.post(
        "http://localhost:8080/api-gateway/auth/user/login",
        { emailId: email, password },
        { headers: { "Content-Type": "application/json" } }
      );

      const token = response.data.jwt;
      if (token) {
        // Instead of decoding JWT here, navigate to OAuthSuccess with JWT in URL
        navigate(`/oauth-success?jwt=${token}`);
      }
    } catch (err) {
      if (err.response && err.response.data && err.response.data.error) {
        setError(err.response.data.error); // Display error from API response
      } else {
        setError("Login failed. Please try again."); // Generic error message
      }
    }
  };

  const handleGoogleLogin = () => {
    window.location.href = "http://localhost:8080/api-gateway/oauth2/authorization/google";
  };

  return (
    <div style={styles.container}>
      <h2>Login</h2>

      <input
        type="email"
        placeholder="Email"
        style={styles.input}
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />

      <input
        type="password"
        placeholder="Password"
        style={styles.input}
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />

      {error && <p style={styles.error}>{error}</p>}

      <button style={styles.button} onClick={handleLogin}>
        Login
      </button>

      <button
        style={{ ...styles.button, backgroundColor: "#DB4437" }}
        onClick={handleGoogleLogin}
      >
        Login with Google
      </button>

      <div style={styles.links}>
        <a href="/forgot-password" style={styles.link}>Forgot Password?</a>
        <a href="/signup" style={styles.link}>Sign Up</a>
      </div>
    </div>
  );
};

const styles = {
  container: {
    width: "300px",
    margin: "auto",
    padding: "20px",
    textAlign: "center",
    border: "1px solid #ccc",
    borderRadius: "10px",
    boxShadow: "0px 0px 10px rgba(0, 0, 0, 0.1)",
    marginTop: "100px",
  },
  input: {
    width: "90%",
    padding: "10px",
    margin: "10px 0",
    border: "1px solid #ccc",
    borderRadius: "5px",
    fontSize: "16px",
  },
  button: {
    width: "100%",
    padding: "10px",
    margin: "10px 0",
    backgroundColor: "#007BFF",
    color: "#fff",
    border: "none",
    borderRadius: "5px",
    fontSize: "16px",
    cursor: "pointer",
  },
  error: {
    color: "red",
    fontSize: "14px",
    marginBottom: "10px",
  },
  links: {
    marginTop: "10px",
  },
  link: {
    color: "#007BFF",
    textDecoration: "none",
    margin: "0 10px",
  },
};

export default LoginPage;
