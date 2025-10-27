import { useNavigate } from "react-router-dom";
import AuthFormBrackground from "./components/AuthFormBrackground";

function EmailVerifiedPage() {
  const navigate = useNavigate();
  const currentTime = new Date().toLocaleTimeString([], {
    hour: "2-digit",
    minute: "2-digit",
  });

  const handleContinue = (e) => {
    e.preventDefault();
    navigate("/login");
  };

  return (
    <AuthFormBrackground
      onSubmit={handleContinue}
      title={""}
      buttonName={"CONTINUAR"}
    >
      <div className="flex flex-col items-center text-center gap-3 mt-6">
        {/* Ícone */}
        <svg
          xmlns="http://www.w3.org/2000/svg"
          className="w-20 h-20 text-green-600"
          fill="currentColor"
          viewBox="0 0 20 20"
        >
          <path
            fillRule="evenodd"
            d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.707a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z"
            clipRule="evenodd"
          />
        </svg>

        {/* Texto principal + emoji na mesma linha */}
        <h1 className="flex items-center justify-center text-[#1D3D1C] font-extrabold text-lg">
          <span className="whitespace-nowrap">
            E-MAIL VERIFICADO COM SUCESSO!
          </span>
          <span className="ml-2">🌱</span>
        </h1>

        {/* Hora */}
        <p className="text-gray-500 text-sm">
          Email confirmado às {currentTime}
        </p>
      </div>
    </AuthFormBrackground>
  );
}

export default EmailVerifiedPage;
