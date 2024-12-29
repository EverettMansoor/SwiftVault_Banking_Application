import React, { useEffect, useState } from "react";
import "./Pages.css";
import PageLayout from "../layouts/PageLayout";
import { useAuth } from "../context/Auth";
import axios from "axios";

const Account = () => {
  const [auth, setAuth] = useAuth();
  const [user, setUser] = useState();

useEffect(() => {
  const fetchUser = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/user/get_by_id/${auth.user.id}`,
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      const data = response.data;

      if (data.success) {
        setUser(data.object);
        setAuth({
          ...auth,
          user: data.object,
        });
      }
    } catch (error) {
      console.log("Error fetching user:", error);
    }
  };

  fetchUser();
  console.log(user);
  
}, []);
  return (
    <PageLayout>
      <div>
        <div className="account-page-container">
          {/* User Profile Section */}
          <div className="user-profile-section">
            <img src="https://via.placeholder.com/120" alt="User Image" />
            <div className="user-details">
              <h2>{auth?.user?.firstName} {auth?.user?.lastName}</h2>
              <p>
                <strong>Username:</strong> {auth?.user?.userName}
              </p>
              <p>
                <strong>Email:</strong> {auth?.user?.email}
              </p>
              <p>
                <strong>Phone:</strong> +1 234 567 890
              </p>
            </div>
          </div>

          {/* Personal Details Section */}
          <div className="personal-details-section">
            <h3>Personal Information</h3>
            <p>
              <strong>Date of Birth:</strong> January 1, 1990
            </p>
            <p>
              <strong>Gender:</strong> {auth?.user?.gender}
            </p>
            <p>
              <strong>Address:</strong> {auth?.user?.address}
            </p>
            <p>
              <strong>Nationality:</strong> American
            </p>
          </div>

          {/* Account Information Section */}
          <div className="account-info-section">
            <h3>Account Information</h3>
            <p>
              <strong>Account Type:</strong> {auth?.user?.account.accountType}
            </p>
            <p>
              <strong>Account Number:</strong> 123-456-789
            </p>
            <p>
              <strong>Account Balance:</strong> ${auth?.user?.account.balance}
            </p>
            <p>
              <strong>Interest Rate:</strong> {auth?.user?.account.interestRate}%
            </p>
          </div>
        </div>
      </div>
    </PageLayout>
  );
};

export default Account;
