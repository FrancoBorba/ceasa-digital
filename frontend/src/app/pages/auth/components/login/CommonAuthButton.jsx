function CommonAuthButton({buttonName}) {
  return (
    <button type="submit" className="bg-[#1D3D1C] text-white rounded-xl shadow-xl py-4 cursor-pointer mx-6 font-extrabold tracking-widest transition-colors duration-500 hover:bg-[#337831]">
      {buttonName}
    </button>
  );
}

export default CommonAuthButton;
