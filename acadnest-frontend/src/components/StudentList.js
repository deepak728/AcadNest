// src/components/StudentList.js

import React from 'react';

const StudentList = ({ students }) => {
  return (
    <div>
      <h2>Search Results</h2>
      <ul>
        {students.map((student) => (
          <li key={student.id}>
            <p><strong>Name:</strong> {student.name}</p>
            <p><strong>Email:</strong> {student.email}</p>
            <p><strong>Roll Number:</strong> {student.rollNumber}</p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default StudentList;
