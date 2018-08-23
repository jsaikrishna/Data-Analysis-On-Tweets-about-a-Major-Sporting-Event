package storm.util;

import org.json.simple.JSONObject;

public class JsonUtil {

    public static String getJson(String state, int count){

        JSONObject stateCount = new JSONObject();
        stateCount.put("State",state);
        stateCount.put("Followers",count);
        String json = stateCount.toJSONString();
        return json;

    }

    public static String getSupportJson(String state, int count){

        JSONObject stateCount = new JSONObject();
        stateCount.put("supportstate",state);
        stateCount.put("poll",count);
        String json = stateCount.toJSONString();
        return json;

    }
    public static String getPollJson(String state, double count){

        JSONObject stateCount = new JSONObject();
        stateCount.put("team",state);
        stateCount.put("overallpoll",count);
        String json = stateCount.toJSONString();
        return json;

    }

    public static String getTopJson(String state, int count){

        JSONObject stateCount = new JSONObject();
        stateCount.put("player",state);
        stateCount.put("counter",count);
        String json = stateCount.toJSONString();
        return json;

    }


}
