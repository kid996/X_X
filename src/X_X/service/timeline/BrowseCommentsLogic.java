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
        String token = String.valueOf(data.get("token"));
        String contentId = String.valueOf(data.get("contentId"));
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
                response.setMessage("\"user is not login!\"");
            }
        }else {
            response.setCode(Response.Status.FAILED);
            response.setData("\"\"");
            response.setMessage("\"request is wrong!\"");
        }
        return response;
    }

    public static void main(String[] agrs){
        OnLineUsers.getInstance().put("123456", "2");
        Request request = new Request();
        JSONObject data = new JSONObject();
        data.put("token","123456");
        data.put("contentId", "1");
        request.setData(data.toString());
        BrowseCommentsLogic b = new BrowseCommentsLogic();
        System.out.println(b.done(request));
    }
}
