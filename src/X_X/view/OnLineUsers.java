package X_X.view;

import java.util.HashMap;

public class OnLineUsers {
    private HashMap<String, String> mOnLineUsers = new HashMap<>();

    private static class InstanceHolder{
        public static OnLineUsers sInstance = new OnLineUsers();
    }

    public static OnLineUsers getInstance(){
        return InstanceHolder.sInstance;
    }

    private OnLineUsers(){

    }

    public void put(String token, String userId){
        mOnLineUsers.put(token, userId);
    }

    public void remove(String token){
        mOnLineUsers.remove(token);
    }

    public boolean containsKey(String token){
        return mOnLineUsers.containsKey(token);
    }

    public String get(String token){
        return mOnLineUsers.getOrDefault(token, null);
    }
}
