package X_X.service.timeline;

import X_X.db.timeline.PostContentDB;
import X_X.model.timeline.PostContent;
import X_X.netUtil.Request;
import X_X.netUtil.Response;
import X_X.view.*;
import net.sf.json.JSONObject;

@Logic(action = LogicAction.PUSH_CONTENT,
params = {"content", "createTime", "title", "token"},
paramsType = {ParamsType.String, ParamsType.String,
        ParamsType.String, ParamsType.String},
paramsCanNull = {false, false, false, false})
public class PushContentLogic implements ILogic {
    @Override
    public void done(Request request, Response response){
        JSONObject data = JSONObject.fromObject(request.getData());
        String content = (String)data.get("content");
        String createTime = (String)data.get("createTime");
        String title = (String)data.get("title");
        String token = (String)data.get("token");
        if(content != null && createTime != null &&
                title != null && token != null){
            String authorId = OnLineUsers.getInstance().get(token);
            PostContent postContent = new PostContent();
            postContent.setContent(content);
            postContent.setmCreateTime(createTime);
            postContent.setTitle(title);
            postContent.setmAuthorId(authorId);
            boolean isSuccess = PostContentDB.add(postContent);
            if(isSuccess) {
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
            response.setMessage("\"request is wrong!\"");
        }
        response.sendResponse();
    }

    public static void main(String[] args){
        OnLineUsers.getInstance().put("123456", "5");
        PushContentLogic pushContentLogic = new PushContentLogic();
        Request request = new Request();
        JSONObject data = new JSONObject();
        data.put("content", "I like winter!");
        data.put("title", "what season do you like?");
        data.put("token", "123456");
        data.put("createTime", "2018-12-27");
        request.setData(data.toString());
//        System.out.println(pushContentLogic.done(request));
    }
}
