import StorageManagerNotification from "./components/notification/StoraeManagerNotification";
import StorageManagerFIlterRadioButtons from "./components/StorageManagerFilterRadioButtons";
import StorageManagerSectionAndHeader from "./components/StorageManagerSectionAndHeader";
import StorageManagerSalesShowButtonQuantity from "./components/pagination/StorageManagerSalesShowButtonQuantity";
import StorageManagerSalesPagination from "./components/pagination/StorageManagerSalesPagination";

function StorageManagerNotificationPage() {
  return (
    <StorageManagerSectionAndHeader title={"Notificações"}>
      <div className="h-full w-full">
        <div className="h-fit w-full flex flex-col gap-10 pt-6">
          <div className="flex justify-center items-center py-1 px-2.5 gap-6 w-fit text-xs bg-[#E8E8E8] rounded-lg">
            <StorageManagerFIlterRadioButtons buttonTitle={"Todas"} />
            <StorageManagerFIlterRadioButtons buttonTitle={"Pedidos"} />
            <StorageManagerFIlterRadioButtons buttonTitle={"FeedBacks"} />
          </div>
          <div className="flex flex-col w-full h-full gap-2">
            <h3 className="text-[#777777] text-sm font-medium">Novos Pedidos</h3>
            <div className="flex flex-col w-full h-full gap-4">
              <StorageManagerNotification />
              <StorageManagerNotification />
            </div>
          </div>
          <div className="flex flex-row justify-between items-center w-full h-full">
            <StorageManagerSalesShowButtonQuantity />
            <StorageManagerSalesPagination />
          </div>
        </div>
      </div>
    </StorageManagerSectionAndHeader>
  );
}

export default StorageManagerNotificationPage;
