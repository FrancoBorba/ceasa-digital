import { createBrowserRouter } from "react-router-dom";
import App from "../App";
import ProtectedRoute from "../components/auth/ProtectedRoute";
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
import ProducerLayout from "../pages/Producer/ProducerLayout";
import ProducerProductRequestPage from "../pages/Producer/ProducerProductRequestPage";
import StorageManagerSalesPage from "../pages/storage/StorageManagerSalesPage";
import StorageManagerNotificationPage from "../pages/storage/StorageManagerNotificationPage";
import StorageManagerPackagesPage from "../pages/storage/StorageManagerPackagesPage";

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
  // Rotas Públicas
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
    path: "/cart",
    element: <Cart />,
  },
  {
    path: "/purchase",
    element: <PurchaseConfirmation />,
  },

  // --- Rotas Protegidas (Exigem Login Genérico) ---
  {
    element: <ProtectedRoute />,
    children: [
      {
        path: "/changepassword",
        element: <ChangePasswordPage />,
      },
      // Perfil de Usuário Comum
      {
        path: "/user/edit-profile",
        element: <UserProfileLayout />,
        children: [
          { index: true, element: <UserProfileInfoPage /> },
          { path: "info", element: <UserProfileInfoPage /> },
          { path: "security", element: <UserProfileSecurityPage /> },
        ],
      },
    ],
  },

  // --- Rotas de Produtor (ROLE_PRODUTOR) ---
  {
    element: <ProtectedRoute allowedRoles={["ROLE_PRODUTOR"]} />,
    children: [
      {
        path: "/producer/products",
        element: <ProducerLayout />,
        children: [{ index: true, element: <ProducerProductRequestPage /> }],
      },
      {
        path: "/producer/edit-profile",
        element: <ProducerProfileLayout />,
        children: [
          { index: true, element: <ProducerProfileInfoPage /> },
          { path: "info", element: <ProducerProfileInfoPage /> },
          { path: "security", element: <ProducerProfileSecurityPage /> },
        ],
      },
      {
        path: "/producer/dashboard",
        element: <DashboardLayout />,
        children: [
          { index: true, element: <DashboardInventoryPage /> },
          { path: "inventory", element: <DashboardInventoryPage /> },
        ],
      },
    ],
  },

  // --- Rotas do Gerente de Estoque (ROLE_ADMIN ou outra Role?) ---
  {
    element: <ProtectedRoute allowedRoles={["ROLE_ADMIN"]} />,
    children: [
      {
        path: "/storage-manager/sales",
        element: <StorageManagerSalesPage />,
      },
      {
        path: "/storage-manager/notification",
        element: <StorageManagerNotificationPage />,
      },
      {
        path: "/storage-manager/packages",
        element: <StorageManagerPackagesPage />,
      },
    ],
  },

  // --- Rotas de Administrador (ROLE_ADMIN) ---
  {
    element: <ProtectedRoute allowedRoles={["ROLE_ADMIN"]} />,
    path: "/admin",
    children: [
      {
        path: "products",
        element: <AdminLayout />,
        children: [{ index: true, element: <AdminProductRequestPage /> }],
      },
      {
        path: "evaluation",
        element: <AdminLayout />,
        children: [{ index: true, element: <AdminProductEvaluationPage /> }],
      },
    ],
  },
]);
