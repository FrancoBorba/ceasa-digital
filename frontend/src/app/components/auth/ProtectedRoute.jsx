import React from "react";
import { Navigate, Outlet } from "react-router-dom";
import { useUser } from "../../context/UserContext";

const ProtectedRoute = ({ allowedRoles }) => {
  const { isAuthenticated, user } = useUser();

  // 1. Verifica se está logado
  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  // 2. Verifica se tem as roles necessárias (se allowedRoles for passado)
  if (allowedRoles && allowedRoles.length > 0) {
    // Verifica se o usuário tem pelo menos uma das roles permitidas
    const hasPermission = user.roles?.some((role) =>
      allowedRoles.includes(role)
    );

    if (!hasPermission) {
      // Se não tiver permissão, redireciona para a Home ou uma página de "Acesso Negado"
      alert("Acesso negado: Você não tem permissão para acessar esta página.");
      return <Navigate to="/" replace />;
    }
  }

  // 3. Se passou por tudo, renderiza o conteúdo da rota (Outlet)
  return <Outlet />;
};

export default ProtectedRoute;
