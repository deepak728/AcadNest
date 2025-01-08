import React, { useState } from "react";
import "./AddStudent.css";
import { Link } from "react-router-dom";


const branches = ["IT", "CSE", "ECE", "ELECTRICAL", "MECHANICAL", "CIVIL"];
const years = ["FIRST", "SECOND", "THIRD", "FOURTH", "FIFTH"];

const AddStudent = () => {
  const [formData, setFormData] = useState({
    name: "",
    rollNo: "",
    emailId: "",
    branch: "",
    year: "",
    photo: "",
    phoneNo: "",
  });

  const [responseMessage, setResponseMessage] = useState("");
  const [error, setError] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Validate mandatory fields
    if (!formData.name || !formData.rollNo || !formData.branch || !formData.year) {
      setResponseMessage("Please fill in all mandatory fields.");
      return;
    }

    try {
      const response = await fetch("http://localhost:8080/people/student/addStudent", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      if (!response.ok) {
        throw new Error(`Error: ${response.statusText}`);
      }

      const data = await response.json();
      setResponseMessage("Student added successfully!");
      setError(null);

      // Reset form
      setFormData({
        name: "",
        rollNo: "",
        emailId: "",
        branch: "",
        year: "",
        photo: "",
        phoneNo: "",
      });
    } catch (err) {
      setError(err.message);
      setResponseMessage("");
    }
  };

  return (
    <div className="add-student-page">
      {/* Top Tile */}
      <div className="top-tile">
        <div className="find-people">Add Student</div>
        <div className="top-buttons">
          <button className="button">Home</button>
          <Link to="/" className="button">
            Search Student
          </Link>
          <button className="button">MyProfile</button>
          <button className="button">Logout</button>
        </div>
      </div>

      {/* Form Container */}
      <form className="student-form" onSubmit={handleSubmit}>
        <div className="form-field">
          <label>Name*</label>
          <input
            type="text"
            name="name"
            value={formData.name}
            onChange={handleChange}
            placeholder="Enter name"
            required
          />
        </div>
        <div className="form-field">
          <label>Roll No*</label>
          <input
            type="number"
            name="rollNo"
            value={formData.rollNo}
            onChange={handleChange}
            placeholder="Enter roll number"
            required
          />
        </div>
        <div className="form-field">
          <label>Email ID</label>
          <input
            type="email"
            name="emailId"
            value={formData.emailId}
            onChange={handleChange}
            placeholder="Enter email ID"
          />
        </div>
        <div className="form-field">
          <label>Branch*</label>
          <select
            name="branch"
            value={formData.branch}
            onChange={handleChange}
            required
          >
            <option value="">Select branch</option>
            {branches.map((branch) => (
              <option key={branch} value={branch}>
                {branch}
              </option>
            ))}
          </select>
        </div>
        <div className="form-field">
          <label>Year*</label>
          <select
            name="year"
            value={formData.year}
            onChange={handleChange}
            required
          >
            <option value="">Select year</option>
            {years.map((year) => (
              <option key={year} value={year}>
                {year}
              </option>
            ))}
          </select>
        </div>
        <div className="form-field">
          <label>Photo URL</label>
          <input
            type="url"
            name="photo"
            value={formData.photo}
            onChange={handleChange}
            placeholder="Enter photo URL"
          />
        </div>
        <div className="form-field">
          <label>Phone No</label>
          <input
            type="tel"
            name="phoneNo"
            value={formData.phoneNo}
            onChange={handleChange}
            placeholder="Enter phone number"
          />
        </div>
        <button type="submit" className="submit-button">
          Add Student
        </button>
      </form>

      {/* Response Message */}
      {responseMessage && <p className="response-message">{responseMessage}</p>}
      {error && <p className="error-message">Error: {error}</p>}
    </div>
  );
};

export default AddStudent;
