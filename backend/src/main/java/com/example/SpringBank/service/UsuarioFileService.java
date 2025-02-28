package com.example.SpringBank.service;

import com.example.SpringBank.model.Usuario;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UsuarioFileService {

    private static final String FILE_PATH = "data/usuarios.json"; // Caminho do arquivo onde os dados dos usuários serão salvos

    public void salvarUsuariosEmArquivo(List<Usuario> usuarios) {
        try {
            // Criar diretório caso não exista
            File directory = new File("data");
            if (!directory.exists()) {
                directory.mkdir();
            }

            // Usando ObjectMapper do Jackson para escrever o arquivo JSON
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(FILE_PATH), usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Usuario> lerUsuariosDeArquivo() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // Ler os dados do arquivo JSON e converter para uma lista de usuários
            return objectMapper.readValue(new File(FILE_PATH), objectMapper.getTypeFactory().constructCollectionType(List.class, Usuario.class));
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Retorna null em caso de erro ao ler o arquivo
        }
    }
}
