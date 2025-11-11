import RegistrationFormBackground from "./RegistrationFormBackground";
import RegistrationInput from "./RegistrationInput";

function RegistrationFormSecondPhase({
  onSubmit,
  children,
  register,
  errors,
  formData,
  userType,
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
      {userType === "PRODUTOR" && (
        <>
          {/* CAMPO DE SELECT ATUALIZADO 
          */}
          <div className="flex flex-col w-[30vw] min-w-[280px]">
            <label className="text-black text-sm font-stretch-expanded font-bold">
              TIPO DE IDENTIFICAÇÃO
              {/* Adiciona o asterisco para combinar com os outros campos obrigatórios */}
              <span className="text-red-500"> *</span>
            </label>

            {/* Div 'relative' para posicionar a seta customizada */}
            <div className="relative w-full">
              <select
                className="w-full h-fit p-0 py-1 text-base font-stretch-condensed
                           border-b border-b-gray-600 outline-none 
                           focus:border-b-green-600 bg-transparent
                           appearance-none" // <-- 1. REMOVE O ESTILO PADRÃO
                {...register("tipoDeIdentificacao", {
                  required: "É necessário selecionar um tipo.",
                })}
              >
                <option value="">Selecione um tipo...</option>
                <option value="DAP">DAP (Declaração de Aptidão ao PRONAF)</option>
                <option value="CNPJ">CNPJ</option>
                <option value="RG">RG (Identidade)</option>
                <option value="OUTRO">Outro</option>
              </select>

              {/* 2. ADICIONA A SETA CUSTOMIZADA */}
              <div className="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700">
                <svg
                  className="fill-current h-4 w-4"
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 20 20"
                >
                  <path d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z" />
                </svg>
              </div>
            </div>

            {errors.tipoDeIdentificacao && (
              <p className="text-red-500 text-xs mt-1">
                {errors.tipoDeIdentificacao.message}
              </p>
            )}
          </div>
          {/* --- Fim da Alteração --- */}

          {/* Reutilizando seu componente RegistrationInput */}
          <RegistrationInput
            labelName={"NÚMERO DE IDENTIFICAÇÃO"}
            type={"text"}
            registration={register("numeroDeIdentificacao", {
              required: "É necessário inserir o número de identificação.",
            })}
            errors={errors?.numeroDeIdentificacao}
            value={formData?.numeroDeIdentificacao}
          />
          {children}
        </>
      )}
    </RegistrationFormBackground>
  );
}

export default RegistrationFormSecondPhase;
