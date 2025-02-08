import React from "react";
import { useNavigate } from "react-router-dom";

const LoginPage = () => {
  const navigate = useNavigate();

  const handleLogin = () => {
    // After login, redirect to search page
    navigate("/search");
  };

  return (
    <div style={styles.container}>
      <h2>Login</h2>
      
      {/* Email Input */}
      <input type="email" placeholder="Email" style={styles.input} />

      {/* Password Input */}
      <input type="password" placeholder="Password" style={styles.input} />

      {/* Login Button */}
      <button style={styles.button} onClick={handleLogin}>Login</button>

      {/* Google OAuth Button */}
      <button style={{ ...styles.button, backgroundColor: "#DB4437" }}>
        Login with Google
      </button>

      {/* Links */}
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
