import { useState, useRef, useEffect } from "react";
import calendarIcon from "../svgs/BellIcon.svg";
import arrowIcon from "../svgs/BellIcon.svg";

function DateRangePicker() {
  const today = new Date();
  const [open, setOpen] = useState(false);
  const [tempRange, setTempRange] = useState({
    from: { day: today.getDate(), month: today.getMonth() + 1, year: today.getFullYear() },
    to: { day: today.getDate(), month: today.getMonth() + 1, year: today.getFullYear() },
  });
  const [confirmedRange, setConfirmedRange] = useState(tempRange);

  const ref = useRef(null);

  // Fecha o seletor ao clicar fora
  useEffect(() => {
    const handleClickOutside = (e) => {
      if (ref.current && !ref.current.contains(e.target)) setOpen(false);
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  // Função auxiliar para gerar listas
  const generateArray = (start, end) => {
    const arr = [];
    for (let i = start; i <= end; i++) arr.push(i);
    return arr;
  };

  const days = generateArray(1, 31);
  const months = [
    "Jan", "Fev", "Mar", "Abr", "Mai", "Jun",
    "Jul", "Ago", "Set", "Out", "Nov", "Dez",
  ];
  const years = [2025];

  const formatDate = ({ day, month, year }) =>
    `${String(day).padStart(2, "0")} ${months[month - 1]} ${year}`;

  const handleConfirm = () => {
    setConfirmedRange(tempRange);
    setOpen(false);
  };

  const handleChange = (type, field, value) => {
    setTempRange((prev) => ({
      ...prev,
      [type]: { ...prev[type], [field]: Number(value) },
    }));
  };

  return (
    <div ref={ref} className="relative w-fit">
      {/* Botão principal */}
      <button
        onClick={() => setOpen(!open)}
        className="flex items-center justify-between bg-[#E8E8E8] rounded-md px-3 py-2 min-w-[250px] text-gray-700 text-sm hover:bg-[#dcdcdc] transition"
      >
        <div className="flex items-center gap-2">
          <img src={calendarIcon} alt="Calendário" className="w-5 h-5 opacity-70" />
          <span>
            {confirmedRange.from && confirmedRange.to
              ? `${formatDate(confirmedRange.from)} - ${formatDate(confirmedRange.to)}`
              : "Selecione o intervalo"}
          </span>
        </div>
        <img src={arrowIcon} alt="Abrir" className="w-4 h-4 opacity-60" />
      </button>

      {/* Menu de seleção */}
      {open && (
        <div className="absolute mt-2 bg-white shadow-lg rounded-xl z-50 border border-gray-200 p-4 flex flex-col gap-4 w-[300px]">
          {/* Seletor de data inicial */}
          <div className="flex flex-col gap-1">
            <span className="text-sm font-medium text-gray-700">Data inicial:</span>
            <div className="flex gap-2">
              <select
                value={tempRange.from.day}
                onChange={(e) => handleChange("from", "day", e.target.value)}
                className="border rounded-md p-1 text-sm text-gray-700 w-full bg-[#F8F8F8]"
              >
                {days.map((d) => (
                  <option key={d}>{d}</option>
                ))}
              </select>
              <select
                value={tempRange.from.month}
                onChange={(e) => handleChange("from", "month", e.target.value)}
                className="border rounded-md p-1 text-sm text-gray-700 w-full bg-[#F8F8F8]"
              >
                {months.map((m, i) => (
                  <option key={m} value={i + 1}>
                    {m}
                  </option>
                ))}
              </select>
              <select
                value={tempRange.from.year}
                onChange={(e) => handleChange("from", "year", e.target.value)}
                className="border rounded-md p-1 text-sm text-gray-700 w-full bg-[#F8F8F8]"
              >
                {years.map((y) => (
                  <option key={y}>{y}</option>
                ))}
              </select>
            </div>
          </div>

          {/* Seletor de data final */}
          <div className="flex flex-col gap-1">
            <span className="text-sm font-medium text-gray-700">Data final:</span>
            <div className="flex gap-2">
              <select
                value={tempRange.to.day}
                onChange={(e) => handleChange("to", "day", e.target.value)}
                className="border rounded-md p-1 text-sm text-gray-700 w-full bg-[#F8F8F8]"
              >
                {days.map((d) => (
                  <option key={d}>{d}</option>
                ))}
              </select>
              <select
                value={tempRange.to.month}
                onChange={(e) => handleChange("to", "month", e.target.value)}
                className="border rounded-md p-1 text-sm text-gray-700 w-full bg-[#F8F8F8]"
              >
                {months.map((m, i) => (
                  <option key={m} value={i + 1}>
                    {m}
                  </option>
                ))}
              </select>
              <select
                value={tempRange.to.year}
                onChange={(e) => handleChange("to", "year", e.target.value)}
                className="border rounded-md p-1 text-sm text-gray-700 w-full bg-[#F8F8F8]"
              >
                {years.map((y) => (
                  <option key={y}>{y}</option>
                ))}
              </select>
            </div>
          </div>

          {/* Botão Confirmar */}
          <button
            onClick={handleConfirm}
            className="bg-green-500 hover:bg-green-600 text-white rounded-md py-1.5 text-sm font-semibold transition"
          >
            Confirmar
          </button>
        </div>
      )}
    </div>
  );
}

export default DateRangePicker;
