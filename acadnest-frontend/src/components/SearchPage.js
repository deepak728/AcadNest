import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";
import StudentList from './StudentList'; 
import './SearchPage.css'; 

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const SearchPage = () => {
  const navigate = useNavigate();
  const [searchQuery, setSearchQuery] = useState('');
  const [students, setStudents] = useState([]);
  const [searched, setSearched] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value);
  };

  const handleSearchClick = async () => {
    if (!searchQuery.trim()) {
      setSearched(false);
      return;
    }

    setLoading(true);
    setSearched(true);
    setError(null);

    try {
      const response = await fetch(`${API_BASE_URL}/people/student/search`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ field: searchQuery.trim() }),
      });

      if (!response.ok) {
        throw new Error(`Error: ${response.statusText}`);
      }

      const data = await response.json();
      setStudents(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      handleSearchClick();
    }
  };

  return (
    <div className="search-page">
      <div className="top-tile">
        <div className="find-people-left">ACADNEST</div>
        <div className="find-people-center">Find People</div>
        <div className="top-buttons">
          <button className="button" onClick={() => navigate("/search")}>Home</button>
          <button className="button" onClick={() => navigate("/add-student")}>Add Student</button>
          <button className="button" onClick={() => navigate("/profile")}>MyProfile</button>
          <button className="button">Logout</button>
        </div>
      </div>

      <div className="search-container">
        <input type="text" className="search-bar" value={searchQuery} onChange={handleSearchChange} onKeyPress={handleKeyPress} placeholder="Search Name, Roll No, Email" />
        <button className="search-button" onClick={handleSearchClick}>Search</button>
      </div>

      <div className="student-grid">
        {loading && <p>Loading students...</p>}
        {error && <p>Error: {error}</p>}
        {searched && !loading && !error && students.length > 0 ? <StudentList students={students} /> : searched && !loading && !error ? <p>No students found.</p> : null}
      </div>
    </div>
  );
};

export default SearchPage;
