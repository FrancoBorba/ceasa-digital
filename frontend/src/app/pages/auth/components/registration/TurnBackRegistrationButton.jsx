import BackRegistrationPhaseIcon from "../../svgs/BackRegistrationPhaseIcon.svg";

function TurnBackRegistrationButton({ onClick }) {
  return (
    <button
      className={
        "fixed top-1/2 left-4 -translate-y-1/2 py-3 px-2 flex items-center justify-center rounded-md hover:cursor-pointer hover:bg-[#f0f0f0]"
      }
      onClick={onClick}
    >
      <img src={BackRegistrationPhaseIcon} alt="Icone de voltar" />
    </button>
  );
}

export default TurnBackRegistrationButton;
