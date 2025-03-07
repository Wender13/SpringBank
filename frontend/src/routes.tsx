// src/routes.tsx
import { BrowserRouter, Routes, Route } from "react-router-dom";
import CriarUsuario from "./components/CriarUsuario";
// Importe outros componentes conforme necessário

const AppRoutes: React.FC = () => (
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<CriarUsuario />} />
      {/* Outras rotas */}
    </Routes>
  </BrowserRouter>
);

export default AppRoutes;
