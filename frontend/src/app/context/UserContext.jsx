import React, { createContext, useContext, useState, useEffect } from "react";

const avatarPlaceholder = "https://i.pravatar.cc/150";

const UserContext = createContext(null);

export function UserProvider({ children }) {
  // 1. Inicializa o estado buscando do localStorage para não perder o login no F5
  const [user, setUser] = useState(() => {
    const savedUser = localStorage.getItem("user");
    return savedUser ? JSON.parse(savedUser) : null;
  });

  const [avatar, setAvatar] = useState(avatarPlaceholder);

  // 2. Função de Login: Salva no estado e no localStorage
  const login = (userData) => {
    setUser(userData);
    localStorage.setItem("user", JSON.stringify(userData));
    // Se tiver token, salve também: localStorage.setItem('token', userData.token);
  };

  // 3. Função de Logout: Limpa tudo
  const logout = () => {
    setUser(null);
    localStorage.removeItem("user");
    localStorage.removeItem("token");
    window.location.href = "/login";
  };

  // 4. Helper para verificar permissões (roles)
  const hasRole = (role) => {
    return user?.roles?.includes(role);
  };

  const value = {
    user,
    userName: user?.nome || "Visitante", // Ajuste conforme seu objeto de usuário
    avatar,
    setAvatar,
    login,
    logout,
    hasRole,
    // AQUI ESTÁ A CHAVE: isAuthenticated precisa ser true se user existir
    isAuthenticated: !!user,
  };

  return <UserContext.Provider value={value}>{children}</UserContext.Provider>;
}

export function useUser() {
  const context = useContext(UserContext);
  if (!context) {
    throw new Error("useUser deve ser usado dentro de um UserProvider");
  }
  return context;
}
