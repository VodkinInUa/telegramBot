package ua.gov.publicfinance.telegrambot.domain.model.dialogue;

import ua.gov.publicfinance.telegrambot.domain.model.dialogue.util.StateConfigGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
public class DatajpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatajpaApplication.class, args);
    }


    @PostConstruct
    public void init() throws IOException {

        StateConfigGenerator.generateStateConfigMap();

    }
}
