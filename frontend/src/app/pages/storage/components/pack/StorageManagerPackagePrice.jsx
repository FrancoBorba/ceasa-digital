function StorageManagerPackagePrice({ label, price }) {
  return (
    <div className="flex flex-row justify-between">
      <span className="text-[0.85rem] font-semibold">{label}</span>
      <span className="text-[#777777] font-light text-xs">
        {new Intl.NumberFormat("pt-BR", {
          style: "currency",
          currency: "BRL",
        }).format(price)}
      </span>
    </div>
  );
}

export default StorageManagerPackagePrice;
