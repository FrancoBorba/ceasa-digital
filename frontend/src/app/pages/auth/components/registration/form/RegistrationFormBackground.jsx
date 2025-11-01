function RegistrationFormBackground({children, onSubmit, buttonName}) {
  return (
    <main className="flex justify-center items-center">
      <form
        className="flex flex-col gap-8 bg-white w-10/12 sm:w-8/12 md:w-6/12 lg:w-4/12 p-8 rounded-2xl overflow-y-auto" 
        onSubmit={onSubmit}
      >
        {children}
        <div className="flex items-center justify-center">
          <button
            type="submit"
            className=" h-12 w-3/6 bg-[#3AB54A] text-white font-bold font-stretch-expanded rounded-lg hover:bg-[#69ea7a] transition-colors hover:cursor-pointer"
          >
            {buttonName}
          </button>
        </div>
      </form>
    </main>
  );
}

export default RegistrationFormBackground;
