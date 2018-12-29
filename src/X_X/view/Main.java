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
            public Response onRequest(Request request){
                return main.dealRequest(request);
            }
        });
    }

    public Response dealRequest(Request request){
        //得到userLogin这个类的class
        Class<? extends ILogic> cls =
                LogicRouter.getInstance().getLogic(request);
        try {
            Constructor con = cls.getDeclaredConstructor();
            //得到userLogin这个类的实例
            Object logic = con.newInstance();
            //得到这个类的done方法
            Method method = cls.getMethod("done", Request.class);
            //给方法传参
            Response response = (Response)method.invoke(logic, request);
            return response;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}

