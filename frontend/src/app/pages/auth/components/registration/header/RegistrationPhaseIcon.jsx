import { motion } from "motion/react";

function RegistrationPhaseIcon({ iconSrc, isRegistrationPhaseConcluded }) {
  return (
    <motion.div
      className="size-10 rounded-full flex items-center justify-center bg-[#CDCDCD] cursor-pointer"
      animate={{
        backgroundColor: isRegistrationPhaseConcluded ? "#3AB54A" : "#CDCDCD",
        transition: {
          duration: 0.3,
          ease: "easeOut",
          delay: 0.5,
        },
      }}
      whileHover={{
        scale: 1.15,
        transition: {
          duration: 0.1,
        },
      }}
      style={{
        boxShadow: "6px 6px 10px rgba(0, 0, 0, 0.3)"
      }}
    >
      <motion.img
        src={iconSrc}
        alt="Ícone"
        className="w-1/2 h-1/2"
        initial={{ opacity: 0 }}
        animate={{
          opacity: isRegistrationPhaseConcluded ? 1 : 0,
        }}
        transition={{
          delay: isRegistrationPhaseConcluded ? 0.5 : 0,
          duration: 0.3,
        }}
      />
    </motion.div>
  );
}

export default RegistrationPhaseIcon;
