import { useState } from "react";
import styles from './ProfileInfoPage.module.css';
import ProfileInput from "../../../auth/components/Profile/Producer/ProfileInput"; 
import ProfileButton from "../../../auth/components/Profile/Producer/ProfileButton"; 

export default function ProfileInfoPage() {
  const [formData, setFormData] = useState({
    gender: "",
    firstName: "",
    lastName: "",
    email: "",
    address: "",
    phone: "",

    isEmailVerified: true 
  });
  
  const [isLoading, setIsLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setIsLoading(true);
    console.log("Salvando mudanças:", formData);
    setTimeout(() => {
      setIsLoading(false);
      alert("Perfil salvo!");
    }, 1500);
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
              disabled={false} 
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