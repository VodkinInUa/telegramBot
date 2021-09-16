package ua.gov.publicfinance.telegrambot.domain.model.valueObjects.dao;

import org.springframework.stereotype.Component;
import ua.gov.publicfinance.telegrambot.domain.model.valueObjects.IncomingMessage;

import java.util.List;

@Component
public interface IncomingMessageDao {

    void addIncomingMessage(IncomingMessage incomingMessage);

    IncomingMessage getIncomingMessageById(long id);

    List listIncomingMessage();
}
