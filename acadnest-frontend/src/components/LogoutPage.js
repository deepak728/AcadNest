import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const LogoutPage = () => {
  const navigate = useNavigate();

  useEffect(() => {
    localStorage.removeItem("jwt");

    setTimeout(() => {
      navigate("/login");
    }, 1000); // Redirect after 1 second
  }, [navigate]);

  return (
    <div style={styles.logoutContainer}>
      <h2>Logging out...</h2>
    </div>
  );
};

const styles = {
  logoutContainer: {
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    justifyContent: "center",
    height: "100vh",
    fontSize: "20px",
  },
};

export default LogoutPage;
