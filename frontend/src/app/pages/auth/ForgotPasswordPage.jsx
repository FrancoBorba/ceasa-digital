import { useState } from "react";
import AuthFormBrackground from "./components/login/AuthFormBrackground";
import AuthUnderlineInputText from "./components/login/AuthUnderlineInputText";
import AuthLinkText from "./components/login/AuthLinkText";
import useForgotPassword from "./hooks/useForgotPassword"; 

function ForgotPasswordPage() {
  const [email, setEmail] = useState("");
  const { tryToSendResetEmail, isLoading, isSuccess } = useForgotPassword();

  const handleForgotPasswordSubmit = async (e) => {
    e.preventDefault();
    if (isLoading) return; 
    await tryToSendResetEmail(email);
  };

  const handleInputChange = (e) => {
    setEmail(e.target.value);
  };


  if (isSuccess) {
    return (
      <div className="flex justify-center h-screen w-screen  bg-gradient-to-b from-[#FFF6E2] to-white shadow items-center py-16">
        <div className="flex flex-col text-center max-w-2xl w-4/12 min-w-xs bg-white px-12 py-8 rounded-2xl shadow-md h-auto gap-4">
          <h1 className="text-[#1D3D1C] text-2xl pb-4 font-extrabold">
            VERIFIQUE SEU E-MAIL
          </h1>
          <p className="text-sm text-gray-700">
            Se uma conta com o e-mail <strong>{email}</strong> existir, enviamos
            um link para você redefinir sua senha.
          </p>
          <div className="pt-4">
            <AuthLinkText link={"/login"}>Voltar para o Login</AuthLinkText>
          </div>
        </div>
      </div>
    );
  }

  return (
    <AuthFormBrackground
      onSubmit={handleForgotPasswordSubmit}
      title={"RECUPERAR SENHA"}
      buttonName={isLoading ? "ENVIANDO..." : "ENVIAR"}
    >
      <p className="text-sm text-center text-gray-600">
        Digite seu e-mail para enviarmos um link de recuperação de senha.
      </p>
      <AuthUnderlineInputText
        name={"email"}
        aboveTypeName={"Email"}
        onTextChanged={handleInputChange}
        type={"email"}
      />
      <div className="flex flex-col gap-1">
        <AuthLinkText link={"/login"}>Voltar para o Login</AuthLinkText>
      </div>
    </AuthFormBrackground>
  );
}

export default ForgotPasswordPage;
