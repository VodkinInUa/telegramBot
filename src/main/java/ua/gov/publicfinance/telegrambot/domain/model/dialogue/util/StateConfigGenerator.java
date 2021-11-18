package ua.gov.publicfinance.telegrambot.domain.model.dialogue.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ua.gov.publicfinance.telegrambot.domain.model.dialogue.StateConfig;
import ua.gov.publicfinance.telegrambot.domain.model.dialogue.stateconfig.StateConfigMap;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class StateConfigGenerator {

    public static void generateStateConfigMap() throws JsonProcessingException {
        String jsonString= null;
        try {
            jsonString = ConfigGenerator.getJsonObjectsFromFile("state_config.json").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fieldName = "config";
        final List<ObjectNode> node1 = new ObjectMapper().readValue(jsonString, new TypeReference<List<ObjectNode>>() {
        });
        List<String> obj = new LinkedList<>();
        for (ObjectNode objectNode : node1) {
            if (objectNode.has(fieldName)) {
                obj.add(objectNode.get(fieldName).toString());
            }
        }
        System.out.println("someArray: " + obj);
        for (String objectString : obj) {
            Map<String, StateConfig> map = new ObjectMapper().readValue(objectString, new TypeReference<Map<String, StateConfig>>() {});
            StateConfigMap.map.putAll(map);
        }
    }
}
