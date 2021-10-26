package ua.gov.publicfinance.telegrambot.application.internal.commandServices;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ua.gov.publicfinance.telegrambot.application.internal.events.NewMessage;
import ua.gov.publicfinance.telegrambot.domain.model.aggrigates.Chat;
import ua.gov.publicfinance.telegrambot.infrastructure.repositories.ChatRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
@SpringBootTest
@TestPropertySource("classpath:applicationTest.properties")
class IncomingMessageServiceTest {

    NewMessage newMessageTest= new NewMessage("some_source","test",1119042575,222);

    @Autowired
    private IncomingMessageService incomingMessageService;
    @Autowired
    private ChatRepository chatRepository;

    @Test
    void onApplicationEvent() {
        incomingMessageService.onApplicationEvent(newMessageTest);

        Chat excludeChat = chatRepository.findByChatId(1119042575).orElse(null);
        assertEquals(excludeChat.getChatId(),1119042575);
       // System.out.println(chatRepository.findByChatId(1119042575).get().getIncomingMessages().toString());
       // assertEquals(excludeChat.getIncomingMessages().get(0).getDate(),any(long.class));

    }
}