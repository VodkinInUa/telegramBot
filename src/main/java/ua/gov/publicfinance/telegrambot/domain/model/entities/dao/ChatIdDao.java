package ua.gov.publicfinance.telegrambot.domain.model.entities.dao;

import org.springframework.stereotype.Component;
import ua.gov.publicfinance.telegrambot.domain.model.entities.ChatID;

@Component
public interface ChatIdDao {

    void addChatId(ChatID chatID);

    ChatID getChatIdById(long id);

}
