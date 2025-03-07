// src/api/usuarioApi.ts
import axios from "axios";

// Defina a URL base do seu backend (certifique-se de que o CORS esteja configurado no Spring Boot)
const API_BASE_URL = "http://localhost:8080/usuario";

export interface Usuario {
  id?: string;
  nome: string;
  email: string;
  CPF: string;
  senha: string;
  saldo?: number;
}

// Função para criar um usuário
export const criarUsuario = async (usuario: Usuario): Promise<Usuario> => {
  const response = await axios.post(`${API_BASE_URL}/criar`, usuario);
  return response.data;
};

// Função para buscar usuário por email
export const buscarUsuarioPorEmail = async (
  email: string
): Promise<Usuario> => {
  // Aqui você pode criar um endpoint específico para busca por email
  // ou usar query parameters (por exemplo: GET /usuario?email=xxx)
  const response = await axios.get(
    `${API_BASE_URL}/buscar-email/${encodeURIComponent(email)}`
  );
  return response.data;
};

// Outras funções podem ser adicionadas, como depositar, sacar, transferir, etc.
