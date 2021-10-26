package ua.gov.publicfinance.telegrambot.application.internal.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class NewMessage extends ApplicationEvent {
    
    private String text;
    private long chatId;
    private int tgMassageId;

    // sanitizeTextMethod from https://answer-id.com/ru/76067316
    private String sanitizeNewMassageText(String text){
        String characterFilter = "[^\\p{L}\\p{M}\\p{N}\\p{P}\\p{Z}\\p{Cf}\\p{Cs}\\s]";
        return text.replaceAll(characterFilter, "");
    }

    public NewMessage(Object source, String text, long chatId, int tgMassageId) {
        super(source);
        this.text = sanitizeNewMassageText(text);
        this.chatId = chatId;
        this.tgMassageId=tgMassageId;
    }
}