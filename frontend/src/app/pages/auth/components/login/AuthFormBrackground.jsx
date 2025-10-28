import CommonAuthButton from "./CommonAuthButton";

function AuthFormBrackground({ children, onSubmit, title, buttonName }) {
  return (
    <div className="flex justify-center h-screen w-screen  bg-gradient-to-b from-[#FFF6E2] to-white shadow items-center py-16">
      <form
        className="flex flex-col justify-around max-w-2xl w-4/12 min-w-xs bg-white px-12
        rounded-2xl shadow-md h-full overflow-y-auto gap-4"
        onSubmit={onSubmit}
      >
        <div className="flex flex-col gap-y-4">
          <h1 className="text-[#1D3D1C] text-2xl pb-8 tracking-widest font-extrabold">
            {title}
          </h1>
          {children}
        </div>
          <CommonAuthButton buttonName={buttonName} />
      </form>
    </div>
  );
}

export default AuthFormBrackground;
