import { useNavigate } from "react-router-dom";
import AuthFormBrackground from "./components/AuthFormBrackground";

function EmailVerificationFailedPage() {
  const navigate = useNavigate();
  const currentTime = new Date().toLocaleTimeString([], {
    hour: "2-digit",
    minute: "2-digit",
  });

  const handleRetry = (e) => {
    e.preventDefault();
    navigate("/resend-verification"); // Ou outra rota para tentar novamente
  };

  return (
    <AuthFormBrackground
      onSubmit={handleRetry}
      title={""}
      buttonName={"TENTAR NOVAMENTE"}
    >
      <div className="flex flex-col items-center text-center gap-3 mt-6">
        {/* Ícone de falha */}
        <svg
          xmlns="http://www.w3.org/2000/svg"
          className="w-20 h-20 text-red-600"
          fill="currentColor"
          viewBox="0 0 20 20"
        >
          <path
            fillRule="evenodd"
            d="M10 18a8 8 0 100-16 8 8 0 000 16zm-2.293-9.707a1 1 0 011.414-1.414L10 8.586l1.879-1.879a1 1 0 111.414 1.414L11.414 10l1.879 1.879a1 1 0 01-1.414 1.414L10 11.414l-1.879 1.879a1 1 0 01-1.414-1.414L8.586 10 6.707 8.121z"
            clipRule="evenodd"
          />
        </svg>

        {/* Texto principal + emoji na mesma linha */}
        <h1 className="flex items-center justify-center text-[#8B1C1C] font-extrabold text-lg">
          <span className="whitespace-nowrap">
            FALHA NA VERIFICAÇÃO DO E-MAIL
          </span>
          <span className="ml-2">❌</span>
        </h1>

        {/* Hora */}
        <p className="text-gray-500 text-sm">
          Tentativa registrada às {currentTime}
        </p>
      </div>
    </AuthFormBrackground>
  );
}

export default EmailVerificationFailedPage;