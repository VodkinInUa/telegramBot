package ua.gov.publicfinance.telegrambot.domain.model.dialogue.actions;

import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RequestToSwagger {

    final static private String host = "chat-bot.openbudget.gov.ua";

    public static String getDisposer(String edrpou) throws Exception {
        String url = "/spending/disposer/"+edrpou;
        Map<String, Object> uriVariables=new HashMap<>();
        uriVariables.put("edrpou",edrpou);
        return sendGet(url, new HashMap<String, Object>());
    }
    public static String putDisposerStates(String edrpou) throws Exception {
        String url = "/spending/disposer/states";
        Map<String, Object> uriVariables=new HashMap<>();
        uriVariables.put("edrpous",edrpou);
        return sendGet1(url, new HashMap<String, Object>());
    }
    public static String getTop10Recipients(String kpk) throws Exception {
        String url = "/spending/top10/recipients?kpk={kpk}&year={year}";
        Map<String, Object> uriVariables=new HashMap<>();
        uriVariables.put("kpk",kpk);// 3511350
        int year = 2020;
        uriVariables.put("year","2020");
        return sendGet1(url, uriVariables);
    }
    private static String sendGet1(String path, Map<String, ?> uriVariables) throws Exception {


        String url = String.format("http://%s%s", host, path);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        //headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        //headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> params = new HashMap<String, String>();
        params.put("kpk", "3511350");
        params.put("year","2020");


        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> httpEntity  = new HttpEntity<>(headers);

        ResponseEntity result = restTemplate.exchange(url, HttpMethod.GET,httpEntity, String.class, params);

        return result.toString();


    }
    private class EdrpouRequest{

    }
    private static String sendGet(String path, Map<String, ?> uriVariables) throws Exception {

        try {
            String url = String.format("http://%s%s", host, path);
            RestTemplate restTemplate = new RestTemplate();
            String result;
            if(uriVariables.isEmpty()){
                result = restTemplate.getForObject(url, String.class);
            }else {
                //restTemplate.se
                result = restTemplate.getForObject(url, JSONObject.class, uriVariables).toString();
            }
            return result;
        } catch (HttpStatusCodeException ex) {
            return ex.getResponseBodyAsString();
        }

    }
}
