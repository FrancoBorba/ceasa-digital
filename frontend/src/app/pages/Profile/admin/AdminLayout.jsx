import React, { useState } from "react";
import { Outlet, NavLink, useLocation } from "react-router-dom";
import {
  UserGroupIcon,
  ClipboardDocumentCheckIcon,
  ArchiveBoxIcon,
  CheckBadgeIcon,
  XMarkIcon,
  UserIcon,
} from "@heroicons/react/24/solid";
import styles from "./AdminLayout.module.css";
import { useUser } from "../../../context/UserContext";
import AdminHeaderWidgets from "../../auth/components/profile/admin/AdminHeaderWidgets";

const NotificationDropdown = ({ onClose }) => {
  const [tab, setTab] = useState("Ver todas");

  const notifications = [
    {
      id: 1,
      name: "Cassia Rodrigues",
      desc: "solicitação de cadastro",
      type: "Cadastros",
      img: "https://i.pravatar.cc/40?img=1",
    },
    {
      id: 2,
      name: "Cláudio Nascimento",
      desc: "atualização de preço - Couve",
      type: "Produtos",
      img: "https://i.pravatar.cc/40?img=2",
    },
    {
      id: 3,
      name: "José Mourinha",
      desc: "atualização de preço - Tomate",
      type: "Produtos",
      img: "https://i.pravatar.cc/40?img=3",
    },
  ];

  const filteredNotifs = notifications.filter(
    (n) => tab === "Ver todas" || n.type === tab
  );

  return (
    <div className={styles.dropdownOverlay} onClick={onClose}>
      <div
        className={styles.dropdownContent}
        onClick={(e) => e.stopPropagation()}
      >
        <div className={styles.dropdownHeader}>
          <h3>Notificações</h3>
          <button onClick={onClose} className={styles.closeButton}>
            <XMarkIcon />
          </button>
        </div>
        <div className={styles.dropdownTabs}>
          <button
            onClick={() => setTab("Ver todas")}
            className={tab === "Ver todas" ? styles.tabActive : styles.tab}
          >
            Ver todas
          </button>
          <button
            onClick={() => setTab("Cadastros")}
            className={tab === "Cadastros" ? styles.tabActive : styles.tab}
          >
            Cadastros
          </button>
          <button
            onClick={() => setTab("Produtos")}
            className={tab === "Produtos" ? styles.tabActive : styles.tab}
          >
            Produtos
          </button>
          <span className={styles.markAsRead}>Marcar como vistas</span>
        </div>
        <div className={styles.dropdownBody}>
          {filteredNotifs.map((notif) => (
            <div key={notif.id} className={styles.notifItem}>
              <img
                src={notif.img}
                alt={notif.name}
                className={styles.notifAvatar}
              />
              <div className={styles.notifText}>
                <strong>{notif.name}</strong> {notif.desc}
              </div>
              <button className={styles.notifButton}>
                {notif.type === "Cadastros" ? "Aprovar" : "Avaliar"}
              </button>
            </div>
          ))}
        </div>
        <div className={styles.dropdownFooter}>
          <button>Visualizar todas as notificações</button>
        </div>
      </div>
    </div>
  );
};

function SidebarLink({ to, icon: Icon, label }) {
  const location = useLocation();
  const isActive = location.pathname.startsWith(to);

  return (
    <NavLink
      to={to}
      className={({ isActive: navIsActive }) =>
        `${styles.navItem} ${navIsActive ? styles.navItemActive : ""}`
      }
    >
      <Icon className={styles.navIcon} />
      <span>{label}</span>
    </NavLink>
  );
}

export default function AdminLayout() {
  const { avatar } = useUser();
  const [isNotifOpen, setIsNotifOpen] = useState(false);

  const displayName = "Itamar Franco";
  const userRole = "Administrador";

  return (
    <>
      <div className={styles.layoutContainer}>
        <aside className={styles.sidebar}>
          <div className={styles.profileCard}>
            <img
              src={avatar || "https://i.pravatar.cc/100?img=5"}
              alt="Foto do Admin"
              className={styles.profilePic}
            />
            <div className={styles.profileNameContainer}>
              <h2 className={styles.profileName}>{displayName}</h2>
              <CheckBadgeIcon className={styles.profileBadge} />
            </div>
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
            <AdminHeaderWidgets
              onNotificationClick={() => setIsNotifOpen(true)}
            />
          </header>

          <main className={styles.mainContent}>
            <Outlet />
          </main>
        </div>
      </div>

      {/* O Dropdown de Notificação renderizado no topo */}
      {isNotifOpen && (
        <NotificationDropdown onClose={() => setIsNotifOpen(false)} />
      )}
    </>
  );
}
