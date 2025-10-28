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
        labelName={"ENDEREÇO "}
        type={"text"}
        registration={register("address", {
          required: "É necessário inserir o seu endereço.",
          minLength: {
            value: 5,
            message: "O endereço precisa ter pelomenos 5 caractéres.",
          },
          maxLength: {
            value: 255,
            message: "O endereço pode ter no máximo 255 caractéres.",
          },
        })}
        errors={errors?.address}
        value={formData?.address}
      />
      <RegistrationInput
        labelName={"TELEFONE"}
        type={"text"}
        registration={register("phoneNumber", {
          required: "É necessário inserir o seu número de telefone.",
          pattern: {
            value: /^[\d]{11}/,
            message: "Deve ser inserido apenas numeros.",
          },
          minLength: {
            value: 11,
            message: "Deve ser inserido o DDD e o número completo.",
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
            value: /^[\d]{11}/,
            message: "Deve ser inserido apenas numeros.",
          },
          minLength: {
            value: 11,
            message: "Deve ser inserido o CPF completo.",
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
