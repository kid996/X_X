package X_X.view;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class T{
    public static void main(String[] args) {
        HashMap<String, String> userContents = new HashMap<>();
        userContents.put("a", "1");
        userContents.put("c","3");
        userContents.put("asas", "asaq");
        userContents.put("aaa","33");
        System.out.println("before : " + userContents);
        for(Iterator<Map.Entry<String, String>> it = userContents.entrySet().iterator() ; it.hasNext();){
//            Map.Entry<String, String> item = it.next();
            it.remove();
        }
        System.out.println("after : " + userContents);
    }
}