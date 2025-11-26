import React, { createContext, useContext, useState, useEffect } from 'react';

const avatarPlaceholder = "https://i.pravatar.cc/150";

const UserContext = createContext(null);

export function UserProvider({ children }) {
  const [userName, setUserName] = useState(null);
  const [userEmail, setUserEmail] = useState(null);
  const [userRole, setUserRole] = useState(null);
  const [avatar, setAvatar] = useState(avatarPlaceholder);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  const handleLogin = (userData) => {
    setUserName(userData.name);
    setUserEmail(userData.email);
    setUserRole(userData.role);
    setAvatar(userData.avatarUrl || avatarPlaceholder);
    setIsAuthenticated(true);
  };

  const handleLogout = () => {
    setUserName(null);
    setUserEmail(null);
    setUserRole(null);
    setAvatar(avatarPlaceholder);
    setIsAuthenticated(false);
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');
  };

  useEffect(() => {
    const token = localStorage.getItem('access_token');
    if (token) {
       setIsAuthenticated(true);
    }
  }, []);

  const value = {
    userName,
    setUserName,
    userEmail,
    setUserEmail,
    userRole,
    setUserRole,
    avatar,
    setAvatar,
    isAuthenticated,
    handleLogin,
    handleLogout
  };

  return (
    <UserContext.Provider value={value}>
      {children}
    </UserContext.Provider>
  );
}

export function useUser() {
  const context = useContext(UserContext);
  if (!context) {
    throw new Error('useUser deve ser usado dentro de um UserProvider');
  }
  return context;
}