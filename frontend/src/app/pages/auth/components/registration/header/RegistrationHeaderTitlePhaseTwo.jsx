function RegistrationHeaderTitlePhaseTwo({userName}) {
  return (
    <div className="flex flex-col items-center w-screen">
      <h1 className="font-bold text-4xl pt-4 pb-2">
        Esta qualse lá, {userName}!
      </h1>
      <h3 className="text-black ">
        Agora só mais algumas informações adicionais
      </h3>
    </div>
  );
}

export default RegistrationHeaderTitlePhaseTwo;