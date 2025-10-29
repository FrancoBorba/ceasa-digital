import { useRef, useState } from "react";
import GenericRegistrationHeader from "./components/registration/header/GenericRegistrationHeader";
import UserRegistrationInfo from "./components/registration/form/UserRegistrationInfo";
import RegistrationHeaderTitlePhaseOne from "./components/registration/header/RegistrationHeaderTitlePhaseOne";
import RegistrationHeaderTitlePhaseTwo from "./components/registration/header/RegistrationHeaderTitlePhaseTwo";
import RegistrationHeaderTitlePhaseThree from "./components/registration/header/RegistrationHeaderTitlePhaseThree";
import { useForm } from "react-hook-form";
import RegistrationFormFirstPhase from "./components/registration/form/RegistrationFormFirstPhase";
import TurnBackRegistrationButton from "./components/registration/TurnBackRegistrationButton";
import RegistrationFormSecondPhase from "./components/registration/form/RegistrationFormSecondPhase";
import RegistrationFormConfirmationPhase from "./components/registration/form/RegistrationFormConfirmationPhase";
import RegistrationInput from "./components/registration/form/RegistrationInput";
import { useNavigate } from "react-router";

function DeliveryManRegistrationpage() {
  const [isPhaseOneCompleted, setIsPhaseOneCompleted] = useState(false);
  const [isPhaseTwoCompleted, setIsPhaseTwoCompleted] = useState(false);
  const [registrationPhase, setRegistrationPhase] = useState(0);
  const [headerTitle, setHeaderTitle] = useState(<RegistrationHeaderTitlePhaseOne userType={"ENTREGADOR"} />);
  const formData = useRef();
  const {
    register,
    handleSubmit,
    formState: { errors },
    watch,
  } = useForm();
  const password = watch("password");
  const navigate = useNavigate();

  const onSubmitFirstPhase = (data) => {
    setIsPhaseOneCompleted(true);
    setRegistrationPhase(registrationPhase + 1);
    setHeaderTitle(<RegistrationHeaderTitlePhaseTwo userName={data.name.split(/\s+/)[0]} />);
  };

  const onSubmitSecondPhase = (data) => {
    setIsPhaseTwoCompleted(true);
    setRegistrationPhase(registrationPhase + 1);
    setHeaderTitle(<RegistrationHeaderTitlePhaseThree />);
    formData.current = { ...data };
  };

  const onSubmitConfirmation = (e) => {
    // TODO: Integration
    e.preventDefault();
    console.log(formData);
  };

  const handleTurnBackRegistration = () => {
    setRegistrationPhase(registrationPhase - 1);

    if (registrationPhase == 0) {
      navigate("/select-register");
      return;
    }

    if (registrationPhase == 1) {
      setIsPhaseOneCompleted(false);
      setHeaderTitle(<RegistrationHeaderTitlePhaseOne userType={"ENTREGADOR"} />);
      return;
    }

    if (registrationPhase == 2) {
      setIsPhaseTwoCompleted(false);
      setHeaderTitle(<RegistrationHeaderTitlePhaseTwo userName={formData.current.name.split(/\s+/)[0]} />);
    }
  };

  return (
    <div className="min-h-screen flex flex-col pt-5 pb-10 bg-[#F9FFF1] relative">
      <GenericRegistrationHeader
        headerTitle={headerTitle}
        isPhaseOneCompleted={isPhaseOneCompleted}
        isPhaseTwoCompleted={isPhaseTwoCompleted}
      />

      <TurnBackRegistrationButton onClick={handleTurnBackRegistration} registrationPhase={registrationPhase} />

      {registrationPhase == 0 && (
        <RegistrationFormFirstPhase
          onSubmit={handleSubmit(onSubmitFirstPhase)}
          register={register}
          errors={errors}
          password={password}
        />
      )}

      {registrationPhase == 1 && (
        <RegistrationFormSecondPhase
          onSubmit={handleSubmit(onSubmitSecondPhase)}
          errors={errors}
          register={register}
          formData={formData}
        >
          <RegistrationInput
            labelName={"CNH"}
            type={"text"}
            registration={register("cnh", {
              required: "É necessário inserir o seu CNH.",
              pattern: {
                value: /^[\d]{11}/,
                message: "Deve ser inserido apenas numeros.",
              },
              minLength: {
                value: 11,
                message: "Deve ser inserido o CNH completo.",
              },
              onChange: (e) => {
                const value = e.target.value;
                e.target.value = value.length > 11 ? value.substring(0, 11) : value;
              },
            })}
            errors={errors?.cnh}
            value={formData?.cnh}
          />
          <RegistrationInput
            labelName={"TIPO DO VEICULO"}
            type={"text"}
            registration={register("vehicleType", {
              required: "É necessário inserir o tipo do seu veículo.",
              maxLength: {
                value: 30,
                message: "O tipo do veículo deve ter no máximo 30 caracteres.",
              },
            })}
            errors={errors?.vehicleType}
            value={formData?.vehicleType}
          />
        </RegistrationFormSecondPhase>
      )}

      {registrationPhase == 2 && (
        <RegistrationFormConfirmationPhase formData={formData} onSubmit={onSubmitConfirmation}>
          <UserRegistrationInfo labelName={"CNH"} value={formData?.current?.cnh} />
          <UserRegistrationInfo labelName={"TIPO DO VEICULO"} value={formData?.current?.vehicleType} />
        </RegistrationFormConfirmationPhase>
      )}
    </div>
  );
}

export default DeliveryManRegistrationpage;
