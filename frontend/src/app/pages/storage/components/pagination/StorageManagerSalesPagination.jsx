import StorageManagerPaginationButton from "./StorageManagerPaginationButton";
import pageLeftArrow from "../../svgs/PageLeftArrow.svg";
import pageRightArrow from "../../svgs/PageRightArrow.svg";

function StorageManagerSalesPagination() {
  return (
    <div className="text-[#777777] text-md flex flex-row gap-1">
      <button className="hover:shadow-md py-0.5 px-2.5 hover:text-black hover:bg-white rounded-md cursor-pointer">
        <img src={pageLeftArrow} alt="" />
      </button>
      <StorageManagerPaginationButton page={1} />
      <StorageManagerPaginationButton page={2} />
      <StorageManagerPaginationButton page={3} />
      <span className="mx-1 text-[#777777]">...</span>
      <StorageManagerPaginationButton page={4} />
      <button className="hover:shadow-md py-0.5 px-2.5 hover:text-black hover:bg-white rounded-md cursor-pointer">
        <img src={pageRightArrow} alt="" />
      </button>
    </div>
  );
}

export default StorageManagerSalesPagination;
