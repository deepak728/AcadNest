import React from 'react';

const StudentList = ({ students }) => {
  return (
    <div className="student-grid">
      {students.map((student) => (
        <div key={student.id} className="student-tile">
          <img
            src={student.photo}
            alt={`${student.name}'s photo`}
            className="student-photo"
          />
          <div className="student-info">
            <p><strong>Name:</strong> {student.name}</p>
            <p><strong>Roll No:</strong> {student.rollNo}</p>
            <p><strong>Branch:</strong> {student.branch}</p>
            <p><strong>Year:</strong> {student.year}</p>
          </div>
        </div>
      ))}
    </div>
  );
};

export default StudentList;