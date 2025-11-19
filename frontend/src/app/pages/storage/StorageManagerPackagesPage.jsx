import StorageManagerPackageConfirmation from "./components/pack/StorageManagerPackageConfirmation";
import StorageManagerSalesPagination from "./components/pagination/StorageManagerSalesPagination";
import StorageManagerFIlterRadioButtons from "./components/common/StorageManagerFilterRadioButtons";
import StorageManagerSectionAndHeader from "./components/common/StorageManagerSectionAndHeader";

function StorageManagerPackagesPage() {
  return (
    <StorageManagerSectionAndHeader title={"Pacotes"}>
      <div className="h-full w-full flex flex-col gap-4 max-w-fit ">
        <div className="h-fit w-full flex flex-col gap-10 pt-6">
          <div className="flex justify-center items-center py-1 px-2.5 gap-12 w-fit text-xs bg-[#E8E8E8] rounded-lg">
            <StorageManagerFIlterRadioButtons buttonTitle={"Montagem"} />
            <StorageManagerFIlterRadioButtons buttonTitle={"Aguardando Entrega"} />
            <StorageManagerFIlterRadioButtons buttonTitle={"Concluído"} />
          </div>
        </div>
        <div className="flex flex-row gap-4 w-full">
          <StorageManagerPackageConfirmation />
          <StorageManagerPackageConfirmation />
          <StorageManagerPackageConfirmation />
        </div>

        <div className="flex justify-center items-center">         <StorageManagerSalesPagination /></div>
      </div>
    </StorageManagerSectionAndHeader>
  );
}

export default StorageManagerPackagesPage;
