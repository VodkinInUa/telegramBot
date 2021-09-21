package ua.gov.publicfinance.telegrambot.infrastructure.services.telegramApi;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ua.gov.publicfinance.telegrambot.application.internal.events.PushMessage;
import ua.gov.publicfinance.telegrambot.application.internal.events.NewMessage;


@Component
@PropertySource("classpath:telegram.properties")
public class UpdateService extends TelegramApi implements ApplicationListener<PushMessage> {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            final String text = update.getMessage().getText();
            final long chatId = update.getMessage().getChatId();
            NewMessage incomingMessage = new NewMessage(this, text, chatId);
            ApplicationEventPublisher publisher = getPublisher();
            publisher.publishEvent(incomingMessage);

        }
    }

    @Override
    public void onApplicationEvent(PushMessage event) {
        String text = event.getText();
        long chatId = event.getChatId();
        SendMessage msg= new SendMessage();
        msg.setChatId(Long.toString(chatId));
        msg.setText(text);
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
