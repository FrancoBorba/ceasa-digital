function StorageManagerOptionButton({ onClick, buttonTitle, buttonIcon, buttonIconAlt }) {
  return (
    <button onClick={onClick} className="w-full bg-white hover:bg-[#F2F2F2] h-10 transition-colors m-0 hover:cursor-pointer">
      <div className="flex justify-start items-center px-3 gap-6">
        <img src={buttonIcon} alt={buttonIconAlt} className="h-6" />
        <span className="font-medium text-[0.9rem]">{buttonTitle}</span>
      </div>
    </button>
  );
}

export default StorageManagerOptionButton;
