package X_X.db.timeline;

import X_X.model.timeline.PostComment;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class PostCommentDB {
    private static final String FILE_PATH =
            "C:\\JavaWorkspace\\X_X\\src\\X_X\\db\\timeline\\postComment.xls";
    private static final String FIRST_SHEET = "postComments";
    private static int sId = 1;

    public static interface POSTCOMMENT_INFO{
        public static int COMMENT_ID = 0;
        public static int COMMENT = 1;
        public static int CREATE_TIME = 2;
        public static int AUTHOR_ID = 3;
    }

    private static void initPostCommentDB(){
        try {
            HSSFWorkbook wb = new HSSFWorkbook();
            wb.createSheet("README");
            FileOutputStream fos = new FileOutputStream(FILE_PATH);
            wb.write(fos);
            fos.flush();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static String queryComment(String contentId){
        try {
            FileInputStream fis = new FileInputStream(FILE_PATH);
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            StringBuilder sb = new StringBuilder();
            if (wb.getSheet(contentId) != null){
                HSSFSheet sheet = wb.getSheet(contentId);
                int num = sheet.getLastRowNum();
                for(int i = 1 ; i <= num ; i ++){
                    HSSFRow row = sheet.getRow(i);
                    sb.append("|| 评论编号：");
                    sb.append(row.getCell(POSTCOMMENT_INFO.COMMENT_ID));
                    sb.append("\t|| 评论内容：");
                    sb.append(row.getCell(POSTCOMMENT_INFO.COMMENT));
                    sb.append("\t|| 作者编号：");
                    sb.append(row.getCell(POSTCOMMENT_INFO.AUTHOR_ID));
                    sb.append("\t|| 发表时间：");
                    sb.append(row.getCell(POSTCOMMENT_INFO.CREATE_TIME));
                    sb.append("\t------------------------------\t");
                }
                wb.close();
                return sb.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static boolean add(PostComment postComment){
        try {
            String contentId = postComment.getContentId();
            FileInputStream fis = new FileInputStream(FILE_PATH);
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            if(wb.getSheet(contentId) == null){
                HSSFSheet sheet = wb.createSheet(contentId);
                HSSFRow row = sheet.createRow(0);
                int commentId = initId(sheet);
                row.createCell(POSTCOMMENT_INFO.COMMENT_ID).
                        setCellValue("id");
                row.createCell(POSTCOMMENT_INFO.COMMENT).
                        setCellValue("comment");
                row.createCell(POSTCOMMENT_INFO.CREATE_TIME).
                        setCellValue("createTime");
                row.createCell(POSTCOMMENT_INFO.AUTHOR_ID).
                        setCellValue("authorId");

                HSSFRow contentRow = sheet.createRow(commentId);
                contentRow.createCell(POSTCOMMENT_INFO.COMMENT_ID).
                        setCellValue(commentId);
                contentRow.createCell(POSTCOMMENT_INFO.COMMENT).
                        setCellValue(postComment.getComment());
                contentRow.createCell(POSTCOMMENT_INFO.CREATE_TIME).
                        setCellValue(postComment.getCreateTime());
                contentRow.createCell(POSTCOMMENT_INFO.AUTHOR_ID).
                        setCellValue(postComment.getAuthorId());

                contentRow.getCell(POSTCOMMENT_INFO.COMMENT_ID).
                        setCellType(CellType.STRING);
                contentRow.getCell(POSTCOMMENT_INFO.COMMENT).
                        setCellType(CellType.STRING);
                contentRow.getCell(POSTCOMMENT_INFO.CREATE_TIME).
                        setCellType(CellType.STRING);
                contentRow.getCell(POSTCOMMENT_INFO.AUTHOR_ID).
                        setCellType(CellType.STRING);
            }else{
                HSSFSheet sheet = wb.getSheet(contentId);
                int commentId = initId(sheet);
                HSSFRow contentRow = sheet.createRow(commentId);
                contentRow.createCell(POSTCOMMENT_INFO.COMMENT_ID).
                        setCellValue(commentId);
                contentRow.createCell(POSTCOMMENT_INFO.COMMENT).
                        setCellValue(postComment.getComment());
                contentRow.createCell(POSTCOMMENT_INFO.CREATE_TIME).
                        setCellValue(postComment.getCreateTime());
                contentRow.createCell(POSTCOMMENT_INFO.AUTHOR_ID).
                        setCellValue(postComment.getAuthorId());

                contentRow.getCell(POSTCOMMENT_INFO.COMMENT_ID).
                        setCellType(CellType.STRING);
                contentRow.getCell(POSTCOMMENT_INFO.COMMENT).
                        setCellType(CellType.STRING);
                contentRow.getCell(POSTCOMMENT_INFO.CREATE_TIME).
                        setCellType(CellType.STRING);
                contentRow.getCell(POSTCOMMENT_INFO.AUTHOR_ID).
                        setCellType(CellType.STRING);
            }
            FileOutputStream fos = new FileOutputStream(FILE_PATH);
            wb.write(fos);
            wb.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    private static int initId(HSSFSheet sheet){
        return sheet.getLastRowNum() + 1;
    }

    public static void main(String[] args){
//        initPostCommentDB();
//        PostComment postComment = new PostComment();
//        postComment.setContentId("3");
//        postComment.setAuthorId("2");
//        postComment.setCreateTime("2018-12-29");
//        postComment.setComment("snow");
//        add(postComment);
        System.out.println(queryComment("2"));
    }
}
