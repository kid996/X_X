package X_X.view;

public class T{
    private T(){

    }

    private static class InstanceHolder{
        private static T instance = new T();
    }
    public static T getInstance(){
        return InstanceHolder.instance;
    }
}