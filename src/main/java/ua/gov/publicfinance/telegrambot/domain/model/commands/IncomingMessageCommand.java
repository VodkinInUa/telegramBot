package ua.gov.publicfinance.telegrambot.domain.model.commands;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class IncomingMessageCommand extends ApplicationEvent {
    private String text;
    private long chatId;
    private int tgMassageId;

    public IncomingMessageCommand(Object source, String text, long chatId, int tgMassageId) {
        super(source);
        this.text = text;
        this.chatId = chatId;
        this.tgMassageId = tgMassageId;
    }

}
