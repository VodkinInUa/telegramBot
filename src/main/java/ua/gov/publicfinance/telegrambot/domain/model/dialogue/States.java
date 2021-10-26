package ua.gov.publicfinance.telegrambot.domain.model.dialogue;

public enum States {
    HOME("Оберіть необхідний розділ"),
    SPENDING("Оберіть необхідний розділ"),
    SPENDING_EIDRPOU("Введіть ЕДРПОУ"),
    SPENDING_EIDRPOU_REQUEST("Відправляємо запит …"),
    SPENDING_EIDRPOU_RESPONSE("One more request ?"),
    SPENDING_STATISTIC("Оберіть необхідний розділ"),
    SPENDING_STATISTIC_REQUEST("Відправляємо запит …"),
    SPENDING_STATISTIC_RESPONSE("One more request ?"),
    OPEN_BUDGET("Оберіть необхідний розділ"),
    IFI(""),
    VAT(""),
    ABOUT("");

    private final String textAction;

    States(String textAction) {
        this.textAction = textAction;
    }

    public String getTextAction() {
        return textAction;
    }
}
