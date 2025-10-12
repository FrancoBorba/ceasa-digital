import CommonAuthButton from "./CommonAuthButton";
import CommunUnderlineInputText from "./CommunUnderlineInputText";
import PasswordUnderlineInputText from "./PasswordUnderlineInputText";

function LoginPage() {
  return (
    <div className="flex justify-center h-screen w-screen  bg-gradient-to-b from-[#FFF6E2] to-white shadow items-center py-16">
      <div
        className="flex flex-col justify-around max-w-2xl w-4/12 min-w-xs bg-white px-12
                  rounded-2xl shadow-md h-full overflow-y-auto gap-4 "
      >
        <div className="flex flex-col gap-y-4">
          
          <h1 className="text-[#1D3D1C] text-2xl pb-16 tracking-widest font-extrabold">LOGIN</h1>

          <CommunUnderlineInputText aboveTypeName={"Email"} />
          <PasswordUnderlineInputText />
          <a href="" className="text-left text-[#1F4B1D] underline text-xs">
            Esqueceu email ou senha?
          </a>
        </div>
        <CommonAuthButton buttonName={"ENTRAR"} />
      </div>
    </div>
  );
}

export default LoginPage;
