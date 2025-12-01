import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import api from '../services/api'
import '../App.css'

function Perfil() {
  const [usuario, setUsuario] = useState(null)
  const [perfil, setPerfil] = useState(null)
  const [loading, setLoading] = useState(true)
  const [editando, setEditando] = useState(false)
  const [formData, setFormData] = useState({})
  const [error, setError] = useState('')
  const [success, setSuccess] = useState('')
  const navigate = useNavigate()

  useEffect(() => {
    const usuarioSalvo = localStorage.getItem('usuario')
    if (!usuarioSalvo) {
      navigate('/login')
      return
    }
    const usuarioObj = JSON.parse(usuarioSalvo)
    setUsuario(usuarioObj)
    carregarPerfil(usuarioObj)
  }, [navigate])

  const carregarPerfil = async (usuarioObj) => {
    try {
      setLoading(true)
      const endpoint = usuarioObj.tipoUsuario === 'PESSOA_FISICA' 
        ? `/perfil/pessoa-fisica/${usuarioObj.id}`
        : `/perfil/pessoa-juridica/${usuarioObj.id}`
      
      const response = await api.get(endpoint)
      console.log('GET', endpoint, 'response.data =', response.data)
      setPerfil(response.data)
      setFormData(response.data)
    } catch (error) {
      console.error('Erro ao carregar perfil:', error)
      setError('Erro ao carregar informações do perfil')
    } finally {
      setLoading(false)
    }
  }

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
      const endpoint = usuario.tipoUsuario === 'PESSOA_FISICA'
        ? `/perfil/pessoa-fisica/${usuario.id}`
        : `/perfil/pessoa-juridica/${usuario.id}`
      
      const response = await api.put(endpoint, formData)
      setPerfil(response.data)
      setEditando(false)
      setSuccess('Perfil atualizado com sucesso!')
      
      // Atualizar dados do usuário no localStorage
      const usuarioAtualizado = {
        ...usuario,
        email: response.data.email
      }
      localStorage.setItem('usuario', JSON.stringify(usuarioAtualizado))
      setUsuario(usuarioAtualizado)
    } catch (err) {
      setError(err.response?.data?.message || err.response?.data || 'Erro ao atualizar perfil')
    }
  }

  const handleCancelar = () => {
    setFormData(perfil)
    setEditando(false)
    setError('')
    setSuccess('')
  }

  if (!usuario || loading) {
    return <div style={{ textAlign: 'center', padding: '40px' }}>Carregando...</div>
  }

  const isPessoaFisica = usuario.tipoUsuario === 'PESSOA_FISICA'

  console.log('perfil state =', perfil)
  console.log('formData state =', formData)

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
        <h1>Meu Perfil</h1>
        <button 
          onClick={() => navigate('/home')} 
          className="btn btn-secondary" 
          style={{ width: 'auto', padding: '8px 20px' }}
        >
          Voltar
        </button>
      </header>

      <div className="card" style={{ maxWidth: '600px' }}>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
          <h2>{isPessoaFisica ? 'Perfil - Pessoa Física' : 'Perfil - Pessoa Jurídica'}</h2>
          {!editando && (
            <button 
              onClick={() => setEditando(true)} 
              className="btn btn-primary"
              style={{ width: 'auto', padding: '8px 20px' }}
            >
              Editar
            </button>
          )}
        </div>

        {error && <div className="error-message">{error}</div>}
        {success && <div className="success-message">{success}</div>}

        {editando ? (
          <form onSubmit={handleSubmit}>
            {isPessoaFisica ? (
              <>
                <div className="form-group">
                  <label htmlFor="email">Email</label>
                  <input
                    type="email"
                    id="email"
                    name="email"
                    value={formData.email || ''}
                    onChange={handleChange}
                    required
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="senha">Nova Senha (deixe em branco para não alterar)</label>
                  <input
                    type="password"
                    id="senha"
                    name="senha"
                    value={formData.senha || ''}
                    onChange={handleChange}
                    placeholder="Deixe em branco para não alterar"
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="nome">Nome</label>
                  <input
                    type="text"
                    id="nome"
                    name="nome"
                    value={formData.nome || ''}
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
                    value={formData.dataNascimento || ''}
                    onChange={handleChange}
                    required
                  />
                </div>
              </>
            ) : (
              <>
                <div className="form-group">
                  <label htmlFor="email">Email</label>
                  <input
                    type="email"
                    id="email"
                    name="email"
                    value={formData.email || ''}
                    onChange={handleChange}
                    required
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="senha">Nova Senha (deixe em branco para não alterar)</label>
                  <input
                    type="password"
                    id="senha"
                    name="senha"
                    value={formData.senha || ''}
                    onChange={handleChange}
                    placeholder="Deixe em branco para não alterar"
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="nomeFantasia">Nome Fantasia</label>
                  <input
                    type="text"
                    id="nomeFantasia"
                    name="nomeFantasia"
                    value={formData.nomeFantasia || ''}
                    onChange={handleChange}
                    required
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="razaoSocial">Razão Social</label>
                  <input
                    type="text"
                    id="razaoSocial"
                    name="razaoSocial"
                    value={formData.razaoSocial || ''}
                    onChange={handleChange}
                    required
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="cnpj">CNPJ</label>
                  <input
                    type="text"
                    id="cnpj"
                    name="cnpj"
                    value={formData.cnpj || ''}
                    onChange={handleChange}
                    required
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="endereco">Endereço</label>
                  <input
                    type="text"
                    id="endereco"
                    name="endereco"
                    value={formData.endereco || ''}
                    onChange={handleChange}
                    required
                  />
                </div>
              </>
            )}
            <div style={{ display: 'flex', gap: '10px', marginTop: '20px' }}>
              <button type="submit" className="btn btn-primary" style={{ flex: 1 }}>
                Salvar
              </button>
              <button 
                type="button" 
                onClick={handleCancelar} 
                className="btn btn-secondary" 
                style={{ flex: 1 }}
              >
                Cancelar
              </button>
            </div>
          </form>
        ) : (
          <div>
            {isPessoaFisica ? (
              <>
                <div style={{ marginBottom: '20px' }}>
                  <strong>Email:</strong> {perfil?.email}
                </div>
                <div style={{ marginBottom: '20px' }}>
                  <strong>Nome:</strong> {perfil?.nome}
                </div>
                <div style={{ marginBottom: '20px' }}>
                  <strong>Data de Nascimento:</strong> {perfil?.dataNascimento ? (perfil.dataNascimento.includes('T') ? new Date(perfil.dataNascimento).toLocaleDateString('pt-BR') : perfil.dataNascimento) : ''}
                </div>
              </>
            ) : (
              <>
                <div style={{ marginBottom: '20px' }}>
                  <strong>Email:</strong> {perfil?.email}
                </div>
                <div style={{ marginBottom: '20px' }}>
                  <strong>Nome Fantasia:</strong> {perfil?.nomeFantasia}
                </div>
                <div style={{ marginBottom: '20px' }}>
                  <strong>Razão Social:</strong> {perfil?.razaoSocial}
                </div>
                <div style={{ marginBottom: '20px' }}>
                  <strong>CNPJ:</strong> {perfil?.cnpj}
                </div>
                <div style={{ marginBottom: '20px' }}>
                  <strong>Endereço:</strong> {perfil?.endereco}
                </div>
              </>
            )}
          </div>
        )}
      </div>
    </div>
  )
}

export default Perfil

