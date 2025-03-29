package dev.jr.SpringBank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "dev.jr.SpringBank") // Garanta que todos os pacotes sejam escaneados
public class SpringBankApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBankApplication.class, args);
    }
}
    