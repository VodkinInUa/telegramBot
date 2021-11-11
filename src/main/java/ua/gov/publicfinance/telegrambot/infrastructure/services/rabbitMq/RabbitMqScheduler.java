package ua.gov.publicfinance.telegrambot.infrastructure.services.rabbitMq;

import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class RabbitMqScheduler {

    private final RabbitListenerEndpointRegistry registry;

    public RabbitMqScheduler(RabbitListenerEndpointRegistry registry) {
        this.registry = registry;
    }

    @Scheduled(cron = "0 1/2 * * * ?",zone="Europe/Kiev")
    public void stopAll(){
        System.out.println("Going to stop …");
        registry.getListenerContainers().forEach(c ->{
            System.out.println("Stopping " + c.toString());
            c.stop();
        });
    }

    @Scheduled(cron = "0 */2 * * * ?", zone="Europe/Kiev")
    public void startAll(){
        System.out.println("Going to start …");
        registry.getListenerContainers().forEach(c ->{
            System.out.println("Starting " + c.toString());
            c.start();
        });
    }
}
