import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "./SearchPage.css"; // Reusing the same CSS for consistency

const DEFAULT_PHOTO = "https://i.pinimg.com/originals/5b/89/f1/5b89f121462393c9144af1dfaa3aa85b.jpg"; // Default image

const ProfilePage = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [userDetails, setUserDetails] = useState(location.state?.userDetails || null);

  useEffect(() => {
    if (!userDetails) {
      const token = localStorage.getItem("token");
      if (token) {
        setUserDetails(parseJwt(token)); // Decode JWT
      } else {
        console.error("No JWT found, redirecting to login...");
        navigate("/login");
      }
    }
  }, [userDetails, navigate]);

  const parseJwt = (token) => {
    try {
      const base64Url = token.split(".")[1];
      const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
      return JSON.parse(atob(base64));
    } catch (error) {
      console.error("Error decoding JWT:", error);
      return null;
    }
  };

  if (!userDetails) {
    return <h2>Loading Profile...</h2>;
  }

  return (
    <div className="search-page">
      <div className="top-tile">
        <div className="find-people-left">ACADNEST</div>
        <div className="find-people-center">My Profile</div>
        <div className="top-buttons">
          <button className="button" onClick={() => navigate("/")}>Home</button>
          <button className="button" onClick={() => navigate("/Search")}>Find People</button>
          <button className="button" onClick={() => navigate("/add-student")}>Add Student</button>
          <button className="button">Logout</button>
        </div>
      </div>

      {/* Profile Content */}
      <div style={styles.profileCard}>
        <img
          src={userDetails.photo && userDetails.photo.trim() ? userDetails.photo : DEFAULT_PHOTO}
          alt="Profile"
          onError={(e) => (e.target.src = DEFAULT_PHOTO)} 
          style={styles.profileImage}
        />
        <h2>{userDetails.name || "N/A"}</h2>
        <p><strong>Email:</strong> {userDetails.email || "N/A"}</p>
        <p><strong>Roll No:</strong> {userDetails.rollNo || "N/A"}</p>
        <p><strong>Branch:</strong> {userDetails.branch || "N/A"}</p>
        <p><strong>Year:</strong> {userDetails.year || "N/A"}</p>
        <p><strong>Phone:</strong> {userDetails.phone || "N/A"}</p>
      </div>
    </div>
  );
};

const styles = {
  profileCard: {
    padding: "20px",
    borderRadius: "10px",
    backgroundColor: "white",
    boxShadow: "0px 4px 10px rgba(0,0,0,0.1)",
    textAlign: "center",
    width: "300px",
    margin: "40px auto",
  },
  profileImage: {
    width: "120px",
    height: "120px",
    borderRadius: "50%",
    objectFit: "cover",
    marginBottom: "10px",
  },
};

export default ProfilePage;
