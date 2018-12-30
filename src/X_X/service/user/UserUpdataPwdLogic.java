package X_X.service.user;

import X_X.db.user.UserDB;
import X_X.netUtil.Request;
import X_X.netUtil.Response;
import X_X.view.ILogic;
import X_X.view.Logic;
import X_X.view.ParamsType;
import X_X.view.LogicAction;
import X_X.view.OnLineUsers;
import net.sf.json.JSONObject;

import java.util.HashMap;

@Logic(action = LogicAction.USER_UPDATE_NAME,
params = {"token", "newPwd"},
paramsType = {ParamsType.String, ParamsType.String},
paramsCanNull = {false, false})
public class UserUpdataPwdLogic implements ILogic {
    @Override
    public Response done(Request request){
        Response response = new Response();
        JSONObject data = JSONObject.fromObject(request.getData());
        String newPwd = (String)data.get("newPwd");
        String token = (String)data.get("token");
        if(newPwd != null && token != null){
            if(OnLineUsers.getInstance().containsKey(token)){
                String userId = OnLineUsers.getInstance().get(token);
                boolean isSuccess = UserDB.updatePwd(userId, newPwd);
                if(isSuccess){
                    //更新token
                    String newToken = ServiceTools.createToken();
                    OnLineUsers.getInstance().remove(token);
                    OnLineUsers.getInstance().put(newToken, userId);
                    if(UserDB.updataToken(userId, newToken)) {
                        response.setCode(Response.Status.SUCCESS);
                        HashMap<String, Object> dataRes = new HashMap<>();
                        dataRes.put("token", "\"" + newToken + "\"");
                        response.setData(dataRes);
                        response.setMessage("\"\"");
                    }else{
                        response.setCode(Response.Status.FAILED);
                        response.setData("\"\"");
                        response.setMessage("\"updata token failed!\"");
                    }
                }else{
                    response.setCode(Response.Status.FAILED);
                    response.setData("\"\"");
                    response.setMessage("\"UserDB is wrong!\"");
                }
            }else{
                response.setCode(Response.Status.FAILED);
                response.setData("\"\"");
                response.setMessage("\"request is wrong!\"");
            }
        }
        return response;
    }

    public static void main(String[] args){
        OnLineUsers.getInstance().put("123456", "5");
        UserUpdataPwdLogic userUpdataPwdLogic = new UserUpdataPwdLogic();
        Request request = new Request();
        JSONObject data = new JSONObject();
        data.put("token", "123456");
        data.put("newPwd", "test123");
        request.setData(data.toString());
        System.out.println(userUpdataPwdLogic.done(request));
    }
}
