function RegistrationHeaderTitlePhaseOne({ userType }) {
  return (
    <div className="flex flex-col items-center w-screen">
      <h1 className="font-bold text-4xl pt-4 pb-2">
        Vamos lá realizar o seu cadastro!
      </h1>
      <h3 className="text-black ">
        Você está realizando o cadastro como{" "}
        <span className="text-green-500 font-bold">{userType}</span>
      </h3>
    </div>
  );
}

export default RegistrationHeaderTitlePhaseOne;
