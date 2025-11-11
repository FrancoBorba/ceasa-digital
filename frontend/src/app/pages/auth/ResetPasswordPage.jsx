import { useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import AuthFormBrackground from "./components/login/AuthFormBrackground";
import AuthUnderlineInputText from "./components/login/AuthUnderlineInputText";
import AuthLinkText from "./components/login/AuthLinkText";
import useResetPassword from "./hooks/useResetPassword"; 

function ResetPasswordPage() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const token = searchParams.get("token");

  const { tryToResetPassword, isLoading, error } = useResetPassword();

  const [formData, setFormData] = useState({
    password: "",
    confirmPassword: "",
  });
  const [clientError, setClientError] = useState("");

  const handleResetPasswordSubmit = async (e) => {
    e.preventDefault();
    setClientError(""); 

    if (!token) {
      setClientError("Link de redefinição inválido ou expirado.");
      return;
    }

    if (formData.password !== formData.confirmPassword) {
      setClientError("As senhas não coincidem!");
      return;
    }

    if (isLoading) return; 

    await tryToResetPassword({ token, password: formData.password });
  };

  const handleInputChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  return (
    <AuthFormBrackground
      onSubmit={handleResetPasswordSubmit}
      title={"MODIFICAR SENHA"}
      buttonName={isLoading ? "SALVANDO..." : "SALVAR"}
    >
     
      {(error || clientError) && (
        <p className="text-sm text-center text-red-600">
          {error || clientError}
        </p>
      )}

      <AuthUnderlineInputText
        name={"password"}
        aboveTypeName={"Nova Senha"}
        onTextChanged={handleInputChange}
        type={"password"}
      />
      <AuthUnderlineInputText
        name={"confirmPassword"}
        aboveTypeName={"Confirmar Nova Senha"}
        onTextChanged={handleInputChange}
        type={"password"}
      />
      <div className="flex flex-col gap-1">
        <AuthLinkText link={"/login"}>Voltar para o Login</AuthLinkText>
      </div>
    </AuthFormBrackground>
  );
}

export default ResetPasswordPage;