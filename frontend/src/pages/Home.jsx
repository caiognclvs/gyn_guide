import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import '../App.css'

function Home() {
  const [usuario, setUsuario] = useState(null)
  const navigate = useNavigate()

  useEffect(() => {
    const usuarioSalvo = localStorage.getItem('usuario')
    if (!usuarioSalvo) {
      navigate('/login')
      return
    }
    setUsuario(JSON.parse(usuarioSalvo))
  }, [navigate])

  const handleLogout = () => {
    localStorage.removeItem('usuario')
    navigate('/login')
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
        <div>
          <span style={{ marginRight: '20px' }}>
            Olá, {usuario.email} ({usuario.tipoUsuario === 'PESSOA_FISICA' ? 'Pessoa Física' : 'Pessoa Jurídica'})
          </span>
          <button onClick={handleLogout} className="btn btn-secondary" style={{ width: 'auto', padding: '8px 20px' }}>
            Sair
          </button>
        </div>
      </header>
      
      <div className="card">
        <h2>Bem-vindo ao Gyn Guide!</h2>
        <p style={{ marginTop: '20px', color: '#666' }}>
          Você está logado como {usuario.tipoUsuario === 'PESSOA_FISICA' ? 'Pessoa Física' : 'Pessoa Jurídica'}.
        </p>
        {usuario.tipoUsuario === 'PESSOA_FISICA' && (
          <p style={{ marginTop: '10px', color: '#666' }}>
            Você pode avaliar estabelecimentos.
          </p>
        )}
        {usuario.tipoUsuario === 'PESSOA_JURIDICA' && (
          <p style={{ marginTop: '10px', color: '#666' }}>
            Você pode cadastrar seus estabelecimentos para serem avaliados.
          </p>
        )}
      </div>
    </div>
  )
}

export default Home

