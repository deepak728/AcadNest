import React, { useState } from 'react';
import './SearchPage.css'; // Import the CSS file for styling

const SearchPage = () => {
  const [searchQuery, setSearchQuery] = useState('');
  const [searched, setSearched] = useState(false);

  // Mock student data
  const students = [
    { id: 1, name: 'John Doe', rollno: 'A001', email: 'john.doe@example.com' },
    { id: 2, name: 'Jane Smith', rollno: 'A002', email: 'jane.smith@example.com' },
    { id: 3, name: 'Sam John', rollno: 'A003', email: 'sam.wilson@example.com' },
    { id: 4, name: 'Lisa Brown', rollno: 'A004', email: 'lisa.brown@example.com' },
    { id: 5, name: 'Tom White', rollno: 'A005', email: 'tom.white@example.com' },
    { id: 6, name: 'Emma Green', rollno: 'A006', email: 'emma.green@example.com' },
    { id: 7, name: 'Oliver Black', rollno: 'A007', email: 'oliver.black@example.com' },
    { id: 8, name: 'Sophia John', rollno: 'A008', email: 'sophia.blue@example.com' },
    { id: 9, name: 'James Red', rollno: 'A009', email: 'james.red@example.com' },
    { id: 10, name: 'Mia Gray', rollno: 'A010', email: 'mia.gray@example.com' },
  ];
  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value);
  };

  const handleSearchClick = () => {
    setSearched(true); // Mark that search has been performed
  };

  // Filter students based on the search query (case insensitive)
  const filteredStudents = students.filter(student =>
    student.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
    student.rollno.toLowerCase().includes(searchQuery.toLowerCase()) ||
    student.email.toLowerCase().includes(searchQuery.toLowerCase())
  );

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

      {/* Grid of students */}
      <div className="student-grid">
        {searched && filteredStudents.length > 0 ? (
          filteredStudents.map((student, index) => (
            <div key={student.id} className="student-tile">
              <h3>{student.name}</h3>
              <p>Roll No: {student.rollno}</p>
              <p>Email: {student.email}</p>
            </div>
          ))
        ) : searched ? (
          <p></p>
        ) : null}
      </div>
    </div>
  );
};

export default SearchPage;