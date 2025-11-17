import React, { useRef, useState, useEffect } from "react";
import GenericRegistrationHeader from "./components/registration/header/GenericRegistrationHeader";
import RegistrationHeaderTitlePhaseOne from "./components/registration/header/RegistrationHeaderTitlePhaseOne";
import RegistrationHeaderTitlePhaseTwo from "./components/registration/header/RegistrationHeaderTitlePhaseTwo";
import RegistrationHeaderTitlePhaseThree from "./components/registration/header/RegistrationHeaderTitlePhaseThree";
import { useForm } from "react-hook-form";
import RegistrationFormFirstPhase from "./components/registration/form/RegistrationFormFirstPhase";
import TurnBackRegistrationButton from "./components/registration/TurnBackRegistrationButton";
import RegistrationFormSecondPhase from "./components/registration/form/RegistrationFormSecondPhase";
import RegistrationFormConfirmationPhase from "./components/registration/form/RegistrationFormConfirmationPhase";
import { useNavigate } from "react-router";
import apiRequester from "./services/apiRequester";
import { useRegistration } from "../../context/RegistrationContext";
import SellerProductsSelector from "./components/registration/SellerProductsSelector";

// Função para buscar produtos públicos
const fetchPublicProducts = async (page = 0, size = 100) => {
  try {
    const response = await apiRequester.get("/api/v1/products", {
      params: { page, size, sortBy: "nome", direction: "asc" },
    });
    return response.data;
  } catch (error) {
    console.error("Erro ao buscar produtos públicos:", error);
    throw error;
  }
};

