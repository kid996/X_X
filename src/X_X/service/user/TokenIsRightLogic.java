package X_X.service.user;

import X_X.db.user.UserDB;
import X_X.model.user.User;
import X_X.netUtil.Request;
import X_X.netUtil.Response;
import X_X.view.*;
import net.sf.json.JSONObject;

import java.util.HashMap;

@Logic(action = LogicAction.TOKEN_IS_RIGHT,
params = {"token"},
paramsType = {ParamsType.String},
paramsCanNull = {false})
public class TokenIsRightLogic implements ILogic {

    @Override
    public Response done(Request request){
        Response response = new Response();
        JSONObject data = JSONObject.fromObject(request.getData());
        String token = (String)data.get("token");
        if(token != null){
            String expireTime = UserDB.getExpireTime(token);
            String nowTime = ServiceTools.getNowTime();
            if(expireTime != null) {
                if (ServiceTools.compareTime(nowTime, expireTime)) {
                    String id = UserDB.getUserIdByToken(token);
                    User user = UserDB.queryUserInfoById(id);
                    if(user != null) {
                        HashMap<String, Object> dataRes = new HashMap<>();
                        dataRes.put("name", "\"" + user.getName() + "\"");
                        dataRes.put("sex", "\"" + user.getSex() + "\"");
                        dataRes.put("age", "\"" + user.getAge() + "\"");
                        dataRes.put("phone", "\"" + user.getPhone() + "\"");
                        response.setCode(Response.Status.SUCCESS);
                        response.setData(dataRes);
                        response.setMessage("\"\"");
                        OnLineUsers.getInstance().put(token, id);
                    }else{
                        response.setCode(Response.Status.FAILED);
                        response.setMessage("\"userDB is wrong!\"");
                        response.setData("\"\"");
                    }
                }else{
                    response.setCode(Response.Status.FAILED);
                    response.setData("\"\"");
                    response.setMessage("\"token is out of date!\"");
                }
            }else{
                response.setCode(Response.Status.FAILED);
                response.setData("\"\"");
                response.setMessage("\"expireTime is null\"");
            }
        }else{
            response.setCode(Response.Status.FAILED);
            response.setData("\"\"");
            response.setMessage("\"request is wrong!\"");
        }
        return response;
    }
}
