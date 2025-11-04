import StorageManagerDatePickerRange from "./components/date/StorageManagerDateIntervalPicker";
import StorageManagerFIlterRadioButtons from "./components/StorageManagerFilterRadioButtons";
import StorageManagerSectionAndHeader from "./components/StorageManagerSectionAndHeader";
import StorageManagerTotalsComparison from "./components/StorageManagerTotalsComparison";
import gainGraphicIcon from "./svgs/GainGraphicIcon.svg";
import losingGraphicIcon from "./svgs/LosingGraphicIcon.svg";
import StorageManagerSalesLabel from "./components/sales/StorageManagerSalesLabel";
import StorageManagerSalesItem from "./components/sales/StorageManagerSalesItem";
import StorageManagerSalesShowButtonQuantity from "./components/pagination/StorageManagerSalesShowButtonQuantity";
import StorageManagerSalesPagination from "./components/pagination/StorageManagerSalesPagination";

function StorageManagerSalesPage() {
  return (
    <StorageManagerSectionAndHeader>
      <div className="flex flex-col h-full w-full gap-4 bg-[#f2f2f2]">
        <div className="h-fit w-full flex flex-row justify-between gap-1 pt-4.5 bg-[#f2f2f2]">
          <StorageManagerTotalsComparison
            title={"Receita Total"}
            value={"R$ 12.540, 32"}
            graphicIcon={gainGraphicIcon}
            interval={"Do ultimo mês"}
            percentDifference={<h4 className="text-green-700 text-[0.8rem]">4,2%</h4>}
          />
          <StorageManagerTotalsComparison
            title={"Pedidos Total"}
            value={"246"}
            graphicIcon={losingGraphicIcon}
            interval={"Do ultimo mês"}
            percentDifference={<h4 className="text-red-700 text-[0.8rem]">-0,2%</h4>}
          />
          <StorageManagerTotalsComparison
            title={"Total de Produtos"}
            value={"450"}
            graphicIcon={losingGraphicIcon}
            interval={"Do ultimo mês"}
            percentDifference={<h4 className="text-red-700 text-[0.8rem]">-2,7%</h4>}
          />
        </div>
        <div className="flex flex-row justify-between max-h-9">
          <div className="flex justify-center items-center py-1 px-2.5 gap-6 w-fit text-sm bg-[#E8E8E8] rounded-lg">
            <StorageManagerFIlterRadioButtons buttonTitle={"Todas"} />
            <StorageManagerFIlterRadioButtons buttonTitle={"Aguardando Montagem"} />
            <StorageManagerFIlterRadioButtons buttonTitle={"Canceladas"} />
            <StorageManagerFIlterRadioButtons buttonTitle={"Finalizadas"} />
          </div>
          <StorageManagerDatePickerRange />
        </div>
        <div className="bg-[#FEFEFE] h-fit w-full py-2 -mt-2 rounded-2xl flex flex-col min-w-128">
          <StorageManagerSalesLabel />
          <StorageManagerSalesItem />
          <StorageManagerSalesItem />
          <StorageManagerSalesItem />
          <StorageManagerSalesItem />
          <StorageManagerSalesItem />
          <StorageManagerSalesItem />
          <StorageManagerSalesItem />
          <StorageManagerSalesItem />
        </div>
        <div className="flex flex-row justify-between items-center w-full h-full">
          <StorageManagerSalesShowButtonQuantity />
          <StorageManagerSalesPagination />
        </div>
      </div>
    </StorageManagerSectionAndHeader>
  );
}

export default StorageManagerSalesPage;
