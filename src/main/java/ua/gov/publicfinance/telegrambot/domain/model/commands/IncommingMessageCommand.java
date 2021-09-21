package ua.gov.publicfinance.telegrambot.domain.model.commands;

public class IncommingMessageCommand {
	private String text;
    private long chatId;

    public IncommingMessageCommand(String text, long chatId) {
        this.text = text;
        this.chatId = chatId;
    }  
    
    public String getText() {
        return text;
    }

    public long getChatId() {
        return chatId;
    }
}
