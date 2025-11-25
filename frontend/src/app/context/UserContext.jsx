import React, { createContext, useContext, useState, useEffect } from "react";

const avatarPlaceholder = "https://i.pravatar.cc/150";

const UserContext = createContext(null);

export function UserProvider({ children }) {
  // 1. Recupera o usuário do localStorage ao iniciar
  const [user, setUser] = useState(() => {
    const savedUser = localStorage.getItem("user");
    return savedUser ? JSON.parse(savedUser) : null;
  });

  const [avatar, setAvatar] = useState(avatarPlaceholder);

  // 2. Função de Login
  const login = (userData) => {
    setUser(userData);
    localStorage.setItem("user", JSON.stringify(userData));
    // O token geralmente já foi salvo pelo hook de login, mas pode reforçar aqui se quiser
  };

  // 3. Função de Logout Centralizada
  const logoutContext = () => {
    setUser(null);
    setAvatar(avatarPlaceholder);
    localStorage.removeItem("user");
    localStorage.removeItem("token");
    localStorage.removeItem("access_token");
    localStorage.removeItem("refresh_token");
  };

  // 4. Helper para verificar roles
  const hasRole = (role) => {
    return user?.roles?.includes(role);
  };

  const value = {
    user,
    userName: user?.nome || "Ceasa Digital", // Nome vindo do objeto user ou fallback
    avatar,
    setAvatar,
    login,
    logout: logoutContext, // Expõe a função de logout
    hasRole,
    isAuthenticated: !!user, // Transforma o objeto user em booleano
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
