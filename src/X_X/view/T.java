package X_X.view;

public class T{
    public static void main(String[] args) throws Exception{
        Class c = Class.forName("X_X.model.user.User");
        Object user = c.newInstance();
        System.out.println(user);
    }
}