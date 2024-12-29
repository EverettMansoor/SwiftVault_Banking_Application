import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Home from "./Pages/Home";
import About from "./Pages/About";
import Register from "./Pages/Auth/Register";
import Login from "./Pages/Auth/Login";
import Dashboard from "./Pages/Dashboard";
import Account from "./Pages/Account";
import CreateBankAccount from "./Pages/CreateBankAccount";
import AllTransaction from "./Pages/AllTransaction";
import SecureRoute from "./route/RoutSecurity";

function App() {
  const router = createBrowserRouter([
    {
      path: "/",
      element: <Home />,
    },
    {
      path: "/sec",
      element: (
        <SecureRoute>
          <About />
        </SecureRoute>
      ),
    },
    {
      path: "/create",
      element: (
        <SecureRoute>
          <CreateBankAccount />
        </SecureRoute>
      ),
    },
    {
      path: "/dashboard",
      element: (
        <SecureRoute>
          <Dashboard />
        </SecureRoute>
      ),
    },
    {
      path: "/account",
      element: (
        <SecureRoute>
          <Account />
        </SecureRoute>
      ),
    },
    {
      path: "/all_transaction",
      element: (
        <SecureRoute>
          <AllTransaction />
        </SecureRoute>
      ),
    },
    {
      path: "/auth/register",
      element: <Register />,
    },
    {
      path: "/auth/login",
      element: <Login />,
    },
  ]);

  return (
    <>
      <RouterProvider router={router} />
    </>
  );
}

export default App;
