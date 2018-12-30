package X_X.service.user;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;


public class ServiceTools {
    public static String createToken() {
        long ct = System.currentTimeMillis();
        Random random = new Random();
        long rd = random.nextInt();
        String token = String.valueOf(ct) + rd;
        return token;
    }

    public static String getNowTime(){
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(tz);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    public static boolean compareTime(String nowTime, String expireTime){
        try {
            SimpleDateFormat sdf =
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(sdf.parse(nowTime).before(sdf.parse(expireTime))){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static String getExpireTime(){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        return sf.format(c.getTime());
    }

    public static void main(String [] args){
//        System.out.println(getNowTime());
//        System.out.println(compareTime("2012-11-30 02:30:16",
//                "2012-11-30 16:18:18"));
        System.out.println(getExpireTime());
    }
}
