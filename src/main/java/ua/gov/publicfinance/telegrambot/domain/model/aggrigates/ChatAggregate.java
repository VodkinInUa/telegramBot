package ua.gov.publicfinance.telegrambot.domain.model.aggrigates;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ua.gov.publicfinance.telegrambot.application.internal.events.PushMessage;
import ua.gov.publicfinance.telegrambot.domain.model.commands.IncomingMessageCommand;
import ua.gov.publicfinance.telegrambot.domain.model.dialogue.Dialogue;
//import ua.gov.publicfinance.telegrambot.domain.model.dialogue.Events;
import ua.gov.publicfinance.telegrambot.domain.model.events.EventFromDialogueStateMachine;
import ua.gov.publicfinance.telegrambot.domain.model.events.EventToDialogueStateMachine;
import ua.gov.publicfinance.telegrambot.domain.model.valueObjects.IncomingMessage;
import ua.gov.publicfinance.telegrambot.infrastructure.repositories.ChatRepository;

import java.time.Instant;
import java.util.Collection;

@NoArgsConstructor
@Component
public class ChatAggregate implements ApplicationListener<ApplicationEvent>{


    private Chat chat;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private Dialogue dialogue;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof IncomingMessageCommand){
            long chatId = ((IncomingMessageCommand) event).getChatId();
            findChatOrCreateNew(chatId);
            IncomingMessage message = addAndSaveIncomingMessage(((IncomingMessageCommand) event));
            doDialogue(message);
        }
        else if (event instanceof EventFromDialogueStateMachine) {
            String messageText = ((EventFromDialogueStateMachine) event).getText();
            long chatId = ((EventFromDialogueStateMachine) event).getChatId();
            Collection<String> availableButtons = ((EventFromDialogueStateMachine) event).getAvailableButtonsFromTargetStates();
            PushMessage pushMessage = new PushMessage(this, messageText, chatId, availableButtons);
            applicationEventPublisher.publishEvent(pushMessage);
        }
    }

//    @Override
//    public void onApplicationEvent(IncomingMessageCommand eventIncomingMessageCommand) {
//        long chatId = eventIncomingMessageCommand.getChatId();
//        findChatOrCreateNew(chatId);
//        IncomingMessage message = addAndSaveIncomingMessage(eventIncomingMessageCommand);
//        doDialogue(message);
//    }
    private void doDialogue(IncomingMessage message) {
        Long chatId = chat.getChatId();
        EventToDialogueStateMachine eventToDialogueStateMachine =new EventToDialogueStateMachine(this, message.getText(), chatId);
        applicationEventPublisher.publishEvent(eventToDialogueStateMachine);
    }

    private IncomingMessage addAndSaveIncomingMessage(IncomingMessageCommand eventIncomingMessageCommand) {
        IncomingMessage message = new IncomingMessage();
        message.setText(eventIncomingMessageCommand.getText());
        message.setDate(Instant.now().getEpochSecond());
        message.setEditDate(Instant.now().getEpochSecond());
        message.setTgMessageId(eventIncomingMessageCommand.getTgMassageId());
        chat.addIncomingMessage(message);
        chatRepository.save(chat);
        return message;
    }

    private void findChatOrCreateNew(long chatId) {
        this.chat = chatRepository.findByChatId(chatId).orElse(new Chat(chatId));
        chatRepository.save(chat);
    }


}
