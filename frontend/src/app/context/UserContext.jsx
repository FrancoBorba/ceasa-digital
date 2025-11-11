import React, { createContext, useContext, useState } from 'react';


const avatarPlaceholder = "https://i.pravatar.cc/150";


const UserContext = createContext(null);


export function UserProvider({ children }) {
  const [userName, setUserName] = useState("Ceasa Digital"); // Nome padrão
  const [avatar, setAvatar] = useState(avatarPlaceholder); // Imagem padrão

  const value = {
    userName,
    setUserName, 
    avatar,
    setAvatar    
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