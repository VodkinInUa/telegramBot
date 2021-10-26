package ua.gov.publicfinance.telegrambot.domain.model.dialogue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.statemachine.service.StateMachineService;

@Configuration
public class DialogueStateMachineJpaConfig {
    @Bean
    public StateMachineRuntimePersister<States, Events, String> stateMachineRuntimePersister(
            JpaStateMachineRepository jpaStateMachineRepository) {
        return new JpaPersistingStateMachineInterceptor<>(jpaStateMachineRepository);
    }

    @Bean
    public StateMachineService<States, Events> stateMachineService(
            @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") StateMachineFactory<States, Events> stateMachineFactory,
            StateMachineRuntimePersister<States, Events, String> stateMachineRuntimePersister) {
        return new DefaultStateMachineService<States, Events>(stateMachineFactory, stateMachineRuntimePersister);
    }

}
