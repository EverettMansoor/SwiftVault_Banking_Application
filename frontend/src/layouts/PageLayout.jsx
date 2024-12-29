import React, { useState } from "react";
import { NavLink, useNavigate, useLocation } from "react-router-dom";
import "./Layouts.css";
import { useAuth } from "../context/Auth";
import { MdOutlineLogout } from "react-icons/md";
import { CgProfile } from "react-icons/cg";

// Icons
import { GoHome } from "react-icons/go";
import { AiOutlineTransaction } from "react-icons/ai";
import { FaRegUserCircle } from "react-icons/fa";
import { AiOutlineExclamationCircle } from "react-icons/ai";

function PageLayout({ children }) {
  const [auth, setAuth] = useAuth();
  const [logoutVisible, setLogoutVisible] = useState(false);
  const navigate = useNavigate();
  const location = useLocation();

  return (
    <div>
      <div className="pageContainer">
        <header className="header">
          <div className="logo">
            <img src="/public/images/svgs/logo.png" className="logoImg" />
            <p>SwiftVault</p>
          </div>
          <nav>
            <NavLink
              to="/dashboard"
              className={({ isActive }) =>
                isActive ? "activeLink" : "navLink"
              }
            >
              <GoHome />
              Home
            </NavLink>
            <NavLink
              to="/all_transaction"
              className={({ isActive }) =>
                isActive ? "activeLink" : "navLink"
              }
            >
              <AiOutlineTransaction />
              All Transactions
            </NavLink>
            <NavLink
              to="/account"
              className={({ isActive }) =>
                isActive ? "activeLink" : "navLink"
              }
            >
              <FaRegUserCircle />
              My Profile
            </NavLink>
            <NavLink
              to="/about"
              className={({ isActive }) =>
                isActive ? "activeLink" : "navLink"
              }
            >
              <AiOutlineExclamationCircle />
              About Us
            </NavLink>
          </nav>
          <div
            className="user-info"
            onClick={() => setLogoutVisible((prevState) => !prevState)}
          >
            <img src="https://via.placeholder.com/40" alt="User Picture" />
            <span id="navUserName">
              {auth?.user?.firstName} {auth?.user?.lastName}
            </span>
            {logoutVisible && (
              <div className="logoutMenu">
                <p
                  style={{ borderBottom: "1px solid rgba(0,0,0,0.1)" }}
                  onClick={() => navigate("/account")}
                >
                  <CgProfile className="icons_normal" />
                  Profile
                </p>
                <p
                  onClick={() => {
                    localStorage.clear();
                    navigate("/auth/login");
                  }}
                >
                  <MdOutlineLogout className="icons_normal" />
                  Logout
                </p>
              </div>
            )}
          </div>
        </header>

        <div className="pageBody">{children}</div>
      </div>
    </div>
  );
}

export default PageLayout;
