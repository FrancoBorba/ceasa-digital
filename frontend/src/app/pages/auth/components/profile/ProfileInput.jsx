import styles from './ProfileInput.module.css';

function ProfileInput({ label, name, value, onChange, type = "text", disabled = false }) {
  return (
    <div className={styles.container}>
      <label className={styles.label} htmlFor={name}>{label}:</label>
      <input
        id={name}
        className={styles.input}
        type={type}
        name={name}
        value={value}
        onChange={onChange}
        disabled={disabled}
        required={!disabled}
      />
    </div>
  );
}
export default ProfileInput;