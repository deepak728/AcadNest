import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import SearchPage from "./components/SearchPage"; // Search Student Page
import AddStudent from "./components/AddStudent"; // Add Student Page

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<SearchPage />} />
        <Route path="/add-student" element={<AddStudent />} />
      </Routes>
    </Router>
  );
};

export default App;
