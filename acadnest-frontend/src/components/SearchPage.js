import React, { useState } from 'react';
import './SearchPage.css'; // Import the CSS file for styling

const SearchPage = () => {
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
      const response = await fetch('https://81ddfa0a-a23a-466f-b66d-cf6f700d7ccf.mock.pstmn.io/search', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ query: searchQuery.trim() }), // Sending search query in the request body
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

  return (
    <div className="search-page">
      {/* Tile with Find People and buttons */}
      <div className="top-tile">
        <div className="find-people">Find People</div>
        <div className="top-buttons">
          <button className="button">Home</button>
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
          students.map((student, index) => (
            <div key={student.id} className="student-tile">
              <h3>{student.name}</h3>
              <p>Roll No: {student.rollno}</p>
              <p>Email: {student.email}</p>
            </div>
          ))
        ) : searched && !loading && !error ? (
          <p>No students found.</p>
        ) : null}
      </div>
    </div>
  );
};

export default SearchPage;
