import RegistrationFormBackground from "./RegistrationFormBackground";
import UserRegistrationInfo from "./UserRegistrationInfo";

function RegistrationFormConfirmationPhase({ children, formData, onSubmit }) {

  const formatPhoneNumber = () => {
    const phoneNumber = formData?.phoneNumber;
    if (phoneNumber)
      return "(" + phoneNumber.substring(0, 2) + ") " + phoneNumber.substring(2, 7) + "-" + phoneNumber.substring(7, 11);
    return phoneNumber;
  };

  const formatCpf = () => {
    const cpf = formData?.cpf;
    if (cpf) 
      return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
    return cpf;
  };

  const formatAddress = () => {
    if (!formData) return "";
    return `${formData.logradouro}, ${formData.numero}` +
           (formData.complemento ? `, ${formData.complemento}` : "") +
           ` - ${formData.bairro}, ${formData.cidade} - ${formData.estado}, CEP: ${formData.cep}`;
  };

  return (
    <RegistrationFormBackground buttonName={"Confirmar"} onSubmit={onSubmit}>
      <dl className="grid grid-cols-1 gap-7 mb-8">
        <UserRegistrationInfo labelName={"NOME"} value={formData?.name} />
        <UserRegistrationInfo labelName={"EMAIL"} value={formData?.email} />
        <UserRegistrationInfo labelName={"TELEFONE"} value={formatPhoneNumber()} />
        <UserRegistrationInfo labelName={"CPF"} value={formatCpf()} />
        <UserRegistrationInfo labelName={"ENDEREÇO"} value={formatAddress()} />
        {children}
      </dl>
    </RegistrationFormBackground>
  );
}

export default RegistrationFormConfirmationPhase;
