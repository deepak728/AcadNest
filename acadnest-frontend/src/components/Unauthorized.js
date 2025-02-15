import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const Unauthorized = () => {
    const navigate = useNavigate();

    useEffect(() => {
        console.log("Unauthorized page loaded. Redirecting in 3 seconds...");
        
        // Redirect to login after 3 seconds
        const timer = setTimeout(() => {
            navigate("/login");
        }, 3000);

        return () => clearTimeout(timer); // Cleanup timer
    }, [navigate]);

    return (
        <div style={{
            textAlign: "center", 
            marginTop: "50px", 
            backgroundColor: "#f8d7da", 
            padding: "20px",
            borderRadius: "8px",
            width: "50%",
            margin: "50px auto",
            boxShadow: "0px 4px 10px rgba(0, 0, 0, 0.1)"
        }}>
            <h1 style={{ color: "#721c24" }}>Unauthorized Access</h1>
            <p style={{ color: "#721c24" }}>You are not authorized to view this page.</p>
            <p style={{ fontWeight: "bold" }}>Redirecting to login...</p>
        </div>
    );
};

export default Unauthorized;
