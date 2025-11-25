import { Outlet, NavLink } from "react-router-dom";
import {
  UserGroupIcon,
  ClipboardDocumentCheckIcon,
  ArchiveBoxIcon,
  UserIcon,
} from "@heroicons/react/24/solid";
import styles from "./AdminLayout.module.css";
import AdminHeaderWidgets from "../auth/components/admin/AdminHeaderWidgets";

function SidebarLink({ to, icon: Icon, label }) {
  return (
    <NavLink
      to={to}
      className={({ isActive }) =>
        `${styles.navItem} ${isActive ? styles.navItemActive : ""}`
      }
    >
      <Icon className={styles.navIcon} />
      <span>{label}</span>
    </NavLink>
  );
}

export default function AdminLayout() {
  const userName = "Joao Silva";
  const avatar = "/images/admin.jpg";
  const userRole = "Administrador";

  return (
    <div className={styles.layoutContainer}>
      <aside className={styles.sidebar}>
        <div className={styles.profileCard}>
          <img src={avatar} alt="Foto do Admin" className={styles.profilePic} />
          <h2 className={styles.profileName}>{userName}</h2>
          <p className={styles.profileRole}>{userRole}</p>
        </div>

        <nav className={styles.navMenu}>
          <ul>
            <li>
              <SidebarLink
                to="/admin/evaluation"
                icon={ClipboardDocumentCheckIcon}
                label="Solicitações de Venda"
              />
            </li>
            <li>
              <SidebarLink
                to="/admin/products"
                icon={ArchiveBoxIcon}
                label="Solicitar Produtos"
              />
            </li>
            <li>
              <SidebarLink
                to="/user/edit-profile"
                icon={UserIcon}
                label="Editar Perfil"
              />
            </li>
          </ul>
        </nav>

        <footer className={styles.sidebarFooter}>
          <p>Vitória da Conquista BA</p>
        </footer>
      </aside>
      <div className={styles.contentWrapper}>
        <header className={styles.topHeader}>
          <AdminHeaderWidgets />
        </header>

        <main className={styles.mainContent}>
          <Outlet />
        </main>
      </div>
    </div>
  );
}
