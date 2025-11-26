import { useState, useEffect } from "react";
import styles from './ProfileInfoPage.module.css';
import ProfileInput from "../../../auth/components/profile/ProfileInput"; 
import ProfileButton from "../../../auth/components/profile/ProfileButton"; 
import { useUser } from "../../../../context/UserContext";

export default function ProfileInfoPage() {
  const { setUserName, setAvatar } = useUser();
  const [isLoading, setIsLoading] = useState(false);
  
  const [formData, setFormData] = useState({
    name: "", // Nome Completo
    email: "",
    cpf: "",
    telefone: "",
    address: ""
  });

  const API_URL = 'http://localhost:8080';

  useEffect(() => {
    async function fetchUserData() {
      const token = localStorage.getItem('authToken');
      try {
        const response = await fetch(`${API_URL}/users/me`, {
          headers: { 'Authorization': `Bearer ${token}` }
        });
        
        if (response.ok) {
          const data = await response.json();
          setFormData(prev => ({
            ...prev,
            name: data.name || "",
            email: data.email || "",
            cpf: data.cpf || "",
            telefone: data.telefone || "",
            address: data.address || ""
          }));
          
          if (data.avatarUrl) setAvatar(data.avatarUrl);
        }
      } catch (error) {
        console.error(error);
      }
    }
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
        body: JSON.stringify({
          name: formData.name,
          email: formData.email,
          telefone: formData.telefone,
          cpf: formData.cpf
        })
      });

      if (!response.ok) throw new Error('Erro ao atualizar');

      const responseData = await response.json();
      setUserName(responseData.name); 
      alert("Perfil atualizado com sucesso!");

    } catch (error) {
      console.error(error);
      alert("Não foi possível atualizar suas informações.");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} className={styles.form}>
      <div>
        <h1 className={styles.header}>INFORMAÇÕES PESSOAIS</h1>
        
        <div className={styles.fieldsContainer}>
          {/* Apenas um campo para Nome Completo */}
          <ProfileInput 
            label="NOME COMPLETO" 
            name="name" 
            value={formData.name} 
            onChange={handleChange} 
          />

          <div>
            <ProfileInput 
              label="EMAIL"
              name="email"
              type="email"
              value={formData.email}
              disabled={true} 
            />
            <span className={styles.emailVerified}>
              ✓ Email verificado
            </span>
          </div>

          <ProfileInput 
            label="CPF" 
            name="cpf" 
            value={formData.cpf} 
            onChange={handleChange} 
          />

          <ProfileInput 
            label="ENDEREÇO" 
            name="address" 
            value={formData.address} 
            onChange={handleChange} 
          />

          <ProfileInput 
            label="NÚMERO DE TELEFONE" 
            name="telefone" 
            type="tel" 
            value={formData.telefone} 
            onChange={handleChange} 
          />
        </div>
      </div>
      
      <div className={styles.buttonContainer}>
        <ProfileButton type="button" variant="outline" disabled={isLoading}>
          DESCARTAR MUDANÇAS
        </ProfileButton>
        <ProfileButton type="submit" variant="primary" disabled={isLoading}>
          {isLoading ? "SALVANDO..." : "SALVAR MUDANÇAS"}
        </ProfileButton>
      </div>
    </form>
  );
}