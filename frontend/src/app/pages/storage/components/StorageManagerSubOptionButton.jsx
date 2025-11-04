function StorageManagerSubOptionButton({ buttonIcon, buttonIconAlt, buttonTitle, onClick }) {
  return (
    <button className="w-full bg-white hover:bg-[#F2F2F2] h-10 transition-colors flex items-center gap-3 pl-8 hover:cursor-pointer">
      <img src={buttonIcon} alt={buttonIconAlt} className="size-4" />
      <span className="text-[0.7rem] font-medium text-[#777777]">{buttonTitle}</span>
    </button>
  );
}

export default StorageManagerSubOptionButton;
