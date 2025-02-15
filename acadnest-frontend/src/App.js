import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LoginPage from "./components/LoginPage";
import SearchPage from "./components/SearchPage";
import AddStudentPage from "./components/AddStudent";
import ProfilePage from "./components/ProfilePage";
import SignUpPage from "./components/SignUpPage";
import OAuthSuccess from "./components/OAuthSuccessPage";
import Unauthorized from "./components/Unauthorized";
import PrivateRoute from "./components/PrivateRoute";
import LogoutPage from "./components/LogoutPage";


function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/" element={<LoginPage />} />
        <Route path="/signup" element={<SignUpPage />} />
        <Route path="/oauth-success" element={<OAuthSuccess />} /> 
        <Route path="/unauthorized" element={<Unauthorized />} />
        <Route
          path="/search"
          element={
            <PrivateRoute>
              <SearchPage />
            </PrivateRoute>
          }
        />
        <Route
          path="/add-student"
          element={
            <PrivateRoute>
              <AddStudentPage />
            </PrivateRoute>
          }
        />
        <Route
          path="/profile"
          element={
            <PrivateRoute>
              <ProfilePage />
            </PrivateRoute>
          }
        />
        <Route
          path="/logout"
          element={
            <PrivateRoute>
              <LogoutPage />
            </PrivateRoute>
          }
        />
      </Routes>
    </Router>
  );
}

export default App;
