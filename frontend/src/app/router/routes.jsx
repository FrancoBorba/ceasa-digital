import { createBrowserRouter } from "react-router-dom";
import App from "../App";
import Cart from "../pages/Cart/Cart";
import PurchaseConfirmation from "../pages/Purchase/Purchase";
import LoginPage from "../pages/auth/LoginPage";
import ProductDetail from "../pages/Product/ProductDetail";
import ClientRegistrationPage from "../pages/auth/ClientRegistrationPage";
import SellerRegistrationPage from "../pages/auth/SellerRegistrationPage";
import DeliveryManRegistrationpage from "../pages/auth/DeliveryManRegistrationPage";
import SelectRegistrationTypePage from "../pages/auth/SelectRegistrationTypePage";
import ChangePasswordPage from "../pages/auth/ChangePasswordPage";
import EmailVerifiedPage from "../pages/auth/EmailVerifiedPage";
import EmailVerifiedFailPage from "../pages/auth/EmailVerifiedFailPage";
import ForgotPasswordPage from "../pages/auth/ForgotPasswordPage";
import ProfileInfoPage from "../pages/Profile/producer/edit-info/ProfileInfoPage";
import ProfileLayout from "../pages/Profile/producer/edit-info/ProfileLayout";
import ProfileSecurityPage from "../pages/Profile/producer/edit-info/ProfileSecurityPage";
import DashboardLayout from "../pages/Profile/producer/dashboard/DashboardLayout";
import DashboardInventoryPage from "../pages/Profile/producer/dashboard/DashboardInventoryPage";
import AdminLayout from "../pages/Profile/admin/AdminLayout";
import AdminProductEvaluationPage from "../pages/Profile/admin/AdminProductEvaluationPage";
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
    path: "/forgot-password",
    element: <ForgotPasswordPage />,
  },
  {
    path: "/changepassword",
    element: <ChangePasswordPage />,
  },
  {
    path: "/producer/edit-profile",
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
    path: "/user/edit-profile",
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
    path: '/producer/dashboard',
    element: <DashboardLayout />,
    children: [
      {
        index: true,
        element: <DashboardInventoryPage /> // Redireciona /dashboard para /inventory
      },
      {
        path: 'inventory',
        element: <DashboardInventoryPage />
      },
    ]
  },
  {
    path: 'admin',
    element: <AdminLayout />,
    children: [
      // Rota padrão: /admin vai para /admin/products
      { index: true, element: <AdminProductEvaluationPage /> }, 
      { path: 'products', element: <AdminProductEvaluationPage /> },
      // { path: 'producers', element: <AdminProducersPage /> },
      // { path: 'registrations', element: <AdminRegistrationsPage /> },
    ]
  },
    


  {
    path: "/emailverified",
    element: <EmailVerifiedPage />,
  },
  {
    path: "/emailverifiedfail",
    element: <EmailVerifiedFailPage />,
  },
]);
