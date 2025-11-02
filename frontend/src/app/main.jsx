import ReactDOM from "react-dom/client";
import React from "react";
import "./index.css";
import {RouterProvider } from "react-router-dom";
import { router } from "./router/routes.jsx";
import { RegistrationProvider } from './context/RegistrationContext'; // Importar

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <RegistrationProvider>
    <RouterProvider router={router} />
    </RegistrationProvider>
  </React.StrictMode>
);
