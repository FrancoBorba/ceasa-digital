import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

function App() {
  const [count, setCount] = useState(0)
  const [backendResponse, setBackendResponse] = useState('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  const testBackendConnection = async () => {
    setLoading(true)
    setError('')
    setBackendResponse('')
    
    try {
      // URL do backend - ajuste conforme sua configuração
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

  return (
    <>
      <div>
        <h1 className="font-bold text-4xl mb-8">CEASA Digital</h1>
        <a href="https://vite.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      
      <h1>Vite + React</h1>
      
      <div className="card">
        <button onClick={() => setCount((count) => count + 1)}>
          count is {count}
        </button>
        <p>
          Edit <code>app/App.jsx</code> and save to test HMR
        </p>
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

        {/* Resultado da requisição */}
        {backendResponse && (
          <div className="mt-4 p-4 bg-green-100 border border-green-400 rounded-lg">
            <h3 className="font-semibold text-green-800">✅ Resposta do Backend:</h3>
            <p className="text-green-700 mt-2">{backendResponse}</p>
          </div>
        )}

        {/* Erro na requisição */}
        {error && (
          <div className="mt-4 p-4 bg-red-100 border border-red-400 rounded-lg">
            <h3 className="font-semibold text-red-800">❌ Erro:</h3>
            <p className="text-red-700 mt-2">{error}</p>
          </div>
        )}

        {/* Informações técnicas */}
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
