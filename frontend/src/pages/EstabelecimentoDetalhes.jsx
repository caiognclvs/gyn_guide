import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import api from '../services/api'
import '../App.css'

function EstabelecimentoDetalhes() {
  const { id } = useParams()
  const [dados, setDados] = useState(null)
  const [loading, setLoading] = useState(true)
  const navigate = useNavigate()

  useEffect(() => {
    carregar()
  }, [id])

  const carregar = async () => {
    try {
      setLoading(true)
      const response = await api.get(`/estabelecimentos/${id}`)
      setDados(response.data)
    } catch (err) {
      console.error('Erro ao carregar detalhes:', err)
    } finally {
      setLoading(false)
    }
  }

  if (loading) return <div style={{ textAlign: 'center', padding: '40px' }}>Carregando...</div>
  if (!dados) return <div style={{ textAlign: 'center', padding: '40px' }}>Detalhes não encontrados.</div>

  return (
    <div className="container">
      <header style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '20px 0', borderBottom: '1px solid #ddd', marginBottom: '30px' }}>
        <h1>{dados.nome}</h1>
        <div>
          <button onClick={() => navigate('/home')} className="btn btn-secondary" style={{ width: 'auto', padding: '8px 20px' }}>Voltar</button>
        </div>
      </header>

      <div className="card" style={{ maxWidth: '900px', margin: '0 auto' }}>
        <div style={{ padding: '20px' }}>
          <h2 style={{ marginTop: 0 }}>{dados.nomeFantasia || dados.nome}</h2>
          <p style={{ color: '#666' }}>{dados.endereco}</p>
          <div style={{ marginTop: '16px', marginBottom: '16px' }}>
            <strong>Média:</strong> {dados.mediaNotas !== null ? dados.mediaNotas.toFixed(1) : '—'}
            {'  '}|{'  '}
            <strong>Numero de avaliações:</strong> {dados.numeroAvaliacoes}
          </div>
          <div style={{ marginTop: '12px' }}>
            <h3>Avaliações</h3>
            {dados.avaliacoes && dados.avaliacoes.length > 0 ? (
              <div style={{ display: 'flex', flexDirection: 'column', gap: '12px', marginTop: '12px' }}>
                {dados.avaliacoes.map(av => (
                  <div key={av.id} style={{ border: '1px solid #eee', padding: '12px', borderRadius: '6px' }}>
                    <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '8px' }}>
                      <strong>{av.autorNome}</strong>
                      <span style={{ color: '#666', fontSize: '12px' }}>{new Date(av.dataAvaliacao).toLocaleString()}</span>
                    </div>
                    <div style={{ marginBottom: '8px' }}>{av.texto}</div>
                    <div>Nota: {av.nota} / 5</div>
                  </div>
                ))}
              </div>
            ) : (
              <p style={{ color: '#666' }}>Nenhuma avaliação cadastrada.</p>
            )}
          </div>
        </div>
      </div>
    </div>
  )
}

export default EstabelecimentoDetalhes
