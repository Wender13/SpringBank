import React from "react";
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import CriarUsuario from "./components/CriarUsuario";
import Login from "./components/Login";
import Dashboard from "./components/Dashboard";

const App: React.FC = () => {
  return (
    <BrowserRouter>
      <div className="App">
        <h1>Banco Virtual - Frontend</h1>

        {/* Menu de Navegação */}
        <nav>
          <ul>
            <li>
              <Link to="/">Login</Link>
            </li>
            <li>
              <Link to="/criar-usuario">Criar Usuário</Link>
            </li>
            <li>
              <Link to="/dashboard">Dashboard</Link>
            </li>
          </ul>
        </nav>

        {/* Definição das Rotas */}
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/criar-usuario" element={<CriarUsuario />} />
          <Route path="/dashboard" element={<Dashboard />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
};

export default App;
