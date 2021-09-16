package ua.gov.publicfinance.telegrambot.infrastructure.services.telegramApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import ua.gov.publicfinance.telegrambot.application.internal.events.GenericSpringAppEvent;

@Component
public class GenericEventPublisher {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

 public void publishGenericAppEvent(final Object message) {
     System.out.println("Publishing generic event.");
     final GenericSpringAppEvent<String> genericSpringEvent = new GenericSpringAppEvent(this, message);
     applicationEventPublisher.publishEvent(genericSpringEvent);
 }


}
