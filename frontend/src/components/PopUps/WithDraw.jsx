import React, { useEffect, useState } from "react";
import "./PopUps.css";
import axios from "axios";
import { useAuth } from "../../context/Auth";


function WithDraw({ toggleWithdraw }) {
  const [transactionAmount, setTransactionAmount] = useState("");
  const [auth, setAuth] = useAuth(); 


  const handleWithdraw = async () => {
    const formData = {
      transactionAmount: transactionAmount,
      transactionType: "withdraw",
    };
    
    

    try {
      const response = await axios.post(
        `http://localhost:8080/transaction/withdraw/perform_transaction/account/${auth?.account?.id}`,
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
        fetchAccount()
      }
    } catch (error) {
      console.log("Error:", error);
      alert(error.response?.data?.message || "Transaction failed");
    }
  };

  const fetchAccount = async () =>{
    try {
      const response = await axios.get(
        auth?.user?.account?.accountType == "Saving" ? 
        `http://localhost:8080/saving_account/account/${auth?.account.id}`:
        `http://localhost:8080/current_account/account/${auth?.account.id}`,
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      const data = response.data;

      if (data.success) {
        alert("Transaction successful");
        toggleWithdraw()
      }
    } catch (error) {
      console.log("Error:", error);
      alert(error.response?.data?.message || "Transaction failed");
    }
  }

  return (
    <div>
      <div className="transactionPopUp">
        <div className="transactionDiv">
          <div className="crossIcon" onClick={toggleWithdraw}>X</div>
          <div className="transactionDivHeader">
            <h1>WithDrawal</h1>
          </div>
          <div className="transactionFields">
            <input
              type="text"
              id="amount"
              className="transactionTextField"
              placeholder="Amount to withdraw"
              value={transactionAmount}
              onChange={(e) => setTransactionAmount(e.target.value)}
            />
          </div>
          <button className="transactionBtn" onClick={handleWithdraw}>
            Withdraw
          </button>
        </div>
      </div>
    </div>
  );
}

export default WithDraw;
