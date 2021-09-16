package ua.gov.publicfinance.telegrambot.infrastructure.services.telegramApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


@Component
@PropertySource("classpath:telegram.properties")
public class UpdateService extends TelegramLongPollingBot {

    @Autowired
    private GenericEventPublisher genericEventPublisher;

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText())
            return;
        final String text = update.getMessage().getText();
        final long chatId = update.getMessage().getChatId();
        final long userId = update.getMessage().getFrom().getId();
        final String name = update.getMessage().getFrom().getFirstName()+" "+update.getMessage().getFrom().getLastName();
        List str=new ArrayList<>();
        str.add(chatId);
        str.add(text);
        str.add(name);

        genericEventPublisher.publishGenericAppEvent(str);
        System.out.println(text+" - from - " + chatId+" "+userId+" "+name);
        SendMessage msg= new SendMessage();
                msg.setChatId(String.valueOf(chatId));
        msg.setText("Привет "+ name+"\nТы написал - "+text);
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
