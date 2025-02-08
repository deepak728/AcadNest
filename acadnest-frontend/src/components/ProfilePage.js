import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";

function ProfilePage() {
  const location = useLocation();
  const params = new URLSearchParams(location.search);
  const email = params.get("email");

  const [user, setUser] = useState(null);

  useEffect(() => {
    // Simulate API call to fetch user info
    async function fetchUserInfo() {
      try {
        console.log(`Fetching user info for ${email}`);
        // Replace this with actual API call
        const userData = {
          name: "John Doe",
          rollNo: "123456",
          email: email || "johndoe@example.com",
          branch: "Computer Science",
          year: "3rd Year",
          photo: "https://via.placeholder.com/100",
          phone: "9876543210",
        };
        setUser(userData);
      } catch (error) {
        console.error("Failed to fetch user info", error);
      }
    }

    fetchUserInfo();
  }, [email]);

  if (!user) return <h2>Loading profile...</h2>;

  return (
    <div style={styles.container}>
      <h2>Profile</h2>
      <img src={user.photo} alt="Profile" style={styles.image} />
      <p><strong>Name:</strong> {user.name}</p>
      <p><strong>Roll No:</strong> {user.rollNo}</p>
      <p><strong>Email:</strong> {user.email}</p>
      <p><strong>Branch:</strong> {user.branch}</p>
      <p><strong>Year:</strong> {user.year}</p>
      <p><strong>Phone:</strong> {user.phone}</p>
    </div>
  );
}

const styles = {
  container: { textAlign: "center", padding: "20px" },
  image: { borderRadius: "50%", width: "100px", height: "100px", marginBottom: "10px" },
};

export default ProfilePage;
