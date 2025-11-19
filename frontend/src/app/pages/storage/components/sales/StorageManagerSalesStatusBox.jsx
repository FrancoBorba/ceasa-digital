function StorageManagerSalesStatusBox({ status }) {
  return (
    <div className="flex justify-center items-center">
      <span className="bg-[#CEFFD4] text-[#3AB54A] px-2 py-1 rounded-md text-xs font-semibold">{status}</span>
    </div>
  );
}

export default StorageManagerSalesStatusBox;
