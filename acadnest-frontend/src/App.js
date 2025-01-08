import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import SearchPage from "./components/SearchPage"; // Your search page component
import AddStudentPage from "./components/AddStudent"; // Your add student page component

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/search" element={<SearchPage />} />   {/* Search Page route */}
        <Route path="/add-student" element={<AddStudentPage />} />  {/* Add Student Page route */}
        <Route path="/" element={<SearchPage />} />  {/* Default route */}
      </Routes>
    </Router>
  );
}

export default App;
