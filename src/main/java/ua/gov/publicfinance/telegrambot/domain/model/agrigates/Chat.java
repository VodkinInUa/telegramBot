package ua.gov.publicfinance.telegrambot.domain.model.agrigates;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.gov.publicfinance.telegrambot.domain.model.entities.ChatID;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CHAT")
public class Chat {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "CHAT_ID", nullable = false)
    private ChatID chat_id;

   // @Column(name = "name", nullable = false)
   // private List IncomingMessage;

}
