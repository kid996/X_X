package X_X.service.user;

import X_X.netUtil.Request;
import X_X.netUtil.Response;
import X_X.view.ILogic;
import X_X.view.Logic;
import X_X.view.ParamsType;
import X_X.view.LogicAction;
import X_X.view.OnLineUsers;
import net.sf.json.JSONObject;

@Logic(action = LogicAction.USER_LOGOUT,
params = {"token"},
paramsType = {ParamsType.String},
paramsCanNull = {false})
public class UserLogoutLogic implements ILogic {
    @Override
    public Response done(Request request){
        Response response = new Response();
        JSONObject data = JSONObject.fromObject(request.getData());
        String token = (String)data.get("token");
        if(token != null){
            if(OnLineUsers.getInstance().containsKey(token)){
                OnLineUsers.getInstance().remove(token);
                response.setCode(Response.Status.SUCCESS);
                response.setData("\"\"");
                response.setMessage("\"\"");
            }else{
                response.setCode(Response.Status.FAILED);
                response.setData("\"\"");
                response.setMessage("\"user isn't login!\"");
            }
        }else{
            response.setCode(Response.Status.FAILED);
            response.setData("\"\"");
            response.setMessage("\"request is wrong!\"");
        }
        return response;
    }

    public static void main(String[] args){
        OnLineUsers.getInstance().put("123456", "5");
        UserLogoutLogic userLogoutLogic = new UserLogoutLogic();
        Request request = new Request();
        JSONObject data = new JSONObject();
        data.put("tokn", "123456");
        request.setData(data.toString());
        System.out.println(userLogoutLogic.done(request));
    }
}
