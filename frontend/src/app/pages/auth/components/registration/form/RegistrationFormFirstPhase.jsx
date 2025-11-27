import RegistrationFormBackground from "./RegistrationFormBackground";
import RegistrationInput from "./RegistrationInput";

function RegistrationFormFirstPhase({ register, onSubmit, errors, password }) {
  return (
    <RegistrationFormBackground onSubmit={onSubmit} buttonName={"Continuar"}>
      <RegistrationInput
        labelName={"NOME COMPLETO"}
        type={"text"}
        registration={register("name", {
          required: "É necessário inserir o nome completo.",
          minLength: {
            value: 10,
            message: "O nome precisa ter pelomenos 10 caractéres.",
          },
          maxLength: {
            value: 255,
            message: "O nome pode ter no máximo 255 caractéres.",
          },
          onChange: (e) => {
            const communNamePrepositions = ["de", "da", "do", "dos", "das"];
            e.target.value = e.target.value
              .toLowerCase()
              .split(" ")
              .map((word) => {
                if (communNamePrepositions.includes(word)) return word;
                return word.charAt(0).toUpperCase() + word.slice(1);
              })
              .join(" ");
          },
        })}
        errors={errors?.name}
      />

      <RegistrationInput
        labelName={"LOGIN"}
        type={"text"}
        registration={register("login", {
          required: "O login é necessário.",
          maxLength: {
            value: 50,
            message: "O login pode ter no máximo 50 caractéres.",
          },
        })}
        errors={errors?.login}
      />

      <RegistrationInput
        labelName={"EMAIL"}
        type={"text"}
        registration={register("email", {
          required: "É necessário insirir o email.",
          pattern: {
            value: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
            message: "O email está em um formato inválido.",
          },
          maxLength: {
            value: 255,
            message: "O email pode ter no máximo 255 caractéres.",
          },
        })}
        errors={errors?.email}
      />

      <RegistrationInput
        labelName={"SENHA"}
        type={"password"}
        registration={register("password", {
          required: "É necessário informar a senha.",
          minLength: {
            value: 8,
            message: "A senha deve ter no mínimo 8 caractéres.",
          },
          maxLength: {
            value: 255,
            message: "A senha pode ter no máximo 255 caractéres.",
          },
        })}
        errors={errors?.password}
      />

      <div className="flex flex-col gap-2">
        <RegistrationInput
          labelName={"CONFIRMAR SENHA"}
          type={"password"}
          registration={register("passwordConfirmation", {
            required: "É necessário confirmar a senha.",
            maxLength: {
              value: 255,
              message:
                "A confirmação de senha pode ter no máximo 255 caractéres.",
            },
            validate: {
              checkPasswordConfirmation: (value) =>
                value === password || "Confirmação de senha incorreta."
            },
          })}
          errors={errors?.passwordConfirmation}
        />
        <h3 className="text-[0.65rem]">
          Todos os campos com <span className="text-red-500">*</span> são
          obrigatórios
        </h3>
      </div>
    </RegistrationFormBackground>
  );
}

export default RegistrationFormFirstPhase;
