import { motion } from "motion/react";

function LineBetweenRegistrationIcon({ isRegistrationPhaseConcluded }) {
  return (
    <div className="h-2 w-22 sm:w-32  lg:w-42  rounded-full bg-[#CDCDCD]">
      <motion.div
        className="bg-[#3AB54A] h-full rounded-full"
        initial={{ width: "0%" }}
        animate={{ width: isRegistrationPhaseConcluded ? "100%" : "0%" }}
        transition={{
          duration: 0.5
        }}
      />
    </div>
  );
}

export default LineBetweenRegistrationIcon;
