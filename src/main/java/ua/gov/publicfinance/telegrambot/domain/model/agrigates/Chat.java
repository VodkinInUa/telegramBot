package ua.gov.publicfinance.telegrambot.domain.model.agrigates;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.gov.publicfinance.telegrambot.application.internal.events.PushMessage;
import ua.gov.publicfinance.telegrambot.domain.model.commands.IncommingMessageCommand;
import ua.gov.publicfinance.telegrambot.domain.model.entities.ChatID;
import ua.gov.publicfinance.telegrambot.domain.model.valueObjects.IncomingMessage;
import ua.gov.publicfinance.telegrambot.domain.model.valueObjects.OutboundMessage;

import javax.persistence.*;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.ArrayList;
import java.util.List;
import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "chat")
public class Chat extends AbstractAggregateRoot<Chat>{

    public Chat (long chatId) {
        setChatId(chatId);
    }
    public Chat (IncommingMessageCommand incommingMessageCommand) {
        setChatId(incommingMessageCommand.getChatId());
        IncommingMessageHandler( incommingMessageCommand );
    }
    public void IncommingMessageHandler(IncommingMessageCommand incommingMessageCommand) {
        IncomingMessage message = new IncomingMessage();
        message.setText(incommingMessageCommand.getText());
        message.setDate(Instant.now().getEpochSecond());
        message.setEditDate(Instant.now().getEpochSecond());
        List<IncomingMessage> messages = getIncomingMessages();
        messages.add(message);
        doDialogue(message);
        setIncomingMessages(messages);
    }

    private void doDialogue(IncomingMessage message) {
        String text = "В чате " + incomingMessages.size() + " сообщений";
        System.out.println("From Chat chatId = " + chatId);
        PushMessage replyMessage = new PushMessage(this, text, chatId);
        registerEvent(replyMessage);
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // @Embedded
    // private ChatID chat_id;
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

}
