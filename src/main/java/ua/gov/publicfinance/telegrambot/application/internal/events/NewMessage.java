package ua.gov.publicfinance.telegrambot.application.internal.events;

import org.springframework.context.ApplicationEvent;

public class NewMessage extends ApplicationEvent {
    
    private String text;
    private long chatId;

    public NewMessage(Object source, String text, long chatId) {
        super(source);
        this.text = text;
        this.chatId = chatId;
    }
    
    public String getText() {
        return text;
    }

    public long getChatId() {
        return chatId;
    }
}