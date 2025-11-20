import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import api from '../services/api'
import '../App.css'

function Home() {
  const [usuario, setUsuario] = useState(null)
  const [estabelecimentos, setEstabelecimentos] = useState([])
  const [loading, setLoading] = useState(true)
  const navigate = useNavigate()

  useEffect(() => {
    const usuarioSalvo = localStorage.getItem('usuario')
    if (!usuarioSalvo) {
      navigate('/login')
      return
    }
    setUsuario(JSON.parse(usuarioSalvo))
    carregarEstabelecimentos()
  }, [navigate])

  const carregarEstabelecimentos = async () => {
    try {
      setLoading(true)
      const response = await api.get('/estabelecimentos/aleatorios?quantidade=3')
      setEstabelecimentos(response.data)
    } catch (error) {
      console.error('Erro ao carregar estabelecimentos:', error)
    } finally {
      setLoading(false)
    }
  }

  const handleLogout = () => {
    localStorage.removeItem('usuario')
    navigate('/login')
  }

  const renderizarEstrelas = (mediaNotas) => {
    if (mediaNotas === null || mediaNotas === undefined) {
      return <span style={{ color: '#999' }}>Sem avaliações</span>
    }
    
    const estrelasCheias = Math.floor(mediaNotas)
    const temMeiaEstrela = mediaNotas % 1 >= 0.5
    const estrelasVazias = 5 - estrelasCheias - (temMeiaEstrela ? 1 : 0)
    
    return (
      <div style={{ display: 'flex', alignItems: 'center', gap: '2px' }}>
        {[...Array(estrelasCheias)].map((_, i) => (
          <span key={i} style={{ color: '#FFD700', fontSize: '18px' }}>★</span>
        ))}
        {temMeiaEstrela && (
          <span style={{ color: '#FFD700', fontSize: '18px' }}>U+2BE8</span>
        )}
        {[...Array(estrelasVazias)].map((_, i) => (
          <span key={i} style={{ color: '#ddd', fontSize: '18px' }}>★</span>
        ))}
        <span style={{ marginLeft: '8px', color: '#666', fontSize: '14px' }}>
          ({mediaNotas.toFixed(1)})
        </span>
      </div>
    )
  }

  if (!usuario) {
    return <div>Carregando...</div>
  }

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
        <h1>Gyn Guide</h1>
        <div style={{ display: 'flex', alignItems: 'center', gap: '15px' }}>
          <span style={{ marginRight: '10px' }}>
            Olá, {usuario.email} ({usuario.tipoUsuario === 'PESSOA_FISICA' ? 'Pessoa Física' : 'Pessoa Jurídica'})
          </span>
          <button 
            onClick={() => navigate('/perfil')} 
            className="btn btn-secondary" 
            style={{ width: 'auto', padding: '8px 20px' }}
          >
            Meu Perfil
          </button>
          {usuario.tipoUsuario === 'PESSOA_JURIDICA' && (
            <button 
              onClick={() => navigate('/meu-estabelecimento')} 
              className="btn btn-secondary" 
              style={{ width: 'auto', padding: '8px 20px' }}
            >
              Meu Estabelecimento
            </button>
          )}
          <button onClick={handleLogout} className="btn btn-secondary" style={{ width: 'auto', padding: '8px 20px' }}>
            Sair
          </button>
        </div>
      </header>
      
      <div style={{ marginBottom: '30px' }}>
        <h2>Estabelecimentos em Destaque</h2>
        <p style={{ color: '#666', marginTop: '10px' }}>
          Descubra novos lugares para visitar
        </p>
      </div>

      {loading ? (
        <div style={{ textAlign: 'center', padding: '40px' }}>
          <p>Carregando estabelecimentos...</p>
        </div>
      ) : estabelecimentos.length === 0 ? (
        <div style={{ textAlign: 'center', padding: '40px' }}>
          <p style={{ color: '#666' }}>Nenhum estabelecimento cadastrado ainda.</p>
        </div>
      ) : (
        <div style={{
          display: 'grid',
          gridTemplateColumns: 'repeat(auto-fit, minmax(300px, 1fr))',
          gap: '30px',
          marginBottom: '30px'
        }}>
          {estabelecimentos.map((estabelecimento) => (
            <div
              key={estabelecimento.id}
              style={{
                background: 'white',
                borderRadius: '8px',
                overflow: 'hidden',
                boxShadow: '0 2px 10px rgba(0, 0, 0, 0.1)',
                transition: 'transform 0.2s, box-shadow 0.2s',
                cursor: 'pointer'
              }}
              onMouseEnter={(e) => {
                e.currentTarget.style.transform = 'translateY(-5px)'
                e.currentTarget.style.boxShadow = '0 4px 20px rgba(0, 0, 0, 0.15)'
              }}
              onMouseLeave={(e) => {
                e.currentTarget.style.transform = 'translateY(0)'
                e.currentTarget.style.boxShadow = '0 2px 10px rgba(0, 0, 0, 0.1)'
              }}
            >
              <div style={{
                width: '100%',
                height: '200px',
                backgroundColor: '#f0f0f0',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                overflow: 'hidden'
              }}>
                {estabelecimento.imagemUrl ? (
                  <img
                    src={estabelecimento.imagemUrl}
                    alt={estabelecimento.nome}
                    style={{
                      width: '100%',
                      height: '100%',
                      objectFit: 'cover'
                    }}
                    onError={(e) => {
                      e.target.style.display = 'none'
                      e.target.nextSibling.style.display = 'flex'
                    }}
                  />
                ) : null}
                <div style={{
                  display: estabelecimento.imagemUrl ? 'none' : 'flex',
                  alignItems: 'center',
                  justifyContent: 'center',
                  width: '100%',
                  height: '100%',
                  fontSize: '48px',
                  color: '#ccc'
                }}>
                  🍽️
                </div>
              </div>
              <div style={{ padding: '20px' }}>
                <h3 style={{ margin: '0 0 10px 0', color: '#333' }}>
                  {estabelecimento.nome}
                </h3>
                {estabelecimento.nomeFantasia && (
                  <p style={{ margin: '0 0 10px 0', color: '#666', fontSize: '14px' }}>
                    {estabelecimento.nomeFantasia}
                  </p>
                )}
                <p style={{ margin: '0 0 15px 0', color: '#888', fontSize: '14px' }}>
                  {estabelecimento.endereco}
                </p>
                <div style={{ marginBottom: '10px' }}>
                  {renderizarEstrelas(estabelecimento.mediaNotas)}
                </div>
                <p style={{ margin: '0', color: '#666', fontSize: '14px' }}>
                  {estabelecimento.numeroAvaliacoes} {estabelecimento.numeroAvaliacoes === 1 ? 'avaliação' : 'avaliações'}
                </p>
              </div>
            </div>
          ))}
        </div>
      )}

      <div style={{ textAlign: 'center', marginTop: '30px' }}>
        <button
          onClick={() => navigate('/estabelecimentos')}
          className="btn btn-primary"
          style={{ width: 'auto', padding: '10px 30px' }}
        >
          Ver Outros Estabelecimentos
        </button>
      </div>
    </div>
  )
}

export default Home

