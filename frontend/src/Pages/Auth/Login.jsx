import React, { useState } from "react";
import "./Auth.css";
import axios from "axios";
import AuthLayout from "../../layouts/AuthLayout";
import { useAuth } from "../../context/Auth";
import { useNavigate } from "react-router-dom";

const API_BASE_URL = "http://localhost:8080";

const Login = () => {
  const navigate = useNavigate();
  const [auth, setAuth] = useAuth();

  const [formData, setFormData] = useState({ email: "", password: "" });
  const [loading, setLoading] = useState(false);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const response = await axios.post(`${API_BASE_URL}/user/login`, formData, {
        headers: { "Content-Type": "application/json" },
      });
      const data = response.data;

      if (data.success) {
        setAuth({ user: data.object, account: null });
        localStorage.setItem(
          "auth",
          JSON.stringify({ user: data.object, account: null })
        );

        setFormData({ email: "", password: "" });
        navigate("/create");
      }
    } catch (error) {
      alert(error.response?.data?.message || "An error occurred. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <AuthLayout>
      <div className="form-container">
        <h2 className="form-title">User Login</h2>
        <form id="loginForm" onSubmit={handleSubmit}>
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
          <button type="submit" className="submit-btn" disabled={loading}>
            {loading ? "Logging in..." : "Submit"}
          </button>
        </form>
        <div className="form-footer">
          Don't have an account? <a href="/auth/register">Register here</a>
        </div>
      </div>
    </AuthLayout>
  );
};

export default Login;
