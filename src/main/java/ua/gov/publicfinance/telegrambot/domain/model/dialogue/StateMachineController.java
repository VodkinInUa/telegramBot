/*
 * Copyright 2016-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ua.gov.publicfinance.telegrambot.domain.model.dialogue;

import ua.gov.publicfinance.telegrambot.domain.model.events.EventFromDialogueStateMachine;
import ua.gov.publicfinance.telegrambot.domain.model.events.EventToDialogueStateMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@Controller
public class StateMachineController implements ApplicationListener<EventFromDialogueStateMachine> {

    private String textMessage;

    private Collection<String> availableButtons;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @RequestMapping("/")
    public String home() {
        return "redirect:/state";
    }

    private StateMachine<String, String> sm;

    @RequestMapping("/state")
    public String feedAndGetStates(@RequestParam(value = "events1", required = false) String events1,
                                   @RequestParam(value = "inputText1", required = false) String inputText1,
                                   Model model) throws Exception {
        long chatId = 11;
        String event = null;
            if (events1 != null) {
            event = events1;
        } else if (inputText1 != null) {
            event = inputText1;
        }
        EventToDialogueStateMachine eventToDialogueStateMachine = new EventToDialogueStateMachine(this, event, chatId);
        applicationEventPublisher.publishEvent(eventToDialogueStateMachine);

        model.addAttribute("allEvents1", getTargetState1());
        model.addAttribute("messages1", createMessages1());
        return "states";
    }

    private String[] getTargetState1() {

        return availableButtons.toArray(new String[0]);
    }

    private String createMessages1() {

        return textMessage;
    }

    @Override
    public void onApplicationEvent(EventFromDialogueStateMachine event) {
        textMessage = event.getText();
        availableButtons = event.getAvailableButtonsFromTargetStates();
    }
}
