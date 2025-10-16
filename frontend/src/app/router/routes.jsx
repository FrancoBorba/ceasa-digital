import { createBrowserRouter } from "react-router-dom";
import App from "../App";
import Cart from "../pages/Cart/Cart";
import PurchaseConfirmation from "../pages/Purchase/Purchase";
import LoginPage from "../pages/auth/LoginPage";
import RegistrationPage from "../pages/auth/RegistrationPage";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
  },
  {
    path: "/login",
    element: <LoginPage />,
  },
  {
    path: "/register",
    element: <RegistrationPage />,
  },
  {
    path: "/cart",
    element: <Cart />,
  },
  {
    path: "/purchase",
    element: <PurchaseConfirmation />,
  },
]);