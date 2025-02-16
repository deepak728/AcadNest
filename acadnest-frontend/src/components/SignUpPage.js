import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const SignUpPage = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleSignUp = async () => {
    try {
      const response = await axios.post(
        "http://localhost:8080/api-gateway/auth/user/register",
        { emailId: email, password },
        {
          headers: { "Content-Type": "application/json" },
          withCredentials: true, // Ensures cookies like JSESSIONID are sent
        }
      );

      const token = response.data.jwt;
      if (token) {
        navigate(`/oauth-success?jwt=${token}`);
      }
    } catch (err) {
      if (err.response && err.response.data && err.response.data.error) {
        setError(err.response.data.error); // Display error from API response
      } else {
        setError("Signup failed. Please try again."); // Generic error message
      }
    }
  };

  const handleGoogleSignUp = () => {
    window.location.href = "http://localhost:8080/api-gateway/oauth2/authorization/google";
  };

  return (
    <div style={styles.container}>
      <h2>Sign Up</h2>

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

      {error && <p style={styles.error}>{error}</p>} {/* Display API error */}

      <button style={styles.button} onClick={handleSignUp}>
        Sign Up
      </button>

      <button
        style={{ ...styles.button, backgroundColor: "#DB4437" }}
        onClick={handleGoogleSignUp}
      >
        Sign Up with Google
      </button>

      <div style={styles.links}>
        <span style={styles.link} onClick={() => navigate("/login")}>
          Already have an account? Login
        </span>
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
    cursor: "pointer",
  },
};

export default SignUpPage;
