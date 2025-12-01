import React, { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import api from '../services/api'
import '../App.css'

function CadastroPessoaFisica() {
  const [formData, setFormData] = useState({
    email: '',
    senha: '',
    nome: '',
    dataNascimento: ''
  })
  const [error, setError] = useState('')
  const [success, setSuccess] = useState('')
  const navigate = useNavigate()

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    })
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')
    setSuccess('')

    try {
      const response = await api.post('auth/cadastro/pessoa-fisica', {
        ...formData,
        dataNascimento: formData.dataNascimento
      })
      setSuccess('Cadastro realizado com sucesso! Redirecionando...')
      setTimeout(() => {
        localStorage.setItem('usuario', JSON.stringify(response.data))
        navigate('/home')
      }, 1500)
    } catch (err) {
      setError(err.response?.data?.message || err.response?.data || 'Erro ao cadastrar')
    }
  }

  return (
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '100vh' }}>
      <div className="card">
        <h1 style={{ textAlign: 'center', marginBottom: '30px', color: '#333' }}>
          Cadastro - Pessoa Física
        </h1>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="email">Email</label>
            <input
              type="email"
              id="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="senha">Senha</label>
            <input
              type="password"
              id="senha"
              name="senha"
              value={formData.senha}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="nome">Nome</label>
            <input
              type="text"
              id="nome"
              name="nome"
              value={formData.nome}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="dataNascimento">Data de Nascimento</label>
            <input
              type="date"
              id="dataNascimento"
              name="dataNascimento"
              value={formData.dataNascimento}
              onChange={handleChange}
              required
            />
          </div>
          {error && <div className="error-message">{error}</div>}
          {success && <div className="success-message">{success}</div>}
          <button type="submit" className="btn btn-primary">
            Cadastrar
          </button>
        </form>
        <div className="link-container">
          <Link to="/login">Já tem uma conta? Faça login</Link>
        </div>
      </div>
    </div>
  )
}

export default CadastroPessoaFisica

