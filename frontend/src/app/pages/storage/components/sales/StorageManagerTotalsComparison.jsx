function StorageManagerTotalsComparison({ graphicIcon, title, value, percentDifference, interval }) {
  return (
    <div className="h-30 w-78 bg-white flex flex-col justify-between py-4 px-2 rounded-2xl shadow">
      <h2 className="text-2xl text-black font-medium">{title}</h2>
      <div className="flex flex-row justify-between">
        <h2 className="text-gray-400 text-2xl font-medium">{value}</h2>
        <div className="flex flex-col justify-center items-center pr-3">
          <div className="flex flew-row justify-evenly gap-3">
            <img className="size-[1.2rem]" src={graphicIcon} alt="Ícone de ganhos de receita." />
            {percentDifference}
          </div>
          <h5 className="text-gray-400 text-[0.5rem]">{interval}</h5>
        </div>
      </div>
    </div>
  );
}

export default StorageManagerTotalsComparison;
