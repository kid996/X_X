package X_X.service.user;

import X_X.db.user.UserDB;
import X_X.model.user.User;
import X_X.netUtil.Request;
import X_X.netUtil.Response;
import X_X.view.ILogic;
import X_X.view.Logic;
import X_X.view.ParamsType;
import X_X.view.LogicAction;
import X_X.view.OnLineUsers;
import net.sf.json.JSONObject;

import java.util.HashMap;

@Logic(action = LogicAction.USER_HOME,
params = {"token"},
paramsType = ParamsType.String,
paramsCanNull = {false})
public class UserHomeLogic implements ILogic {
    @Override
    public void done(Request request, Response response){
        JSONObject data = JSONObject.fromObject(request.getData());
        if(data.has("token")) {
            String token = (String) data.get("token");
            if (OnLineUsers.getInstance().containsKey(token)) {
                String userId = OnLineUsers.getInstance().get(token);
                User user = UserDB.queryUserInfoById(userId);
                if(user != null){
                    HashMap<String, Object> dataRes = new HashMap<>();
                    dataRes.put("name", "\"" + user.getName() + "\"");
                    dataRes.put("sex", "\"" + user.getSex() + "\"");
                    dataRes.put("age", "\"" + user.getAge() + "\"");
                    dataRes.put("phone", "\"" + user.getPhone() + "\"");
                    response.setCode(Response.Status.SUCCESS);
                    response.setData(dataRes);
                    response.setMessage("\"\"");
                }else{
                    response.setCode(Response.Status.FAILED);
                    response.setData("\"\"");
                    response.setMessage("\"UserDB is wrong!\"");
                }
            } else {
                response.setCode(Response.Status.FAILED);
                response.setData("\"\"");
                response.setMessage("\"token is wrong!\"");
            }
        }else{
            response.setCode(Response.Status.FAILED);
            response.setData("\"\"");
            response.setMessage("\"request is wrong!\"");
        }
        response.sendResponse();
    }

    public static void main(String[] args){
        OnLineUsers.getInstance().put("123456", "5");
        UserHomeLogic userHomeLogic = new UserHomeLogic();
        Request request = new Request();
        JSONObject data = new JSONObject();
        data.put("token", "123456");
        request.setData(data.toString());
//        Response response = userHomeLogic.done(request);
//        System.out.println(response);
    }
}
