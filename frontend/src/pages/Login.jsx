import React, { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import api from '../services/api'
import '../App.css'

function Login() {
  const [email, setEmail] = useState('')
  const [senha, setSenha] = useState('')
  const [error, setError] = useState('')
  const navigate = useNavigate()

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')

    try {
      const response = await api.post('/auth/login', { email, senha })
      // Salvar informações do usuário (em produção, usar token JWT)
      localStorage.setItem('usuario', JSON.stringify(response.data))
      navigate('/home')
    } catch (err) {
      setError(err.response?.data || 'Erro ao fazer login')
    }
  }

  return (
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '100vh' }}>
      <div className="card">
        <h1 style={{ textAlign: 'center', marginBottom: '30px', color: '#333' }}>
          Gyn Guide
        </h1>
        <h2 style={{ textAlign: 'center', marginBottom: '30px', color: '#666' }}>
          Login
        </h2>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="email">Email</label>
            <input
              type="email"
              id="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="senha">Senha</label>
            <input
              type="password"
              id="senha"
              value={senha}
              onChange={(e) => setSenha(e.target.value)}
              required
            />
          </div>
          {error && <div className="error-message">{error}</div>}
          <button type="submit" className="btn btn-primary">
            Entrar
          </button>
        </form>
        <div className="link-container">
          <p>
            Não tem uma conta?{' '}
            <Link to="/cadastro/pessoa-fisica">Cadastre-se como Pessoa Física</Link>
            {' ou '}
            <Link to="/cadastro/pessoa-juridica">Pessoa Jurídica</Link>
          </p>
        </div>
      </div>
    </div>
  )
}

export default Login

