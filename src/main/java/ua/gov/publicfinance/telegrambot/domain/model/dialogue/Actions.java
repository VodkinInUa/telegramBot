package ua.gov.publicfinance.telegrambot.domain.model.dialogue;

import ua.gov.publicfinance.telegrambot.domain.model.dialogue.stateconfig.StateConfigMap;
import ua.gov.publicfinance.telegrambot.domain.model.events.EventFromDialogueStateMachine;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Component

public class Actions {

    private EventFromDialogueStateMachine eventFromDialogueStateMachine = new EventFromDialogueStateMachine(this);

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    public void collectDataEvent(StateContext<String, String> context, String text, Collection<String> availableEvents) {
        eventFromDialogueStateMachine.setChatId(context.getStateMachine().getId());
        eventFromDialogueStateMachine.setText(text);
        eventFromDialogueStateMachine.setAvailableButtonsFromTargetStates(availableEvents);
    }

    public void collectDataEvent(StateContext<String, String> context, String text) {
        eventFromDialogueStateMachine.setChatId(context.getStateMachine().getId());
        eventFromDialogueStateMachine.setText(text);
    }

    private void publishEvent() {
        applicationEventPublisher.publishEvent(eventFromDialogueStateMachine);
    }

    @Bean
    public Action<String, String> firstInitialAction() {
        return new Action<String, String>() {
            @Override
            public void execute(StateContext<String, String> context) {
                String stateKey = context.getStateMachine().getInitialState().getId();
                String text = "Вітаємо тебе новий користувач " + stateKey;
                collectDataEvent(context, text + " EVENT");
                publishEvent();
            }
        };
    }

    @Bean
    public Action<String, String> enterAction() {
        return new Action<String, String>() {
            @Override
            public void execute(StateContext<String, String> context) {
                String stateKey = context.getStateMachine().getState().getId();
                String stateConfigMessage = StateConfigMap.map.get(stateKey).messages.onEnter;
                String text = stateConfigMessage + " " + stateKey;

                Collection<String> availableTargetStates = get1AvailableTargetStates(context.getStateMachine(), stateKey);
                collectDataEvent(context, text + " EVENT", availableTargetStates);
                publishEvent();
            }
        };
    }

    @Bean
    public Action<String, String> inputCodeAction() {
        return new Action<String, String>() {
            @Override
            public void execute(StateContext<String, String> context) {
                String code = context.getMessageHeaders().get("text").toString();
                System.out.println(code);

//                Message<String> eventMessage = MessageBuilder
//                        .withPayload(Events.WS_REQUEST)
//                        .setHeader("edrpou", code)
//                        .build();
//                context.getStateMachine().sendEvent(Mono.just(eventMessage)).subscribe();
            }
        };
    }

    @Bean
    public Action<String, String> requestAction() {
        return new Action<String, String>() {
            @SneakyThrows
            @Override
            public void execute(StateContext<String, String> context) {

                String edrpou = context.getMessageHeaders().get("text").toString();
                String stateKey = context.getStateMachine().getState().getId();
                String classModelString = StateConfigMap.map.get(stateKey).request.classModelString;
                String method = StateConfigMap.map.get(stateKey).request.method;
                String expressionString = StateConfigMap.map.get(stateKey).request.expressionString;
                String searchParam1 = edrpou;

                Class classModelClass = Class.forName(classModelString);
                Constructor classConstructor = classModelClass.getConstructor();
                Object newInstance = classConstructor.newInstance();

                Class[] cArg = new Class[1];
                cArg[0] = String.class;
                Method resultMetod = newInstance.getClass().getMethod(method, cArg);
                Object result1 = resultMetod.invoke(newInstance, searchParam1);
                SpelExpressionParser parser1 = new SpelExpressionParser();
                String message1 = parser1.parseRaw(expressionString).getValue(result1).toString();

                Collection<String> availableTargetStates = get1AvailableTargetStates(context.getStateMachine(), stateKey);
                collectDataEvent(context,message1+" EVENT", availableTargetStates);
                publishEvent();
            }
        };
    }
    public static Collection<String> get1AvailableTargetStates(StateMachine<String, String> sm, String targetState){
        Collection<String> availableTargetStates = sm.getTransitions()
                .stream()
                .filter(p -> p.getSource().getId().equals(targetState))
                .filter(p -> p.getKind().name().equals("EXTERNAL"))
                .map(p -> p.getTarget().getId())
                .collect(Collectors.toCollection(ArrayList::new));
        Collection<String> buttons = new ArrayList<>();
        for (String state : availableTargetStates) {
            buttons.add(StateConfigMap.map.get(state).button);
        }
        return buttons;
    }
}

