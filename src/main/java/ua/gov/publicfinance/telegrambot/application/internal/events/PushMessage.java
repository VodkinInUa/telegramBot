package ua.gov.publicfinance.telegrambot.application.internal.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
//import ua.gov.publicfinance.telegrambot.domain.model.dialogue.Events;

import java.util.ArrayList;
import java.util.Collection;


@Getter
public class PushMessage extends ApplicationEvent {
    
    private String text;
    private long chatId;
    private Collection<String> availableButtons = new ArrayList<>();


    public PushMessage(Object source, String text, long chatId) {
        super(source);
        this.text = text;
        this.chatId = chatId;
    }

    public PushMessage(Object source, String text, long chatId, Collection<String> availableButtons) {
        super(source);
        this.text = text;
        this.chatId = chatId;
        this.availableButtons=availableButtons;
    }

    public Collection<String> getAvailableEvents() {
        return this.availableButtons;
    }
    
}
