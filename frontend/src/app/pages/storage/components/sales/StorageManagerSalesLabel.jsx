import StorageManagerSalesCheckbox from "./StorageManagerSalesCheckbox";

function StorageManagerSalesLabel() {
  return (
    <div
      className="grid grid-cols-[0.15fr_1fr_1.5fr_2fr_1.15fr_2fr_2fr_1.2fr] gap-4 bg-[#FEFEFE] px-2 py-3 
        rounded-t-2xl font-semibold text-black text-sm"
    >
      <StorageManagerSalesCheckbox />
      <label>Pedido</label>
      <label>Data</label>
      <label>Cliente</label>
      <label>Itens</label>
      <label className="flex justify-center items-center">Pagamento</label>
      <label className="flex justify-center items-center">Status</label>
      <label>Total</label>
    </div>
  );
}

export default StorageManagerSalesLabel;
