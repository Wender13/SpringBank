import React, { useState } from "react";

const CriarUsuario: React.FC = () => {
  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [cpf, setCpf] = useState(""); // Agora está incluso corretamente
  const [senha, setSenha] = useState("");
  const [mensagem, setMensagem] = useState("");

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    const novoUsuario = {
      nome,
      email,
      CPF: cpf, // Garante que está enviando corretamente
      senha,
      saldo: 0.0, // Pode definir um saldo inicial
    };

    try {
      const response = await fetch("http://localhost:8080/usuarios", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(novoUsuario),
      });

      if (!response.ok) {
        throw new Error("Erro ao criar usuário");
      }

      setMensagem("Usuário criado com sucesso!");
      setNome("");
      setEmail("");
      setCpf(""); // Resetando CPF
      setSenha("");
    } catch (error) {
      setMensagem("Erro ao criar usuário. Verifique os dados.");
    }
  };

  return (
    <div>
      <h2>Criar Usuário</h2>
      {mensagem && <p>{mensagem}</p>}
      <form onSubmit={handleSubmit}>
        <div>
          <label>Nome:</label>
          <input
            type="text"
            value={nome}
            onChange={(e) => setNome(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Email:</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div>
          <label>CPF:</label>
          <input
            type="text"
            value={cpf}
            onChange={(e) => setCpf(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Senha:</label>
          <input
            type="password"
            value={senha}
            onChange={(e) => setSenha(e.target.value)}
            required
          />
        </div>
        <button type="submit">Criar Usuário</button>
      </form>
    </div>
  );
};

export default CriarUsuario;
