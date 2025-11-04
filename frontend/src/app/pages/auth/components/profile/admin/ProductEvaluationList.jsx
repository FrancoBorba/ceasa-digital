import React from 'react';
import styles from './ProductEvaluationList.module.css';

const ProductEvaluationList = ({ produtos, onApprove, onDeny }) => {
  return (
    <div className={styles.listContainer}>
      {/* Cabeçalho da Lista */}
      <header className={styles.listHeader}>
        <span className={styles.col1}>Produto</span>
        <span className={styles.col2}>Categoria</span>
        <span className={styles.col3}>Nome do Produtor</span>
        <span className={styles.col4}>Avaliar</span>
      </header>

      {/* Itens da Lista */}
      <div className={styles.listBody}>
        {produtos.map(produto => (
          <div key={produto.id} className={styles.listItem}>
            {/* Coluna Produto */}
            <div className={styles.col1}>
              <img src={produto.img} alt={produto.nome} className={styles.productImage} />
              <span className={styles.productName}>{produto.nome}</span>
            </div>
            
            {/* Coluna Categoria */}
            <div className={styles.col2}>
              <span className={styles.categoriaTag}>{produto.categoria}</span>
            </div>
            
            {/* Coluna Quantidade */}
            <div className={styles.col3}>
              {produto.nomeDoProdutor}
            </div>
            
            {/* Coluna Avaliar (Botões) */}
            <div className={styles.col4}>
              <button 
                className={`${styles.btn} ${styles.btnApprove}`}
                onClick={() => onApprove(produto.id)}
              >
                Aprovar
              </button>
              <button 
                className={`${styles.btn} ${styles.btnDeny}`}
                onClick={() => onDeny(produto.id)}
              >
                Negar
              </button>
            </div>
          </div>
        ))}
      </div>
      

      <footer className={styles.listFooter}>
        <div className={styles.footerMostrar}>
          <label htmlFor="mostrar">Mostrar</label>
          <select id="mostrar" name="mostrar" defaultValue="10">
            <option value="10">10</option>
            <option value="20">20</option>
            <option value="50">50</option>
          </select>
        </div>
        <div className={styles.pagination}>
          <button>{"<"}</button>
          <button className={styles.pageActive}>1</button>
          <button>2</button>
          <button>3</button>
          <span>...</span>
          <button>10</button>
          <button>{">"}</button>
        </div>
      </footer>
    </div>
  );
};

export default ProductEvaluationList;