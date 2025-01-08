import React from 'react';

const defaultImage =
  'https://i.pinimg.com/originals/5b/89/f1/5b89f121462393c9144af1dfaa3aa85b.jpg'; // Default photo link

const StudentList = ({ students }) => {
  return (
    <>
      {students.map((student) => (
        <div key={student.id} className="student-tile">
          <img
            src={student.photo || defaultImage} // Use default image if no photo provided
            alt={`${student.name || 'Default'}'s photo`}
            className="student-photo"
            onError={(e) => {
              e.target.onerror = null; // Prevent infinite loop
              e.target.src = defaultImage; // Set default image if the link is invalid
            }}
          />
          <div className="student-info">
            <p>
              <strong>Name:</strong> {student.name}
            </p>
            <p>
              <strong>Roll No:</strong> {student.rollNo}
            </p>
            <p>
              <strong>Branch:</strong> {student.branch}
            </p>
            <p>
              <strong>Year:</strong> {student.year}
            </p>
          </div>
        </div>
      ))}
    </>
  );
};

export default StudentList;
