import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const OAuthSuccess = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const jwt = urlParams.get("jwt");

    if (jwt) {
      console.log("JWT from URL:", jwt);
      localStorage.setItem("jwt", jwt); // Store JWT in localStorage

      // Decode JWT to extract user details (optional)
      const userDetails = parseJwt(jwt);
      console.log("User details:", userDetails);

      // Navigate after a small delay to ensure storage completes
      setTimeout(() => {
        navigate("/profile", { state: { userDetails } });
      }, 100);
    } else {
      console.error("JWT token missing in URL");
      navigate("/signup");
    }
  }, [navigate]);

  // Improved JWT Decoder
  const parseJwt = (token) => {
    try {
      const base64Url = token.split(".")[1];
      const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
      const jsonPayload = decodeURIComponent(
        atob(base64)
          .split("")
          .map((c) => "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2))
          .join("")
      );
      return JSON.parse(jsonPayload);
    } catch (error) {
      console.error("Error decoding JWT:", error);
      return null;
    }
  };

  return <h2>Redirecting...</h2>;
};

export default OAuthSuccess;
