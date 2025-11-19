import React, { useState } from 'react';
import styles from './ProducerListTable.module.css';
import { PencilIcon, TrashIcon, ChevronLeftIcon, ChevronRightIcon } from '@heroicons/react/24/solid';

const AvatarWithInitials = ({ initials }) => {
  const colors = [styles.avatarColor1, styles.avatarColor2, styles.avatarColor3, styles.avatarColor4, styles.avatarColor5, styles.avatarColor6];
  const charCode = initials.charCodeAt(0) || 0;
  const colorClass = colors[charCode % colors.length];
  
  return (
    <div className={`${styles.avatar} ${colorClass}`}>{initials}</div>
  );
};

const StatusBadge = ({ status }) => {
  const isAtivo = status === 'Ativo';
  const colorClass = isAtivo ? styles.badgeAtivo : styles.badgeInativo;
  return (
    <span className={`${styles.badge} ${colorClass}`}>{status}</span>
  );
};


const mockProducers = [
  { id: 1, name: 'Bruno Sena', initials: 'BS', email: 'bruno.sena@example.com', phone: '(11) 9 8123-4567', status: 'Ativo' },
  { id: 2, name: 'Camila Santos', initials: 'CS', email: 'camila.santos@example.com', phone: '(21) 9 7210-3345', status: 'Inativo' },
  { id: 3, name: 'Maria Dulce', initials: 'MD', email: 'maria.dulce@example.com', phone: '(31) 9 9345-2211', status: 'Ativo' },
  { id: 4, name: 'Antonio Peixoto', initials: 'AP', email: 'antonio.peixoto@example.com', phone: '(77) 99999-9887', status: 'Inativo' },
  { id: 5, name: 'Vicente Barbosa', initials: 'VB', email: 'vicente.barbosa@example.com', phone: '(71) 9 9877-0099', status: 'Ativo' },
  { id: 6, name: 'Julio Nardes', initials: 'JN', email: 'julio.nardes@example.com', phone: '(85) 9 8988-7766', status: 'Ativo' },
];

const ProducerListTable = ({ searchTerm }) => {
  const [producers, setProducers] = useState(mockProducers);

  const filteredProducers = producers.filter(p => 
    p.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
    p.email.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const handleDelete = (id) => {
    if (window.confirm("Tem certeza que deseja excluir este produtor?")) {
      setProducers(producers.filter(p => p.id !== id));
    }
  };

  const [currentPage, setCurrentPage] = useState(1);
  const totalPages = 10; // Exemplo

  return (
    <div className={styles.tableContainer}>
      <table className={styles.table}>
        <thead>
          <tr>
            <th>Nome</th>
            <th>Email</th>
            <th>Fone</th>
            <th>Status</th>
            <th>Editar</th>
          </tr>
        </thead>
        <tbody>
          {filteredProducers.map(producer => (
            <tr key={producer.id}>
              <td>
                <div className={styles.nameCell}>
                  <AvatarWithInitials initials={producer.initials} />
                  <span>{producer.name}</span>
                </div>
              </td>
              <td>{producer.email}</td>
              <td>{producer.phone}</td>
              <td><StatusBadge status={producer.status} /></td>
              <td>
                <div className={styles.actionCell}>
                  <button className={styles.btnIcon} title="Editar">
                    <PencilIcon />
                  </button>
                  <button className={`${styles.btnIcon} ${styles.btnDelete}`} onClick={() => handleDelete(producer.id)} title="Excluir">
                    <TrashIcon />
                  </button>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      
      <footer className={styles.tableFooter}>
        <div className={styles.footerMostrar}>
          <label htmlFor="mostrar">Mostrar</label>
          <select id="mostrar" name="mostrar" defaultValue="10">
            <option value="10">10</option>
            <option value="20">20</option>
            <option value="50">50</option>
          </select>
        </div>
        <div className={styles.pagination}>
          <button disabled={currentPage === 1}><ChevronLeftIcon /></button>
          <button className={styles.pageActive}>1</button>
          <button>2</button>
          <button>3</button>
          <span>...</span>
          <button>10</button>
          <button disabled={currentPage === totalPages}><ChevronRightIcon /></button>
        </div>
      </footer>
    </div>
  );
};

export default ProducerListTable;