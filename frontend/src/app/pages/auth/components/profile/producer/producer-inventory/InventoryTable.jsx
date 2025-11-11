import React from 'react';
import styles from './InventoryTable.module.css';
import { 
  PencilIcon,       // Ícone de Editar
  TrashIcon,        // Ícone de Excluir
  ChevronLeftIcon,  // Ícone de Paginação
  ChevronRightIcon  // Ícone de Paginação
} from '@heroicons/react/24/solid';

const InventoryTable = ({ produtos, onEdit, onDelete }) => {
  return (
    <>
      {/* Contêiner da Tabela para Gerenciamento */}
      <div className={styles.tabelaContainer}>
        <table className={styles.tabela}>
          
          {/* Cabeçalho da Tabela */}
          <thead>
            <tr>
              <th><input type="checkbox" /></th>
              <th>No</th>
              <th>Produto</th>
              <th>ID do produto</th>
              <th>Categoria</th>
              <th>Estoque</th>
              <th>Ação</th>
            </tr>
          </thead>
          
          {/* Corpo da Tabela */}
          <tbody>
            {produtos.map((produto, index) => (
              <tr key={produto.id}>
                <td><input type="checkbox" /></td>
                <td>{String(index + 1).padStart(2, '0')}</td>
                <td>{produto.nome}</td>
                <td>{produto.idProduto}</td>
                <td>
                  <span className={styles.categoriaTag}>
                    {produto.categoria}
                  </span>
                </td>
                <td>{produto.estoque}</td>
                <td>
                  {/* Botões de Ação (História de Usuário: Gerenciar) */}
                  <div className={styles.actionButtons}>
                    <button 
                      className={`${styles.btnIcon} ${styles.btnEdit}`} 
                      onClick={() => onEdit(produto.id)}
                      title="Editar"
                    >
                      <PencilIcon className={styles.icon} />
                    </button>
                    <button 
                      className={`${styles.btnIcon} ${styles.btnDelete}`} 
                      onClick={() => onDelete(produto.id)}
                      title="Excluir"
                    >
                      <TrashIcon className={styles.icon} />
                    </button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Rodapé com Paginação */}
      <footer className={styles.tabelaFooter}>
        <div className={styles.footerMostrar}>
          <label htmlFor="mostrar">Mostrar</label>
          <select id="mostrar" name="mostrar" defaultValue="10">
            <option value="10">10</option>
            <option value="20">20</option>
            <option value="50">50</option>
          </select>
        </div>
        <div className={styles.pagination}>
          <button title="Anterior">
            <ChevronLeftIcon className={styles.icon} />
          </button>
          <button className={styles.pageActive}>1</button>
          <button>2</button>
          <button>3</button>
          <span>...</span>
          <button>10</button>
          <button title="Próxima">
            <ChevronRightIcon className={styles.icon} />
          </button>
        </div>
      </footer>
    </>
  );
};

export default InventoryTable;