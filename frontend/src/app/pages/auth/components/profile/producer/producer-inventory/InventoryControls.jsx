
import { MagnifyingGlassIcon, AdjustmentsHorizontalIcon, PlusIcon } from '@heroicons/react/24/solid';
import styles from './InventoryControls.module.css'; // Este arquivo você já tem

const InventoryControls = ({ onAddProduct }) => {
  return (
    <>
      <header className={styles.header}>
        <div>
          <h1 className={styles.title}>Inventário</h1>
          <p className={styles.breadcrumb}>Dashboard / Inventário</p>
        </div>
        <button className={styles.btnPrimary} onClick={onAddProduct}>
          <PlusIcon className={styles.btnIcon} /> 
          Adicionar Novo Produto
        </button>
      </header>

      <div className={styles.controls}>
        <div className={styles.searchBar}>
          <MagnifyingGlassIcon className={styles.searchIcon} />
          <input type="text" placeholder="Pesquisar..." />
        </div>
        <button className={styles.btnFilter}>
          <AdjustmentsHorizontalIcon className={styles.btnIcon} />
          Filtrar
        </button>
      </div>
    </>
  );
};

export default InventoryControls;