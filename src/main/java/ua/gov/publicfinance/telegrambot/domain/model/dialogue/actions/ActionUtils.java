package ua.gov.publicfinance.telegrambot.domain.model.dialogue.actions;

import org.springframework.statemachine.StateMachine;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ua.gov.publicfinance.telegrambot.domain.model.dialogue.Events;
import ua.gov.publicfinance.telegrambot.domain.model.dialogue.States;

import javax.ws.rs.GET;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public final class ActionUtils {

    public static Collection<Events> getAvailableEventsFromState(StateMachine<States, Events> sm, States state) {
        return sm.getTransitions()
                .stream()
                .filter(p -> p.getSource().getId().equals(state))
                .filter(p -> p.getKind().name().equals("EXTERNAL"))
                .map(p -> p.getTrigger().getEvent())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static String consumeRestApi(String url) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(url, String.class);
            return result;
        } catch (HttpStatusCodeException ex) {
            return ex.getResponseBodyAsString();
        }

    }
}
