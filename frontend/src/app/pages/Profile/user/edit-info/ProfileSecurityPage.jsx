import React, { useState } from 'react';
import styles from './ProfileSecurityPage.module.css';
import { useUser } from '../../../../context/UserContext.jsx';
import ProfileInput from '../../../auth/components/profile/ProfileInput';
import ProfileButton from '../../../auth/components/profile/ProfileButton';
import useChangePassword from '../../../auth/hooks/useChangePassword';

const ProfileSecurityPage = () => {
  const { } = useUser(); 
  const { tryToChangePassword, isLoading } = useChangePassword();

  const [formData, setFormData] = useState({
    currentPassword: '',
    newPassword: '',
    confirmPassword: ''
  });

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

    try {
      await tryToChangePassword({
        currentPassword: formData.currentPassword,
        newPassword: formData.newPassword
      });

      alert("Senha alterada com sucesso!");
      setFormData({ currentPassword: '', newPassword: '', confirmPassword: '' });

    } catch (error) {
      alert(error.message);
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

          <div className={styles.row}>
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
        </div>

        <div className={styles.footer}>
          <button type="button" className={styles.cancelButton} onClick={handleCancel}>
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