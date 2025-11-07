function StorageManagerPaymentMethod({paymentMethod}) {
  return (
    <div className="w-full h-full flex justify-center items-center">
      <span
        className="w-fit h-fit bg-[#CEFFD4] text-[#00853D] py-1 px-8
                      rounded-xl"
      >
        {paymentMethod}
      </span>
    </div>
  );
}

export default StorageManagerPaymentMethod;
