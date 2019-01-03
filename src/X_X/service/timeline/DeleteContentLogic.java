package X_X.service.timeline;

import X_X.db.timeline.PostContentDB;
import X_X.netUtil.Request;
import X_X.netUtil.Response;
import X_X.view.ILogic;
import X_X.view.OnLineUsers;
import net.sf.json.JSONObject;

public class DeleteContentLogic implements ILogic {
    @Override
    public void done(Request request, Response response){
        JSONObject data = JSONObject.fromObject(request.getData());
        String token = (String)data.get("token");
        String contentId = (String)data.get("contentId");
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
    }
}
