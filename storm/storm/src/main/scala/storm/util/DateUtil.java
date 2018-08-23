package storm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    public static Date getDate(String time) throws ParseException {
        String TWITTER="EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(TWITTER);
        sf.setLenient(true);

        return sf.parse(time);
    }

    public static Date getCurrentTime() throws ParseException {

        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

        return dateFormatLocal.parse( dateFormatGmt.format(new Date()) );
    }

    public static boolean isDuringMatch(int hours,int minutes){

        if(hours>=14&&hours<=20) {
            return true;
        }
        else
            return false;

    }

    public static boolean isAfterMatch(int hours,int minutes){

        if(hours>20){
            return true;
        }
        else
            return false;

    }

    public static boolean beforeMatch(int hours,int minutes){

        if(hours<=14){
            return true;
        }
        else
            return false;

    }
}
