package storm.util;

import java.util.*;

public class HashTagUtil {

    Map<String,List<String>> teamToHashTagMap = new HashMap<>();
    Map<String,List<String>> teamToHandleMap = new HashMap<>();

    List<String> cskTags = new ArrayList<>();
    List<String> cskHandles = new ArrayList<>();

    List<String> rcbTags = new ArrayList<>();
    List<String> rcbHandles = new ArrayList<>();

    List<String> srhTags = new ArrayList<>();
    List<String> srhHandles = new ArrayList<>();

    List<String> kkrTags = new ArrayList<>();
    List<String> kkrHandles = new ArrayList<>();

    List<String> miTags = new ArrayList<>();
    List<String> miHandles = new ArrayList<>();

    List<String> ddTags = new ArrayList<>();
    List<String> ddHandles = new ArrayList<>();

    List<String> rrTags = new ArrayList<>();
    List<String> rrHandles = new ArrayList<>();

    List<String> kxipTags = new ArrayList<>();
    List<String> kxipHandles = new ArrayList<>();
    List<String> playersList = new ArrayList<>();
    public HashTagUtil(){

        //CSK Tags and Handles
        cskTags.add("csk");
        cskTags.add("yellove");
        cskTags.add("whistlepodu");
        cskTags.add("chennaisuperkings");
        cskHandles.add("@ChennaiIPL");
        cskHandles.add("@msdhoni");
        cskHandles.add("@ImRaina");
        cskHandles.add("@RayuduAmbati");
        cskHandles.add("@DJBravo47");
        cskHandles.add("@ShaneRWatson33");
        playersList.addAll(cskHandles);

        teamToHashTagMap.put("CSK",cskTags);
        teamToHandleMap.put("CSK",cskHandles);

        //RCB tags and Handles
        rcbTags.add("rcb");
        rcbTags.add("playbold");
        rcbTags.add("viratkohli");

        rcbHandles.add("@RCBTweets");
        rcbHandles.add("@ImKohli18");
        rcbHandles.add("@ABdeVilliers17");
        rcbHandles.add("@yuzi_chahal");
        rcbHandles.add("@y_umesh");
        playersList.addAll(rcbHandles);

        teamToHashTagMap.put("RCB",rcbTags);
        teamToHandleMap.put("RCB",rcbHandles);

        //SRH tags and Handles
        srhTags.add("srh");
        srhTags.add("orangearmy");
        srhTags.add("sunrisers");

        srhHandles.add("@SunRisers");
        srhHandles.add("@KanyWilliamson");
        srhHandles.add("@rashidkhan_19");
        srhHandles.add("@BhuviOfficial");
        srhHandles.add("@iamyusufpathan");
        playersList.addAll(srhHandles);

        teamToHashTagMap.put("SRH",srhTags);
        teamToHandleMap.put("SRH",srhHandles);


        //KKR Tags and handles
        kkrTags.add("kkr");
        kkrTags.add("korbolorbojeetbo");
        kkrTags.add("kkrhaitaiyaar");
        kkrTags.add("kkrfan");

        kkrHandles.add("@KKRiders");
        kkrHandles.add("@DineshKarthik");
        kkrHandles.add("@SunilPNarine74");
        kkrHandles.add("@Russell12A");
        playersList.addAll(kkrHandles);

        teamToHashTagMap.put("KKR",kkrTags);
        teamToHandleMap.put("KKR",kkrHandles);

        //MI Tags and Handles
        miTags.add("mi");
        miTags.add("cricketmerijaan");
        miTags.add("mumbaiindians");

        miHandles.add("@mipaltan");
        miHandles.add("@MarkandeMayank");
        miHandles.add("@surya_14kumar");
        miHandles.add("@ImRo45");
        miHandles.add("@KieronPollard55");
        playersList.addAll(miHandles);

        teamToHashTagMap.put("MI",miTags);
        teamToHandleMap.put("MI",miHandles);

        //DD tags and Handles
        ddTags.add("dd");
        ddTags.add("shreyasiyer");

        ddHandles.add("@DelhiDaredevils");
        ddHandles.add("@ShreyasIyer8");
        ddHandles.add("@trent_boult");
        ddHandles.add("@RishabPant777");
        playersList.addAll(ddHandles);

        teamToHashTagMap.put("DD",ddTags);
        teamToHandleMap.put("DD",ddHandles);


        //RR Tags and Handles
        rrTags.add("hallabol");
        rrTags.add("rr");
        rrTags.add("rajasthanroyals");
        rrTags.add("jazbajeetka");

        rrHandles.add("@rajasthanroyals");
        rrHandles.add("@ajinkyarahane88");
        rrHandles.add("@IamSanjuSamson");
        rrHandles.add("@benstokes38");
        rrHandles.add("@JUnadkat");
        playersList.addAll(rrHandles);

        teamToHashTagMap.put("RR",rrTags);
        teamToHandleMap.put("RR",rrHandles);

        //KXIP tags and Handles
        kxipTags.add("kingsxipunjab");
        kxipTags.add("livepunjabiplaypunjab");
        kxipTags.add("kxip");

        kxipHandles.add("@lionsdenkxip");
        kxipHandles.add("@ashwinravi99");
        kxipHandles.add("@henrygayle");
        kxipHandles.add("@klrahul11");
        kxipHandles.add("@Mujeeb_Zadran");
        playersList.addAll(kxipHandles);

        teamToHashTagMap.put("KXIP",kxipTags);
        teamToHandleMap.put("KXIP",kxipHandles);

    }

    public List<String> getHandlesForTeam(String team)
    {
        return teamToHandleMap.get(team);
    }

    public List<String> getTagList(String team)
    {
        return teamToHashTagMap.get(team);
    }

    public  List<String> getUsers(String tweet){
        List<String> list = new ArrayList<>();
        String handles = "";
        String[] splited = tweet.split("\\s+");
        for (String elem : splited) {
            String newElem = "";
            if (elem.startsWith("@")) {
                 list.add(elem);
            }
        }

        return list;
    }

    public boolean containsPlayer(String name){
        return playersList.contains(name);
    }

}
