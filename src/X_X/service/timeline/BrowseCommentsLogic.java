package X_X.service.timeline;

import X_X.db.timeline.PostCommentDB;
import X_X.netUtil.Request;
import X_X.netUtil.Response;
import X_X.view.ILogic;
import X_X.view.Logic;
import X_X.view.ParamsType;
import X_X.view.LogicAction;
import X_X.view.OnLineUsers;
import net.sf.json.JSONObject;

import java.util.HashMap;

@Logic(action = LogicAction.BROWSE_COMMENTS,
params = {"token", "contentId"},
paramsType = {ParamsType.String, ParamsType.String},
paramsCanNull = {false, false})
public class BrowseCommentsLogic implements ILogic {
    @Override
    public Response done(Request request){
        Response response = new Response();
        JSONObject data = JSONObject.fromObject(request.getData());
        String token = (String)data.getOrDefault("token", null);
        String contentId = (String)data.getOrDefault("contentId", null);
        if(token != null && contentId != null){
            if(OnLineUsers.getInstance().containsKey(token)){
                String comments = PostCommentDB.queryComment(contentId);
                if(comments != null){
                    HashMap<String, Object> dataRes = new HashMap<>();
                    dataRes.put("comments", "\"" + comments + "\"");
                    response.setCode(Response.Status.SUCCESS);
                    response.setData(dataRes);
                    response.setMessage("\"\"");
                }else{
                    response.setCode(Response.Status.FAILED);
                    response.setData("\"\"");
                    response.setMessage("\"PostCommentDB is wrong!\"");
                }
            }else{
                response.setCode(Response.Status.FAILED);
                response.setData("\"\"");
                response.setMessage("\"request is wrong!\"");
            }
        }
        return response;
    }
}
