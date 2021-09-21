package ua.gov.publicfinance.telegrambot.domain.model.valueObjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "outbound_message")
public class OutboundMessage {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column( name = "tg_message_id" )
    private long tgMessageId;
    
    @Column(name = "date", nullable = false)
    private long date;

    @Column(name = "edit_date", nullable = false)
    private long editDate;

    @Column(name = "text", nullable = false)
    private String text;

}
