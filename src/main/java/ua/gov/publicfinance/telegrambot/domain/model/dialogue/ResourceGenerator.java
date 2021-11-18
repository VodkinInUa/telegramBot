package ua.gov.publicfinance.telegrambot.domain.model.dialogue;
import ua.gov.publicfinance.telegrambot.domain.model.dialogue.util.ConfigGenerator;

import java.io.IOException;

public class ResourceGenerator {
    public static void main(String[] args) throws IOException {
        ConfigGenerator.generate();
    }
}
