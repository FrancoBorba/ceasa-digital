import startRegistrationPhaseIcon from "../../../svgs/StartRegistrationPhaseIcon.svg";
import secondRegistrationPhaseIcon from "../../../svgs/SecondRegistrationPhaseIcon.svg";
import thirdRegistrationPhaseIcon from "../../../svgs/ThirdRegistrationPhaseIcon.svg";
import RegistrationPhaseIcon from "./RegistrationPhaseIcon";
import LineBetweenRegistrationIcon from "./LineBetweenRegistrationIcon";

function GenericRegistrationHeader({
  headerTitle,
  isPhaseOneCompleted,
  isPhaseTwoCompleted,
}) {
  return (
    <header className="flex flex-col items-center w-screen">
      {headerTitle}
      <div className="flex flex-row justify-center items-center gap-2 py-4">
        <RegistrationPhaseIcon
          iconSrc={startRegistrationPhaseIcon}
          isRegistrationPhaseConcluded={true}
        />
        <LineBetweenRegistrationIcon
          isRegistrationPhaseConcluded={isPhaseOneCompleted}
        />
        <RegistrationPhaseIcon
          iconSrc={secondRegistrationPhaseIcon}
          isRegistrationPhaseConcluded={isPhaseOneCompleted}
        />
        <LineBetweenRegistrationIcon
          isRegistrationPhaseConcluded={isPhaseTwoCompleted}
        />
        <RegistrationPhaseIcon
          iconSrc={thirdRegistrationPhaseIcon}
          isRegistrationPhaseConcluded={isPhaseTwoCompleted}
        />
      </div>
    </header>
  );
}

export default GenericRegistrationHeader;
