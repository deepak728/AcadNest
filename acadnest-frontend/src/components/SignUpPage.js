import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const SignUpPage = () => {
  const navigate = useNavigate();

  const handleGoogleSignUp = () => {
    // Redirect to the backend OAuth2 endpoint
    window.location.href = "http://localhost:8081/oauth2/authorization/google";
  };

  // Handle OAuth2 success response
  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const jwt = urlParams.get("jwt");

    if (jwt) {
      // Store JWT in localStorage
      localStorage.setItem("token", jwt);

      // Decode JWT to get user details (optional)
      const userDetails = parseJwt(jwt);
      console.log("User details:", userDetails);

      // Redirect to profile page with user details
      navigate("/profile", { state: { userDetails } });
    }
  }, []);

  // Function to decode JWT (Basic parsing, use a library like jwt-decode in production)
  const parseJwt = (token) => {
    try {
      const base64Url = token.split(".")[1];
      const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
      return JSON.parse(atob(base64));
    } catch (error) {
      console.error("Error decoding JWT:", error);
      return null;
    }
  };

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
      <button
        style={{ ...styles.button, backgroundColor: "#DB4437" }}
        onClick={handleGoogleSignUp}
      >
        Sign Up with Google
      </button>

      {/* Links */}
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
