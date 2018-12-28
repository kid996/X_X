package X_X.service.timeline;

import X_X.db.timeline.PostContentDB;
import X_X.netUtil.Request;
import X_X.netUtil.Response;
import X_X.view.ILogic;
import X_X.view.Logic;
import X_X.view.ParamsType;
import X_X.view.LogicAction;
import X_X.view.OnLineUsers;
import net.sf.json.JSONObject;

@Logic(action = LogicAction.DELETE_CONTENT,
params = {"token", "contentId"},
paramsType = {ParamsType.String, ParamsType.String},
paramsCanNull = {false, false})
public class DeleteContentLogic implements ILogic {
    @Override
    public Response done(Request request){
        Response response = new Response();
        JSONObject data = JSONObject.fromObject(request.getData());
        String token = (String)data.getOrDefault("token", null);
        String contentId = (String)data.getOrDefault("contentId", null);
        if(token != null && contentId != null){
            if(OnLineUsers.getInstance().containsKey(token)){
                boolean isSuccess = PostContentDB.deleteContent(contentId);
                if(isSuccess){
                    response.setCode(Response.Status.SUCCESS);
                    response.setData("\"\"");
                    response.setMessage("\"\"");
                }else{
                    response.setCode(Response.Status.FAILED);
                    response.setData("\"\"");
                    response.setMessage("\"PostContentDB is wrong!\"");
                }
            }else{
                response.setCode(Response.Status.FAILED);
                response.setData("\"\"");
                response.setMessage("\"\"");
            }
        }else{
            response.setCode(Response.Status.FAILED);
            response.setData("\"\"");
            response.setMessage("\"request is wrong!\"");
        }
        return response;
    }
}
