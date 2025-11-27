import React, { useState } from 'react';
import styles from './AdminProducerListPage.module.css';
import { MagnifyingGlassIcon } from '@heroicons/react/24/solid';
import ProducerListTable from '../../auth/components/profile/admin/ProducerListTable';

const ProducerListPage = () => {
  const [searchTerm, setSearchTerm] = useState('');

  return (
    <div className={styles.wrapper}>
      <header className={styles.header}>
        <div>
          <h1 className={styles.title}>Lista dos Produtores</h1>
          <p className={styles.breadcrumb}>Dashboard / Lista dos Produtores</p>
        </div>
        <div className={styles.searchBar}>
          <MagnifyingGlassIcon className={styles.searchIcon} />
          <input 
            type="text" 
            placeholder="Pesquisar..." 
            className={styles.searchInput}
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>
      </header>
      
      <ProducerListTable searchTerm={searchTerm} />
    </div>
  );
};

export default ProducerListPage;