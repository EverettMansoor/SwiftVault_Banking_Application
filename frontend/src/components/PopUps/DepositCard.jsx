import React, { useState } from "react";
import "./PopUps.css";
import axios from "axios";
import { useAuth } from "../../context/Auth";

function DepositCard({ toggleDeposit }) {
  const [transactionAmount, setTransactionAmount] = useState("");
  const [auth, setAuth] = useAuth();

  // Handle Deposit action
  const handleDeposit = async () => {
    const formData = {
      transactionAmount: transactionAmount,
      transactionType: "deposit",
    };

    try {
      const response = await axios.post(
        `http://localhost:8080/transaction/deposit/perform_transaction/account/${auth?.account?.id}`,
        formData,
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      const data = response.data;

      if (data.success) {
        alert("Transaction successful");
        fetchAccount(); // Fetch updated account after successful deposit
      }
    } catch (error) {
      console.log("Error:", error);
      alert(error.response?.data?.message || "An error occurred");
    }
  };

  // Fetch account data after transaction
  const fetchAccount = async () => {
    try {
      const response = await axios.get(
        auth?.user?.account?.accountType === "Saving"
          ? `http://localhost:8080/saving_account/account/${auth?.account.id}`
          : `http://localhost:8080/current_account/account/${auth?.account.id}`,
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      const data = response.data;

      if (data.success) {      
        toggleDeposit(); // Close the deposit card
      }
    } catch (error) {
      console.log("Error fetching account:", error);
      alert(error.response?.data?.message || "Transaction failed");
    }
  };

  return (
    <div className="transactionPopUp">
      <div className="transactionDiv">
        <div className="crossIcon" onClick={toggleDeposit}>
          X
        </div>
        <div className="transactionDivHeader">
          <h1>Deposit</h1>
        </div>
        <div className="transactionFields">
          <input
            type="text"
            id="amount"
            className="transactionTextField"
            placeholder="Amount to deposit"
            value={transactionAmount}
            onChange={(e) => setTransactionAmount(e.target.value)}
          />
        </div>
        <button className="transactionBtn" onClick={handleDeposit}>
          Deposit
        </button>
      </div>
    </div>
  );
}

export default DepositCard;
