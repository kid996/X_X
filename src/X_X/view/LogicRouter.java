package X_X.view;

import X_X.netUtil.Request;
import X_X.service.timeline.*;
import X_X.service.user.*;

import java.util.HashMap;

public class LogicRouter {
    private static LogicRouter sInstance = new LogicRouter();

    public static LogicRouter getInstance() {
        return sInstance;
    }

    private HashMap<Integer, Class<? extends ILogic>> mLogics;

    public LogicRouter() {
        mLogics = new HashMap<>();
        mLogics.put(LogicAction.USER_LOGIN, UserLoginLogic.class);
        mLogics.put(LogicAction.USER_INFO, UserInfoLogic.class);
        mLogics.put(LogicAction.USER_LOGOUT, UserLogoutLogic.class);
        mLogics.put(LogicAction.USER_REGISTED, UserRegistedLogic.class);
        mLogics.put(LogicAction.USER_UPDATE_NAME, UserUpdataNameLogic.class);
        mLogics.put(LogicAction.USER_UPDATE_PWD, UserUpdataPwdLogic.class);
        mLogics.put(LogicAction.USER_HOME, UserHomeLogic.class);
        mLogics.put(LogicAction.BROWSE_COMMENTS, BrowseCommentsLogic.class);
        mLogics.put(LogicAction.BROWSE_CONTENTS, BrowseContentsLogic.class);
        mLogics.put(LogicAction.DELETE_CONTENT, DeleteContentLogic.class);
        mLogics.put(LogicAction.PUSH_CONTENT, PushContentLogic.class);
        mLogics.put(LogicAction.PUSH_COMMENT, PushCommentLogic.class);
    }

    public Class<? extends ILogic> getLogic(Request request) {
        return mLogics.get(Integer.valueOf(request.getAction()));
    }
}