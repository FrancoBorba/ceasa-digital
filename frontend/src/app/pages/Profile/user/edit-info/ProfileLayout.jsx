import { Outlet, Link, NavLink, useLocation } from "react-router-dom";
import { useRef } from "react"; 
import { 
  UserIcon, 
  Squares2X2Icon, 
  ShoppingCartIcon, 
  HomeIcon,
  LockClosedIcon,
} from '@heroicons/react/24/solid';
import { PencilIcon } from '@heroicons/react/24/solid';
import styles from './ProfileLayout.module.css';


import { useUser } from '../../../../context/UserContext.jsx'; 

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

  const isInfoActive = to === "/user/edit-profile" && location.pathname === "/user/edit-profile";
  const finalIsActive = (to === "/user/edit-profile") ? isInfoActive : isActive;

  return (
    <NavLink
      to={to}
      className={`${styles.subLink} ${finalIsActive ? styles.subLinkActive : ''}`}
      style={finalIsActive ? {
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
  
  const { userName, avatar, setAvatar } = useUser();
  const userRole = "Usuario"; 

  const avatarPlaceholder = "https://i.pravatar.cc/150";
  const fileInputRef = useRef(null);

  const handleImageChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      if (avatar && avatar !== avatarPlaceholder) {
        URL.revokeObjectURL(avatar);
      }
      const newPreviewUrl = URL.createObjectURL(file);
      
      setAvatar(newPreviewUrl); 
      
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
        <MainSidebarLink to="/" icon={Squares2X2Icon} label="DASHBOARD" />
        <MainSidebarLink to="/user/edit-profile" icon={PencilIcon} label="EDITAR PERFIL" />
        <MainSidebarLink to="/" icon={HomeIcon} label="HOME" />
      </aside>
      
      <main className={styles.mainContentArea}>
        <div className={styles.contentBox}>
          
          <nav className={styles.subSidebar}>
            <div className={styles.subSidebarProfile}>

              <div className={styles.avatarContainer}>
                <img 
                  src={avatar}
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

              <h2 className={styles.profileName}>{userName.toUpperCase()}</h2>
              <span className={styles.profileRole}>{userRole}</span>
            </div>
            
            <ul className={styles.subLinkList}>
              <li>
                <SubSidebarLink 
                  to="/user/edit-profile" 
                  icon={UserIcon} 
                  label="INFORMAÇÕES PESSOAIS" 
                />
              </li>
              <li>
                <SubSidebarLink 
                  to="/user/edit-profile/security" 
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