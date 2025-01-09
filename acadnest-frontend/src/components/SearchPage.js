import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";
import StudentList from './StudentList'; // Import the StudentList component
import './SearchPage.css'; // Import the CSS file for styling

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
      console.log('Sending request to backend...');
      const response = await fetch('http://ec2-13-233-136-208.ap-south-1.compute.amazonaws.com:8080/people/student/search', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ field: searchQuery.trim() }),
      });

      console.log('Response status:', response.status);

      if (!response.ok) {
        throw new Error(`Error: ${response.statusText}`);
      }

      const data = await response.json();
      console.log('Data received:', data);
      setStudents(data);
    } catch (err) {
      console.error('Error:', err);
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      handleSearchClick(); // Trigger search on Enter key
    }
  };

  return (
    <div className="search-page">
      {/* Top tile with ACADNEST, Find People, and buttons */}
      <div className="top-tile">
        <div className="find-people-left">ACADNEST</div> {/* Left-aligned ACADNEST */}
        <div className="find-people-center">Find People</div> {/* Center-aligned Find People */}
        <div className="top-buttons">
          <button className="button">Home</button>
          <button className="button" onClick={() => navigate("/add-student")}>
            Add Student
          </button>
          <button className="button">MyProfile</button>
          <button className="button">Logout</button>
        </div>
      </div>

      {/* Search Bar */}
      <div className="search-container">
        <input
          type="text"
          className="search-bar"
          value={searchQuery}
          onChange={handleSearchChange}
          onKeyPress={handleKeyPress} // Add Enter key listener
          placeholder="Search Name, Roll No, Email"
        />
        <button className="search-button" onClick={handleSearchClick}>
          Search
        </button>
      </div>

      {/* Display loading, error, or student tiles */}
     <div className="student-grid">
        {loading && <p>Loading students...</p>}
        {error && <p>Error: {error}</p>}
        {searched && !loading && !error && students.length > 0 ? (
          <StudentList students={students} />
        ) : searched && !loading && !error ? (
          <p>No students found.</p>
        ) : null}
      </div>
    </div>
  );
};

export default SearchPage;