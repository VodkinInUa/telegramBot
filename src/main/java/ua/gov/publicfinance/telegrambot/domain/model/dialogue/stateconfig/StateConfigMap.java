package ua.gov.publicfinance.telegrambot.domain.model.dialogue.stateconfig;

import ua.gov.publicfinance.telegrambot.domain.model.dialogue.StateConfig;

import java.util.HashMap;
import java.util.Map;

public class StateConfigMap {
    private static StateConfigMap stateConfigMap=new StateConfigMap();
    public static Map <String, StateConfig> map=new HashMap<>();
}
