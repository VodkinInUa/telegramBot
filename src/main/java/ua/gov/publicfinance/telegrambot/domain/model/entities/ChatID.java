package ua.gov.publicfinance.telegrambot.domain.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
public class ChatID {

    private long chat_id;

    public void setChatId(long id) {
        chat_id = id;
    }
}
