package storm.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateUtil {

    
    Map<String,String> statetoCodeMap = new HashMap();

    public StateUtil(){
        statetoCodeMap.put("Andhra Pradesh","ap");
        statetoCodeMap.put("Arunachal Pradesh","arp");
        statetoCodeMap.put("Assam","as");
        statetoCodeMap.put("Bihar","bh");
        statetoCodeMap.put("Chhattisgarh","cj");
        statetoCodeMap.put("Goa","go");
        statetoCodeMap.put("Gujarat","gj");
        statetoCodeMap.put("Haryana","hn");
        statetoCodeMap.put("Himachal Pradesh","hp");
        statetoCodeMap.put("Jharkhand","jh");
        statetoCodeMap.put("Karnataka","ka");
        statetoCodeMap.put("Kerala","ke");
        statetoCodeMap.put("Madhya Pradesh","mp");
        statetoCodeMap.put("Maharashtra","mh");
        statetoCodeMap.put("Manipur","man");
        statetoCodeMap.put("Meghalaya","meg");
        statetoCodeMap.put("Mizoram","miz");
        statetoCodeMap.put("Nagaland","ng");
        statetoCodeMap.put("Odisha","od");
        statetoCodeMap.put("Punjab","pb");
        statetoCodeMap.put("Rajasthan","rj");
        statetoCodeMap.put("Sikkim","sk");
        statetoCodeMap.put("Tamil Nadu","tn");
        statetoCodeMap.put("Telangana","tg");
        statetoCodeMap.put("Tripura","tr");
        statetoCodeMap.put("Uttar Pradesh","up");
        statetoCodeMap.put("Uttarakhand","uk");
        statetoCodeMap.put("West Bengal","wb");
        statetoCodeMap.put("Chandigarh","cha");
        statetoCodeMap.put("Delhi","dl");
        statetoCodeMap.put("Pondicherry","pon");
    }

    public boolean containsState(String state){
        return statetoCodeMap.containsKey(state);
    }

    public String getCodeForState(String state){
        return statetoCodeMap.get(state);
    }

}
