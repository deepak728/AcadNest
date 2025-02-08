import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const OAuthSuccess = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const jwt = urlParams.get("jwt");

    if (jwt) {
      console.log("JWT from URL:", jwt);
      localStorage.setItem("token", jwt);

      // Decode JWT to extract user details (optional)
      const userDetails = parseJwt(jwt);
      console.log("User details:", userDetails);

      // Delay navigation slightly to ensure storage completes
      setTimeout(() => {
        navigate("/profile", { state: { userDetails } });
      }, 100);
    } else {
      console.error("JWT token missing in URL");
      navigate("/signup");
    }
  }, []);

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

  return <h2>Redirecting...</h2>;
};

export default OAuthSuccess;
