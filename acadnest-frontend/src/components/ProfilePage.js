import React from "react";
import { useNavigate } from "react-router-dom";
import "./SearchPage.css"; // Use the same CSS for consistency

const ProfilePage = () => {
  const navigate = useNavigate();

  // Mock data (Replace this with an API call to fetch user data)
  const user = {
    name: "John Doe",
    rollNo: "12345",
    email: "john@example.com",
    branch: "Computer Science",
    year: "3rd Year",
    photo: "https://via.placeholder.com/100",
    phoneNo: "9876543210",
  };

  return (
    <div className="search-page">
      <div className="top-tile">
        <div className="find-people-left">ACADNEST</div>
        <div className="find-people-center">My Profile</div>
        <div className="top-buttons">
          <button className="button" onClick={() => navigate("/search")}>Search Student</button>
          <button className="button" onClick={() => navigate("/add-student")}>Add Student</button>
          <button className="button">Logout</button>
        </div>
      </div>

      {/* Profile Details */}
      <div className="profile-container">
        <img src={user.photo} alt="Profile" className="profile-photo" />
        <div className="profile-info">
          <p><strong>Name:</strong> {user.name}</p>
          <p><strong>Roll No:</strong> {user.rollNo}</p>
          <p><strong>Email:</strong> {user.email}</p>
          <p><strong>Branch:</strong> {user.branch}</p>
          <p><strong>Year:</strong> {user.year}</p>
          <p><strong>Phone No:</strong> {user.phoneNo}</p>
        </div>
      </div>
    </div>
  );
};

export default ProfilePage;
