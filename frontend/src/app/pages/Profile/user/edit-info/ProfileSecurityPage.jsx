import React, { useState } from 'react';
import styles from './ProfileSecurityPage.module.css';
import ProfileInput from "../../../auth/components/profile/ProfileInput";
import ProfileButton from "../../../auth/components/profile/ProfileButton";


export default function ProfileSecurityPage() {
  const [formData, setFormData] = useState({
    currentPassword: '',
    newPassword: '',
    confirmPassword: ''
  });
  const [isLoading, setIsLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: value
    }));
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true);

    if (formData.newPassword !== formData.confirmPassword) {
      alert("As novas senhas não coincidem!");
      setIsLoading(false);
      return;
    }
    if (formData.newPassword.length < 6) { 
      alert("A nova senha deve ter pelo menos 6 caracteres.");
      setIsLoading(false);
      return;
    }

    const API_URL = 'http://localhost:8080';
    const token = localStorage.getItem('authToken'); 

    try {
      const payload = {
        senhaAtual: formData.currentPassword,
        novaSenha: formData.newPassword
      };

      const response = await fetch(
        `${API_URL}/users/me/password`, 
        {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
          },
          body: JSON.stringify(payload)
        }
      );

      if (!response.ok) {
        if (response.status === 400) {
          throw new Error("A senha atual está incorreta.");
        }
        throw new Error(`Erro na API: ${response.status}`);
      }

      alert("Senha alterada com sucesso!");
      setFormData({ currentPassword: '', newPassword: '', confirmPassword: '' });

    } catch (err) {
      console.error("Erro ao alterar senha:", err);
      alert(err.message || "Não foi possível alterar sua senha.");
    } finally {
      setIsLoading(false);
    }
  };
  return (
    <form className={styles.form}>
      <div>
        <h1 className={styles.header}>LOGIN E SENHA</h1>
        <div className={styles.fieldsContainer}>
          <ProfileInput 
            label="SENHA ATUAL"
            name="currentPassword"
            type="password"
          />
          <ProfileInput 
            label="NOVA SENHA"
            name="newPassword"
            type="password"
          />
          <ProfileInput 
            label="CONFIRMAR NOVA SENHA"
            name="confirmPassword"
            type="password"
          />
        </div>
      </div>
      
      <div className={styles.buttonContainer}>
        <ProfileButton type="button" variant="outline">
          DESCARTAR MUDANÇAS
        </ProfileButton>
        <ProfileButton type="submit" variant="primary">
          SALVAR MUDANÇAS
        </ProfileButton>
      </div>
    </form>
  );
}