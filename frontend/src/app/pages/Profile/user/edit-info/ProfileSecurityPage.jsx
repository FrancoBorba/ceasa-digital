import styles from './ProfileSecurityPage.module.css';
import ProfileInput from "../../../auth/components/profile/ProfileInput";
import ProfileButton from "../../../auth/components/profile/ProfileButton";

export default function ProfileSecurityPage() {
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