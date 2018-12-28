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

import java.util.HashMap;

@Logic(action = LogicAction.BROWSE_CONTENTS,
params = {"token"},
paramsType = {ParamsType.String},
paramsCanNull = {false})
public class BrowseContentsLogic implements ILogic {
    @Override
    public Response done(Request request){
        Response response = new Response();
        JSONObject data = JSONObject.fromObject(request.getData());
        String token = (String)data.getOrDefault("token", null);
        if(token != null){
            if(OnLineUsers.getInstance().containsKey(token)){
                String postContents = PostContentDB.queryAllContents();
                if(postContents != null){
                    HashMap<String, Object> dataRes = new HashMap<>();
                    dataRes.put("postContents", "\"" + postContents + "\"");
                    response.setData(Response.Status.SUCCESS);
                    response.setData(dataRes);
                    response.setMessage("\"\"");
                }else{
                    response.setCode(Response.Status.FAILED);
                    response.setData("\"\"");
                    response.setMessage("\"PostContentDB is wrong!\"");
                }
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
        BrowseContentsLogic browseContentsLogic = new BrowseContentsLogic();
        Request request = new Request();
        JSONObject data = new JSONObject();
        data.put("token", "123456");
        request.setData(data.toString());
        System.out.println(browseContentsLogic.done(request));
    }
}
