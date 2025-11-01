import styles from './ProfileButton.module.css';

function ProfileButton({ children, type = "button", variant = "primary", onClick, disabled = false }) {

  const classNames = [
    styles.baseButton,
    variant === 'primary' ? styles.primary : styles.outline
  ].join(' ');

  return (
    <button 
      type={type} 
      className={classNames}
      onClick={onClick}
      disabled={disabled}
    >
      {children}
    </button>
  );
}
export default ProfileButton;