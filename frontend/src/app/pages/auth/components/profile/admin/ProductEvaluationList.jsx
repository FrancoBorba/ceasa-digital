import React from "react";
import styles from "./ProductEvaluationList.module.css";

const ProductEvaluationList = ({ produtos, onApprove, onDeny }) => {
  return (
    <div className={styles.listContainer}>
      <header className={styles.listHeader}>
        <span className={styles.col1}>Produto</span>
        <span className={styles.col2}>Categoria</span>
        <span className={styles.col3}>Nome do Produtor</span>
        <span className={styles.col4}>Avaliar</span>
      </header>

      <div className={styles.listBody}>
        {produtos.length === 0 ? (
          <p className={styles.noItems}>
            Nenhum produto pendente de avaliação.
          </p>
        ) : (
          produtos.map((solicitacao) => (
            <div key={solicitacao.id} className={styles.listItem}>
              {/* Coluna Produto */}
              <div className={styles.col1}>
                <img
                  src={solicitacao.produtoImgUrl}
                  alt={solicitacao.produtoNome}
                  className={styles.productImage}
                />
                <span className={styles.productName}>
                  {solicitacao.produtoNome}
                </span>
              </div>

              <div className={styles.col2}>
                <span className={styles.categoriaTag}>
                  {solicitacao.produtoCategoriaNome}
                </span>
              </div>
              <div className={styles.col3}>{solicitacao.produtorNome}</div>

              <div className={styles.col4}>
                <button
                  className={`${styles.btn} ${styles.btnApprove}`}
                  onClick={() => onApprove(solicitacao.id)}
                >
                  Aprovar
                </button>
                <button
                  className={`${styles.btn} ${styles.btnDeny}`}
                  onClick={() => onDeny(solicitacao.id)}
                >
                  Negar
                </button>
              </div>
            </div>
          ))
        )}
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