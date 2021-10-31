package ua.gov.publicfinance.telegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("ua.gov.publicfinance.telegrambot.domain.model")
@EnableJpaRepositories("ua.gov.publicfinance.telegrambot.infrastructure.repositories")
public class TelegramBotApplication {

    public static void main(final String[] args) {
        SpringApplication.run(TelegramBotApplication.class, args);
    }

}
