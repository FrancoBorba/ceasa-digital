import { useState } from "react";
import { setToken } from "../../api/authTokenService";
import apiRequester from "../../api/apiRequester";
import { useNavigate } from "react-router";
import AuthFormBrackground from "../../components/auth/AuthFormBrackground";
import AuthUnderlineInputText from "../../components/auth/AuthUnderlineInputText";

function LoginPage() {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });

  const navigate = useNavigate();

  const handleLoginFormInputChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const handleLoginFormSubmit = async (e) => {
    e.preventDefault();

    try {
      const { data } = await sendLoginRequestAndReturnDataResponse();
      console.log(data);
      setToken(data.access_token);
      navigate("/");
    } catch (err) {
      console.error("Erro no login:", err);
      alert("Usuário ou senha incorretos");
    }

    function sendLoginRequestAndReturnDataResponse() {
      return apiRequester.post("/oauth2-docs/login", null, {
        params: {
          username: formData.username,
          password: formData.password,
          grant_type: "password",
        },
      });
    }
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
      <a href="" className="text-left text-[#1F4B1D] underline text-xs">
        Esqueceu email ou senha?
      </a>
    </AuthFormBrackground>
  );
}

export default LoginPage;
