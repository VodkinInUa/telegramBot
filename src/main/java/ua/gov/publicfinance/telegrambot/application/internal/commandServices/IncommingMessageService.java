package ua.gov.publicfinance.telegrambot.application.internal.commandServices;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import ua.gov.publicfinance.telegrambot.application.internal.events.NewMessage;
import ua.gov.publicfinance.telegrambot.domain.model.agrigates.Chat;
import ua.gov.publicfinance.telegrambot.domain.model.commands.IncommingMessageCommand;
import ua.gov.publicfinance.telegrambot.infrastructure.repositories.ChatRepository;

@Component
public class IncommingMessageService implements ApplicationListener<NewMessage>{

    @Autowired
    private ChatRepository repository;

	@Override
    @Transactional
    public void onApplicationEvent(NewMessage event) {
        System.out.println("Received message to chat_id - " + event.getChatId() + " text: '" + event.getText() +"'");
        long chatId = event.getChatId();
        IncommingMessageCommand command = new IncommingMessageCommand(event.getText(), chatId);
        Chat chat = repository.findByChatId(chatId).orElse(new Chat(chatId));        
        chat.IncommingMessageHandler(command);
        repository.save(chat);
        repository.flush();
    }

}
