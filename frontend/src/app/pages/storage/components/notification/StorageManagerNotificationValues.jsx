import StorageManagerPaymentMethod from "./StorageManagerPaymentMethod";

function StorageManagerNotificationValues() {
  return (
    <div className="grid grid-cols-[1.5fr_1fr_2.5fr_2.5fr_1.25fr_1.25fr] text-xs">
      <div className="flex flex-col font-medium">
        <span>Cebola Roxa</span>
        <span>Tomate</span>
        <span>Batata Inglesa</span>
      </div>
      <div className="flex flex-col font-medium text-[#777777]">
        <span>1kg</span>
        <span>3kg</span>
        <span>6kg</span>
      </div>
      <div className="w-full h-full flex justify-center items-center">
        <StorageManagerPaymentMethod paymentMethod={"Dinheiro"} />
      </div>
      <div className="flex flex-col font-medium">
        <span>Rua dos Banzeiro, N 356</span>
        <span>Bairro: Ayrton Senna</span>
      </div>
      <span className="flex flex-col font-medium line-">R$ 7,00</span>
      <span className="flex flex-col font-medium line-">R$ 35,00</span>
    </div>
  );
}

export default StorageManagerNotificationValues;
