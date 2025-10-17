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
import SellerProductsSelector from "./components/registration/SellerProductsSelector";

function SellerRegistrationPage() {
  const [selectedProducts, setSelectedProducts] = useState([]);
  const [isPhaseOneCompleted, setIsPhaseOneCompleted] = useState(false);
  const [isPhaseTwoCompleted, setIsPhaseTwoCompleted] = useState(false);
  const [registrationPhase, setRegistrationPhase] = useState(0);
  const [headerTitle, setHeaderTitle] = useState(<RegistrationHeaderTitlePhaseOne userType={"PRODUTOR"} />);
  const formData = useRef();
  const {
    register,
    handleSubmit,
    formState: { errors },
    watch,
  } = useForm();
  const password = watch("password");

  const onSubmitFirstPhase = (data) => {
    setIsPhaseOneCompleted(true);
    setRegistrationPhase(registrationPhase + 1);
    setHeaderTitle(<RegistrationHeaderTitlePhaseTwo userName={data.name.split(/\s+/)[0]} />);
  };

  const onSubmitSecondPhase = (data) => {
    if (selectedProducts.length == 0) {
      alert("Selecione pelomenos um produto que deseje vender.");
      return;
    }

    setIsPhaseTwoCompleted(true);
    setRegistrationPhase(registrationPhase + 1);
    setHeaderTitle(<RegistrationHeaderTitlePhaseThree />);
    formData.current = { ...data, products: selectedProducts };
  };

  const onSubmitConfirmation = (e) => {
    // TODO: Integration
    e.preventDefault();
    console.log(formData);
  }

  const handleTurnBackRegistration = () => {
    setRegistrationPhase(registrationPhase - 1);
    if (registrationPhase == 1) {
      setIsPhaseOneCompleted(false);
      setHeaderTitle(<RegistrationHeaderTitlePhaseOne userType={"PRODUTOR"} />);
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
          <SellerProductsSelector selectedProducts={selectedProducts} setSelectedProducts={setSelectedProducts} />
        </RegistrationFormSecondPhase>
      )}

      {registrationPhase == 2 && (
        <RegistrationFormConfirmationPhase formData={formData} onSubmit={onSubmitConfirmation}>
          <UserRegistrationInfo labelName={"PRODUTOS"} value={formData?.current?.products.join(", ")} />
        </RegistrationFormConfirmationPhase>
      )}
    </div>
  );
}

export default SellerRegistrationPage;
