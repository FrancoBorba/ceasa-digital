import { useState } from "react";
import CommonAuthButton from "./CommonAuthButton";
import CommunUnderlineInputText from "./CommunUnderlineInputText";
import PasswordUnderlineInputText from "./PasswordUnderlineInputText";
import { setToken } from "../../api/authTokenService";
import apiRequester from "../../api/apiRequester";
import { useNavigate } from "react-router";

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
    <div className="flex justify-center h-screen w-screen  bg-gradient-to-b from-[#FFF6E2] to-white shadow items-center py-16">
      <form
        className="flex flex-col justify-around max-w-2xl w-4/12 min-w-xs bg-white px-12
        rounded-2xl shadow-md h-full overflow-y-auto gap-4"
        onSubmit={handleLoginFormSubmit}
      >
        <div className="flex flex-col gap-y-4">
          <h1 className="text-[#1D3D1C] text-2xl pb-16 tracking-widest font-extrabold">
            LOGIN
          </h1>

          <CommunUnderlineInputText
            name={"username"}
            aboveTypeName={"Email"}
            onTextChanged={handleLoginFormInputChange}
          />
          <PasswordUnderlineInputText
            name={"password"}
            onTextChanged={handleLoginFormInputChange}
          />
          <a href="" className="text-left text-[#1F4B1D] underline text-xs">
            Esqueceu email ou senha?
          </a>
        </div>
        <CommonAuthButton buttonName={"ENTRAR"} />
      </form>
    </div>
  );
}

export default LoginPage;
