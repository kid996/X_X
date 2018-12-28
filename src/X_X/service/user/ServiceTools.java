package X_X.service.user;

import java.util.Random;


public class ServiceTools {
    public static String createToken() {
        long ct = System.currentTimeMillis();
        Random random = new Random();
        long rd = random.nextInt();
        String token = String.valueOf(ct) + rd;
        return token;
    }
}
