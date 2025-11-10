import { useRef, useState } from "react";
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

function ClientRegistrationPage() {
  const [isPhaseOneCompleted, setIsPhaseOneCompleted] = useState(false);
  const [isPhaseTwoCompleted, setIsPhaseTwoCompleted] = useState(false);
  const [registrationPhase, setRegistrationPhase] = useState(0);
  const [headerTitle, setHeaderTitle] = useState(
    <RegistrationHeaderTitlePhaseOne userType={"CLIENTE"} />
  );

  const formData = useRef();

  const {
    register,
    handleSubmit,
    formState: { errors },
    watch,
  } = useForm();

  const password = watch("password");
  const navigate = useNavigate();

  // ----- FASE 1 -----
  const onSubmitFirstPhase = (data) => {
    setIsPhaseOneCompleted(true);
    setRegistrationPhase(registrationPhase + 1);
    setHeaderTitle(<RegistrationHeaderTitlePhaseTwo userName={data.name.split(/\s+/)[0]} />);
    formData.current = { ...data }; // salvar dados da fase 1
  };

  // ----- FASE 2 -----
  const onSubmitSecondPhase = (data) => {
    setIsPhaseTwoCompleted(true);
    setRegistrationPhase(registrationPhase + 1);
    setHeaderTitle(<RegistrationHeaderTitlePhaseThree />);
    formData.current = {
      ...formData.current,
      // Dados de endereço e telefone/CPF
      cep: data.cep,
      logradouro: data.logradouro,
      numero: data.numero,
      complemento: data.complemento,
      bairro: data.bairro,
      cidade: data.cidade,
      estado: data.estado,
      phoneNumber: data.phoneNumber,
      cpf: data.cpf,
    };
  };

  // ----- CONFIRMAÇÃO -----
 const onSubmitConfirmation = async (e) => {
  e.preventDefault();

  if (!formData.current) {
    alert("Dados de registro incompletos.");
    return;
  }

  try {
    // Criar usuário
    const userResponse = await apiRequester.post('/auth/register', {
      name: formData.current.name,
      email: formData.current.email,
      password: formData.current.password,
      telefone: formData.current.phoneNumber, // sem formatação
      cpf: formData.current.cpf,              // sem formatação
    });

    // Criar endereço
    await apiRequester.post('/enderecos', {
      cep: formData.current.cep,
      logradouro: formData.current.logradouro,
      numero: formData.current.numero,
      complemento: formData.current.complemento,
      bairro: formData.current.bairro,
      cidade: formData.current.cidade,
      estado: formData.current.estado,
    });

    alert("Registro completo feito com sucesso!");
    // Redirecionar para login
    navigate("/login");
  } catch (err) {
    console.error(err);
    alert(err.response?.data?.details || "Erro no registro");
  }
};



  // ----- VOLTAR -----
  const handleTurnBackRegistration = () => {
    setRegistrationPhase(registrationPhase - 1);

    if (registrationPhase === 0) {
      navigate("/select-register");
      return;
    }

    if (registrationPhase === 1) {
      setIsPhaseOneCompleted(false);
      setHeaderTitle(<RegistrationHeaderTitlePhaseOne userType={"CLIENTE"} />);
      return;
    }

    if (registrationPhase === 2) {
      setIsPhaseTwoCompleted(false);
      setHeaderTitle(
        <RegistrationHeaderTitlePhaseTwo userName={formData.current.name.split(/\s+/)[0]} />
      );
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
          formData={formData.current}
        />
      )}

      {registrationPhase === 2 && (
        <RegistrationFormConfirmationPhase
          formData={formData.current}
          onSubmit={onSubmitConfirmation}
        />
      )}
    </div>
  );
}

export default ClientRegistrationPage;
