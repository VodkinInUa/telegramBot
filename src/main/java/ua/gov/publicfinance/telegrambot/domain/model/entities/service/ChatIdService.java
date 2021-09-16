package ua.gov.publicfinance.telegrambot.domain.model.entities.service;

import org.springframework.stereotype.Component;
import ua.gov.publicfinance.telegrambot.domain.model.entities.ChatID;


@Component

public interface ChatIdService {
    void addChatId(ChatID chatID);

    ChatID getChatIdById(long id);
}
