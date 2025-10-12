function CommunUnderlineInputText({aboveTypeName}) {
  return (
    <div className="flex flex-col">
      <h2 className="text-black font-bold text-xs">{aboveTypeName}:</h2>
      <input className="border-b border-[#00853D] text-black outline-none
      focus:border-[#00B050] focus:border-2" type="text" />
    </div>
  );
}

export default CommunUnderlineInputText;
