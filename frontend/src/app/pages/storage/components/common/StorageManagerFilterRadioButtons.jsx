function StorageManagerFIlterRadioButtons({ buttonTitle }) {
  return (
    <button
      className="w-fit px-2 h-7 focus:bg-[#ffffff] hover:bg-[#ffffff] focus:text-black hover:text-black text-xs
        rounded-md text-[#777777] font-medium hover:cursor-pointer"
    >
      {buttonTitle}
    </button>
  );
}

export default StorageManagerFIlterRadioButtons;
