import StorageManagerSalesCheckbox from "./StorageManagerSalesCheckbox";
import StorageManagerSalesStatusBox from "./StorageManagerSalesStatusBox";

function StorageManagerSalesItem() {
  return (
    <div
      className="grid grid-cols-[0.15fr_1fr_1.5fr_2fr_1.15fr_2fr_2fr_1.2fr] gap-4 bg-[#FEFEFE] items-center p-2 
              text-xs text-[#777777]"
    >
      <StorageManagerSalesCheckbox />
      <div>#2435</div>
      <div>11 Out, 2025</div>
      <div>Augusto Rodrigues</div>
      <div>4 Itens</div>
      <StorageManagerSalesStatusBox status={"Concluido"}/>
      <StorageManagerSalesStatusBox status={"Finalizado"}/>
      <div className="text-black">R$ 97,50</div>
    </div>
  );
}

export default StorageManagerSalesItem;
