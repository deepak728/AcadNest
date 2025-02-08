import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LoginPage from "./components/LoginPage";
import SearchPage from "./components/SearchPage";
import AddStudentPage from "./components/AddStudent";
import ProfilePage from "./components/ProfilePage";
import SignUpPage from "./components/SignUpPage";
import OAuthSuccess from "./components/OAuthSuccessPage";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/search" element={<SearchPage />} />
        <Route path="/add-student" element={<AddStudentPage />} />
        <Route path="/profile" element={<ProfilePage />} />
        <Route path="/" element={<LoginPage />} />
        <Route path="/signup" element={<SignUpPage />} />
        <Route path="/oauth-success" element={<OAuthSuccess />} /> 
      </Routes>
    </Router>
  );
}

export default App;
