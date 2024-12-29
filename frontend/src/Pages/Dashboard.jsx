import React, { useState, useEffect } from "react";
import "./Pages.css";
import PageLayout from "../layouts/PageLayout";
import { useAuth } from "../context/Auth";
import WithDraw from "../components/PopUps/WithDraw";
import DepositCard from "../components/PopUps/DepositCard";
// Icons

import { FaArrowUp, FaArrowDown } from "react-icons/fa6";
import axios from "axios";
import dateAndTimeFormatter from "../utils/dateTimeFormatter";
import { useNavigate } from "react-router-dom";

const Dashboard = () => {
  const navigate = useNavigate();
  const [auth, setAuth] = useAuth();
  const [allTransactions, setAllTransactions] = useState([]);
  const [widthDrawPopUp, setWidthDrawPopUp] = useState(false);
  const [depositPopUp, setDepositPopUp] = useState(false);
  const [loadingTransactions, setLoadingTransactions] = useState(true);
  const [account, setAccount] = useState();
  const API_BASE_URL = "http://localhost:8080";

  const fetchAccount = async () => {
    try {
      const response = await axios.get(
        account?.accountType == "Saving"
          ? `http://localhost:8080/saving_account/account/${account.id}`
          : `http://localhost:8080/current_account/account/${account.id}`,
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      const data = response.data;

      if (data.success) {
        setAuth({
          ...auth,
          account: data.object,
        });
        localStorage.setItem(
          "auth",
          JSON.stringify({
            ...auth,
            account: data.object,
          })
        );
      }
    } catch (error) {
      console.log("Error:", error);
      alert(error.response?.data?.message || "Transaction failed");
    }
  };

  useEffect(() => {
    if (account?.allTransactions) {
      setAllTransactions(account?.allTransactions);
      setLoadingTransactions(false);
    } else {
      setLoadingTransactions(true);
    }
  }, [account?.allTransactions]);

  useEffect(() => {
    if (auth?.account) setAccount(auth?.account);
  }, [auth]);

  const toggleWithdraw = () => {
    setWidthDrawPopUp(!widthDrawPopUp);
    fetchAccount();
  };

  const toggleDeposit = () => {
    setDepositPopUp(!depositPopUp);
    fetchAccount();
  };

  const formatNumber = (num) => {
    return num ? num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") : "";
  };

  return (
    <PageLayout>
      <div className="dashboard-container">
        {widthDrawPopUp && <WithDraw toggleWithdraw={toggleWithdraw} />}
        {depositPopUp && <DepositCard toggleDeposit={toggleDeposit} />}

        <div className="section balance-section">
          <div>
            <p className="balance-amount">
              ${formatNumber(account?.balance) || 0}
            </p>
            <p className="interest-rate">
              {auth?.user?.account?.accountType === "Saving"
                ? `Interest Rate : ${account?.interestRate || 0}%`
                : `Overdraft : $${account?.overdraft || 0}`}
            </p>
          </div>
        </div>

        <div className="section account-type-section">
          <h2>{account?.accountType || "Account"} Account</h2>
          <p>
            Enjoy flexible transactions, a great interest rate, and modern
            banking services tailored to your needs.
          </p>
        </div>

        <div className="section">
          <h2>Transactions</h2>
          <table className="transactions-table">
            <thead>
              <tr>
                <th>Date</th>
                <th>Description</th>
                <th>Amount</th>
                <th style={{ textAlign: "right" }}>Time</th>
              </tr>
            </thead>
            <tbody>
              {loadingTransactions ? (
                <tr>
                  <td
                    colSpan="3"
                    style={{ textAlign: "center", padding: "1rem" }}
                  >
                    Loading Transactions...
                  </td>
                </tr>
              ) : allTransactions.length > 0 ? (
                allTransactions.slice(0, 3).map((trn, index) => (
                  <tr key={index}>
                    <td>
                      {dateAndTimeFormatter(trn.createdAt, "date") || "N/A"}
                    </td>
                    <td>
                      {" "}
                      {trn.transactionType === "deposit" ? (
                        <FaArrowDown />
                      ) : (
                        <FaArrowUp />
                      )}{" "}
                      {trn.transactionType || "N/A"}
                    </td>
                    <td
                      style={{
                        color:
                          trn.transactionType === "deposit" ? "green" : "red",
                        fontWeight: "600",
                      }}
                    >
                      ${trn.transactionAmount || 0}
                    </td>
                    <td style={{ textAlign: "right" }}>
                      {dateAndTimeFormatter(trn.createdAt, "time") || "N/A"}
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td
                    colSpan="3"
                    style={{ textAlign: "center", padding: "1rem" }}
                  >
                    No Transactions you've done recently
                  </td>
                </tr>
              )}
            </tbody>
          </table>
          <button
            className="allTrnsBtn"
            onClick={() => navigate("/all_transaction")}
            style={{ marginTop: "15px" }}
          >
            View All Transactions
          </button>
        </div>

        {/* Features Section */}
        <div className="section features-section">
          <div className="feature-card" onClick={toggleWithdraw}>
            <img src="/public/images/svgs/withdraw.svg" alt="tcImg" />
            <h3>Withdraw Money</h3>
            <p>Securely withdraw funds anytime.</p>
          </div>

          <div className="feature-card" onClick={toggleDeposit}>
            <img src="/public/images/svgs/deposit.svg" alt="tcImg" />
            <h3>Deposit Money</h3>
            <p>Convenient and fast deposits.</p>
          </div>

          <div className="feature-card">
            <img src="/public/images/svgs/bill.svg" alt="tcImg" />

            <h3>Pay Bills</h3>
            <p>Quick and easy bill payments.</p>
          </div>
          <div className="feature-card">
            <img src="/public/images/svgs/mtrn.svg" alt="tcImg" />

            <h3>Transfer Funds</h3>
            <p>Seamlessly transfer funds worldwide.</p>
          </div>
        </div>
      </div>
    </PageLayout>
  );
};

export default Dashboard;
