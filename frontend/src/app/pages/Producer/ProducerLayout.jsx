import { Outlet, NavLink } from "react-router-dom";
import {
  UserGroupIcon,
  ClipboardDocumentCheckIcon,
  ArchiveBoxIcon,
} from "@heroicons/react/24/solid";
import styles from "./ProducerLayout.module.css";
import ProducerHeaderWidgets from "../auth/components/producer/ProducerHeaderWidgets";

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

export default function ProducerLayout() {
  const userName = "Maria Silva";
  const avatar = "/images/producer.jpg";
  const userRole = "Produtor";

  return (
    <div className={styles.layoutContainer}>
      <aside className={styles.sidebar}>
        <div className={styles.profileCard}>
          <img
            src={avatar}
            alt="Foto do Produtor"
            className={styles.profilePic}
          />
          <h2 className={styles.profileName}>{userName}</h2>
          <p className={styles.profileRole}>{userRole}</p>
        </div>

        <nav className={styles.navMenu}>
          <ul>
            <li>
              <SidebarLink
                to="/producer/producers"
                icon={UserGroupIcon}
                label="Lista dos Produtores"
              />
            </li>
            <li>
              <SidebarLink
                to="/producer/registrations"
                icon={ClipboardDocumentCheckIcon}
                label="Avaliar Cadastros"
              />
            </li>
            <li>
              <SidebarLink
                to="/producer/products"
                icon={ArchiveBoxIcon}
                label="Solicitação de Produtos"
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
          <ProducerHeaderWidgets />
        </header>

        <main className={styles.mainContent}>
          <Outlet />
        </main>
      </div>
    </div>
  );
}
