package X_X.service.timeline;

import X_X.db.timeline.PostCommentDB;
import X_X.model.timeline.PostComment;
import X_X.netUtil.Request;
import X_X.netUtil.Response;
import X_X.view.ILogic;
import X_X.view.Logic;
import X_X.view.ParamsType;
import X_X.view.LogicAction;
import X_X.view.OnLineUsers;
import net.sf.json.JSONObject;

@Logic(action = LogicAction.PUSH_COMMENT,
params = {"token", "comment", "contentId", "createTime"},
paramsType = {ParamsType.String, ParamsType.String,
        ParamsType.String, ParamsType.String},
paramsCanNull = {false, false,false, false, false})
public class PushCommentLogic implements ILogic {
    @Override
    public void done(Request request, Response response){
        JSONObject data = JSONObject.fromObject(request.getData());
        String token = (String)data.get("token");
        String comment = (String)data.get("comment");
        String contentId = (String)data.get("contentId");
        String createTime = (String)data.get("createTime");
        if(token != null && comment != null && contentId != null
                && createTime != null){
            String userId = OnLineUsers.getInstance().get(token);
            PostComment postComment = new PostComment();
            postComment.setComment(comment);
            postComment.setContentId(contentId);
            postComment.setCreateTime(createTime);
            postComment.setAuthorId(userId);
            boolean isSuccess = PostCommentDB.add(postComment);
            if(isSuccess){
                response.setCode(Response.Status.SUCCESS);
                response.setData("\"\"");
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
        response.sendResponse();
    }

    public static void main(String[] args){
        PushCommentLogic p = new PushCommentLogic();
        OnLineUsers.getInstance().put("123", "2");
        Request request = new Request();
        JSONObject data = new JSONObject();
        data.put("token", "123");
        data.put("comment", "new year");
        data.put("contentId", "1");
        data.put("createTime", "2019-12-30");
        request.setData(data.toString());
//        System.out.println(p.done(request));
    }
}
