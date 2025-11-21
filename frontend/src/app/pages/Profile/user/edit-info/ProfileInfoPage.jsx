import { useState, useEffect } from "react";
import styles from './ProfileInfoPage.module.css';
import ProfileInput from "../../../auth/components/profile/ProfileInput"; 
import ProfileButton from "../../../auth/components/profile/ProfileButton"; 
import { useUser } from '../../../../context/UserContext.jsx'; 

export default function ProfileInfoPage() {
  const { userName, setUserName, userEmail } = useUser(); 

  const [formData, setFormData] = useState({
    name: '',
    email: '' 
  });
  useEffect(() => {
    setFormData({
      name: userName || '',
      email: userEmail || ''
    });
  }, [userName, userEmail]);
  
  
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

    const API_URL = 'http://localhost:8080';
    const token = localStorage.getItem('authToken'); 

    try {
      const payload = {
        name: formData.name,
        email: formData.email
      };

      const response = await fetch(
        `${API_URL}/users/me`, 
        {
          method: 'PATCH', 
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}` 
          },
          body: JSON.stringify(payload) 
        }
      );

      if (!response.ok) {
        throw new Error(`Erro na API: ${response.status}`);
      }
      const data = await response.json();
      setUserName(data.name); 

      alert("Informações atualizadas com sucesso!");
      } catch (err) {
      console.error("Erro ao atualizar perfil:", err);
      alert("Não foi possível atualizar suas informações.");
    } finally {
      setIsLoading(false);
    }
  };

  const handleDiscard = () => {
  };

  return (
    <form onSubmit={handleSubmit} className={styles.form}>
      <div>
        <h1 className={styles.header}>INFORMAÇÕES PESSOAIS</h1>
        
        <div className={styles.fieldsContainer}>
          {/* ... (campos 'gender', 'firstName', 'lastName') ... */}
          <fieldset className={styles.radioGroup}>
            <legend style={{ display: 'none' }}>Gênero</legend>
            <label className={styles.radioLabel}>
              <span className={styles.radioLabelText}>HOMEM</span>
              <input 
                type="radio" name="gender" value="male" 
                checked={formData.gender === 'male'} 
                onChange={handleChange} 
                className={styles.radioInput}
              />
            </label>
            <label className={styles.radioLabel}>
              <span className={styles.radioLabelText}>MULHER</span>
              <input 
                type="radio" name="gender" value="female" 
                checked={formData.gender === 'female'} 
                onChange={handleChange} 
                className={styles.radioInput}
              />
            </label>
          </fieldset>
          
          <div className={styles.row}>
            <ProfileInput label="NOME" name="firstName" value={formData.firstName} onChange={handleChange} />
            <ProfileInput label="SOBRENOME" name="lastName" value={formData.lastName} onChange={handleChange} />
          </div>

          {/* Email */}
          <div>
            <ProfileInput 
              label="EMAIL"
              name="email"
              type="email"
              value={formData.email}
              onChange={handleChange}
              required
            />

           
            {formData.isEmailVerified && (
              <span className={styles.emailVerified}>
                ✓ Email verificado
              </span>
            )}
            
         
          </div>

          <ProfileInput label="ENDEREÇO" name="address" value={formData.address} onChange={handleChange} />
          <ProfileInput label="NÚMERO DE TELEFONE" name="phone" type="tel" value={formData.phone} onChange={handleChange} />
        </div>
      </div>
      
      {/* Botões */}
      <div className={styles.buttonContainer}>
        <ProfileButton type="button" variant="outline" onClick={handleDiscard} disabled={isLoading}>
          DESCARTAR MUDANÇAS
        </ProfileButton>
        <ProfileButton type="submit" variant="primary" disabled={isLoading}>
          {isLoading ? "SALVANDO..." : "SALVAR MUDANÇAS"}
        </ProfileButton>
      </div>
    </form>
  );
}