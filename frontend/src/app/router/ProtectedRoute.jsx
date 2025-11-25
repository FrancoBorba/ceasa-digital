import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import { jwtDecode } from "jwt-decode";
import { getAccessToken } from '../pages/auth/services/authTokenStorage';

export const ProtectedRoute = ({ allowedRoles }) => {
  const token = getAccessToken();

  // 1. Se não tem token, manda pro Login
  if (!token) {
    return <Navigate to="/login" replace />;
  }

  try {
    // 2. Decodifica o token para ler os dados
    const decodedToken = jwtDecode(token);
    
    const userRoles = decodedToken.roles || decodedToken.authorities || []; 

    // 3. Verifica se alguma role do usuário bate com as roles permitidas
    // Se allowedRoles não for passado, apenas estar logado é suficiente
    const hasPermission = !allowedRoles || allowedRoles.some(role => userRoles.includes(role));

    if (!hasPermission) {
        // Se logado mas sem permissão, manda pra Home (ou uma página 403)
        return <Navigate to="/" replace />;
    }

    // 4. Se passou em tudo, renderiza a rota filha
    return <Outlet />;

  } catch (error) {
    // Se o token for inválido ou corrompido, força logout
    return <Navigate to="/login" replace />;
  }
};