package app.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"app.bank"})
@EnableJpaRepositories(basePackages = {"app.bank"})
@SpringBootApplication
public class BankSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankSpringApplication.class, args);
    }

}
