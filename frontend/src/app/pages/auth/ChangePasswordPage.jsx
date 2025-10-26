import { useState } from "react";
import AuthFormBrackground from "./components/AuthFormBrackground";
import AuthUnderlineInputText from "./components/AuthUnderlineInputText";
import useChangePassword from "./hooks/useChangePassword";

function ChangePasswordPage() {
  const { tryToChangePassword } = useChangePassword();
  const [formData, setFormData] = useState({
    oldPassword: "",
    newPassword: "",
    confirmPassword: "",
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (formData.newPassword !== formData.confirmPassword) {
      alert("As senhas novas não coincidem!");
      return;
    }
    await tryToChangePassword(formData);
  };

  const handleInputChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  return (
    <AuthFormBrackground
      onSubmit={handleSubmit}
      title={"ALTERAR SENHA"}
      buttonName={"CONFIRMAR"}
    >
      <AuthUnderlineInputText
        name={"oldPassword"}
        aboveTypeName={"Senha atual"}
        onTextChanged={handleInputChange}
        type={"password"}
      />
      <AuthUnderlineInputText
        name={"newPassword"}
        aboveTypeName={"Nova senha"}
        onTextChanged={handleInputChange}
        type={"password"}
      />
      <AuthUnderlineInputText
        name={"confirmPassword"}
        aboveTypeName={"Confirmar nova senha"}
        onTextChanged={handleInputChange}
        type={"password"}
      />
    </AuthFormBrackground>
  );
}

export default ChangePasswordPage;
