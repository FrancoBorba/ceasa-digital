import StorageManagerPaymentMethod from "../notification/StorageManagerPaymentMethod";
import StorageManagerPackageItemWeight from "./StorageManagerPackageItemWeight";
import StorageManagerPackagePrice from "./StorageManagerPackagePrice";

function StorageManagerPackageConfirmation() {
  return (
    <div className="flex flex-col w-full h-full bg-white p-6 pt-12 rounded-3xl gap-3.5 text-ellipsis whitespace-nowrap max-w-92 min-w-86 overflow-x-scroll shadow">
      <div className="flex flex-col ">
        <h4 className="text-black font-bold text-[0.95rem] ">
          Pedidos #2341 -{" "}
          <span className="text-xs text-[#777777]  whitespace-normal ">Adevaldo Dos Santos Pereira</span>
        </h4>
        <h5 className="-mt-1 text-xs text-[#3AB54A] font-medium">Pedido realizado aguardando montagem</h5>
      </div>
      <div className="flex flex-row justify-between text-[0.85rem] font-semibold">
        <span>Itens</span>
        <span>Quantidade</span>
      </div>
      <div>
        <StorageManagerPackageItemWeight itemName={"Cebola"} weight={1} />
        <StorageManagerPackageItemWeight itemName={"Tomate"} weight={5} />
        <StorageManagerPackageItemWeight itemName={"Batata Inglesa"} weight={5} />
      </div>
      <div className="flex flex-row justify-between">
        <span className="text-[0.85rem] font-semibold">Método Pagamento</span>
        <StorageManagerPaymentMethod paymentMethod={"Dinheiro"} />
      </div>
      <span className="text-[0.85rem] font-semibold">Endereço</span>
      <span className="text-[0.85rem] font-medium text-[#777777]">Rua dos Banzeiro, N 356 Bairro: Ayrtor Senna</span>
      <div className="overflow-hidden flex justify-center items-center max-w-86">
        <span className="text-[0.85rem] font-semibold">
          -----------------------------------------------------------
        </span>
      </div>
      <div className="flex flex-col pt-4 pb-1 gap-3.5">
        <StorageManagerPackagePrice label={"Frete"} price={7.5} />
        <StorageManagerPackagePrice label={"Total"} price={35} />
      </div>
      <div className="flex justify-center items-center">
        <button
          className="bg-[#3AB54A] text-white text-sm font-semibold px-6 py-2 rounded-md w-fit h-fit
        hover:bg-[#4cdb5f] hover:cursor-pointer transition-colors duration-300 mt-2"
        >
          Confirmar Montagem
        </button>
      </div>
    </div>
  );
}

export default StorageManagerPackageConfirmation;
