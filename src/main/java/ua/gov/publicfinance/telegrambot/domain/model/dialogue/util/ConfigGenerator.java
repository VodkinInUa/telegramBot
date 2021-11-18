package ua.gov.publicfinance.telegrambot.domain.model.dialogue.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ConfigGenerator {

    private ConfigGenerator configGenerator=new ConfigGenerator();

    public static void generate() throws IOException {
        System.out.println("Start Config generate.......");
        List<ObjectNode> nodeListFromConfigDir = getJsonObjectsFromFile("configurerStateMachine/**/*.json");
        List<ObjectNode> nodeListStateMachineConfig = getNodeListStateMachineConfig(nodeListFromConfigDir);
        List<ObjectNode> nodeListStateConfig = getNodeListStateConfig(nodeListFromConfigDir);
        writeJsonFileConfigToResources("statemachine_config", nodeListStateMachineConfig);
        writeJsonFileConfigToResources("state_config", nodeListStateConfig);

        //todo пернести заполнение мапы
//        StateConfigGenerator.generateStateConfig(nodeListStateConfig.toString());

        System.out.println("Config generated");
    }

    static List<ObjectNode> getJsonObjectsFromFile(String location) throws IOException {
        final Resource[] possiblePropertiesResources = ResourcePatternUtils.getResourcePatternResolver(new ResourceLoader() {
            @Override
            public Resource getResource(String location) {
                return null;
            }

            @Override
            public ClassLoader getClassLoader() {
                return null;
            }
        }).getResources("classpath*:" + location);
        List<ObjectNode> objectNodeList = new ArrayList<>();
        try {
            for (Resource res : possiblePropertiesResources) {
                objectNodeList.addAll(new ObjectMapper().readValue(new FileReader(res.getFile().toString()), new TypeReference<List<ObjectNode>>() {
                }));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(objectNodeList);
        return objectNodeList;
    }

    static List<ObjectNode> getNodeListStateMachineConfig(List<ObjectNode> nodeList) {
        List<ObjectNode> nodeList1=new ArrayList<>();
        nodeList1.addAll(nodeList);
        String fieldName = "_class";
        nodeList1.removeIf(s -> !s.has(fieldName));
        Collections.sort(nodeList1, new MyJsonComparator());
        return nodeList1;
    }

    static List<ObjectNode> getNodeListStateConfig(List<ObjectNode> nodeList) {
        List<ObjectNode> nodeList1=new ArrayList<>();
        nodeList1.addAll(nodeList);
        String fieldName = "config";
        nodeList1.removeIf(s -> !s.has(fieldName));
        return nodeList1;
    }

    static class MyJsonComparator implements Comparator<ObjectNode> {

        @Override
        public int compare(ObjectNode o1, ObjectNode o2) {
            String v1;
            try {
                v1 = o1.get("@id").toString();
            } catch (Exception e) {
                return 0;
            }
            String v3;
            try {
                v3 = o2.get("@id").toString();
            } catch (Exception e) {
                return -1;
            }

            return v1.compareTo(v3);
        }

    }

    static void writeJsonFileConfigToResources(String fileName, List<ObjectNode> nodeList) {
        String nodeListString = nodeList.toString();
        try {
            FileWriter file = new FileWriter("src/main/resources/" + fileName + ".json", false);
            file.write(nodeListString);
            file.close();
            System.out.println("Done src/main/resources/" + fileName + ".json");
        } catch (Exception e) {

            e.getMessage();

        }
    }
}
