import RegistrationFormBackground from "./RegistrationFormBackground";
import UserRegistrationInfo from "./UserRegistrationInfo";

function RegistrationFormConfirmationPhase({ children, formData, onSubmit, userType }) {
  // Garante compatibilidade com useRef e useState
  const data = formData?.current || formData || {};

  // Detecta tipo de estrutura automaticamente (fallback se não for passado)
  const detectedType = userType
    ? userType
    : data.producerData || data.produtosSelecionados
    ? "PRODUTOR"
    : "CLIENTE";

  // Extrai os possíveis blocos de dados
  const userData = data.userData || data;
  const producerData = data.producerData || {};
  const produtosSelecionados = data.produtosSelecionados || [];

  // Formatadores auxiliares
  const formatPhoneNumber = (phoneNumber) => {
    if (!phoneNumber) return "";
    const digits = phoneNumber.replace(/\D/g, "");
    return `(${digits.substring(0, 2)}) ${digits.substring(2, 7)}-${digits.substring(7, 11)}`;
  };

  const formatCpf = (cpf) => {
    if (!cpf) return "";
    const digits = cpf.replace(/\D/g, "");
    return `${digits.substring(0, 3)}.${digits.substring(3, 6)}.${digits.substring(6, 9)}-${digits.substring(9, 11)}`;
  };

  // ----------------------------------------------------------
  // 🎯 Diferentes retornos conforme o tipo de usuário
  // ----------------------------------------------------------

  if (detectedType === "PRODUTOR") {
    return (
      <RegistrationFormBackground buttonName="Confirmar" onSubmit={onSubmit}>
        <dl className="grid grid-cols-1 gap-7 mb-8">
          <h2 className="text-xl font-semibold text-gray-800 mb-2">Confirme seus dados de Produtor</h2>

          <UserRegistrationInfo labelName="NOME" value={userData.name} />
          <UserRegistrationInfo labelName="EMAIL" value={userData.email} />
          <UserRegistrationInfo
            labelName="TELEFONE"
            value={formatPhoneNumber(producerData.phoneNumber)}
          />
          <UserRegistrationInfo labelName="CPF" value={formatCpf(producerData.cpf)} />
          <UserRegistrationInfo
            labelName="NÚMERO DE IDENTIFICAÇÃO"
            value={producerData.numeroDeIdentificacao}
          />
          <UserRegistrationInfo
            labelName="TIPO DE IDENTIFICAÇÃO"
            value={producerData.tipoDeIdentificacao}
          />
          {Array.isArray(produtosSelecionados) && produtosSelecionados.length > 0 && (
            <div>
              <p className="font-semibold text-gray-700 mb-2">Produtos Selecionados:</p>
              <ul className="list-disc ml-5 text-gray-800">
                {produtosSelecionados.map((p) => (
                  <li key={p.id}>{p.nome}</li>
                ))}
              </ul>
            </div>
          )}

          {children}
        </dl>
      </RegistrationFormBackground>
    );
  }

 
  if (detectedType === "CLIENTE") {
    return (
      <RegistrationFormBackground buttonName="Confirmar" onSubmit={onSubmit}>
        <dl className="grid grid-cols-1 gap-7 mb-8">
          <h2 className="text-xl font-semibold text-gray-800 mb-2">Confirme seus dados de Cliente</h2>

          <UserRegistrationInfo labelName="NOME" value={data.name} />
          <UserRegistrationInfo labelName="EMAIL" value={data.email} />
          <UserRegistrationInfo
            labelName="TELEFONE"
            value={formatPhoneNumber(data.phoneNumber)}
          />
          <UserRegistrationInfo labelName="CPF" value={formatCpf(data.cpf)} />
          <UserRegistrationInfo labelName="ENDEREÇO" value={data.address} />

          {children}
        </dl>
      </RegistrationFormBackground>
    );
  }

  return (
    <RegistrationFormBackground buttonName="Confirmar" onSubmit={onSubmit}>
      <p className="text-gray-500 italic">
        Estrutura de dados desconhecida. Verifique o tipo de usuário.
      </p>
    </RegistrationFormBackground>
  );
}

export default RegistrationFormConfirmationPhase;
