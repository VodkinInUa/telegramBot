package ua.gov.publicfinance.telegrambot.domain.model.dialogue.actions;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.guard.Guard;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;
import ua.gov.publicfinance.telegrambot.application.internal.events.PushMessage;
import ua.gov.publicfinance.telegrambot.domain.model.dialogue.Events;
import ua.gov.publicfinance.telegrambot.domain.model.dialogue.States;
import ua.gov.publicfinance.telegrambot.domain.model.events.EventFromDialogueStateMachine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static ua.gov.publicfinance.telegrambot.domain.model.dialogue.actions.ActionUtils.*;

@Configuration
public abstract class SpendingActions extends EnumStateMachineConfigurerAdapter<States, Events> {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    private EventFromDialogueStateMachine eventFromDialogueStateMachine=new EventFromDialogueStateMachine(this);

    @Bean
    public Action<States, Events> inputCodeAction() {
        return new Action<States, Events>() {
            @Override
            public void execute(StateContext<States, Events> context) {
                String code = context.getMessageHeaders().get("text").toString();
                System.out.println(code);

                Message<Events> eventMessage = MessageBuilder
                        .withPayload(Events.WS_REQUEST)
                        .setHeader("edrpou", code)
                        .build();
                context.getStateMachine().sendEvent(Mono.just(eventMessage)).subscribe();
            }
        };
    }
    @Bean
    public Action<States, Events> requestAction() {
        return new Action<States, Events>() {
            @SneakyThrows
            @Override
            public void execute(StateContext<States, Events> context) {
                initialText(context);
                publishEvent();

                String edrpou = context.getMessageHeaders().get("edrpou").toString();
                String url = "http://chat-bot.openbudget.gov.ua/spending/disposer/" + edrpou;

                String result = consumeRestApi(url);

                //String result = RequestToSwagger.getDisposer(edrpou);// consumeRestApi(url);
                //String result = RequestToSwagger.getTop10Recipients(edrpou);// consumeRestApi(url);
                //String result = RequestToSwagger.putDisposerStates(edrpou);// consumeRestApi(url); 41242590

                eventFromDialogueStateMachine.setText(result);
                publishEvent();

                System.out.println("=========requestAction=======");
                System.out.println(context.getStateMachine().getState().getId().toString());

                Message<Events> responseEventMessage = MessageBuilder
                        .withPayload(Events.WS_RESPONSE)
                        .build();

                context.getStateMachine().sendEvent(Mono.just(responseEventMessage)).subscribe();

            }


        };
    }

    @Bean
    public Action<States, Events> responseAction() {
        return new Action<States, Events>() {
            @Override
            public void execute(StateContext<States, Events> context) {
                initialText(context);
                publishEvent();

                System.out.println("=========responseAction========");
                System.out.println(context.getStateMachine().getState().getId().toString());

                Message<Events> eventMessage = MessageBuilder
                        .withPayload(Events.BACK)
                        .build();
                context.getStateMachine().sendEvent(Mono.just(eventMessage)).subscribe();
            }
        };
    }

    @Bean
    public Action<States, Events> errorAction() {
        return new Action<States, Events>() {
            @Override
            public void execute(StateContext<States, Events> context) {
                long chatId = Long.valueOf(context.getStateMachine().getId());
                String errorMessage = "Error on state " + context.getSource().getId() + " - " + context.getException().getMessage();
                PushMessage replyMessage = new PushMessage(this, errorMessage, chatId);
                applicationEventPublisher.publishEvent(replyMessage);
            }
        };
    }

    @Bean
    public Guard<States, Events> validateCodeGuard() {
        return new Guard<States, Events>() {

            @Override
            public boolean evaluate(StateContext<States, Events> context) {
                String code = context.getMessageHeaders().get("text").toString();
                if (code.length() == 8 || code.length() == 10)
                    return true;
                return false;
            }
        };
    }


    @Bean
    public Action<States, Events> firstInitialAction() {
        return new Action<States, Events>() {
            @Override
            public void execute(StateContext<States, Events> context) {

                String text = "Вітаємо тебе новий користувач";
                System.out.println(context.getStateMachine().getId());
                long chatId = Long.valueOf(context.getStateMachine().getId());

                PushMessage replyMessage = new PushMessage(this, text, chatId);
                applicationEventPublisher.publishEvent(replyMessage);
            }
        };
    }


    @Bean
    public Action<States, Events> initialAction() {
        return new Action<States, Events>() {

            @Override
            public void execute(StateContext<States, Events> context) {
                initialText(context);
                Collection<Events> availableEvents =  getAvailableEventsFromState(context.getStateMachine(), context.getTarget().getId());
                eventFromDialogueStateMachine.setAvailableEvents(availableEvents);
                publishEvent();
            }
        };
    }

    private void initialText(StateContext<States, Events> context){
        String text = context.getTarget().getId().getTextAction();
        //String text ="textAction = \"" + context.getTarget().getId().getTextAction() + "\" from State " + context.getTarget().getId().name();
        System.out.println(context.getStateMachine().getId());
        long chatId = Long.valueOf(context.getStateMachine().getId());
        eventFromDialogueStateMachine.setText(text);
        eventFromDialogueStateMachine.setChatId(chatId);
    }

    private void publishEvent(){
        applicationEventPublisher.publishEvent(eventFromDialogueStateMachine);
    }
}
