package ua.gov.publicfinance.telegrambot.domain.model.dialogue;

import ua.gov.publicfinance.telegrambot.domain.model.dialogue.stateconfig.StateConfigMap;
import ua.gov.publicfinance.telegrambot.domain.model.events.EventToDialogueStateMachine;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Mono;

@Component
public class Dialogue implements ApplicationListener<EventToDialogueStateMachine> {

    @Autowired
    private StateMachineService<String,String> stateMachineService;

    private StateMachine<String,String> currentStateMachine;

    @SneakyThrows
    @Override
    public void onApplicationEvent(EventToDialogueStateMachine event) {
        String chatId=event.getChatId();
        String text=event.getText();
        currentStateMachine = getStateMachine(chatId);
//        currentStateMachine = stateMachineService.acquireStateMachine(chatId);
        try {
            String button = convertEventButtonToEventStateMachine(text);
            //todo переделать
            if (button==text){throw new Exception();}
            Message<String> eventMessage = MessageBuilder
                    .withPayload(button)
                    .setHeader("header_key", button)
                    .build();
            currentStateMachine.sendEvent(Mono.just(eventMessage)).blockLast();
            System.out.println("Sending message to sm " + eventMessage.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Message<String> eventMessage = MessageBuilder
                    .withPayload("SAY")
                    .setHeader("text", text)
                    .build();
            currentStateMachine.sendEvent(Mono.just(eventMessage)).subscribe();
        }
    }

    private String convertEventButtonToEventStateMachine(String eventButton) {
        StateConfigMap.map.containsKey(eventButton);

        for (StateConfig stateConfig : StateConfigMap.map.values()) {
            if (stateConfig.button.equals(eventButton)){

                return stateConfig.transitionTargetStateEvent;
            }
        }
        return eventButton;
    }
    private synchronized StateMachine<String, String> getStateMachine(String machineId){
        if (currentStateMachine == null) {
            currentStateMachine = stateMachineService.acquireStateMachine(machineId, false);
            currentStateMachine.startReactively().block();
        } else if (!ObjectUtils.nullSafeEquals(currentStateMachine.getId(), machineId)) {
            stateMachineService.releaseStateMachine(currentStateMachine.getId());
            currentStateMachine.stopReactively().block();
            currentStateMachine = stateMachineService.acquireStateMachine(machineId, false);
            currentStateMachine.startReactively().block();
        }
        return currentStateMachine;
    }
}
