function StorageManagerIconButton({buttonIcon, onClick, buttonIconAlt}) {
  return (
    <button className="size-9 p-1 flex justify-center items-center hover:bg-[#E8E8E8] hover:cursor-pointer rounded-2xl">
      <img className="size-fit" src={buttonIcon} alt={buttonIconAlt} />
    </button>
  );
}

export default StorageManagerIconButton;
