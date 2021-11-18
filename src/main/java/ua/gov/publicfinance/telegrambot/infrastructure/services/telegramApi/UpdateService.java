package ua.gov.publicfinance.telegrambot.infrastructure.services.telegramApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ua.gov.publicfinance.telegrambot.application.internal.events.PushMessage;
import ua.gov.publicfinance.telegrambot.application.internal.events.NewMessage;
//import ua.gov.publicfinance.telegrambot.domain.model.dialogue.Events;

import java.io.IOException;
import java.util.*;


@Component
@PropertySource("classpath:telegram.properties")
public class UpdateService extends TelegramApi implements ApplicationListener<PushMessage> {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            final String text = update.getMessage().getText();
//            String text1;
//            try {
//                text1 = Arrays.stream(Events.values())
////                        .map(s-> {
////                            System.out.print(s.getValue());
////                            return (s.getValue()==null)?s.getValue():s.name();})
//                        .filter((t) -> {
//                            System.out.println("\t"+t.getValue());
//                            return t.getValue().startsWith(text);})
//                        .findFirst()
//                        .get().name();
//            }
//            catch (NoSuchElementException | NullPointerException e){
//                text1=text;
//                System.out.println(e);
//            }
//            System.out.println("==============="+text1);
            final long chatId = update.getMessage().getChatId();
            final int tgMassageId = update.getMessage().getMessageId();
            NewMessage incomingMessage = new NewMessage(this, text, chatId, tgMassageId);
           // ApplicationEventPublisher publisher = getPublisher();
            applicationEventPublisher.publishEvent(incomingMessage);
        }
    }


    @Override
    public void onApplicationEvent(PushMessage eventPushMessage) {
        SendMessage sendMessage = buildSendMessage(eventPushMessage);
        sendMessage.setParseMode("HTML");
        try {
            System.out.println(execute(sendMessage).getMessageId());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private SendMessage buildSendMessage(PushMessage eventPushMessage) {
        SendMessage msg= new SendMessage();
        String text = eventPushMessage.getText();
        long chatId = eventPushMessage.getChatId();
        msg.setChatId(Long.toString(chatId));
        msg.setText(text);

        if ( ! eventPushMessage.getAvailableEvents().isEmpty() ) {
            Collection<String> events = eventPushMessage.getAvailableEvents();

            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            replyKeyboardMarkup.setSelective(true);
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setOneTimeKeyboard(false);
            List<KeyboardRow> keyboard = new ArrayList<>();
            KeyboardRow keyboardFirstRow = new KeyboardRow();
            System.out.println(events);
            for (String item : events) {
                keyboardFirstRow.add(item);
            }
            keyboard.add(keyboardFirstRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            msg.setReplyMarkup(replyKeyboardMarkup);
        }
        return msg;
    }
    private InlineKeyboardMarkup getInlineKeybouard() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        InlineKeyboardButton spending = new InlineKeyboardButton();
        spending.setText("Публічні фінанси");
        spending.setCallbackData("spending");

        InlineKeyboardButton openBudget = new InlineKeyboardButton();
        openBudget.setText("Інформація про бюджет");
        openBudget.setCallbackData("openBudget");

        firstRow.add(spending);
        firstRow.add(openBudget);

        keyboard.add(firstRow);
        markup.setKeyboard(keyboard);

        return markup;


    }
    private static ReplyKeyboardMarkup getMainMenuKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add("Публічні фінанси"); //"spending"
        keyboardFirstRow.add("Інформація про бюджет"); //"openBudget"
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add("Спіьні з МФО проекти"); //"proIF"
        keyboardSecondRow.add("Відшкодування ПДВ"); //"VAT"
        keyboardSecondRow.add("Інформація про мінфін"); //"about"
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

}
