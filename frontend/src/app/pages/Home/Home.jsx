import React, { useState, useEffect } from 'react';
import Header from '../../layouts/header/header';

const Home = () => {
    const [currentSlide, setCurrentSlide] = useState(0);

    // Imagens promocionais para o carrossel
    const promotionalImages = [
        {
            id: 1,
            title: "FRUTAS FRESCAS",
            subtitle: "Direto do pomar para sua mesa",
            image: "https://images.unsplash.com/photo-1610832958506-aa56368176cf?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80"
        },
        {
            id: 2,
            title: "VERDURAS & LEGUMES",
            subtitle: "Colhidos hoje mesmo",
            image: "https://images.unsplash.com/photo-1540420773420-3366772f4999?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2084&q=80"
        },
        {
            id: 3,
            title: "CARNES SELECIONADAS",
            subtitle: "Qualidade premium garantida",
            image: "https://images.unsplash.com/photo-1529692236671-f1f6cf9683ba?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80"
        },
        {
            id: 4,
            title: "QUEIJOS ARTESANAIS",
            subtitle: "Sabores únicos e tradicionais",
            image: "https://images.unsplash.com/photo-1486297678162-eb2a19b0a32d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2073&q=80"
        }
    ];

    // Autoplay do carrossel
    useEffect(() => {
        const interval = setInterval(() => {
            setCurrentSlide((prev) => (prev + 1) % promotionalImages.length);
        }, 3500); // Passa a cada 1 segundo

        return () => clearInterval(interval);
    }, [promotionalImages.length]);

    const nextSlide = () => {
        setCurrentSlide((prev) => (prev + 1) % promotionalImages.length);
    };

    const prevSlide = () => {
        setCurrentSlide((prev) => (prev - 1 + promotionalImages.length) % promotionalImages.length);
    };

    const goToSlide = (index) => {
        setCurrentSlide(index);
    };

    return (
        <div className="min-h-screen bg-gray-50">
            <Header />
            <main className="p-5 px-4 md:p-5 md:px-4">
                {/* Carrossel*/}
                <section className="w-full max-w-none m-0">
                    <div className="relative w-full h-[400px] md:h-96 sm:h-80 overflow-hidden rounded-2xl md:rounded-xl shadow-2xl group">
                        <button 
                            className="absolute top-1/2 left-5 md:left-3 -translate-y-1/2 bg-transparent border-none w-20 h-20 md:w-16 md:h-16 text-5xl md:text-4xl font-bold text-white cursor-pointer transition-all duration-300 z-10 opacity-0 group-hover:opacity-100 hover:text-blue-500 hover:scale-110 focus:outline-none active:outline-none" 
                            style={{textShadow: '2px 2px 4px rgba(0, 0, 0, 0.8)'}}
                            onClick={prevSlide}
                        >
                            &#8249;
                        </button>
                        
                        <div className="w-full h-full relative">
                            <div className="w-full h-full relative">
                                <img 
                                    src={promotionalImages[currentSlide].image} 
                                    alt={promotionalImages[currentSlide].title}
                                    className="w-full h-full object-cover transition-transform duration-500"
                                />
                                <div 
                                    className="absolute inset-0 flex flex-col justify-center items-center text-center text-white"
                                    style={{
                                        background: 'linear-gradient(135deg, rgba(0, 0, 0, 0.6) 0%, rgba(0, 0, 0, 0.3) 50%, rgba(0, 0, 0, 0.7) 100%)'
                                    }}
                                >
                                    <h2 
                                        className="text-6xl md:text-4xl sm:text-3xl font-black mb-2 tracking-widest md:tracking-wide"
                                        style={{textShadow: '2px 2px 4px rgba(0, 0, 0, 0.8)'}}
                                    >
                                        {promotionalImages[currentSlide].title}
                                    </h2>
                                    <p 
                                        className="text-2xl md:text-xl sm:text-lg font-semibold opacity-90"
                                        style={{textShadow: '1px 1px 2px rgba(0, 0, 0, 0.8)'}}
                                    >
                                        {promotionalImages[currentSlide].subtitle}
                                    </p>
                                </div>
                            </div>
                        </div>
                        
                        <button 
                            className="absolute top-1/2 right-5 md:right-3 -translate-y-1/2 bg-transparent border-none w-20 h-20 md:w-16 md:h-16 text-5xl md:text-4xl font-bold text-white cursor-pointer transition-all duration-300 z-10 opacity-0 group-hover:opacity-100 hover:text-blue-500 hover:scale-110 focus:outline-none active:outline-none"
                            style={{textShadow: '2px 2px 4px rgba(0, 0, 0, 0.8)'}}
                            onClick={nextSlide}
                        >
                            &#8250;
                        </button>
                        
                        {/* Indicadores */}
                        <div className="absolute bottom-5 left-1/2 -translate-x-1/2 flex justify-center gap-2 z-10">
                            {promotionalImages.map((_, index) => (
                                <button
                                    key={index}
                                    className={`w-2 h-2 rounded-full border-none cursor-pointer transition-all duration-300 focus:outline-none active:outline-none ${
                                        index === currentSlide 
                                            ? 'bg-white scale-125' 
                                            : 'bg-white bg-opacity-50 hover:bg-opacity-80 hover:scale-110'
                                    }`}
                                    style={{
                                        aspectRatio: '1 / 1',
                                        padding: '0',
                                        appearance: 'none',
                                        WebkitAppearance: 'none',
                                        lineHeight: '0',
                                        display: 'inline-block'
                                    }}
                                    onClick={() => goToSlide(index)}
                                />
                            ))}
                        </div>
                    </div>
                </section>
            </main>
        </div>
    );
};

export default Home;