import React, { useEffect, useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import api from '../services/api'
import '../App.css'

function Estabelecimentos() {
  const [estabelecimentos, setEstabelecimentos] = useState([])
  const [loading, setLoading] = useState(true)
  const navigate = useNavigate()

  useEffect(() => {
    carregarTodos()
  }, [])

  const carregarTodos = async () => {
    try {
      setLoading(true)
      const response = await api.get('/estabelecimentos/todos')
      setEstabelecimentos(response.data)
    } catch (err) {
      console.error('Erro ao carregar estabelecimentos:', err)
    } finally {
      setLoading(false)
    }
  }

  if (loading) return <div style={{ textAlign: 'center', padding: '40px' }}>Carregando...</div>

  return (
    <div className="container">
      <header style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '20px 0', borderBottom: '1px solid #ddd', marginBottom: '30px' }}>
        <h1>Estabelecimentos</h1>
        <div>
          <button onClick={() => navigate('/home')} className="btn btn-secondary" style={{ width: 'auto', padding: '8px 20px' }}>Voltar</button>
        </div>
      </header>

      {estabelecimentos.length === 0 ? (
        <div style={{ textAlign: 'center', padding: '40px' }}>
          <p>Nenhum estabelecimento cadastrado.</p>
        </div>
      ) : (
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(240px, 1fr))', gap: '20px' }}>
          {estabelecimentos.map(est => (
            <div key={est.id} style={{ background: 'white', borderRadius: '8px', overflow: 'hidden', boxShadow: '0 2px 8px rgba(0,0,0,0.08)', cursor: 'pointer' }} onClick={() => navigate(`/estabelecimentos/${est.id}`)}>
              <div style={{ width: '100%', height: '160px', background: '#f0f0f0', display: 'flex', alignItems: 'center', justifyContent: 'center', overflow: 'hidden' }}>
                {est.imagemUrl ? (
                  <img src={est.imagemUrl} alt={est.nome} style={{ width: '100%', height: '100%', objectFit: 'cover' }} onError={(e) => e.target.style.display = 'none'} />
                ) : (
                  <div style={{ fontSize: '48px', color: '#ccc' }}>🍽️</div>
                )}
              </div>
              <div style={{ padding: '12px 16px' }}>
                <h3 style={{ margin: 0, fontSize: '16px' }}>{est.nome}</h3>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}

export default Estabelecimentos
