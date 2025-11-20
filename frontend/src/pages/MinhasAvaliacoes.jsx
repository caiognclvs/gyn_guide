import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import api from '../services/api'
import '../App.css'

function MinhasAvaliacoes() {
  const [usuario, setUsuario] = useState(null)
  const [avaliacoes, setAvaliacoes] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const navigate = useNavigate()

  useEffect(() => {
    const usuarioSalvo = localStorage.getItem('usuario')
    if (!usuarioSalvo) {
      navigate('/login')
      return
    }
    const usuarioObj = JSON.parse(usuarioSalvo)
    if (usuarioObj.tipoUsuario !== 'PESSOA_FISICA') {
      navigate('/home')
      return
    }
    setUsuario(usuarioObj)
    carregarAvaliacoes(usuarioObj.id)
  }, [navigate])

  const carregarAvaliacoes = async (autorId) => {
    try {
      setLoading(true)
      const response = await api.get(`/avaliacoes/minhas/${autorId}`)
      // response is expected to be ordered by date desc from backend
      setAvaliacoes(response.data)
    } catch (err) {
      console.error('Erro ao carregar avaliações:', err)
      setError('Erro ao carregar avaliações')
    } finally {
      setLoading(false)
    }
  }

  if (!usuario) return <div>Carregando...</div>

  return (
    <div className="container">
      <header style={{
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
        padding: '20px 0',
        borderBottom: '1px solid #ddd',
        marginBottom: '30px'
      }}>
        <h1>Minhas Avaliações</h1>
        <button
          onClick={() => navigate('/home')}
          className="btn btn-secondary"
          style={{ width: 'auto', padding: '8px 20px' }}
        >
          Voltar
        </button>
      </header>

      <div className="card" style={{ maxWidth: '800px' }}>
        {error && <div className="error-message">{error}</div>}

        {loading ? (
          <div style={{ textAlign: 'center', padding: '40px' }}>Carregando...</div>
        ) : avaliacoes.length === 0 ? (
          <div style={{ textAlign: 'center', padding: '40px', color: '#666' }}>
            O usuário ainda não fez nenhuma avaliação
          </div>
        ) : (
          <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
            {avaliacoes.map(av => (
              <div key={av.id} style={{ padding: '16px', border: '1px solid #eee', borderRadius: '8px' }}>
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                  <div style={{ fontWeight: '600' }}>{av.autorNome}</div>
                  <div style={{ color: '#888', fontSize: '12px' }}>{new Date(av.dataAvaliacao).toLocaleString('pt-BR')}</div>
                </div>
                <div style={{ marginTop: '8px', display: 'flex', alignItems: 'center', gap: '8px' }}>
                  <div style={{ background: '#FFD700', color: '#fff', padding: '6px 10px', borderRadius: '6px', fontWeight: '700' }}>{av.nota}</div>
                  <div style={{ color: '#333' }}>{av.texto}</div>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  )
}

export default MinhasAvaliacoes
