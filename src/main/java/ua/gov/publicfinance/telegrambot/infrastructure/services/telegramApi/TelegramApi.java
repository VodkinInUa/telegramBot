package ua.gov.publicfinance.telegrambot.infrastructure.services.telegramApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

@Component
@PropertySource("classpath:telegram.properties")
public abstract class TelegramApi extends TelegramLongPollingBot {
	@Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public ApplicationEventPublisher getPublisher() {
    	return applicationEventPublisher;
    }
}