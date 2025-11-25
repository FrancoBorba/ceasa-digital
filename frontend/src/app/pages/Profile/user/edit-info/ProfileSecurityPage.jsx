import React, { useState, useEffect } from 'react';
import styles from './ProfileSecurityPage.module.css';
import ProfileInput from "../../../auth/components/profile/ProfileInput";
import ProfileButton from "../../../auth/components/profile/ProfileButton";

const ProfileSecurityPage = () => {
  const [formData, setFormData] = useState({
    currentPassword: '',
    newPassword: '',
    confirmPassword: ''
  });
  const [isLoading, setIsLoading] = useState(false);

  const API_URL = 'http://localhost:8080';

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (formData.newPassword !== formData.confirmPassword) {
      alert("As novas senhas não coincidem!");
      return;
    }

    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    
    if (!passwordRegex.test(formData.newPassword)) {
      alert("A senha deve ter no mínimo 8 caracteres, com letras maiúsculas, minúsculas, números e caracteres especiais.");
      return;
    }

    setIsLoading(true);
    const token = localStorage.getItem('authToken');

    try {
      const payload = {
        oldPassword: formData.currentPassword, 
        newPassword: formData.newPassword      
      };

      const response = await fetch(`${API_URL}/users/me/password`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(payload)
      });

      if (!response.ok) {
        if (response.status === 400) {
           const errorData = await response.json().catch(() => null);
           throw new Error(errorData?.message || "Dados inválidos ou senha antiga incorreta.");
        }
        throw new Error("Erro ao alterar senha.");
      }

      alert("Senha alterada com sucesso!");
      setFormData({ currentPassword: '', newPassword: '', confirmPassword: '' });

    } catch (err) {
      console.error(err);
      alert(err.message);
    } finally {
      setIsLoading(false);
    }
  };

  const handleCancel = () => {
    setFormData({ currentPassword: '', newPassword: '', confirmPassword: '' });
  };

 return (
    <div className={styles.wrapper}>
      <h1 className={styles.header}>LOGIN E SENHA</h1>

      <form className={styles.form} onSubmit={handleSubmit}>
        
        <div className={styles.fieldsContainer}>
          <ProfileInput
            label="SENHA ATUAL:"
            type="password"
            name="currentPassword"
            value={formData.currentPassword}
            onChange={handleChange}
            required
          />
          <ProfileInput
            label="NOVA SENHA:"
            type="password"
            name="newPassword"
            value={formData.newPassword}
            onChange={handleChange}
            required
          />
          
          <ProfileInput
            label="CONFIRMAR NOVA SENHA:"
            type="password"
            name="confirmPassword"
            value={formData.confirmPassword}
            onChange={handleChange}
            required
          />
        </div>

        <div className={styles.footer}>
          <button 
            type="button" 
            className={styles.cancelButton}
            onClick={handleCancel}
          >
            DESCARTAR MUDANÇAS
          </button>
          
          <ProfileButton type="submit" disabled={isLoading}>
            {isLoading ? "SALVANDO..." : "SALVAR MUDANÇAS"}
          </ProfileButton>
        </div>
      </form>
    </div>
  );
};

export default ProfileSecurityPage;