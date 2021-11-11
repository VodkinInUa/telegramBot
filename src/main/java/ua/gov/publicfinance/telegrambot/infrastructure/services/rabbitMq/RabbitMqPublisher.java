package ua.gov.publicfinance.telegrambot.infrastructure.services.rabbitMq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import ua.gov.publicfinance.telegrambot.application.internal.events.SubscribeEvent;
import ua.gov.publicfinance.telegrambot.application.internal.events.UnsubscribeEvent;

import java.time.Instant;

@Component
public class RabbitMqPublisher{

    private final RabbitTemplate rabbitTemplate;

    public RabbitMqPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @EventListener
    public void subscribeEventHandler(SubscribeEvent event) {
        Message<String> message = this.messageFromEvent(event);
        rabbitTemplate.convertAndSend("tg.bot.exchange","tg.subscribe",message);
    }

    @EventListener
    public void unsubscribeEventHandler(UnsubscribeEvent event) {
        Message<String> message = this.messageFromEvent(event);
        rabbitTemplate.convertAndSend("tg.bot.exchange","tg.unsubscribe",message);
    }

    private Message<String> messageFromEvent(SubscribeEvent event){
        String payload = this.jsonFromEvent(event);
        Message<String> eventMessage = MessageBuilder
                .withPayload(payload)
                .setHeader("Send datetime", Instant.now().getEpochSecond())
                .build();
        return  eventMessage;
    }

    private String jsonFromEvent(SubscribeEvent event){
        String json = "{\"target\": \""+event.getTarget()+"\"," +
                "\"theme\": \""+event.getTheme()+"\"," +
                "\"subscriber\": \""+event.getSubscriber()+"\"," +
                "\"subscriptionId\": \""+event.getSubscriptionId()+"\""+
                "}";
        return json;
    }

    private Message<String> messageFromEvent(UnsubscribeEvent event){
        String payload = this.jsonFromEvent(event);
        Message<String> eventMessage = MessageBuilder
                .withPayload(payload)
                .setHeader("Send datetime", Instant.now().getEpochSecond())
                .build();
        return  eventMessage;
    }

    private String jsonFromEvent(UnsubscribeEvent event){
        String json = "{\"target\": \""+event.getTarget()+"\"," +
                "\"theme\": \""+event.getTheme()+"\"," +
                "\"subscriber\": \""+event.getSubscriber()+"\"," +
                "\"subscriptionId\": \""+event.getSubscriptionId()+"\""+
                "}";
        return json;
    }
}
