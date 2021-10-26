package ua.gov.publicfinance.telegrambot.domain.model.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;
import ua.gov.publicfinance.telegrambot.domain.model.dialogue.Events;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
public class EventFromDialogueStateMachine extends ApplicationEvent {


    private String text;
    private long chatId;
    private Collection<Events> availableEvents = new ArrayList<>();

    public EventFromDialogueStateMachine(Object source) {
        super(source);
    }
    public EventFromDialogueStateMachine(Object source, String text, long chatId) {
        super(source);
        this.text=text;
        this.chatId = chatId;
    }

    public EventFromDialogueStateMachine(Object source, String text, long chatId, Collection<Events> availableEvents) {
        super(source);
        this.text=text;
        this.chatId = chatId;
        this.availableEvents=availableEvents;
    }
}