function SellerRegistrationPage() {
  const [isPhaseOneCompleted, setIsPhaseOneCompleted] = useState(false);
  const [isPhaseTwoCompleted, setIsPhaseTwoCompleted] = useState(false);
  const [registrationPhase, setRegistrationPhase] = useState(0);
  const [headerTitle, setHeaderTitle] = useState(
    <RegistrationHeaderTitlePhaseOne userType={"PRODUTOR"} />
  );
  const formData = useRef({});
  const {
    register,
    handleSubmit,
    formState: { errors },
    watch,
  } = useForm();
  const password = watch("password");
  const navigate = useNavigate();
  const { setRegData } = useRegistration();

  // Estados para produtos
  const [availableProducts, setAvailableProducts] = useState([]);
  const [selectedProductIds, setSelectedProductIds] = useState([]);
  const [productsError, setProductsError] = useState(null);
  const [productsLoading, setProductsLoading] = useState(false);

  // useEffect para buscar produtos ao carregar
  useEffect(() => {
    const loadProducts = async () => {
      setProductsLoading(true);
      setProductsError(null);
      try {
        const productData = await fetchPublicProducts(0, 100);
        setAvailableProducts(productData.content || []);
      } catch (err) {
        setProductsError("Falha ao carregar a lista de produtos.");
      } finally {
        setProductsLoading(false);
      }
    };
    loadProducts();
  }, []);

  const onSubmitFirstPhase = (data) => {
    formData.current = { ...formData.current, userData: data };
    setIsPhaseOneCompleted(true);
    setRegistrationPhase(registrationPhase + 1);

    const firstName = data.name ? data.name.split(/\s+/)[0] : "Usuário";
    setHeaderTitle(<RegistrationHeaderTitlePhaseTwo userName={firstName} />);
  };

  const onSubmitSecondPhase = (data) => {
    if (!selectedProductIds || selectedProductIds.length === 0) {
      alert("Selecione pelo menos um produto que deseje vender.");
      return;
    }

    formData.current = {
      ...formData.current,
      producerData: data,
      produtosIds: selectedProductIds || [],
    };

    setIsPhaseTwoCompleted(true);
    setRegistrationPhase(registrationPhase + 1);
    setHeaderTitle(<RegistrationHeaderTitlePhaseThree />);
    console.log("formData antes de confirmar:", formData);
  };

  const onSubmitConfirmation = async (e) => {
    e.preventDefault();

    const { userData, producerData, produtosIds } = formData.current;

    if (!userData || !producerData || !Array.isArray(produtosIds) || produtosIds.length === 0) {
      alert("Dados incompletos. Por favor, revise o formulário.");
      return;
    }

    try {
      // Criação do usuário
      const userResponse = await apiRequester.post("/auth/register", {
        name: userData.name,
        email: userData.email,
        password: userData.password,
        telefone: producerData.phoneNumber,
        cpf: producerData.cpf,
      });

      const  user  = userResponse.data;
      console.log("Usuário criado:", user);
      setRegData({ userId: user.id });

      // Criação do produtor vinculado
      const producerResponse = await apiRequester.post("/produtor", {
        idUser: user.id,
        numeroDeIdentificacao: producerData.numeroDeIdentificacao,
        tipoDeIdentificacao: producerData.tipoDeIdentificacao,
      });

      const produtor = producerResponse.data;
      console.log("Produtor criado:", produtor);

      const payload = {
        idProdutor: produtor.id,
        produtosIds: produtosIds,
      };
        
      console.log("ENVIANDO PARA /solicitar-venda:", JSON.stringify(payload, null, 2));
        
      const productLinkResponse = await apiRequester.post(
        "/api/v1/produtor-produtos/solicitar-venda",
        payload // Envia o payload
      );

      console.log("Produtos vinculados:", productLinkResponse.data);

      alert("Cadastro completo com sucesso! Agora você já pode acessar o sistema.");
      navigate("/login");
    } catch (err) {
      console.error("Erro no fluxo de cadastro completo:", err);
      const details =
        err.response?.data?.details ||
        err.response?.data?.message ||
        "Erro inesperado ao concluir o cadastro.";
      alert(details);
    }
  };

  // --- Voltar fase ---
  const handleTurnBackRegistration = () => {
    if (registrationPhase > 0) {
      setRegistrationPhase(registrationPhase - 1);
      if (registrationPhase === 1) {
        setHeaderTitle(<RegistrationHeaderTitlePhaseOne userType={"PRODUTOR"} />);
      } else if (registrationPhase === 2) {
        const userName =
          formData.current.userData?.name?.split(/\s+/)[0] || "Usuário";
        setHeaderTitle(<RegistrationHeaderTitlePhaseTwo userName={userName} />);
      }
    }
  };

  return (
    <div className="min-h-screen flex flex-col pt-5 pb-10 bg-[#F9FFF1] relative">
      <GenericRegistrationHeader
        headerTitle={headerTitle}
        isPhaseOneCompleted={isPhaseOneCompleted}
        isPhaseTwoCompleted={isPhaseTwoCompleted}
      />

      <TurnBackRegistrationButton
        onClick={handleTurnBackRegistration}
        registrationPhase={registrationPhase}
      />

      {registrationPhase === 0 && (
        <RegistrationFormFirstPhase
          onSubmit={handleSubmit(onSubmitFirstPhase)}
          register={register}
          errors={errors}
          password={password}
        />
      )}

      {registrationPhase === 1 && (
        <RegistrationFormSecondPhase
          onSubmit={handleSubmit(onSubmitSecondPhase)}
          errors={errors}
          register={register}
          formData={formData}
          userType="PRODUTOR"
        >
          <div className="mt-4">
            <h3 className="text-lg font-semibold mb-3">
              Selecione os Produtos que Deseja Vender
            </h3>
            {productsLoading && <p>Carregando produtos...</p>}
            {productsError && <p className="text-red-500">{productsError}</p>}
            {Array.isArray(availableProducts) && availableProducts.length > 0 && (
              <SellerProductsSelector
                products={availableProducts}
                onSelectionChange={(productObjects) => {
                  const ids = (productObjects || []).map(p => p.id);
                  setSelectedProductIds(ids);
                }}
              />
            )}
          </div>
        </RegistrationFormSecondPhase>
      )}

      {registrationPhase === 2 && (
        <RegistrationFormConfirmationPhase
          formData={formData}
          onSubmit={onSubmitConfirmation}
          userType="PRODUTOR"
        >
          {Array.isArray(formData.current.produtosIds) && (
            <p>
              Produtos selecionados: {formData.current.produtosIds.length} item(s)
            </p>
          )}
        </RegistrationFormConfirmationPhase>
      )}
    </div>
  );
}

export default SellerRegistrationPage;
