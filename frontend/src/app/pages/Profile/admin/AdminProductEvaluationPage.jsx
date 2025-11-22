import React, { useState, useEffect } from "react";
import styles from "./AdminProductEvaluationPage.module.css";
import { MagnifyingGlassIcon } from "@heroicons/react/24/solid";
import ProductEvaluationList from "../../auth/components/profile/admin/ProductEvaluationList";

import apiRequester from "../../auth/services/apiRequester";

const AdminProductEvaluationPage = () => {
  const [produtos, setProdutos] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchSolicitacoes = async () => {
      try {
        setLoading(true);
        const response = await apiRequester.get(
          "/api/v1/produtor-produtos?status=STATUS_PENDENTE"
        );

        setProdutos(response.data);
      } catch (error) {
        console.error("Erro ao buscar solicitações:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchSolicitacoes();
  }, []);

  /**
   * 3. Atualiza handleApprove para chamar a API
   */
  const handleApprove = async (idSolicitacao) => {
    try {
      // 4. Use o apiRequester e o caminho completo do endpoint
      await apiRequester.put(
        `/api/v1/produtor-produtos/admin/avaliar/${idSolicitacao}?status=STATUS_ATIVO`
      );

      setProdutos((produtosAtuais) =>
        produtosAtuais.filter((p) => p.id !== idSolicitacao)
      );
    } catch (error) {
      console.error(`Erro ao aprovar solicitação ${idSolicitacao}:`, error);
    }
  };

  /**
   * 4. Atualiza handleDeny para chamar a API
   */
  const handleDeny = async (idSolicitacao) => {
    try {
      // 5. Use o apiRequester e o caminho completo do endpoint
      await apiRequester.put(
        `/api/v1/produtor-produtos/admin/avaliar/${idSolicitacao}?status=STATUS_REJEITADO`
      );

      setProdutos((produtosAtuais) =>
        produtosAtuais.filter((p) => p.id !== idSolicitacao)
      );
    } catch (error) {
      console.error(`Erro ao negar solicitação ${idSolicitacao}:`, error);
    }
  };

  return (
    <div className={styles.wrapper}>
      <header className={styles.header}>
        <div>
          <h1 className={styles.title}>Lista de produtos</h1>
          <p className={styles.breadcrumb}>Dashboard / Lista de Prod...</p>
        </div>
        <div className={styles.searchBar}>
          <MagnifyingGlassIcon />
          <input type="text" placeholder="Pesquisar..." />
        </div>
      </header>

      {loading ? (
        <p>Carregando solicitações...</p>
      ) : (
        <ProductEvaluationList
          produtos={produtos}
          onApprove={handleApprove}
          onDeny={handleDeny}
        />
      )}
    </div>
  );
};

export default AdminProductEvaluationPage;