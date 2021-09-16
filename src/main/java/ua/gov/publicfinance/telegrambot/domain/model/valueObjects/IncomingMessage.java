package ua.gov.publicfinance.telegrambot.domain.model.valueObjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "INCOMING_MASSAGE")
public class IncomingMessage {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(name = "MASSAGE_CONTENT", nullable = false)
    private String massageContent;
}
