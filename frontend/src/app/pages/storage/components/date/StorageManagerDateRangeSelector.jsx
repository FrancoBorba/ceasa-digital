import StorageManagerDateSelector from "./StorageManagerDateSelector";

function StorageManagerDateRangeSelector({ title }) {
  const months = ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"];
  const years = [2025];

  return (
    <div
      className="relative w-full h-fit flex flex-col bg-[#f2f2f2] px-4 py-2.5 items-center justify-center gap-1 
        rounded-lg shadow-md"
    >
      <h3 className="font-medium text-[0.9rem] text-[#777777]">{title}</h3>
      <div className="flex flex-row justify-between gap-3 ">
        <StorageManagerDateSelector value={0} possibleValues={[1,2,3,4,5,6,7,8,9,10]} />
        <StorageManagerDateSelector value={months.at(0)} possibleValues={months} />
        <StorageManagerDateSelector value={years.at(0)} possibleValues={years} />
      </div>
    </div>
  );
}

export default StorageManagerDateRangeSelector;
