package ua.gov.publicfinance.telegrambot.domain.model.dialogue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ua.gov.publicfinance.telegrambot.application.internal.events.SubscribeEvent;
import ua.gov.publicfinance.telegrambot.application.internal.events.UnsubscribeEvent;
import ua.gov.publicfinance.telegrambot.domain.model.events.EventToDialogueStateMachine;

@Component
public class Dialogue implements ApplicationListener<EventToDialogueStateMachine> {

    @Autowired
    private StateMachineService<States,Events> stateMachineService;
    @Autowired
    private ApplicationEventPublisher publisher;

    private StateMachine<States,Events> sm;

    @Override
    public void onApplicationEvent(EventToDialogueStateMachine event) {
        String chatId=event.getChatId();
        String text=event.getText();
        sm = stateMachineService.acquireStateMachine(chatId);
        System.out.println("++++++++++Dialogue.message++++++++++");
        System.out.println(sm.getExtendedState());
        System.out.println(text);

        try {
            Events events = Events.valueOf(text);
            Message<Events> eventMessage = MessageBuilder
                    .withPayload(events)
                    .setHeader("header_key", "header_value")
                    .build();
            sm.sendEvent(Mono.just(eventMessage)).subscribe();
            System.out.println("Sending message to sm " + eventMessage.toString());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            Message<Events> eventMessage = MessageBuilder
                    .withPayload(Events.SAY)
                    .setHeader("text", text)
                    .build();
            sm.sendEvent(Mono.just(eventMessage)).subscribe();
            System.out.println("Sending message to sm " + eventMessage.toString());
            this.testSubscribe(text, chatId, "states", "00013480",999999L);
        }
    }

    private void testSubscribe(String text, String chatId, String theme, String target, long subscribeId){
        if ( text.equals("subscribe") ){
            SubscribeEvent event = new SubscribeEvent(this,target,theme,chatId,subscribeId);
            publisher.publishEvent(event);
            System.out.println("Send event " + event.toString());
        } else if ( text.equals("unsubscribe") ) {
            UnsubscribeEvent event = new UnsubscribeEvent(this,target,theme,chatId,subscribeId);
            publisher.publishEvent(event);
        }

    }

}
