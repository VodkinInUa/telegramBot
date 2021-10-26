package ua.gov.publicfinance.telegrambot.domain.model.aggrigates;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.gov.publicfinance.telegrambot.domain.model.valueObjects.IncomingMessage;
import ua.gov.publicfinance.telegrambot.domain.model.valueObjects.OutboundMessage;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity

@Table(name = "chat")
public class Chat {

    public Chat (long chatId) {
        setChatId(chatId);
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "chat_id")
    private long chatId;

    @OneToMany( targetEntity = IncomingMessage.class, 
                cascade = CascadeType.ALL,
                fetch = FetchType.LAZY,
                orphanRemoval = true)
    @JoinColumn(name = "chatId", referencedColumnName = "id")
    private List<IncomingMessage> incomingMessages = new ArrayList<>();

    @OneToMany(targetEntity = OutboundMessage.class, 
                cascade = CascadeType.ALL,
                fetch = FetchType.LAZY,
                orphanRemoval = true)
    @JoinColumn(name = "chatId", referencedColumnName = "id")
    private List<OutboundMessage> outboundMessages = new ArrayList<>();


    public void addIncomingMessage (IncomingMessage message){
        List<IncomingMessage> messages = getIncomingMessages();
        messages.add(message);
        setIncomingMessages(messages);
    }
}
