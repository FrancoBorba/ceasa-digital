import RegistrationFormBackground from "./RegistrationFormBackground";
import RegistrationInput from "./RegistrationInput";

function RegistrationFormSecondPhase({
  onSubmit,
  children,
  register,
  errors,
  formData,
}) {
  return (
    <RegistrationFormBackground onSubmit={onSubmit} buttonName={"Continuar"}>
      <RegistrationInput
        labelName={"CEP"}
        type={"text"}
        registration={register("cep", {
          required: "É necessário inserir o CEP.",
          pattern: {
            value: /^\d{5}-\d{3}$/,
            message: "CEP inválido. Formato: 12345-678",
          },
        })}
        errors={errors?.cep}
        value={formData?.cep}
      />
      <RegistrationInput
        labelName={"LOGRADOURO"}
        type={"text"}
        registration={register("logradouro", {
          required: "É necessário inserir o logradouro.",
        })}
        errors={errors?.logradouro}
        value={formData?.logradouro}
      />
      <RegistrationInput
        labelName={"NÚMERO"}
        type={"text"}
        registration={register("numero", {
          required: "É necessário inserir o número.",
        })}
        errors={errors?.numero}
        value={formData?.numero}
      />
      <RegistrationInput
        labelName={"COMPLEMENTO"}
        type={"text"}
        registration={register("complemento")}
        errors={errors?.complemento}
        value={formData?.complemento}
      />
      <RegistrationInput
        labelName={"BAIRRO"}
        type={"text"}
        registration={register("bairro", {
          required: "É necessário inserir o bairro.",
        })}
        errors={errors?.bairro}
        value={formData?.bairro}
      />
      <RegistrationInput
        labelName={"CIDADE"}
        type={"text"}
        registration={register("cidade", {
          required: "É necessário inserir a cidade.",
        })}
        errors={errors?.cidade}
        value={formData?.cidade}
      />
      <RegistrationInput
        labelName={"ESTADO"}
        type={"text"}
        registration={register("estado", {
          required: "É necessário inserir o estado.",
          maxLength: {
            value: 2,
            message: "Informe a sigla do estado (ex: BA).",
          },
        })}
        errors={errors?.estado}
        value={formData?.estado}
      />
      <RegistrationInput
        labelName={"TELEFONE"}
        type={"text"}
        registration={register("phoneNumber", {
          required: "É necessário inserir o seu número de telefone.",
          pattern: {
            value: /^[\d]{11}$/,
            message: "Deve ser inserido apenas números, 11 dígitos.",
          },
          onChange: (e) => {
            const value = e.target.value;
            e.target.value = value.length > 11 ? value.substring(0, 11) : value;
          },
        })}
        errors={errors?.phoneNumber}
        value={formData?.phoneNumber}
      />
      <RegistrationInput
        labelName={"CPF"}
        type={"text"}
        registration={register("cpf", {
          required: "É necessário inserir o seu CPF.",
          pattern: {
            value: /^[\d]{11}$/,
            message: "Deve ser inserido apenas números.",
          },
          onChange: (e) => {
            const value = e.target.value;
            e.target.value = value.length > 11 ? value.substring(0, 11) : value;
          },
        })}
        errors={errors?.cpf}
        value={formData?.cpf}
      />
      {children}
    </RegistrationFormBackground>
  );
}

export default RegistrationFormSecondPhase;
