package dev.jr.SpringBank.model;

import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transactions")
public class Transaction {
    
    @Id  // Define o identificador no MongoDB
    private String id;

    private String type;
    private User user;
    private User benefited;
    private double value;
    private Instant dateHour;

    // Construtor sem parâmetros
    public Transaction() {
    }

    // Construtor para type saque e deposito
    public Transaction(User user, String type, double value, Instant dateHour) {
        this.type = type;
        this.user = user;
        this.value = value;
        this.dateHour = dateHour;
    }
    
    // Construtor para type transferencia
    public Transaction(User user, User benefited, double value, Instant dateHour) {
        this.type = "TRANSFERENCIA";
        this.user = user;
        this.benefited = benefited;
        this.value = value;
        this.dateHour = dateHour;
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
     * @return String return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return String return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return String return the benefited
     */
    public User getBenefited() {
        return benefited;
    }

    /**
     * @param benefited the benefited to set
     */
    public void setBenefited(User benefited) {
        this.benefited = benefited;
    }

    /**
     * @return double return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * @return String return the data
     */
    public Instant getDateHour() {
        return dateHour;
    }

    /**
     * @param data the data to set
     */
    public void setDateHour(Instant dateHour) {
        this.dateHour = dateHour;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", type='" + getType() + "'" +
            ", user='" + getUser() + "'" +
            ", benefited='" + getBenefited() + "'" +
            ", value='" + getValue() + "'" +
            ", dateHour='" + getDateHour() + "'" +
            "}";
    }
}