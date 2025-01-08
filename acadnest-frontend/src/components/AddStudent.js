import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./AddStudent.css";

const AddStudent = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    name: "",
    rollNo: "",
    emailId: "",
    branch: "",
    year: "",
    photo: "",
    phoneNo: "",
  });
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

const handleSubmit = async (e) => {
  e.preventDefault();
  setError(""); 
  setSuccess(""); 

  try {
    const response = await fetch("http://localhost:8080/people/student/addStudent", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(formData),
    });

    if (!response.ok) {
      const contentType = response.headers.get("Content-Type");
      
      if (contentType && contentType.includes("application/json")) {
        // If the response is JSON, parse and use the message from it
        const errorData = await response.json();
        throw new Error(errorData.message || "An error occurred");
      } else {
        // If the response is plain text, read the response as text
        const errorText = await response.text();
        throw new Error(errorText || "An error occurred");
      }
    }

    const data = await response.json();
    setSuccess(data.message || "Student added successfully!");
  } catch (err) {
    setError(err.message);
  }
};



  return (
   <div className="add-student-page">
      {/* Top Tile */}
      <div className="top-tile">
        <div className="find-people-left">ACADNEST</div>
        <div className="find-people-center">Add Student</div>
        <div className="top-buttons">
          <button className="button" onClick={() => navigate("/")}>Home</button>
          <button className="button" onClick={() => navigate("/search")}>Search Student</button>
          <button className="button">MyProfile</button>
          <button className="button">Logout</button>
        </div>
      </div>

      {/* Add Student Form */}
      <div className="form-container">
        <form className="add-student-form" onSubmit={handleSubmit}>
          <input
            type="text"
            name="name"
            placeholder="Name *"
            value={formData.name}
            onChange={handleChange}
            required
          />
          <input
            type="number"
            name="rollNo"
            placeholder="Roll Number *"
            value={formData.rollNo}
            onChange={handleChange}
            required
          />
          <input
            type="email"
            name="emailId"
            placeholder="Email ID"
            value={formData.emailId}
            onChange={handleChange}
          />
          <select name="branch" value={formData.branch} onChange={handleChange} required>
            <option value="">Select Branch *</option>
            <option value="IT">IT</option>
            <option value="CSE">CSE</option>
            <option value="ECE">ECE</option>
            <option value="ELECTRICAL">ELECTRICAL</option>
            <option value="MECHANICAL">MECHANICAL</option>
            <option value="CIVIL">CIVIL</option>
          </select>
          <select name="year" value={formData.year} onChange={handleChange} required>
            <option value="">Select Year *</option>
            <option value="FIRST">FIRST</option>
            <option value="SECOND">SECOND</option>
            <option value="THIRD">THIRD</option>
            <option value="FOURTH">FOURTH</option>
            <option value="FIFTH">FIFTH</option>
          </select>
          <input
            type="text"
            name="photo"
            placeholder="Photo URL"
            value={formData.photo}
            onChange={handleChange}
          />
          <input
            type="text"
            name="phoneNo"
            placeholder="Phone Number"
            value={formData.phoneNo}
            onChange={handleChange}
          />
          <button type="submit" className="submit-button">
            Add Student
          </button>
        </form>

        {/* Feedback Messages */}
        {success && <p className="success-message">{success}</p>}
        {error && <p className="error-message">{error}</p>}
      </div>
    </div>
  );
};

export default AddStudent;
