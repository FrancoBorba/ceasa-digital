function StorageManagerPackageItemWeight({itemName, weight}) {
  return (
    <div className="flex flex-row justify-between font-medium text-[0.8rem]">
      <span>{itemName}</span>
      <span className="text-[#777777]">{weight}kg</span>
    </div>
  );
}

export default StorageManagerPackageItemWeight;
