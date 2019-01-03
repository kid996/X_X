package X_X.service.user;

import X_X.db.user.UserDB;
import X_X.model.user.User;
import X_X.netUtil.Request;
import X_X.netUtil.Response;
import X_X.view.ILogic;
import X_X.view.Logic;
import X_X.view.ParamsType;
import X_X.view.LogicAction;
import net.sf.json.JSONObject;

import java.util.HashMap;

@Logic(action = LogicAction.USER_INFO,
params = {"phone"},
paramsType = {ParamsType.String},
paramsCanNull = {false})
public class UserInfoLogic implements ILogic {
    @Override
    public void done(Request request, Response response){
        JSONObject data = JSONObject.fromObject(request.getData());
        String phone = String.valueOf(data.get("phone"));
        if(phone != null) {
            User user = UserDB.queryUserInfoByPhone(phone);
            if (user != null) {
                HashMap<String, Object> dataRes = new HashMap<>();
                response.setCode(Response.Status.SUCCESS);
                dataRes.put("age", "\"" + user.getAge() + "\"");
                dataRes.put("name", "\"" + user.getName() + "\"");
                dataRes.put("phone", "\"" + user.getPhone() + "\"");
                dataRes.put("sex", "\"" + user.getSex() + "\"");
                response.setData(dataRes);
                response.setMessage("\"\"");
            } else {
                response.setCode(Response.Status.FAILED);
                response.setData("\"\"");
                response.setMessage("\"the user is not exist !\"");
            }
        }else{
            response.setCode(Response.Status.FAILED);
            response.setData("\"\"");
            response.setMessage("\"request is wrong!\"");
        }
        response.sendResponse();
    }

    public static void main(String[] args){
        UserInfoLogic userInfoLogic = new UserInfoLogic();
        Request request = new Request();
        JSONObject data = new JSONObject();
        data.put("pone", "1884544703");
        request.setData(data.toString());
//        System.out.println(userInfoLogic.done(request));
    }
}
