package ua.gov.publicfinance.telegrambot.application.internal.events;

import org.springframework.context.event.EventListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class NewMessage {
    @EventListener
    public void onApplicationEvent(@NonNull final GenericSpringAppEvent event) {
        StringBuilder sb = new StringBuilder();
        List msg= (List) event.getWhat();
        msg.forEach(user ->
                sb.append("chatId: ")
                        .append(user)

        );
        System.out.println("Received spring generic event - " + event.getWhat()+ sb);
    }
}
