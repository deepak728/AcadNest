import React from "react";
import { useNavigate } from "react-router-dom";

const SignUpPage = () => {
  const navigate = useNavigate();

  return (
    <div style={styles.container}>
      <h2>Sign Up</h2>
      
      {/* Email Input */}
      <input type="email" placeholder="Email" style={styles.input} />

      {/* Password Input */}
      <input type="password" placeholder="Password" style={styles.input} />

      {/* Sign Up Button */}
      <button style={styles.button}>Sign Up</button>

      {/* Google OAuth Sign Up Button */}
      <button style={{ ...styles.button, backgroundColor: "#DB4437" }}>
        Sign Up with Google
      </button>

      {/* Links */}
      <div style={styles.links}>
        <span style={styles.link} onClick={() => navigate("/login")}>Already have an account? Login</span>
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
    cursor: "pointer",
  },
};

export default SignUpPage;
