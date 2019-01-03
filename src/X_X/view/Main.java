package X_X.view;

import X_X.netUtil.Request;
import X_X.netUtil.Response;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args){
        final Main main = new Main();
        Server.getInstance().start(new Server.CallBack() {
            @Override
            public void onRequest(Request request, Response response){
                main.dealRequest(request, response);
            }
        });
    }

    public void dealRequest(Request request, Response response){
        //得到class
        Class<? extends ILogic> cls =
                LogicRouter.getInstance().getLogic(request);
        try {
            Constructor con = cls.getDeclaredConstructor();
            //得到userLogin这个类的实例
            ILogic logic = (ILogic)con.newInstance();
            //得到这个类的done方法
            Method method = cls.getMethod("done", Request.class, Response.class);
            //给方法传参
            //?传入的logic方法会对response进行处理，然后在方法中发送请求？
            method.invoke(logic, request, response);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}

