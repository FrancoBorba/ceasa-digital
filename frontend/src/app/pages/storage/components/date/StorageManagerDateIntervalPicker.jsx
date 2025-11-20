import { useEffect, useRef, useState } from "react";
import calendarIcon from "../../svgs/CalendarIcon.svg";
import downArrowIcon from "../../svgs/DownArrowIcon.svg";
import StorageManagerDateRangeSelector from "./StorageManagerDateRangeSelector";

function StorageManagerDateIntervalPicker() {
  const [openSelection, setOpenSelection] = useState(false);
  const ref = useRef(null);

  useEffect(() => {
    const handleClickOutside = (e) => {
      if (ref.current && !ref.current.contains(e.target)) setOpenSelection(false);
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  return (
    <div ref={ref} className="flex flex-col w-64 gap-2 z-10">
      <button
        onClick={() => setOpenSelection(!openSelection)}
        className="w-full min-h-9 bg-[#E8E8E8] flex flex-row gap-2.5 justify-between items-center pl-5 pr-1 rounded-lg 
          hover:bg-[#dad5d5] hover:cursor-pointer"
      >
        <img className="size-5" src={calendarIcon} alt="" />
        <span className="text-sm text-[#777777] font-medium">5 Nov 2025 - 5 Nov 2025</span>
        <img className="mt-0.5 size-2" src={downArrowIcon} alt="" />
      </button>
      {openSelection && (
        <div className="flex flex-col gap-2 bg-[#e8e8e8c9]  p-2 rounded-lg justify-center items-center">
          <StorageManagerDateRangeSelector title={"Data Inicial:"} />
          <StorageManagerDateRangeSelector title={"Data Final:"} />
          <button
            className="w-4/12 h-8 mt-2 shadow-md bg-white rounded-md transition-colors cursor-pointer font-medium
            text-xs hover:bg-[#f2f2f2]"
          >
            Confirmar
          </button>
        </div>
      )}
    </div>
  );
}

export default StorageManagerDateIntervalPicker;
