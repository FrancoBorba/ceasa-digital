import RegistrationInput from "./components/registration/form/RegistrationInput";
import ceasaDigitalIcon from "./svgs/CeasaDigitalRegisterIcon.svg";
import deliveryRegisterIcon from "./svgs/DeliveryManRegisterIcon.svg";
import clientRegisterIcon from "./svgs/ClientRegisterIcon.svg";
import sellerRegisterIcon from "./svgs/SellerRegisterIcon.svg";
import RegistrationTypeButton from "./components/registration/RegistrationTypeButton";
import { useNavigate } from "react-router";

function SelectRegistrationTypePage() {
  const navigate = useNavigate();

  return (
    <main className="flex justify-center items-center bg-[#F9FFF1] py-[6vh]">
      <div
        className="flex flex-col items-center justify-center gap-[18vh] bg-white w-10/12 sm:w-9/12 md:w-7/12 lg:w-5/12 
          py-12 px-0 sm:px-10 rounded-2xl overflow-y-auto"
      >
        <div className="flex flex-col items-center justify-center gap-4">
          <img className="size-18 " src={ceasaDigitalIcon} alt="Logo do ceasa digital." />
          <h1 className="font-bold text-2xl text-center">
            Olá, você está na tela para escolher o tipo de cadastro, selecione uma das opções abaixo
          </h1>
        </div>

        <div className="flex flex-rom justify-between gap-2">
          <RegistrationTypeButton
            onClick={() => navigate("/client-register")}
            icon={clientRegisterIcon}
            userType={"CLIENTE"}
            finalPhrase={"do nosso sistema"}
          />
          <RegistrationTypeButton
            onClick={() => navigate("/delivery-register")}
            icon={deliveryRegisterIcon}
            userType={"ENTREGADOR"}
            finalPhrase={"da plataforma"}
          />
          <RegistrationTypeButton
            onClick={() => navigate("/seller-register")}
            icon={sellerRegisterIcon}
            userType={"VENDEDOR"}
            finalPhrase={"da plataforma"}
          />
        </div>
      </div>
    </main>
  );
}

export default SelectRegistrationTypePage;
