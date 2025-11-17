import React, { useEffect, useRef } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useLogout } from '../../pages/auth/hooks/useLogout';
import './header.css';
import AddressModal from '../../components/AddressModal.jsx';
import LocalizationIcon from '../../pages/auth/svgs/LocalizationIcon.svg';

const Header = () => {
  const logout = useLogout();
  const searchInputRef = useRef(null);
  const cartCountRef = useRef(0);
  const navigate = useNavigate();
  const [isAddressOpen, setIsAddressOpen] = React.useState(false);
  const [savedAddress, setSavedAddress] = React.useState(null);

  useEffect(() => {
    // Setup search functionality when component mounts
    setupSearchFunctionality();
  }, []);

  // Search functionality
  const handleSearch = () => {
    const searchTerm = searchInputRef.current ? searchInputRef.current.value.trim() : '';

    if (searchTerm) {
      console.log('Searching for:', searchTerm);
      performSearch(searchTerm);
    }
  };

  const handleSearchInput = (event) => {
    const searchTerm = event.target.value;
    // Implement real-time search suggestions here
    console.log('Search input changed:', searchTerm);
  };

  const performSearch = (searchTerm) => {
    // Implement actual search functionality
    console.log('Performing search for:', searchTerm);

    // Example: redirect to search results page
    // window.location.href = `/search?q=${encodeURIComponent(searchTerm)}`;
  };

  const setupSearchFunctionality = () => {
    // Additional search setup if needed
    console.log('Search functionality initialized');
  };

  // Cart functionality
  const handleCartClick = () => {
    console.log('Cart button clicked');
    navigate('/cart');
  };

  const updateCartCount = (count) => {
    cartCountRef.current = count;
    // Update cart icon with item count if needed
    console.log('Cart count updated:', count);
  };

  // Profile functionality
  const handleProfileClick = () => {
    console.log('Profile button clicked');
    navigate('/user/edit-profile');
  };

  // Utility methods
  const showNotification = (message, type = 'info') => {
    console.log(`${type.toUpperCase()}: ${message}`);
  };

  // Address prompt action
  const handleAddressClick = () => {
    setIsAddressOpen(true);
  };

  const handleAddressSave = (address) => {
    setSavedAddress(address);
    showNotification('Endereço salvo', 'success');
  };

  const handleAddressDelete = () => {
    setSavedAddress(null);
    showNotification('Endereço excluído', 'info');
  };

  return (
    <>
    <header className="w-full h-100 bg-ceasa-green px-4 flex items-center justify-between shadow-sm">
      {/* Logo */}
      <div className="flex items-center">
        <div className="logo-container">
          <img
            src="/logo.png"
            alt="Ceasa Digital Logo"
            className="h-20 w-20"
          />
        </div>
      </div>

      {/* Search Bar */}
      <div className="flex-1 max-w-2xl mx-8">
        <div className="relative flex border border-transparent rounded-full shadow-sm">
          <input
            ref={searchInputRef}
            type="text"
            placeholder="Buscar no Ceasa Digital..."
            className="flex-1 px-4 py-3 rounded-l-full border-0 bg-white text-gray-700 placeholder-gray-500 focus:outline-none"
            onChange={handleSearchInput}
            onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
          />
          <div
            className="rounded-r-full px-4 py-3 flex items-center justify-center cursor-pointer transition-colors"
            style={{ backgroundColor: '#2D4C2D' }}
            onClick={handleSearch}
          >
            <svg 
              className="w-5 h-5 text-white" 
              fill="none" 
              stroke="currentColor" 
              viewBox="0 0 24 24"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth="2"
                d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
              />
            </svg>
          </div>
        </div>
      </div>

      {/* Right Icons and Address prompt */}
      <div className="flex items-center space-x-4">
        {/* Address prompt (near cart) */}
        <div
          className="address-prompt flex items-center px-3 py-2 rounded-md cursor-pointer text-white"
          onClick={handleAddressClick}
          title="Adicione seu endereço"
        >
          <img src={LocalizationIcon} alt="Localização" className="w-8 h-8 mr-2" />
          <div className="flex flex-col leading-tight select-none">
            <span className="text-[10px] sm:text-xs">Adicione seu</span>
            <span className="text-xs sm:text-sm font-semibold address-value">
              {savedAddress?.district && savedAddress?.city
                ? `Endereço: ${savedAddress.district}, ${savedAddress.city}`
                : 'Endereço'}
            </span>
          </div>
        </div>
        {/* Shopping Cart */}
        <svg
          className="w-10 h-10 text-white cursor-pointer hover:text-gray-200 transition-colors"
          fill="none"
          stroke="currentColor"
          viewBox="0 0 24 24"
          onClick={handleCartClick}
        >
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="1.5" d="M2.25 3h1.386c.51 0 .955.343 1.087.835l.383 1.437M7.5 14.25a3 3 0 00-3 3h15.75m-12.75-3h11.218c1.121-2.3 2.1-4.684 2.924-7.138a60.114 60.114 0 00-16.536-1.84M7.5 14.25L5.106 5.272M6 20.25a.75.75 0 11-1.5 0 .75.75 0 011.5 0zm12.75 0a.75.75 0 11-1.5 0 .75.75 0 011.5 0z" />
        </svg>

        {/* User Profile */}
        <svg
          className="w-10 h-10 text-white cursor-pointer hover:text-gray-200 transition-colors"
          fill="none"
          stroke="currentColor"
          viewBox="0 0 24 24"
          onClick={handleProfileClick}
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth="2"
            d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
          />
        </svg>
        {/* NOVO: Logout Icon */}
        <svg
          className="w-10 h-10 text-white cursor-pointer hover:text-red-300 transition-colors"
          fill="none"
          stroke="currentColor"
          viewBox="0 0 24 24"
          onClick={logout}
          title="Sair"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth="2"
            d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"
          />
        </svg>

      </div>
    </header>
    <AddressModal
      isOpen={isAddressOpen}
      onClose={() => setIsAddressOpen(false)}
      onSave={handleAddressSave}
      onDelete={handleAddressDelete}
      initialAddress={savedAddress}
    />
    </>
  );
};

export default Header;