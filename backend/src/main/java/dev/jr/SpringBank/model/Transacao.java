package dev.jr.SpringBank.model;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transacoes")
public class Transacao {
    
    @Id  // Define o identificador no MongoDB
    private String id;

    private String tipo;
    private Usuario usuario;
    private Usuario destinatario;
    private double valor;
    private LocalDateTime dataHora;

    // Construtor sem parâmetros
    public Transacao() {
    }

    // Construtor para tipo saque e deposito
    public Transacao(Usuario usuario, String tipo, double valor, LocalDateTime dataHora) {
        this.tipo = tipo;
        this.usuario = usuario;
        this.valor = valor;
        this.dataHora = dataHora;
    }
    
    // Construtor para tipo transferencia
    public Transacao(Usuario usuario, Usuario destinatario, double valor, LocalDateTime dataHora) {
        this.tipo = "TRANSFERENCIA";
        this.usuario = usuario;
        this.destinatario = destinatario;
        this.valor = valor;
        this.dataHora = dataHora;
    }

    

    /**
     * @return String return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return String return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return String return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return String return the destinatario
     */
    public Usuario getDestinatario() {
        return destinatario;
    }

    /**
     * @param destinatario the destinatario to set
     */
    public void setDestinatario(Usuario destinatario) {
        this.destinatario = destinatario;
    }

    /**
     * @return double return the valor
     */
    public double getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * @return String return the data
     */
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    /**
     * @param data the data to set
     */
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", usuario='" + getUsuario() + "'" +
            ", destinatario='" + getDestinatario() + "'" +
            ", valor='" + getValor() + "'" +
            ", dataHora='" + getDataHora() + "'" +
            "}";
    }
}