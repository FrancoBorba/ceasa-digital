function RegistrationTypeButton({ icon, userType, finalPhrase, onClick }) {
  return (
    <div className="flex flex-col justify-center items-center gap-8 bg-[#F9FFF1]">
      <img className="size-12" src={icon} alt="Icone de registro para entregador" />
      <h3 className="text-center">
        Essa opção é para você <span className="text-green-500 font-bold">{userType}</span> {finalPhrase}
      </h3>
      <button
        className="flex items-center justify-center px-3 py-2 bg-green-800 rounded-xl text-white font-bold 
          hover:cursor-pointer hover:bg-green-600 transition-colors duration-300 shadow-md shadow-green-800"
        onClick={onClick}
      >
        ESCOLHER
      </button>
    </div>
  );
}

export default RegistrationTypeButton;
