import { useState } from 'react'
import './App.css'
import Login from '../pages/Login/Login'
import ForgotPassword from '../pages/ForgotPassword/ForgotPassword'

function App() {
  const [backendResponse, setBackendResponse] = useState('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const [page, setPage] = useState('home');

  const testBackendConnection = async () => {
    setLoading(true)
    setError('')
    setBackendResponse('')
    try {
      const apiUrl = import.meta.env.VITE_API_URL || 'http://localhost:8080'
      const response = await fetch(`${apiUrl}/users/public`)
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`)
      }
      const data = await response.text()
      setBackendResponse(data)
    } catch (err) {
      setError(`Erro na comunicação: ${err.message}`)
    } finally {
      setLoading(false)
    }
  }

  // Se estiver na página de redefinição, mostra só ela
  if (page === 'forgotPassword') {
    return (
      <ForgotPassword onBack={() => setPage('home')} />
    )
  }

  // Página principal (home/login + formulário de backend)
  return (
    <>
      <div>
        <h1 className="font-bold text-8xl mb-8">CEASA Digital</h1>
        <Login onForgotPassword={() => setPage('forgotPassword')} />
      </div>

      {/* Seção de teste do Backend */}
      <div className="card bg-gray-100 p-6 rounded-lg mt-8">
        <h2 className="text-2xl font-bold mb-4 text-black">🧪 Teste de Comunicação Backend</h2>
        <button 
          onClick={testBackendConnection}
          disabled={loading}
          className={`px-6 py-3 rounded-lg font-semibold text-white transition-colors ${
            loading 
              ? 'bg-gray-400 cursor-not-allowed' 
              : 'bg-blue-500 hover:bg-blue-600 active:bg-blue-700'
          }`}
        >
          {loading ? '🔄 Testando...' : '🚀 Testar Endpoint Público'}
        </button>
        {backendResponse && (
          <div className="mt-4 p-4 bg-green-100 border border-green-400 rounded-lg">
            <h3 className="font-semibold text-green-800">✅ Resposta do Backend:</h3>
            <p className="text-green-700 mt-2">{backendResponse}</p>
          </div>
        )}
        {error && (
          <div className="mt-4 p-4 bg-red-100 border border-red-400 rounded-lg">
            <h3 className="font-semibold text-red-800">❌ Erro:</h3>
            <p className="text-red-700 mt-2">{error}</p>
          </div>
        )}
        <div className="mt-4 text-sm text-gray-600">
          <p><strong>Endpoint:</strong> GET /users/public</p>
          <p><strong>URL Base:</strong> {import.meta.env.VITE_API_URL || 'http://localhost:8080'}</p>
        </div>
      </div>
      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
    </>
  )
}

export default App