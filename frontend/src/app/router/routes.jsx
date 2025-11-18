import { createBrowserRouter } from "react-router-dom";
import App from "../App";
import Cart from "../pages/Cart/Cart";
import PurchaseConfirmation from "../pages/Purchase/Purchase";
import LoginPage from "../pages/auth/LoginPage";
import ProductDetail from "../pages/Product/ProductDetail";
import ForgotPasswordPage from "../pages/auth/ForgotPasswordPage";
import ResetPasswordPage from "../pages/auth/ResetPasswordPage";
import ClientRegistrationPage from "../pages/auth/ClientRegistrationPage";
import SellerRegistrationPage from "../pages/auth/SellerRegistrationPage";
import DeliveryManRegistrationpage from "../pages/auth/DeliveryManRegistrationPage";
import SelectRegistrationTypePage from "../pages/auth/SelectRegistrationTypePage";
import ChangePasswordPage from "../pages/auth/ChangePasswordPage";
import EmailVerifiedPage from "../pages/auth/EmailVerifiedPage";
import EmailVerifiedFailPage from "../pages/auth/EmailVerifiedFailPage";

import { ProtectedRoute } from "./ProtectedRoute";

// Admin imports
import AdminLayout from "../pages/Administrador/AdminLayout";
import AdminProductRequestPage from "../pages/Administrador/AdminProductRequestPage.jsx";
import AdminLayoutProfile from "../pages/Profile/admin/AdminLayout";
import AdminProductEvaluationPage from "../pages/Profile/admin/AdminProductEvaluationPage";

// Profile imports
import { default as UserProfileLayout } from "../pages/Profile/user/edit-info/ProfileLayout";
import { default as UserProfileInfoPage } from "../pages/Profile/user/edit-info/ProfileInfoPage";
import { default as UserProfileSecurityPage } from "../pages/Profile/user/edit-info/ProfileSecurityPage";

import { default as ProducerProfileLayout } from "../pages/Profile/producer/edit-info/ProfileLayout";
import { default as ProducerProfileInfoPage } from "../pages/Profile/producer/edit-info/ProfileInfoPage";
import { default as ProducerProfileSecurityPage } from "../pages/Profile/producer/edit-info/ProfileSecurityPage";

import DashboardLayout from "../pages/Profile/producer/dashboard/DashboardLayout";
import DashboardInventoryPage from "../pages/Profile/producer/dashboard/DashboardInventoryPage";

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
    path: "/client-register",
    element: <ClientRegistrationPage />,
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
    path: "/productDetail/:id",
    element: <ProductDetail />,
  },
  {
    path: "/seller-register",
    element: <SellerRegistrationPage />,
  },
  {
    path: "/delivery-register",
    element: <DeliveryManRegistrationpage />,
  },
  {
    path: "/select-register",
    element: <SelectRegistrationTypePage />,
  },
  {
    path: "/emailverified",
    element: <EmailVerifiedPage />,
  },
  {
    path: "/emailverifiedfail",
    element: <EmailVerifiedFailPage />,
  },
  {
    element: <ProtectedRoute allowedRoles={['ROLE_USER']} />,
    children: [
      {
        path: "/cart",
        element: <Cart />,
      },
      {
        path: "/purchase",
        element: <PurchaseConfirmation />,
      },
      {
        path: "/changepassword",
        element: <ChangePasswordPage />,
      },
      {
        path: "/user/edit-profile",
        element: <UserProfileLayout />,
        children: [
          {
            index: true,
            element: <UserProfileInfoPage />
          },
          {
            path: "info",
            element: <UserProfileInfoPage />
          },
          {
            path: "security",
            element: <UserProfileSecurityPage />
          }
        ]
      }
    ]
  },
  {
    element: <ProtectedRoute allowedRoles={['ROLE_PRODUTOR']} />,
    children: [
      {
        path: "/producer/edit-profile",
        element: <ProducerProfileLayout />,
        children: [
          {
            index: true,
            element: <ProducerProfileInfoPage />
          },
          {
            path: "info",
            element: <ProducerProfileInfoPage />
          },
          {
            path: "security",
            element: <ProducerProfileSecurityPage />
          }
        ]
      },
      {
        path: '/producer/dashboard',
        element: <DashboardLayout />,
        children: [
          {
            index: true,
            element: <DashboardInventoryPage />
          },
          {
            path: 'inventory',
            element: <DashboardInventoryPage />
          },
        ]
      }
    ]
  },
{
    path: "/admin",
    element: <ProtectedRoute allowedRoles={['ROLE_ADMIN']} />,
    children: [
      {
        path: "products",
        element: <AdminLayout />,
        children: [
          {
            index: true,
            element: <AdminProductRequestPage />,
          }
        ]
      },
      {
        path: "evaluation",
        element: <AdminLayoutProfile />,
        children: [
          {
            index: true,
            element: <AdminProductEvaluationPage />,
          }
        ]
      }
    ]
  }
]);