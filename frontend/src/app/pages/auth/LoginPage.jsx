import {useEffect, useState } from "react";
import AuthFormBrackground from "../../components/auth/AuthFormBrackground";
import AuthUnderlineInputText from "../../components/auth/AuthUnderlineInputText";
import useUserLoginAuthentication from "../../hooks/auth/useUserLoginAuthentication";
import AuthLinkText from "../../components/auth/AuthLinkText";
import useTryToRefreshAccessToken from "../../hooks/auth/useTryToRefreshAccessToken";

function LoginPage() {
  const tryToRefreshAccessToken = useTryToRefreshAccessToken();
  useEffect(() => {
    tryToRefreshAccessToken();
  }, [tryToRefreshAccessToken]);

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
        <AuthLinkText link={"/"} >Esqueceu o e-mail ou senha?</AuthLinkText>
        <AuthLinkText link={"/register"} >Crie sua conta</AuthLinkText>
      </div>
    </AuthFormBrackground>
  );
}

export default LoginPage;
