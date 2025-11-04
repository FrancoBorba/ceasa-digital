import magnifyingGlassIcon from "../svgs/MagnifyingGlassIcon.svg";

function SearchBar() {
  return (
    <label className="flex flex-row gap-2 items-center bg-[#E8E8E8] rounded-lg px-4 py-2 w-56 cursor-text">
      <img src={magnifyingGlassIcon} alt="Ícone de lupa." />
      <input
        type="text"
        placeholder="Pesquisar.."
        className="bg-transparent outline-none text-[#777777] w-full placeholder-gray-400 h-5 text-sm"
      />
    </label>
  );
}

export default SearchBar;
