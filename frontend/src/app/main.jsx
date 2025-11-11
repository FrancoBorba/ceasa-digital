import ReactDOM from "react-dom/client";
import React from "react";
import "./index.css";
import { RouterProvider } from "react-router-dom";
import { router } from "./router/routes.jsx";
import { RegistrationProvider } from './context/RegistrationContext';
import { UserProvider } from './context/UserContext.jsx';

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <RegistrationProvider>
      <UserProvider>
        <RouterProvider router={router} />
      </UserProvider>
    </RegistrationProvider>
  </React.StrictMode>
);
