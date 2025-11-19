import React from 'react'
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom'
import Login from './pages/Login'
import CadastroPessoaFisica from './pages/CadastroPessoaFisica'
import CadastroPessoaJuridica from './pages/CadastroPessoaJuridica'
import Home from './pages/Home'
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
          <Route path="/" element={<Navigate to="/login" replace />} />
        </Routes>
      </div>
    </Router>
  )
}

export default App

