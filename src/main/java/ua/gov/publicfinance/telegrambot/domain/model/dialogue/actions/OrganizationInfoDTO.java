package ua.gov.publicfinance.telegrambot.domain.model.dialogue.actions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationInfoDTO {
    public String address;
    public String director;
    public String edrpou;
    public String email;
    public String koatuu;
    public String kopfg;
    public String kved;
    public String kvedName;
    public String orgForm;
    public String orgName;
    public String orgType;
    public String phone;
    public String registrationDate;
    public String state;

    public String toString() {
        return
                "<b>Код за ЄДРПОУ</b>  -  " + edrpou +
                        "\n<b>Категорія суб'єкта</b>  -  " + orgType +
                        "\n<b>Код за КОПФГ</b>  -  " + kopfg + " - " + orgForm +
                        "\n<b>Код за КВЕД (основний)</b>  -  " + kved + " - " + kvedName +
                        "\n<b>Код за КОАТУУ</b>  -  " + koatuu +
                        "\n<b>Місцезнаходження</b>  -  " + address +
                        "\n<b>Керівник</b>  -  " + director +
                        "\n<b>Кабінет</b>  -  " + state;
    }
}
