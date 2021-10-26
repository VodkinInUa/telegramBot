package ua.gov.publicfinance.telegrambot.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EventToDialogueStateMachine extends ApplicationEvent {

    private String text;
    private String chatId;

    public EventToDialogueStateMachine(Object source, String text, Long chatId) {
        super(source);
        this.text = text;
        this.chatId = chatId.toString();
    }
}
