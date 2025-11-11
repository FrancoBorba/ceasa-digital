import ReactDOM from "react-dom/client";
import React from "react";
import "./index.css";
import {RouterProvider } from "react-router-dom";
import { router } from "./router/routes.jsx";
import { RegistrationProvider } from './context/RegistrationContext'; // Importar
import { UserProvider } from './context/UserContext.jsx';

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <RegistrationProvider>
    <RouterProvider router={router} />
    </RegistrationProvider>
  </React.StrictMode>
),

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <UserProvider >
    <RouterProvider router={router} />
    </UserProvider>
  </React.StrictMode>
);
