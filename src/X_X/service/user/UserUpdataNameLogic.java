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

@Logic(action = LogicAction.USER_UPDATE_NAME,
params = {"token", "newName"},
paramsType = {ParamsType.String, ParamsType.String},
paramsCanNull = {false, false})
public class UserUpdataNameLogic implements ILogic {
    @Override
    public void done(Request request, Response response){
        JSONObject data = JSONObject.fromObject(request.getData());
        if(data.has("token")&&data.has("newName")) {
            String token = (String) data.get("token");
            String newName = (String) data.get("newName");
            if(OnLineUsers.getInstance().containsKey(token)){
                String userId = OnLineUsers.getInstance().get(token);
                System.out.println(OnLineUsers.getInstance().get(token));
                boolean isSuccess = UserDB.updateName(userId, newName);
                if(isSuccess){
                    response.setCode(Response.Status.SUCCESS);
                    response.setData("\"\"");
                    response.setMessage("\"\"");
                }else{
                    response.setCode(Response.Status.FAILED);
                    response.setData("\"\"");
                    response.setMessage("\"UserDB is wrong!\"");
                }
            }else{
                response.setCode(Response.Status.FAILED);
                response.setData("\"\"");
                response.setMessage("\"User isn't login!\"");
            }
        }else{
            response.setCode(Response.Status.FAILED);
            response.setData("\"\"");
            response.setMessage("\"request is wrong!\"");
        }
        response.sendResponse(response);
    }

    public static void main(String[] args){
        OnLineUsers.getInstance().put("123456", "18844544703");
        UserUpdataNameLogic userUpdataNameLogic = new UserUpdataNameLogic();
        Request request = new Request();
        JSONObject data = new JSONObject();
        data.put("token", "123456");
        data.put("newName", "test");
        request.setData(data.toString());
//        System.out.println(userUpdataNameLogic.done(request));
    }
}
