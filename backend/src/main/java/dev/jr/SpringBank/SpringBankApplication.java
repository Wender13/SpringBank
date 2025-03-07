package dev.jr.SpringBank;

// import dev.jr.SpringBank.service.UsuarioService;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBankApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringBankApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
    }
}