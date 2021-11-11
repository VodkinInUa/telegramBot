package ua.gov.publicfinance.telegrambot.application.internal.events;

import org.springframework.context.ApplicationEvent;

public class SubscribeEvent extends ApplicationEvent {
    private String target;
    private String theme;
    private String subscriber;
    private long subscriptionId;

    public SubscribeEvent(Object source, String target, String theme, String subscriber, long subscriptionId) {
        super(source);
        this.target = target;
        this.theme = theme;
        this.subscriber = subscriber;
        this.subscriptionId = subscriptionId;
    }

    public long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public String toString() {
        return "SubscribeEvent{" +
                "target='" + target + '\'' +
                ", theme='" + theme + '\'' +
                ", subscriber='" + subscriber + '\'' +
                ", subscriptionId=" + subscriptionId +
                '}';
    }
}
