import React, { useState } from "react";
import AuthLayout from "../layouts/AuthLayout";
import { useAuth } from "../context/Auth";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const API_BASE_URL = "http://localhost:8080";

function CreateBankAccount() {
  const [auth, setAuth] = useAuth();
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const fetchUser = async (updatedAccount) => {
    try {
      const response = await axios.get(
        `${API_BASE_URL}/user/get_by_id/${auth?.user?.id}`,
        {
          headers: { "Content-Type": "application/json" },
        }
      );
      const data = response.data;

      if (data.success) {
        const updatedAuth = {
          user: data.object,
          account: updatedAccount || null,
        };

        setAuth(updatedAuth);
        localStorage.setItem("auth", JSON.stringify(updatedAuth));
      }
    } catch (error) {
      console.error("Error fetching user:", error);
    }
  };

  const createAccount = async (type) => {
    setLoading(true);
    try {
      const response = await axios.post(
        `${API_BASE_URL}/${type}_account/${auth.user.id}/register_account`,
        {},
        { headers: { "Content-Type": "application/json" } }
      );

      const data = response.data;
      if (data.success) {
        const account = data.object;
        alert("Account successfully created!");
        alert(`Your account ID: ${account.id}`);

        // Fetch user with updated account
        await fetchUser(account);

        navigate("/dashboard");
      } else {
        throw new Error("Account creation failed. Please try again.");
      }
    } catch (error) {
      console.error("Error creating account:", error);
      alert(error.response?.data?.message || "Something went wrong!");
    } finally {
      setLoading(false);
    }
  };

  return (
    <AuthLayout>
      <div className="createAccPage">
        <div className="caText">
          <h1>
            Hello {auth.user.firstName} {auth.user.lastName}!
          </h1>
          <h2>Select the type of account to create</h2>
        </div>
        <div className="caCards">
          {/* Saving Account */}
          <div className="caCard">
            <img
              src="/public/images/svgs/saving.svg"
              className="accImgs"
              alt=""
            />
            <h3 className="caCardTitle">Saving Account</h3>
            <div className="caCardBody">
              <p>
                A Saving Account helps you save securely, earn interest, and
                access funds easily for emergencies or long-term needs.
              </p>
              <button
                className="caCardBtn"
                onClick={() => createAccount("saving")}
                disabled={loading}
              >
                {loading ? "Processing..." : "Apply"}
              </button>
            </div>
          </div>

          {/* Current Account */}
          <div className="caCard">
            <img
              src="/public/images/svgs/current.svg"
              className="accImgs"
              alt=""
            />
            <h3 className="caCardTitle">Current Account</h3>
            <div className="caCardBody">
              <p>
                A Current Account is for daily transactions with unlimited
                deposits and withdrawals, ideal for regular expenses or business
                use.
              </p>
              <button
                className="caCardBtn"
                onClick={() => createAccount("current")}
                disabled={loading}
              >
                {loading ? "Processing..." : "Apply"}
              </button>
            </div>
          </div>
        </div>
      </div>
    </AuthLayout>
  );
}

export default CreateBankAccount;
