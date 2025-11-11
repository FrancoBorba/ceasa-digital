import React, { createContext, useState, useContext } from 'react';

const RegistrationContext = createContext();

export const useRegistration = () => useContext(RegistrationContext);

export const RegistrationProvider = ({ children }) => {
    const [registrationData, setRegistrationData] = useState({
        userId: null,
        confirmationToken: null,
    });

    const setRegData = (data) => {
        setRegistrationData(prev => ({ ...prev, ...data }));
    };

    return (
        <RegistrationContext.Provider value={{ ...registrationData, setRegData }}>
            {children}
        </RegistrationContext.Provider>
    );
};