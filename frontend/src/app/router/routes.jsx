import { createBrowserRouter } from "react-router-dom";
import App from "../App";
import Cart from "../pages/Cart/Cart";
import PurchaseConfirmation from "../pages/Purchase/Purchase";
import LoginPage from "../pages/auth/LoginPage";
import RegistrationPage from "../pages/auth/RegistrationPage";
import ProductDetail from "../pages/Product/ProductDetail";
import ForgotPasswordPage from "../pages/auth/ForgotPasswordPage";
import ResetPasswordPage from "../pages/auth/ResetPasswordPage";
import ProfileLayout from "../pages/Profile/ProfileLayout";
import ProfileInfoPage from "../pages/Profile/ProfileInfoPage";
import ProfileSecurityPage from "../pages/Profile/ProfileSecurityPage";

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
    path: "/forgot-password",
    element: <ForgotPasswordPage />,
  },
  {
    path: "/reset-password",
    element: <ResetPasswordPage />,
  },
  {
    path: "/edit-profile",
    element: <ProfileLayout />,
    children: [
      {
        index: true, 
        element: <ProfileInfoPage /> 
      },
      {
        path: "info",
        element: <ProfileInfoPage />
      },
      {
        path: "security",
        element: <ProfileSecurityPage />
      }
    ]
  },
  {
    path: "/cart",
    element: <Cart />,
  },
  {
    path: "/purchase",
    element: <PurchaseConfirmation />,
  },
  {
    path: "/productDetail/:id",
    element: <ProductDetail />,
  },
]);