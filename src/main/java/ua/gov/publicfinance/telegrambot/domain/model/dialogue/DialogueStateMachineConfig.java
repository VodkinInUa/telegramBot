package ua.gov.publicfinance.telegrambot.domain.model.dialogue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.state.State;
import ua.gov.publicfinance.telegrambot.domain.model.dialogue.actions.SpendingActions;


@Configuration
@EnableStateMachineFactory
public class DialogueStateMachineConfig extends SpendingActions{

    @Autowired
    private StateMachineRuntimePersister<States, Events, String> stateMachineRuntimePersister;

    @Override
    public void configure(
            StateMachineConfigurationConfigurer
                    <States, Events> config) throws Exception {

        config.withConfiguration()
                .autoStartup(true)
                .and()
                .withPersistence()
                .runtimePersister(stateMachineRuntimePersister);
    }


    @Override
    public void configure(
            StateMachineStateConfigurer<States, Events> states)
            throws Exception {

        states.withStates()
                .initial(States.HOME, firstInitialAction())
                .state(States.HOME, initialAction())
                    .state(States.SPENDING, initialAction())
                        .state(States.SPENDING_EIDRPOU, initialAction())
                            .state(States.SPENDING_EIDRPOU_REQUEST, requestAction())
                            .state(States.SPENDING_EIDRPOU_RESPONSE, responseAction())
                            .state(States.SPENDING_EIDRPOU_SUBSCRIBE, initialAction())
                            .state(States.SPENDING_EIDRPOU_DYNAMIC, initialAction())
                                .state(States.SPENDING_EIDRPOU_DYNAMIC_EXPENSES, initialAction())
                                    .state(States.SPENDING_EIDRPOU_DYNAMIC_EXPENSES_REQUEST, requestAction())
                                    .state(States.SPENDING_EIDRPOU_DYNAMIC_EXPENSES_RESPONSE, responseAction())
                                .state(States.SPENDING_EIDRPOU_DYNAMIC_INCOMES, initialAction())
                        .state(States.SPENDING_STATISTIC, initialAction())
                            .state(States.SPENDING_STATISTIC_REQUEST, requestAction())
                            .state(States.SPENDING_STATISTIC_RESPONSE, responseAction())
                    .state(States.OPEN_BUDGET, initialAction());
    }



    @Override
    public void configure(
            StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {

        transitions
                .withExternal().source(States.HOME).target(States.SPENDING).event(Events.TO_SPENDING)
                    .and().withExternal().source(States.SPENDING).target(States.SPENDING_EIDRPOU).event(Events.TO_SPENDING_EIDRPOU)
                        .and().withLocal().source(States.SPENDING_EIDRPOU).target(States.SPENDING_EIDRPOU_REQUEST).event(Events.WS_REQUEST)
                            .and().withInternal().source(States.SPENDING_EIDRPOU)
                                    .event(Events.SAY)
                                    .action(inputCodeAction())
                                    // .guard(validateCodeGuard())
                                .and().withExternal().source(States.SPENDING_EIDRPOU_REQUEST).target(States.SPENDING_EIDRPOU_RESPONSE).event(Events.WS_RESPONSE)
                                .and().withLocal().source(States.SPENDING_EIDRPOU_RESPONSE).target(States.SPENDING_EIDRPOU).event(Events.BACK)
                                        .and().withExternal().source(States.SPENDING_EIDRPOU).target(States.SPENDING_EIDRPOU_SUBSCRIBE).event(Events.TO_SPENDING_EIDRPOU_SUBSCRIBE)
                                        .and().withExternal().source(States.SPENDING_EIDRPOU).target(States.SPENDING_EIDRPOU_DYNAMIC).event(Events.TO_SPENDING_EIDRPOU_DYNAMIC)
                                        .and().withExternal().source(States.SPENDING_EIDRPOU_DYNAMIC).target(States.SPENDING_EIDRPOU_DYNAMIC_EXPENSES).event(Events.TO_SPENDING_EIDRPOU_DYNAMIC_EXPENSES)
                                                .and().withLocal().source(States.SPENDING_EIDRPOU_DYNAMIC_EXPENSES).target(States.SPENDING_EIDRPOU_DYNAMIC_EXPENSES_REQUEST).event(Events.WS_REQUEST)
                                                .and().withInternal().source(States.SPENDING_EIDRPOU_DYNAMIC_EXPENSES)
                                                .event(Events.SAY)
                                                .action(inputCodeAction())
                                                // .guard(validateCodeGuard())
                    .and().withExternal().source(States.SPENDING).target(States.SPENDING_STATISTIC).event(Events.TO_SPENDING_STATISTIC)
                            .and()
                    .withExternal().source(States.SPENDING).target(States.HOME).event(Events.TO_HOME).and()
                        .withExternal().source(States.SPENDING_EIDRPOU).target(States.HOME).event(Events.TO_HOME).and()
                            .withExternal().source(States.SPENDING_EIDRPOU_SUBSCRIBE).target(States.HOME).event(Events.TO_HOME).and()
                    .withExternal().source(States.SPENDING_STATISTIC).target(States.HOME).event(Events.TO_HOME).and()

                .withExternal().source(States.HOME).target(States.OPEN_BUDGET).event(Events.TO_OPEN_BUDGET)
                    .and()
                .withExternal().source(States.OPEN_BUDGET).target(States.HOME).event(Events.TO_HOME);
    }
}