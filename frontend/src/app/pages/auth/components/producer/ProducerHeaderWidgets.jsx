import React from 'react';
import styles from './ProducerHeaderWidgets.module.css';
import { BellIcon, EnvelopeIcon, Cog6ToothIcon } from '@heroicons/react/24/outline'; 

const ProducerHeaderWidgets = () => {
  const hasNotifications = false;

  return (
    <div className={styles.widgetsContainer}>
      <button className={styles.iconButton}>
        <BellIcon className={styles.icon} />
        {hasNotifications && <span className={styles.notificationBadge}></span>}
      </button>

      <button className={styles.iconButton}>
        <EnvelopeIcon className={styles.icon} />
      </button>

      <button className={styles.iconButton}>
        <Cog6ToothIcon className={styles.icon} />
      </button>
    </div>
  );
};

export default ProducerHeaderWidgets;
