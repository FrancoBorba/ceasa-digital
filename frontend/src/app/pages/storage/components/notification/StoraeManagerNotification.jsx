import quitNotificationButtonIcon from "../../svgs/QuitNotificationButtonIcon.svg";
import StorageManagerNotificationLabel from "./StorageManagerNotificationLabel";
import StorageManagerNotificationValues from "./StorageManagerNotificationValues";

function StorageManagerNotification() {
  return (
    <div className="bg-white h-fit w-full rounded-4xl p-5 flex flex-col gap-1.5 shadow text-sm">
      <div className="flex flex-col">
        <div className="flex flex-row justify-between">
          <h4 className="text-black font-semibold">
            Pedidos #2341 - <span className="text-xs text-[#777777]">Adevaldo Pereira Dos Santos</span>
          </h4>
          <button
            className="size-6 hover:cursor-pointer hover:bg-[#e8e8e8] transition
              duration-300 rounded-full p-1"
          >
            <img src={quitNotificationButtonIcon} alt="" />
          </button>
        </div>
        <h5 className="-mt-1 text-xs text-[#3AB54A] font-medium">Pedido realizado aguardando montagem</h5>
      </div>
      <StorageManagerNotificationLabel />
      <StorageManagerNotificationValues />
      <button
        className="bg-[#3AB54A] text-white text-sm font-semibold px-6 py-2 rounded-md w-fit h-fit
        hover:bg-[#4cdb5f] hover:cursor-pointer transition-colors duration-300 mt-2"
      >
        Realizar Montagem
      </button>
    </div>
  );
}

export default StorageManagerNotification;
