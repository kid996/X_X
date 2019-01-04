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

@Logic(action = LogicAction.USER_LOGIN,
params = {"phone", "pwd"},
paramsType = {ParamsType.String, ParamsType.String},
paramsCanNull = {false, false})
public class UserLoginLogic implements ILogic {
    @Override
    public void done(Request request, Response response){
        JSONObject data = JSONObject.fromObject(request.getData());
        String phone = data.getString("phone");
        String pwd = data.getString("pwd");
        User user = UserDB.queryUserInfoByPhone(phone);
        if(user == null) {
            response.setCode(Response.Status.FAILED);
            response.setMessage("\"user isn't exist!\"");
            response.setData("\"\"");
        }else if(user.getPwd().equals(pwd)) {
            String newToken = ServiceTools.createToken();
            HashMap<String, Object> dataRes = new HashMap<>();
            String userId = UserDB.getUserIdByPhone(phone);
            if(UserDB.updataToken(userId, newToken)) {
                String expireTime = ServiceTools.getExpireTime();
                if(UserDB.updataExpireTime(userId, expireTime)) {
                    String name = UserDB.getUserNameById(userId);
                    dataRes.put("token", "\"" + newToken + "\"");
                    dataRes.put("name", "\"" + name + "\"");
                    response.setCode(Response.Status.SUCCESS);
                    response.setMessage("\"\"");
                    response.setData(dataRes);
                    //将用户放入缓存
                    OnLineUsers.getInstance().put(newToken, userId);
                }else{
                    response.setCode(Response.Status.FAILED);
                    response.setMessage("\"updata ExpireTime failed!\"");
                    response.setData("\"\"");
                }
            }else{
                response.setCode(Response.Status.FAILED);
                response.setMessage("\"updata token failed!\"");
                response.setData("\"\"");
            }
        }else {
            response.setCode(Response.Status.FAILED);
            response.setMessage("\"phone and pwd doesn't match!\"");
            response.setData("\"\"");
        }
        //TODO 这样好蠢
        response.sendResponse();
    }

    public static void main(String[] args){
        UserLoginLogic userLoginLogic = new UserLoginLogic();
        Request request = new Request();
        JSONObject data = new JSONObject();
        data.put("phone", "18844544703");
        data.put("pwd", "t13");
        request.setData(data.toString());
//        System.out.println(userLoginLogic.done(request));
    }
}
