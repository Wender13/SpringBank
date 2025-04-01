package dev.jr.SpringBank.model;

import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transactions")
public class Transaction {

    @Id
    private String id;
    private TransactionType type;
    private String user;
    private String beneficiary;
    private double value;
    private Instant dateHour;

    public Transaction() {
    }

    public Transaction(String user, String beneficiary, TransactionType type, double value, Instant dateHour) {
        this.user = user;
        this.beneficiary = beneficiary;
        this.type = type;
        this.value = value;
        this.dateHour = dateHour;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Instant getDateHour() {
        return dateHour;
    }

    public void setDateHour(Instant dateHour) {
        this.dateHour = dateHour;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", user='" + user + '\'' +
                ", beneficiary='" + beneficiary + '\'' +
                ", value=" + value +
                ", dateHour=" + dateHour +
                '}';
    }
}