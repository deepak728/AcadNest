import React from 'react';

const defaultImage =
  'https://as1.ftcdn.net/jpg/00/64/67/52/1000_F_64675209_7ve2XQANuzuHjMZXP3aIYIpsDKEbF5dD.jpg'; // Default photo link

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
