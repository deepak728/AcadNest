import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";

const ProfilePage = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [userDetails, setUserDetails] = useState(location.state?.userDetails || null);

  useEffect(() => {
    if (!userDetails) {
      // Try fetching from localStorage
      const token = localStorage.getItem("token");
      if (token) {
        setUserDetails(parseJwt(token)); // Decode JWT
      } else {
        console.error("No JWT found, redirecting to login...");
        navigate("/login"); // Redirect if no token
      }
    }
  }, [userDetails, navigate]);

  useEffect(() => {
    console.log("UserDetails:", userDetails);
}, [userDetails]);


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

  if (!userDetails) {
    return <h2>Loading Profile...</h2>;
  }

  return (
    <div>
      <h2>Welcome, {userDetails.sub}</h2>
      <p>Email: {userDetails.sub}</p>
      <p>Role: {userDetails.role}</p>
    </div>
  );
};

export default ProfilePage;
