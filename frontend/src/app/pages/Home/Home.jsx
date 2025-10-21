import React, { useState, useEffect } from 'react';
import Header from '../../layouts/header/header';
import Footer from '../../layouts/footer/footer';
import { getProducts } from '../Product/services/productService';
import Products from '../Product/Product';

const Home = () => {
    const [currentSlide, setCurrentSlide] = useState(0);
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);

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
        }, 3500); // Passa a cada x milissegundos

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
    //retornando produtos do backend
    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const data = await getProducts();
                setProducts(data);
            } catch (error) {
                console.error('Erro ao buscar produtos:', error);
            } finally {
                setLoading(false);
            }
        };
        fetchProducts();
    }, []);

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
                            {promotionalImages.map((img, index) => (
                            <div
                                key={index}
                                className={`absolute inset-0 transition-opacity duration-3500 ease-in-out ${
                                index === currentSlide ? 'opacity-100 z-10' : 'opacity-0 z-0'
                                }`}
                            >
                                <img
                                src={img.image}
                                alt={img.title}
                                className="w-full h-full object-cover"
                                />
                                <div
                                className="absolute inset-0 flex flex-col justify-center items-center text-center text-white"
                                style={{
                                    background:
                                    'linear-gradient(135deg, rgba(0, 0, 0, 0.6) 0%, rgba(0, 0, 0, 0.3) 50%, rgba(0, 0, 0, 0.7) 100%)',
                                }}
                                >
                                    <h2
                                    className="text-6xl md:text-4xl sm:text-3xl font-black mb-2 tracking-widest md:tracking-wide"
                                    style={{ textShadow: '2px 2px 4px rgba(0, 0, 0, 0.8)' }}
                                    >
                                    {img.title}
                                </h2>
                                <p
                                    className="text-2xl md:text-xl sm:text-lg font-semibold opacity-90"
                                    style={{ textShadow: '1px 1px 2px rgba(0, 0, 0, 0.8)' }}
                                >
                                    {img.subtitle}
                                </p>
                                </div>
                            </div>
                            ))}
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

                {/* Espaço reservado para Calendário Sazonal */}
                <section className="w-full max-w-none m-0 mt-8 mb-8">
                    <div className="bg-white rounded-2xl shadow-lg p-8 border border-gray-200">
                        <div className="text-center">
                            <h2 className="text-2xl md:text-3xl font-bold text-gray-800 mb-4">
                                Calendário Sazonal
                            </h2>
                            <div className="bg-gray-50 rounded-xl p-12 border-2 border-dashed border-gray-300">
                                <div className="flex flex-col items-center justify-center space-y-4">
                                    <svg 
                                        className="w-16 h-16 text-gray-400" 
                                        fill="none" 
                                        stroke="currentColor" 
                                        viewBox="0 0 24 24"
                                    >
                                        <path 
                                            strokeLinecap="round" 
                                            strokeLinejoin="round" 
                                            strokeWidth={2} 
                                            d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" 
                                        />
                                    </svg>
                                    <p className="text-gray-500 font-medium">
                                        Calendário em desenvolvimento
                                    </p>
                                    <p className="text-sm text-gray-400 max-w-md">
                                        Em breve você poderá visualizar quais produtos estão na época ideal para consumo
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                {/* Produtos em destaque */}
                <Products />
                
            </main>
            <Footer />
        </div>
    );
};

export default Home;