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
//        Request request = new Request();
//        request.setAction(String.valueOf(LogicAction.USER_INFO));
//        HashMap<String, Object> data = new HashMap<>();
//        data.put("phone", "18844544703");
//        request.setData(data.toString());
//        System.out.println(main.dealRequest(request));
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
//        String action = request.getAction();
//        Response response = null;
//        switch (Integer.valueOf(action)){
//            case LogicAction.USER_INFO:
//                response = ServiceTools.userInfo(request.getData());
//                break;
//            case LogicAction.USER_LOGIN:
//                response = ServiceTools.userLogin(request.getData());
//                break;
//            case LogicAction.USER_REGISTE:
//                response = ServiceTools.userRegiste(request.getData());
//                break;
//            case LogicAction.USER_UPDATE_NAME:
//                response = ServiceTools.userUpdateName(request.getData());
//                break;
//            case LogicAction.PUSH_CONTENT:
//                response = TimeLineService.PushContentLogic(request.getData());
//                break;
//            case LogicAction.BROWSE_CONTENTS:
//                response = TimeLineService.browseContents(request.getData());
//                break;
//            case LogicAction.USER_LOGOUT:
//                response = ServiceTools.userLogout(request.getData());
//                break;
//            case LogicAction.USER_UPDATE_PWD:
//                response = ServiceTools.userUpdatePwd(request.getData());
//                break;
//            case LogicAction.BROWSE_COMMENTS:
//                response = TimeLineService.browseComments(request.getData());
//                break;
//            case LogicAction.PUSH_COMMENT:
//                response = TimeLineService.pushComment(request.getData());
//                break;
//            case LogicAction.DELETE_CONTENT:
//                response = TimeLineService.deleteContent(request.getData());
//                break;
//            default:
//                break;
//        }

