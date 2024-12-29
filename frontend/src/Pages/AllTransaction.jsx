import React, { useEffect, useState } from "react";
import PageLayout from "../layouts/PageLayout";
import { useAuth } from "../context/Auth";
import dateAndTimeFormatter from "../utils/dateTimeFormatter";
// Icons
import { FaArrowUp, FaArrowDown } from "react-icons/fa6";


function AllTransaction() {
  const [auth, setAuth] = useAuth();
  const [account, setAccount] = useState();
  const [allTransactions, setAllTransactions] = useState([]);
  const [loadingTransactions, setLoadingTransactions] = useState(true);


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

  return (
    <PageLayout>
      <div className="section">
        <h2>All Transactions</h2>
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
              allTransactions.map((trn, index) => (
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
      </div>
    </PageLayout>
  );
}

export default AllTransaction;
