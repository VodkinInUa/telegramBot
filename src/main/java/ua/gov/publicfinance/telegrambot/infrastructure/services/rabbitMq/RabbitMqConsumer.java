package ua.gov.publicfinance.telegrambot.infrastructure.services.rabbitMq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import ua.gov.publicfinance.telegrambot.application.internal.events.PushMessage;

@Component
public class RabbitMqConsumer {

    private final ApplicationEventPublisher applicationEventPublisher;

    public RabbitMqConsumer(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @RabbitListener(queues = "notification")
    public void receiveMessageFromSubscribe (Message<String> message) {
        String payload = message.getPayload();
        String chatId = (String) message.getHeaders().get("Subscriber");
        System.out.println("==========================");
        System.out.println("Received from queues 'notification':");
        System.out.println(payload);
        System.out.println("==========================");
        PushMessage event = new PushMessage(this,payload,Long.parseLong(chatId));
        applicationEventPublisher.publishEvent(event);

    }
}
