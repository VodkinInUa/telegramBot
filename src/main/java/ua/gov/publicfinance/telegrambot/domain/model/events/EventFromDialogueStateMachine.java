package ua.gov.publicfinance.telegrambot.domain.model.events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;
import java.util.Collection;


@Getter
@Setter
public class EventFromDialogueStateMachine extends ApplicationEvent {


    private String text;
    private long chatId;
    private Collection<String> availableButtonsFromTargetStates = new ArrayList<>();

    public EventFromDialogueStateMachine(Object source) {
        super(source);
    }

    public EventFromDialogueStateMachine(Object source, String text, long chatId, Collection<String> availableEvents) {
        super(source);
        this.text = text;
        this.chatId = chatId;
        this.availableButtonsFromTargetStates = availableEvents;
    }

    public EventFromDialogueStateMachine(Object source, String text, long chatId) {
        super(source);
        this.text = text;
        this.chatId = chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = Long.valueOf(chatId);
        System.out.println();
    }
}
