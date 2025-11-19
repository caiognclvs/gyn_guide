import React from 'react'
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom'
import Login from './pages/Login'
import CadastroPessoaFisica from './pages/CadastroPessoaFisica'
import CadastroPessoaJuridica from './pages/CadastroPessoaJuridica'
import Home from './pages/Home'
import Perfil from './pages/Perfil'
import MeuEstabelecimento from './pages/MeuEstabelecimento'
import './App.css'

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/cadastro/pessoa-fisica" element={<CadastroPessoaFisica />} />
          <Route path="/cadastro/pessoa-juridica" element={<CadastroPessoaJuridica />} />
          <Route path="/home" element={<Home />} />
          <Route path="/perfil" element={<Perfil />} />
          <Route path="/meu-estabelecimento" element={<MeuEstabelecimento />} />
          <Route path="/" element={<Navigate to="/login" replace />} />
        </Routes>
      </div>
    </Router>
  )
}

export default App

