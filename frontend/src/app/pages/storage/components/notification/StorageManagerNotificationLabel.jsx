function StorageManagerNotificationLabel() {
  return (
    <div className="grid grid-cols-[1.5fr_1fr_2.5fr_2.5fr_1.25fr_1.25fr] text-black font-medium">
      <span>Itens</span>
      <span>Quantidade</span>
      <div className="w-full h-full flex justify-center items-center">
        <span>Método Pagamento</span>
      </div>
      <span>Endereço</span>
      <span>Frete</span>
      <span>Total</span>
    </div>
  );
}

export default StorageManagerNotificationLabel;
