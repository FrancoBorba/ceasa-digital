import React, { useEffect, useState } from 'react';

export default function AddressModal({ isOpen, onClose, onSave, onDelete, initialAddress }) {
  const [form, setForm] = useState({
    street: '',
    number: '',
    district: '',
    city: '',
    state: '',
    cep: '',
    complement: '',
  });
  const [errors, setErrors] = useState({});

  useEffect(() => {
    if (initialAddress) {
      setForm(prev => ({ ...prev, ...initialAddress }));
    } else {
      try {
        const saved = localStorage.getItem('userAddress');
        if (saved) setForm(prev => ({ ...prev, ...JSON.parse(saved) }));
      } catch {}
    }
  }, [initialAddress]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  };

  const validate = () => {
    const nextErrors = {};
    if (!form.street?.trim()) nextErrors.street = 'Informe a rua';
    if (!form.number?.trim()) nextErrors.number = 'Informe o número';
    if (!form.city?.trim()) nextErrors.city = 'Informe a cidade';
    if (!form.cep?.trim()) nextErrors.cep = 'Informe o CEP';
    setErrors(nextErrors);
    return Object.keys(nextErrors).length === 0;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!validate()) return;
    try {
      localStorage.setItem('userAddress', JSON.stringify(form));
    } catch {}
    onSave?.(form);
    onClose?.();
  };

  const handleDelete = () => {
    try {
      localStorage.removeItem('userAddress');
    } catch {}
    onDelete?.();
    onClose?.();
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center">
      {/* Backdrop */}
      <div className="absolute inset-0 bg-black/40" onClick={onClose} />

      {/* Modal */}
      <div className="relative bg-white rounded-lg shadow-lg w-[95%] max-w-xl p-5">
        <div className="flex items-center justify-between mb-4">
          <h2 className="text-lg font-semibold">Adicionar Endereço</h2>
          <button
            type="button"
            className="text-gray-500 hover:text-gray-700"
            onClick={onClose}
            aria-label="Fechar"
          >
            ✕
          </button>
        </div>

        <form onSubmit={handleSubmit} className="space-y-3">
          <div className="grid grid-cols-1 sm:grid-cols-3 gap-3">
            <div className="sm:col-span-2">
              <label className="block text-sm mb-1">Rua</label>
              <input
                name="street"
                value={form.street}
                onChange={handleChange}
                className="w-full border rounded-md px-3 py-2"
              />
              {errors.street && <p className="text-red-600 text-xs mt-1">{errors.street}</p>}
            </div>
            <div>
              <label className="block text-sm mb-1">Número</label>
              <input
                name="number"
                value={form.number}
                onChange={handleChange}
                className="w-full border rounded-md px-3 py-2"
              />
              {errors.number && <p className="text-red-600 text-xs mt-1">{errors.number}</p>}
            </div>
          </div>

          <div className="grid grid-cols-1 sm:grid-cols-3 gap-3">
            <div>
              <label className="block text-sm mb-1">Bairro</label>
              <input
                name="district"
                value={form.district}
                onChange={handleChange}
                className="w-full border rounded-md px-3 py-2"
              />
            </div>
            <div>
              <label className="block text-sm mb-1">Cidade</label>
              <input
                name="city"
                value={form.city}
                onChange={handleChange}
                className="w-full border rounded-md px-3 py-2"
              />
              {errors.city && <p className="text-red-600 text-xs mt-1">{errors.city}</p>}
            </div>
            <div>
              <label className="block text-sm mb-1">Estado</label>
              <input
                name="state"
                value={form.state}
                onChange={handleChange}
                className="w-full border rounded-md px-3 py-2"
              />
            </div>
          </div>

          <div className="grid grid-cols-1 sm:grid-cols-3 gap-3">
            <div>
              <label className="block text-sm mb-1">CEP</label>
              <input
                name="cep"
                value={form.cep}
                onChange={handleChange}
                placeholder="00000-000"
                className="w-full border rounded-md px-3 py-2"
              />
              {errors.cep && <p className="text-red-600 text-xs mt-1">{errors.cep}</p>}
            </div>
            <div className="sm:col-span-2">
              <label className="block text-sm mb-1">Complemento</label>
              <input
                name="complement"
                value={form.complement}
                onChange={handleChange}
                className="w-full border rounded-md px-3 py-2"
              />
            </div>
          </div>

          <div className="flex justify-between items-center pt-2">
            <button
              type="button"
              className="px-4 py-2 rounded-md border text-red-700 border-red-300 hover:bg-red-50"
              onClick={handleDelete}
            >
              Excluir endereço
            </button>
            <div className="flex gap-3">
            <button
              type="button"
              className="px-4 py-2 rounded-md border"
              onClick={onClose}
            >
              Cancelar
            </button>
            <button
              type="submit"
              className="px-4 py-2 rounded-md text-white"
              style={{ backgroundColor: '#2c4c2b' }}
            >
              Salvar endereço
            </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}