import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import api from '../services/api'
import '../App.css'

function MeuEstabelecimento() {
  const [usuario, setUsuario] = useState(null)
  const [estabelecimento, setEstabelecimento] = useState(null)
  const [loading, setLoading] = useState(true)
  const [editando, setEditando] = useState(false)
  const [formData, setFormData] = useState({
    nome: '',
    endereco: '',
    descricao: '',
    imagemUrl: ''
  })
  const [selectedFile, setSelectedFile] = useState(null)
  const [previewUrl, setPreviewUrl] = useState('')
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
    if (usuarioObj.tipoUsuario !== 'PESSOA_JURIDICA') {
      navigate('/home')
      return
    }
    setUsuario(usuarioObj)
    carregarEstabelecimento(usuarioObj.id)
  }, [navigate])

  const carregarEstabelecimento = async (proprietarioId) => {
    try {
      setLoading(true)
      const response = await api.get(`/estabelecimentos/meu-estabelecimento/${proprietarioId}`)
      setEstabelecimento(response.data)
      setFormData({
        nome: response.data.nome || '',
        endereco: response.data.endereco || '',
        descricao: response.data.descricao || '',
        imagemUrl: response.data.imagemUrl || ''
      })
    } catch (error) {
      if (error.response?.status === 404) {
        // Estabelecimento ainda não foi criado
        setEstabelecimento(null)
        setEditando(true) // Já abre em modo de edição para criar
      } else {
        console.error('Erro ao carregar estabelecimento:', error)
        setError('Erro ao carregar informações do estabelecimento')
      }
    } finally {
      setLoading(false)
    }
  }

  const handleChange = (e) => {
    const { name, value } = e.target
    setFormData({
      ...formData,
      [name]: value
    })
  }

  const handleFileChange = (file) => {
    if (!file) return
    setSelectedFile(file)
    setPreviewUrl(URL.createObjectURL(file))
    // clear any imagemUrl string when a file is chosen
    setFormData(prev => ({ ...prev, imagemUrl: '' }))
  }

  const onFileInputChange = (e) => {
    const file = e.target.files && e.target.files[0]
    if (file) handleFileChange(file)
  }

  const handleRemoveFile = () => {
    setSelectedFile(null)
    setPreviewUrl('')
  }

  const handleDragOver = (e) => {
    e.preventDefault()
    e.stopPropagation()
  }

  const handleDrop = (e) => {
    e.preventDefault()
    e.stopPropagation()
    const file = e.dataTransfer.files && e.dataTransfer.files[0]
    if (file) handleFileChange(file)
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')
    setSuccess('')

    try {
      const endpoint = `/estabelecimentos/meu-estabelecimento/${usuario.id}`
      const method = estabelecimento ? 'put' : 'post'
      // Monta FormData para suportar upload de arquivo
      const payload = new FormData()
      payload.append('nome', formData.nome)
      payload.append('endereco', formData.endereco)
      if (formData.descricao) payload.append('descricao', formData.descricao)
      // Se o usuário escolheu um arquivo envia como 'imagem', caso contrário envia imagemUrl (pode estar vazio)
      if (selectedFile) {
        payload.append('imagem', selectedFile)
      } else if (formData.imagemUrl) {
        payload.append('imagemUrl', formData.imagemUrl)
      }

      const response = await api[method](endpoint, payload)
      setEstabelecimento(response.data)
      setEditando(false)
      setSuccess(estabelecimento ? 'Estabelecimento atualizado com sucesso!' : 'Estabelecimento criado com sucesso!')
    } catch (err) {
      setError(err.response?.data?.message || err.response?.data || 'Erro ao salvar estabelecimento')
    }
  }

  const handleCancelar = () => {
    if (estabelecimento) {
      setFormData({
        nome: estabelecimento.nome || '',
        endereco: estabelecimento.endereco || '',
        descricao: estabelecimento.descricao || '',
        imagemUrl: estabelecimento.imagemUrl || ''
      })
      setEditando(false)
    } else {
      // Se não tem estabelecimento, volta para home
      navigate('/home')
    }
    setError('')
    setSuccess('')
  }

  if (!usuario || loading) {
    return <div style={{ textAlign: 'center', padding: '40px' }}>Carregando...</div>
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
        <h1>Meu Estabelecimento</h1>
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
          <h2>{estabelecimento ? 'Editar Estabelecimento' : 'Cadastrar Estabelecimento'}</h2>
          {!editando && estabelecimento && (
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
            <div className="form-group">
              <label htmlFor="nome">Nome do Estabelecimento *</label>
              <input
                type="text"
                id="nome"
                name="nome"
                value={formData.nome}
                onChange={handleChange}
                required
                placeholder="Ex: Restaurante do João"
              />
            </div>
            <div className="form-group">
              <label htmlFor="endereco">Endereço *</label>
              <input
                type="text"
                id="endereco"
                name="endereco"
                value={formData.endereco}
                onChange={handleChange}
                required
                placeholder="Ex: Rua das Flores, 123 - Centro"
              />
            </div>
            <div className="form-group">
              <label htmlFor="descricao">Descrição</label>
              <textarea
                id="descricao"
                name="descricao"
                value={formData.descricao}
                onChange={handleChange}
                rows="4"
                style={{
                  width: '100%',
                  padding: '12px',
                  border: '1px solid #ddd',
                  borderRadius: '4px',
                  fontSize: '16px',
                  fontFamily: 'inherit',
                  resize: 'vertical'
                }}
                placeholder="Descreva seu estabelecimento..."
              />
            </div>
            <div className="form-group">
              <label htmlFor="imagemUrl">URL da Imagem</label>
              <input
                type="url"
                id="imagemUrl"
                name="imagemUrl"
                value={formData.imagemUrl}
                onChange={handleChange}
                placeholder="https://exemplo.com/imagem.jpg"
              />
              <div style={{ marginTop: '12px' }}>
                <label style={{ display: 'block', marginBottom: '8px' }}>Ou envie uma imagem (arraste e solte)</label>
                <div
                  onDragOver={handleDragOver}
                  onDrop={handleDrop}
                  style={{
                    border: '2px dashed #ccc',
                    padding: '18px',
                    borderRadius: '8px',
                    textAlign: 'center',
                    cursor: 'pointer'
                  }}
                >
                  {previewUrl ? (
                    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                      <img src={previewUrl} alt="preview" style={{ maxWidth: '100%', maxHeight: '200px', borderRadius: '6px', marginBottom: '8px', objectFit: 'cover' }} />
                      <div style={{ display: 'flex', gap: '8px' }}>
                        <button type="button" className="btn btn-secondary" onClick={handleRemoveFile}>Remover</button>
                      </div>
                    </div>
                  ) : (
                    <div>
                      <p style={{ margin: 0, color: '#666' }}>Arraste um arquivo aqui ou clique para selecionar</p>
                      <input type="file" accept="image/*" onChange={onFileInputChange} style={{ marginTop: '12px' }} />
                    </div>
                  )}
                </div>
              </div>
            </div>
            <div style={{ display: 'flex', gap: '10px', marginTop: '20px' }}>
              <button type="submit" className="btn btn-primary" style={{ flex: 1 }}>
                {estabelecimento ? 'Salvar' : 'Cadastrar'}
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
            {estabelecimento ? (
              <>
                {estabelecimento.imagemUrl && (
                  <div style={{ marginBottom: '20px', textAlign: 'center' }}>
                    <img 
                      src={estabelecimento.imagemUrl} 
                      alt={estabelecimento.nome}
                      style={{
                        maxWidth: '100%',
                        maxHeight: '300px',
                        borderRadius: '8px',
                        objectFit: 'cover'
                      }}
                      onError={(e) => {
                        e.target.style.display = 'none'
                      }}
                    />
                  </div>
                )}
                <div style={{ marginBottom: '20px' }}>
                  <strong>Nome:</strong> {estabelecimento.nome}
                </div>
                <div style={{ marginBottom: '20px' }}>
                  <strong>Endereço:</strong> {estabelecimento.endereco}
                </div>
                {estabelecimento.descricao && (
                  <div style={{ marginBottom: '20px' }}>
                    <strong>Descrição:</strong>
                    <p style={{ marginTop: '8px', whiteSpace: 'pre-wrap' }}>{estabelecimento.descricao}</p>
                  </div>
                )}
              </>
            ) : (
              <div style={{ textAlign: 'center', padding: '40px', color: '#666' }}>
                <p>Você ainda não cadastrou um estabelecimento.</p>
                <button 
                  onClick={() => setEditando(true)} 
                  className="btn btn-primary"
                  style={{ marginTop: '20px', width: 'auto', padding: '10px 30px' }}
                >
                  Cadastrar Estabelecimento
                </button>
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  )
}

export default MeuEstabelecimento

