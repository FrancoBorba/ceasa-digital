import downArrowBlackIcon from "../../svgs/DownArrowBlackIcon.svg";

function StorageManagerSalesShowButtonQuantity() {
  return (
    <button
      className="flex flex-row bg-white text-black items-center gap-4 hover:cursor-pointer rounded-sm
        hover:bg-[#E8E8E8] text-xs py-0.5 px-3 font-medium transition-colors duration-300 h-full"
    >
      <div className="flex flex-row gap-1">
        Mostrar <span>4</span>
      </div>
      <img src={downArrowBlackIcon} />
    </button>
  );
}

export default StorageManagerSalesShowButtonQuantity;
