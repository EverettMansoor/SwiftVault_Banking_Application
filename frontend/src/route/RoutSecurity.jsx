import React from "react";
import { Navigate } from "react-router-dom";
import { useAuth } from "../context/Auth";

function SecureRoute({ children }) {
  const [auth] = useAuth();

  if (!auth || !auth.user) {
    return <Navigate to="/auth/login" replace />;
  }

  return children;
}

export default SecureRoute;
