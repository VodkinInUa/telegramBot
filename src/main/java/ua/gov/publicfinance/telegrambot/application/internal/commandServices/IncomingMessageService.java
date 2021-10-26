package ua.gov.publicfinance.telegrambot.application.internal.commandServices;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import ua.gov.publicfinance.telegrambot.application.internal.events.NewMessage;
import ua.gov.publicfinance.telegrambot.domain.model.commands.IncomingMessageCommand;

@Component
public class IncomingMessageService implements ApplicationListener<NewMessage> {


    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public void onApplicationEvent(NewMessage eventNewMessage) {
        String messageText = eventNewMessage.getText();
        long chatId = eventNewMessage.getChatId();
        int tgMassageId = eventNewMessage.getTgMassageId();

        System.out.println("Received message to chat_id - " + chatId + " text: '" + messageText + "'");
        IncomingMessageCommand command = new IncomingMessageCommand(this, messageText, chatId, tgMassageId);
        applicationEventPublisher.publishEvent(command);
    }
}
