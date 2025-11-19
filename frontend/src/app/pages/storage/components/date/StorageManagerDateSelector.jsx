function StorageManagerDateSelector({ value, possibleValues }) {
  return (
    <select
      className="w-16 h-6 rounded-md bg-white text-black font-medium text-sm p-1 shadow-md hover:cursor-pointer"
      value={value}
    >
      {possibleValues.map((possibleValue) => (
        <option key={possibleValue}>{possibleValue}</option>
      ))}
    </select>
  );
}

export default StorageManagerDateSelector;
