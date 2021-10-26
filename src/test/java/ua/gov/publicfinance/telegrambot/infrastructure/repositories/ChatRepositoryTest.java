package ua.gov.publicfinance.telegrambot.infrastructure.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ua.gov.publicfinance.telegrambot.domain.model.aggrigates.Chat;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource("classpath:applicationTest.properties")
class ChatRepositoryTest {
    @Autowired
    private ChatRepository chatRepository;

    @Test
    void findByChatId() {
        Chat chat =new Chat(123);
        chatRepository.save(chat);
        chatRepository.flush();
        Chat excludeChat = chatRepository.findByChatId(123).orElse(new Chat(321));

        assertEquals(excludeChat.getChatId(),chat.getChatId());
    }
}