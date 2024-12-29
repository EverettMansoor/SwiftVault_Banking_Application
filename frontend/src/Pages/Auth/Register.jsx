import React, { useState } from "react";
import "./Auth.css";
import axios from "axios";
import AuthLayout from "../../layouts/AuthLayout";
import { useNavigate } from "react-router-dom";

const Register = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    address: "",
    password: "",
    confirmPassword: "",
    gender: "",
  });

  const [loading, setLoading] = useState(false);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log("Submitting form with data:", formData);
    setLoading(true);
    
    // Create JSON object from form data
    if(formData.password != formData.confirmPassword){
      alert("Password doesnot matchig")
      return null;
    }
    const jsonData = {
      firstName: formData.firstName,
      lastName: formData.lastName,
      email: formData.email,
      address: formData.address,
      gender: formData.gender,
      password: formData.password,
    };

    try {
      const response = await axios.post(
        "http://localhost:8080/user/register",
        jsonData, 
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      const data = response.data;
      console.log("Response data:", data);

      if (data.success) {
        alert("Successfully Registered");
        navigate("/auth/login");
      }
    } catch (error) {
      console.log("Error:", error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <AuthLayout>
      <div className="form-container">
        <h2 className="form-title">Registration</h2>
        <form id="registrationForm" onSubmit={handleSubmit}>
          <div className="form-group">
            <input
              type="text"
              id="firstName"
              name="firstName"
              placeholder=" "
              value={formData.firstName}
              onChange={handleInputChange}
              required
            />
            <label htmlFor="firstName">First Name</label>
          </div>
          <div className="form-group">
            <input
              type="text"
              id="lastName"
              name="lastName"
              placeholder=" "
              value={formData.lastName}
              onChange={handleInputChange}
              required
            />
            <label htmlFor="lastName">Last Name</label>
          </div>
          <div className="form-group">
            <input
              type="email"
              id="email"
              name="email"
              placeholder=" "
              value={formData.email}
              onChange={handleInputChange}
              required
            />
            <label htmlFor="email">Email</label>
          </div>
          <div className="form-group">
            <input
              type="text"
              id="address"
              name="address"
              placeholder=" "
              value={formData.address}
              onChange={handleInputChange}
              required
            />
            <label htmlFor="address">Address</label>
          </div>
          {/* Gender field is now required */}
          <div className="form-group">
            <select
              id="gender"
              name="gender"
              value={formData.gender}
              onChange={handleInputChange}
              required
            >
              <option value="">Select Gender</option>
              <option value="male">Male</option>
              <option value="female">Female</option>
              <option value="other">Other</option>
            </select>
            <label htmlFor="gender">Gender</label>
          </div>
          <div className="form-group">
            <input
              type="password"
              id="password"
              name="password"
              placeholder=" "
              value={formData.password}
              onChange={handleInputChange}
              required
            />
            <label htmlFor="password">Password</label>
          </div>
          {/* Confirm Password field is not required */}
          <div className="form-group">
            <input
              type="password"
              id="confirmPassword"
              name="confirmPassword"
              placeholder=" "
              value={formData.confirmPassword}
              onChange={handleInputChange}
            />
            <label htmlFor="confirmPassword">Confirm Password</label>
          </div>
          <button type="submit" className="submit-btn">
            Submit
          </button>
        </form>
        <div className="form-footer" id="formFooter">
          Already have an account? <a href="#">Login here</a>
        </div>
      </div>
    </AuthLayout>
  );
};

export default Register;
