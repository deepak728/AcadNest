import React from "react";
import { Navigate } from "react-router-dom";

const PrivateRoute = ({ children }) => {
  const token = localStorage.getItem("jwt"); // Check for JWT token

  return token ? children : <Navigate to="/unauthorized" />;
};

export default PrivateRoute;
