package ua.gov.publicfinance.telegrambot.domain.model.valueObjects.dao;

import org.springframework.stereotype.Repository;
import ua.gov.publicfinance.telegrambot.domain.model.valueObjects.IncomingMessage;

import java.util.List;

@Repository
public class IncomingMassageDaoImpl implements IncomingMessageDao{
    @Override
    public void addIncomingMessage(IncomingMessage incomingMessage) {

    }

    @Override
    public IncomingMessage getIncomingMessageById(long id) {
        return null;
    }

    @Override
    public List listIncomingMessage() {
        return null;
    }
}
