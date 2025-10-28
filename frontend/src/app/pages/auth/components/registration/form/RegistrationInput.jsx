import { useEffect, useState } from "react";

function RegistrationInput({ type, labelName, registration, errors }) {
  const [userTryToInputAInvalidValue, setUserTryToInputAInvalidValue] = useState(false);
  useEffect(() => {
    if (errors) {
      setUserTryToInputAInvalidValue(true);
    }
  }, [errors]);

  return (
    <div className="flex flex-col">
      <label className="text-black text-xs font-stretch-expanded">
        {labelName} <span className="text-red-500">*</span>
      </label>
      <input
        className={`transition-discrete duration-500 focus:outline-0
          border-b-1 border-b-gray-600
          ${userTryToInputAInvalidValue && "border-b-2 border-b-green-500"}
          ${errors && "border-b-2 border-b-red-500"}
        `}
        type={type}
        {...registration}
      />
      {errors && (
        <h3 className="text-red-500 text-[0.7rem]">{errors?.message}</h3>
      )}
    </div>
  );
}

export default RegistrationInput;
