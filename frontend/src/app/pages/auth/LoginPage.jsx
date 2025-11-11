import {useState } from "react";
import AuthFormBrackground from "./components/login/AuthFormBrackground";
import AuthUnderlineInputText from "./components/login/AuthUnderlineInputText";
import useUserLoginAuthentication from "./hooks/useUserLoginAuthentication";
import AuthLinkText from "./components/login/AuthLinkText";
//import useEffectTryToRefreshAccessToken from "./hooks/useEffectTryToRefreshAccessToken";

function LoginPage() {
  //useEffectTryToRefreshAccessToken();

  const { tryToAuthenticateUser } = useUserLoginAuthentication();
  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });

  const handleLoginFormSubmit = async (e) => {
    e.preventDefault();
    tryToAuthenticateUser(formData);
  };

  const handleLoginFormInputChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  return (
    <AuthFormBrackground
      onSubmit={handleLoginFormSubmit}
      title={"LOGIN"}
      buttonName={"ENTRAR"}
    >
      <AuthUnderlineInputText
        name={"username"}
        aboveTypeName={"Email"}
        onTextChanged={handleLoginFormInputChange}
        type={"email"}
      />
      <AuthUnderlineInputText
        name={"password"}
        aboveTypeName={"Senha"}
        onTextChanged={handleLoginFormInputChange}
        type={"password"}
      />
      <div className="flex flex-col gap-1">
        <AuthLinkText link={"/forgot-password"} >Esqueceu o e-mail ou senha?</AuthLinkText>
        <AuthLinkText link={"/select-register"} >Crie sua conta</AuthLinkText>
      </div>
    </AuthFormBrackground>
  );
}

export default LoginPage;
