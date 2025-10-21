import { Outlet, Link, NavLink, useLocation } from "react-router-dom";
import { useState, useRef } from "react";
import { 
  UserIcon, 
  ViewGridIcon, 
  ShoppingCartIcon, 
  HomeIcon,
  LockClosedIcon,
} from '@heroicons/react/solid';
import { PencilIcon } from '@heroicons/react/outline';
import styles from './ProfileLayout.module.css';

function MainSidebarLink({ to, icon: Icon, label }) {
  const location = useLocation();
  const isActive = location.pathname.startsWith(to);

  return (
    <Link 
      to={to} 
      className={`${styles.mainSidebarLink} ${isActive ? styles.mainSidebarLinkActive : ''}`}
    >
      <Icon className={styles.mainSidebarIcon} />
      <span>{label}</span>
    </Link>
  );
}

function SubSidebarLink({ to, icon: Icon, label }) {
  const location = useLocation();
  const isActive = location.pathname.endsWith(to.split('/').pop()); 

  return (
    <NavLink
      to={to}
      className={`${styles.subLink} ${isActive ? styles.subLinkActive : ''}`}
      style={isActive ? {
        backgroundColor: '#E6F4E6',
        color: '#1D3D1C',
        fontWeight: 'bold',
      } : {}}
    >
      <Icon className={styles.subLinkIcon} />
      <span>{label}</span>
    </NavLink>
  );
}

export default function ProfileLayout() {
  
  const avatarPlaceholder = "https://i.pravatar.cc/150";
  const [avatarPreview, setAvatarPreview] = useState(avatarPlaceholder);
  const fileInputRef = useRef(null);

  const handleImageChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      if (avatarPreview && avatarPreview !== avatarPlaceholder) {
        URL.revokeObjectURL(avatarPreview);
      }
      const newPreviewUrl = URL.createObjectURL(file);
      setAvatarPreview(newPreviewUrl);
      console.log("Arquivo selecionado para upload:", file);
    }
  };

  const handleEditClick = () => {
    fileInputRef.current.click();
  };


  return (
    <div className={styles.layoutContainer}>
      
      <aside className={styles.mainSidebar}>
        <MainSidebarLink to="/profile-view" icon={UserIcon} label="PERFIL" />
        <MainSidebarLink to="/dashboard" icon={ViewGridIcon} label="DASHBOARD" />
        <MainSidebarLink to="/cart" icon={ShoppingCartIcon} label="COMPRAS" />
        <MainSidebarLink to="/edit-profile/info" icon={PencilIcon} label="EDITAR PERFIL" />
        <MainSidebarLink to="/" icon={HomeIcon} label="HOME" />
      </aside>
      
      <main className={styles.mainContentArea}>
        <div className={styles.contentBox}>
          
          <nav className={styles.subSidebar}>
            <div className={styles.subSidebarProfile}>

              <div className={styles.avatarContainer}>
                <img 
                  src={avatarPreview}
                  alt="Usuário" 
                  className={styles.avatar}
                />
                <input 
                  type="file"
                  ref={fileInputRef}
                  onChange={handleImageChange}
                  accept="image/*"
                  style={{ display: 'none' }}
                />
                <button 
                  type="button"
                  onClick={handleEditClick}
                  className={styles.editAvatarButton}
                >
                  <PencilIcon className={styles.editAvatarIcon} />
                </button>
              </div>

              <h2 className={styles.profileName}>USUÁRIO</h2>
              <span className={styles.profileRole}>Produtor</span>
            </div>
            
            <ul className={styles.subLinkList}>
              <li>
                <SubSidebarLink 
                  to="/edit-profile/info" 
                  icon={UserIcon} 
                  label="INFORMAÇÕES PESSOAIS" 
                />
              </li>
              <li>
                <SubSidebarLink 
                  to="/edit-profile/security" 
                  icon={LockClosedIcon} 
                  label="LOGIN E SENHA" 
                />
              </li>
            </ul>
          </nav>

          <div className={styles.pageContent}>
            <Outlet />
          </div>
        </div>
      </main>
    </div>
  );
}