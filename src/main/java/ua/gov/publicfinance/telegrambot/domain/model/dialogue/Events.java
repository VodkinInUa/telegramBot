package ua.gov.publicfinance.telegrambot.domain.model.dialogue;

public enum Events {
    TO_SPENDING("Публічні кошти"),
    TO_HOME("На головну"),
    TO_OPEN_BUDGET("Інформація про бюджет"),
    TO_SPENDING_EIDRPOU("Пошук за ЄДРПОУ"),
    TO_SPENDING_STATISTIC("Статистика по витратам/надходженням"),
    SAY("SAY"),
    WS_REQUEST("WS_REQUEST"),
    WS_RESPONSE("WS_RESPONSE"),
    BACK("BACK");

    private final String value;

    Events(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
