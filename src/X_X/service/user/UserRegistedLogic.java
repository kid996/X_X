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
@Logic(action = LogicAction.USER_UPDATE_PWD,
params = {"name", "phone", "pwd", "age", "sex"},
paramsType = {ParamsType.String, ParamsType.String,
        ParamsType.String, ParamsType.String, ParamsType.String},
paramsCanNull = {false, false, false, false, false})
public class UserRegistedLogic implements ILogic {
    @Override
    public void done(Request request, Response response){
        JSONObject data = JSONObject.fromObject(request.getData());
        if(data.has("name") && data.has("phone")
                && data.has("pwd") && data.has("age")
                && data.has("sex")){
            String token = ServiceTools.createToken();
            User user = new User();
            user.setAge((String)data.get("age"));
            user.setName((String)data.get("name"));
            user.setPhone((String)data.get("phone"));
            user.setPwd((String)data.get("pwd"));
            user.setSex((String)data.get("sex"));
            user.setToken(token);
            boolean isSuccess = UserDB.addUser(user);
            if(isSuccess){
                String expireTime = ServiceTools.getExpireTime();
                String userId = UserDB.
                        getUserIdByPhone((String)data.get("phone"));
                if(UserDB.updataExpireTime(userId, expireTime)) {
                    HashMap<String, Object> dataRes = new HashMap<>();
                    dataRes.put("token", "\"" + token + "\"");
                    response.setCode(Response.Status.SUCCESS);
                    response.setData(dataRes);
                    response.setMessage("\"\"");
                    //将用户放入缓存
                    OnLineUsers.getInstance().put(token, userId);
                }else{
                    response.setCode(Response.Status.FAILED);
                    response.setData("\"\"");
                    response.setMessage("\"updata expireTime failed!\"");
                }
            }else{
                response.setCode(Response.Status.FAILED);
                response.setData("\"\"");
                response.setMessage("\"UserDB failed!\"");
            }
        }else{
            response.setCode(Response.Status.FAILED);
            response.setData("\"\"");
            response.setMessage("\"request is wrong!\"");
        }
        response.sendResponse(response);
    }

    public static void main(String[] args){
        UserRegistedLogic userRegistedLogic = new UserRegistedLogic();
        Request request = new Request();
        JSONObject data = new JSONObject();
        data.put("name", "r");
        data.put("sex", "man");
        data.put("age", "22");
        data.put("pwd", "r123");
        data.put("phone", "18844544321");
        request.setData(data.toString());
//        System.out.println(userRegistedLogic.done(request));
    }
}
