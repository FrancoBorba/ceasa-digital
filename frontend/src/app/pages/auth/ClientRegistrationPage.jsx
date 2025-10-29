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

function ClientRegistrationPage() {
  const [isPhaseOneCompleted, setIsPhaseOneCompleted] = useState(false);
  const [isPhaseTwoCompleted, setIsPhaseTwoCompleted] = useState(false);
  const [registrationPhase, setRegistrationPhase] = useState(0);
  const [headerTitle, setHeaderTitle] = useState(<RegistrationHeaderTitlePhaseOne userType={"CLIENTE"} />);
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
      setHeaderTitle(<RegistrationHeaderTitlePhaseOne userType={"CLIENTE"} />);
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
        />
      )}

      {registrationPhase == 2 && (
        <RegistrationFormConfirmationPhase formData={formData} onSubmit={onSubmitConfirmation} />
      )}
    </div>
  );
}

export default ClientRegistrationPage;
