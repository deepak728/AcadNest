import React from "react";

const ForgotPasswordPage = () => {
  return (
    <div style={styles.container}>
      <h2>Forgot Password?</h2>
      
      {/* Image */}
      <img
        src="https://i.pinimg.com/1200x/c8/91/41/c89141249ec76948bc5691aacae2c3c6.jpg"
        alt="Lazy Developer Meme"
        style={styles.image}
      />

      {/* Message */}
      <p style={styles.message}>
        Developer was too lazy to create this feature.  <br />
        <b>Talk to him. 🤷‍♂️</b>
      </p>
    </div>
  );
};

const styles = {
  container: {
    width: "400px",
    margin: "auto",
    padding: "20px",
    textAlign: "center",
    border: "1px solid #ccc",
    borderRadius: "10px",
    boxShadow: "0px 0px 10px rgba(0, 0, 0, 0.1)",
    marginTop: "100px",
  },
  image: {
    width: "100%",
    borderRadius: "10px",
    marginBottom: "10px",
  },
  message: {
    fontSize: "16px",
    fontWeight: "bold",
  },
};

export default ForgotPasswordPage;
