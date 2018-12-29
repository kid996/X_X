package X_X.db.timeline;

import X_X.model.timeline.PostComment;

public class PostCommentDB {
    private static final String FILE_PATH =
            "C:\\JavaWorkspace\\X_X\\src\\X_X\\db\\timeline\\postComment.xsl";
    private static final String FIRST_SHEET = "postComments";
    private static int sId = 1;

    public static interface POSTCOMMENT_INFO{
        public static int COMMENT_ID = 0;
        public static int CONTENT_ID = 1;
        public static int COMMENT = 2;
        public static int CREATE_TIME = 3;
        public static int AUTHOR_ID = 4;
    }

    private void initPostCommentDB(){

    }

    public static String queryComment(String contentId){
        return null;
    }

    public static boolean add(PostComment postComment){
        return false;
    }
}
