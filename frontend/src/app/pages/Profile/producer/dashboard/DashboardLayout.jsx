import { Outlet, NavLink } from "react-router-dom";
import { 
  ArchiveBoxIcon, 
  ShoppingBagIcon,  
  BellIcon          
} from '@heroicons/react/24/solid';
import styles from './DashboardLayout.module.css';
import { useUser } from '../../../../context/UserContext.jsx'; 

function SidebarLink({ to, icon: Icon, label }) {
  return (
    <NavLink
      to={to}
      className={({ isActive }) => 
        `${styles.navItem} ${isActive ? styles.navItemActive : ''}`
      }
    >
      <Icon className={styles.navIcon} />
      <span>{label}</span>
    </NavLink>
  );
}

export default function DashboardLayout() {
  
  const { userName, avatar } = useUser();
  const userRole = "Produtor"; // Mantido fixo

  return (
    <div className={styles.layoutContainer}>
      <aside className={styles.sidebar}>
        <div className={styles.profileCard}>
          
          <img src={avatar} alt="Foto do Produtor" className={styles.profilePic} />
          <h2 className={styles.profileName}>{userName}</h2>
          <p className={styles.profileRole}>{userRole}</p>

        </div>
        
        <nav className={styles.navMenu}>
          <ul>
            <li>
              <SidebarLink 
                to="/producer/dashboard" 
                icon={ArchiveBoxIcon} 
                label="Inventário" 
              />
            </li>
            <li>
              <SidebarLink 
                to="/dashboard/solicitations" 
                icon={ShoppingBagIcon} 
                label="Solicitação de Prod..." 
              />
            </li>
            <li>
              <SidebarLink 
                to="/dashboard/notifications" 
                icon={BellIcon} 
                label="Notificações" 
              />
            </li>
          </ul>
        </nav>

        <footer className={styles.sidebarFooter}>
          <p>Vitória da Conquista BA</p>
        </footer>
      </aside>

      <main className={styles.mainContent}>
        <Outlet /> 
      </main>
    </div>
  );
}