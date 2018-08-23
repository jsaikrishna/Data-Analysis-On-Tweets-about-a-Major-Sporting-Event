package storm.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchUtil {

    Map<String,String> matchMap ;

    public MatchUtil(){
        matchMap = new HashMap<>();
        matchMap.put("28_4","CSK,MI");
        matchMap.put("29_4","RCB,KKR");
        matchMap.put("30_4","CSK,DD");
        matchMap.put("1_5","RCB,MI");
        matchMap.put("2_5","DD,RR");
        matchMap.put("3_5","KKR,CSK");
        matchMap.put("4_5","KXIP,MI");
        matchMap.put("5_5","RCB,CSK");
        matchMap.put("6_5","KXIP,RR");
        matchMap.put("7_5","SRH,RCB");
        matchMap.put("8_5","RR,KXIP");
        matchMap.put("9_5","KKR,MI");
        matchMap.put("10_5","DD,SRH");
    }

    public String getTeams(String dateMonth){
        return matchMap.get(dateMonth);
    }

    public boolean tweetRelaventToMatch(List<String> handlesFortheMatch, List<String> hashtagsFortheMatch,String tweet, List<String> hashTagsInTweet){

        for(String hashTag : hashTagsInTweet){
            if(hashtagsFortheMatch.contains(hashTag)) {
                return true;
            }
        }

        for(String handle : handlesFortheMatch){
            if(tweet.contains(handle)) {
                return true;
            }
        }

        return false;
    }

    public boolean getPoll(List<String> handlesFortheMatchTeam0,List<String> handlesFortheMatchTeam1, List<String> hashtagsFortheMatchTeam0,List<String> hashtagsFortheMatchTeam1,String tweet, List<String> hashTagsInTweet){
        Integer countT0=0,countT1=0;
        for(String handle :handlesFortheMatchTeam0){
            if(tweet.contains(handle)) {
                countT0+=1;
            }
        }
        for(String handle :handlesFortheMatchTeam1){
            if(tweet.contains(handle)) {
                countT1+=1;
            }
        }
        for(String handle :hashtagsFortheMatchTeam0){
            if(hashTagsInTweet.contains(handle)) {
                countT0+=1;
            }
        }
        for(String handle :hashtagsFortheMatchTeam1){
            if(hashTagsInTweet.contains(handle)) {
                countT1+=1;
            }
        }
        Integer flag;
        if (countT0<countT1){
            flag =1;
        }
        else{
            flag =0;
        }

        //println("Tweet:"+text)

        Integer sentiment =  SentimentAnalysisUtils.detectSentiment(tweet);
        Integer poll0=0;
        Integer poll1=0;

        if (sentiment==0 && flag==1){
            poll0+=1;
        }
        else if(sentiment==1 && flag==1){
            poll1+=1;
        }
        else if(sentiment==0 && flag==0){
            poll1+=1;
        }
        else{
            poll0+=1;
        }
        if(poll1<poll0){
            return false;
        }
        else{
            return true;
        }

        //return false;
    }

}
