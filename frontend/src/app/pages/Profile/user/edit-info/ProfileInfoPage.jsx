import React, { useState, useEffect } from 'react';
import styles from './ProfileInfoPage.module.css';
import ProfileInput from "../../../auth/components/profile/ProfileInput"; 
import ProfileButton from "../../../auth/components/profile/ProfileButton"; 
import { useUser } from '../../../../context/UserContext';

const ProfileInfoPage = () => {
  const { setUserName, setAvatar } = useUser(); 
  const [isLoading, setIsLoading] = useState(false);
  const [formData, setFormData] = useState({
    name: '',
    surname: '',
    email: '',
    phone: '',
    address: '',
    gender: '' 
  });

  const API_URL = 'http://localhost:8080';

  useEffect(() => {
    const fetchUserData = async () => {
      const token = localStorage.getItem('authToken');
      try {
        const response = await fetch(`${API_URL}/users/me`, {
          headers: { 'Authorization': `Bearer ${token}` }
        });
        
        if (response.ok) {
          const data = await response.json();
          setFormData({
            name: data.name || '',
            surname: data.surname || '', 
            email: data.email || '',
            phone: data.phone || '',
            address: data.address || '',
            gender: data.gender || ''
          });
          if(data.avatarUrl) setAvatar(data.avatarUrl); 
        }
      } catch (error) {
        console.error("Erro ao buscar dados:", error);
      }
    };

    fetchUserData();
  }, [setAvatar]); 

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    const token = localStorage.getItem('authToken');

    try {
      const response = await fetch(`${API_URL}/users/me`, {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(formData) 
      });

      if (!response.ok) throw new Error('Erro ao atualizar');

      const responseData = await response.json();
      setUserName(responseData.name); 
      alert("Dados atualizados com sucesso!");

    } catch (err) {
      console.error(err);
      alert("Não foi possível salvar as mudanças.");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className={styles.wrapper}>
      <h1 className={styles.title}>INFORMAÇÕES PESSOAIS</h1>

      <form className={styles.form} onSubmit={handleSubmit}>
      
        <div className={styles.genderGroup}>
          <label>
            <input 
              type="radio" 
              name="gender" 
              value="M" 
              checked={formData.gender === 'M'} 
              onChange={handleChange} 
            /> HOMEM
          </label>
          <label style={{marginLeft: '20px'}}>
            <input 
              type="radio" 
              name="gender" 
              value="F" 
              checked={formData.gender === 'F'} 
              onChange={handleChange} 
            /> MULHER
          </label>
        </div>

        <div className={styles.row}>
          <ProfileInput
            label="NOME:"
            name="name"
            value={formData.name}
            onChange={handleChange}
          />
          <ProfileInput
            label="SOBRENOME:"
            name="surname"
            value={formData.surname}
            onChange={handleChange}
          />
        </div>

        <ProfileInput
          label="EMAIL:"
          name="email"
          value={formData.email}
          onChange={handleChange}
          disabled={true} 
        />
        <p className={styles.verified}>✓ Email verificado</p>

        <ProfileInput
          label="ENDEREÇO:"
          name="address"
          value={formData.address}
          onChange={handleChange}
        />

        <ProfileInput
          label="NÚMERO DE TELEFONE:"
          name="phone"
          value={formData.phone}
          onChange={handleChange}
        />
       <div className={styles.footer}>
          <button type="button" className={styles.cancelButton}>
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

export default ProfileInfoPage;