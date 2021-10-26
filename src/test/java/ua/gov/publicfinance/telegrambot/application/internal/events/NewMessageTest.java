package ua.gov.publicfinance.telegrambot.application.internal.events;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class NewMessageTest {
    String text="▓ railway??\n" +
            "→ Cats and dogs\n" +
            "I'm on \uD83D\uDD25\n" +
            "Apples ⚛ \n" +
            "✅ Vi sign\n" +
            "♛ I'm the king ♛ \n" +
            "Corée ♦ du Nord ☁  (French)\n" +
            " gjør at både ◄╗ (Norwegian)\n" +
            "Star me ★\n" +
            "Star ⭐ once more\n" +
            "早上好 ♛ (Chinese)\n" +
            "Καλημέρα ✂ (Greek)\n" +
            "another ✓ sign ✓\n" +
            "добрай раніцы ✪ (Belarus)\n" +
            "◄ शुभ प्रभात ◄ (Hindi)\n" +
            "✪ ✰ ❈ ❧ Let's get together ★. We shall meet at 12/10/2018 10:00 AM at Tony's.❉";
    NewMessage msg = new NewMessage("some source", text, 111, 222);


    @Test
    void getText() {
        String expected = msg.getText();
        String actual = " railway??\n" +
                " Cats and dogs\n" +
                "I'm on \n" +
                "Apples  \n" +
                " Vi sign\n" +
                " I'm the king  \n" +
                "Corée  du Nord   (French)\n" +
                " gjør at både  (Norwegian)\n" +
                "Star me \n" +
                "Star  once more\n" +
                "早上好  (Chinese)\n" +
                "Καλημέρα  (Greek)\n" +
                "another  sign \n" +
                "добрай раніцы  (Belarus)\n" +
                " शुभ प्रभात  (Hindi)\n" +
                "    Let's get together . We shall meet at 12/10/2018 10:00 AM at Tony's.";
        assertEquals(expected, actual);
    }

    @Test
    void getChatId() {
        long expected = msg.getChatId();
        long actual = 111;
        assertEquals(expected, actual);
    }

    @Test
    void getTgMassageId() {
        int expected = msg.getTgMassageId();
        int actual = 222;
        assertEquals(expected, actual);
    }
}