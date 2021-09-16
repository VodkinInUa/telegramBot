package ua.gov.publicfinance.telegrambot.domain.model.entities.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.gov.publicfinance.telegrambot.domain.model.entities.ChatID;
import ua.gov.publicfinance.telegrambot.domain.model.entities.dao.ChatIdDao;


@Service
@AllArgsConstructor
public class ChatIdServiceImpl implements ChatIdService{

    private ChatIdDao chatIdDao;


    @Override
    public void addChatId(ChatID chatID) {
        this.chatIdDao.addChatId(chatID);
    }


    @Override
    public ChatID getChatIdById(long id) {
        return this.chatIdDao.getChatIdById(id);
    }

}
