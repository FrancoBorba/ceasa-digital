import {useState } from "react";
import AuthFormBrackground from "./components/login/AuthFormBrackground";
import AuthUnderlineInputText from "./components/login/AuthUnderlineInputText";
import useEffectTryToRefreshAccessToken from "./hooks/useEffectTryToRefreshAccessToken";

function ClientRegistrationPage() {
  useEffectTryToRefreshAccessToken();

  const [formData, setFormData] = useState({
    fullName: "",
    email: "",
    phoneNumber: "",
    password: "",
    passwordConfirmation: "",
  });

  const sendRegisterRequest = (e) => {
    e.preventDefault();
    console.log(formData);
  };

  const handleLoginFormInputChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  return (
    <AuthFormBrackground
      onSubmit={sendRegisterRequest}
      title={"CADASTRO"}
      buttonName={"ENTRAR"}
    >
      <AuthUnderlineInputText
        name={"fullName"}
        aboveTypeName={"NOME E SOBRENOME"}
        onTextChanged={handleLoginFormInputChange}
        type={"text"}
      />

      <AuthUnderlineInputText
        name={"email"}
        aboveTypeName={"EMAIL"}
        onTextChanged={handleLoginFormInputChange}
        type={"email"}
      />
      <AuthUnderlineInputText
        name={"phoneNumber"}
        aboveTypeName={"N° DE TELEFONE"}
        onTextChanged={handleLoginFormInputChange}
        type={"text"}
      />

      <AuthUnderlineInputText
        name={"password"}
        aboveTypeName={"SENHA"}
        onTextChanged={handleLoginFormInputChange}
        type={"password"}
      />

      <AuthUnderlineInputText
        name={"passwordConfirmation"}
        aboveTypeName={"REPETIR SENHA"}
        onTextChanged={handleLoginFormInputChange}
        type={"password"}
      />
    </AuthFormBrackground>
  );
}

export default ClientRegistrationPage;
