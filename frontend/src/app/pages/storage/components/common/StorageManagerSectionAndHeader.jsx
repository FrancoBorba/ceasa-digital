import { useState } from "react";
import salesIcon from "../../svgs/SalesIcon.svg";
import notificationIcon from "../../svgs/NotificationIcon.svg";
import packagesIcon from "../../svgs/PackagesIcon.svg";
import waitingAssemblyIcon from "../../svgs/WaitingAssemblyIcon.svg";
import completedSalesIcon from "../../svgs/CompletedSalesIcon.svg";
import canceledSalesIcon from "../../svgs/CanceledSalesIcon.svg";
import bellIcon from "../../svgs/BellIcon.svg";
import engineIcon from "../../svgs/EngineIcon.svg";
import letterIcon from "../../svgs/LetterIcon.svg";
import StorageManagerOptionButton from "./StorageManagerOptionButton";
import StorageManagerSubOptionButton from "./StorageManagerSubOptionButton";
import SearchBar from "./StorageManagerSearchBar";
import StorageManagerPageTitle from "./StorageManagerPageTitle";
import StorageManagerIconButton from "./StorageManagerIconButton";
import { useNavigate, useLocation } from "react-router";

function StorageManagerSectionAndHeader({ children, title }) {
  const [menuOpen, setMenuOpen] = useState(false);
  const navigate = useNavigate();
  const location = useLocation();

  const handleSalesButtonClick = () => {
    const storageManagerSalesPath = "/storage-manager/sales";
    if (location.pathname == storageManagerSalesPath) {
      setMenuOpen(!menuOpen);
    } else if (menuOpen) {
      navigate(storageManagerSalesPath);
    }
    setMenuOpen(!menuOpen);
  };

  const handleNotificationButtonClick = () => {
    const storageManagerNotificationPath = "/storage-manager/notification";
    if (location.pathname != storageManagerNotificationPath) {
      navigate(storageManagerNotificationPath);
    }
  };

  const handlePackagesButtonClick = () => {
    const storageManagerPackagesPath = "/storage-manager/packages";
    if (location.pathname != storageManagerPackagesPath) {
      navigate(storageManagerPackagesPath);
    }
  };

  return (
    <div className="h-full w-full pt-14 pb-8 pl-10 pr-6">
      <div className="fixed inset-0 bg-[#f2f2f2] -z-10" />
      <div className="flex flex-row justify-start gap-6 w-full h-full">
        <section className="h-[37.7rem] relative bg-white w-70 min-w-50 rounded-4xl z-10 flex flex-col justify-between items-center gap-6 pb-4 px-2 mt-14 shadow">
          <div className="flex flex-col items-center">
            <img
              src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQTOZaOqdQatrMBJCWv-iQ4Tk-ptgyGixnJkQ&s"
              alt="Foto de perfil do usuário logado."
              className="size-16 rounded-full p-1 bg-green-700 relative -mt-8 z-20"
            />
            <div className="py-5 flex flex-col justify-center items-center">
              <h1 className="text-black font-semibold text-2xl">Joel</h1>
              <h3 className="text-[#777777] text-[0.8rem]">Gerente de estoque</h3>
            </div>
          </div>

          <div className="flex flex-col w-full">
            <div className="w-full">
              <StorageManagerOptionButton
                onClick={() => handleSalesButtonClick()}
                buttonIcon={salesIcon}
                buttonTitle={"Vendas"}
                buttonIconAlt={"Ícone de vendas."}
              />
              {menuOpen && (
                <div className="flex flex-col">
                  <StorageManagerSubOptionButton
                    buttonIcon={waitingAssemblyIcon}
                    buttonIconAlt={"Ícone de aguardando montagem."}
                    buttonTitle={"Aguardando Montagem"}
                  />
                  <StorageManagerSubOptionButton
                    buttonIcon={canceledSalesIcon}
                    buttonIconAlt={"Ícone de vendas canceladas."}
                    buttonTitle={"Canceladas"}
                  />
                  <StorageManagerSubOptionButton
                    buttonIcon={completedSalesIcon}
                    buttonIconAlt={"Ícone de vendas finalizadas."}
                    buttonTitle={"Finalizadas"}
                  />
                </div>
              )}
              <StorageManagerOptionButton
                onClick={() => handleNotificationButtonClick()}
                buttonIcon={notificationIcon}
                buttonTitle={"Notificações"}
                buttonIconAlt={"Ícone de notificações."}
              />
              <StorageManagerOptionButton
                onClick={() => handlePackagesButtonClick()}
                buttonIcon={packagesIcon}
                buttonTitle={"Pacotes"}
                buttonIconAlt={"Ícone de pacotes."}
              />
            </div>
          </div>

          <h3 className="text-gray-400 text-[0.8rem] text-center mt-auto">Localização</h3>
        </section>
        <div className="flex flex-col w-full">
          <div className="flex flex-row justify-between -mt-2 w-full">
            <StorageManagerPageTitle title={title} />
            <div className="flex justify-evenly flex-row h-fit gap-2">
              <SearchBar />
              <StorageManagerIconButton buttonIcon={bellIcon} buttonIconAlt={"Ícone de sino."} />
              <StorageManagerIconButton buttonIcon={engineIcon} buttonIconAlt={"Ícone de engrenagem."} />
              <StorageManagerIconButton buttonIcon={letterIcon} buttonIconAlt={"Ícone de carta."} />
            </div>
          </div>
          {children}
        </div>
      </div>
    </div>
  );
}

export default StorageManagerSectionAndHeader;
