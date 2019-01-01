package X_X.db.timeline;

import X_X.model.timeline.PostContent;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import java.io.*;

public class PostContentDB {
    private static final String FILE_PATH =
            "C:\\JavaWorkspace\\X_X\\src\\X_X\\db\\timeline\\postContent.xls";
    private static final String FIRST_SHEET = "postContents";
    private static int sId = 1;

    public static interface POSTCONTENT_INFO{
        public static final int ID = 0;
        public static final int TITLE = 1;
        public static final int CONTENT = 2;
        public static final int CREATE_TIME = 3;
        public static final int AUTHORID = 4;
    }

    private static void initId(){
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(FILE_PATH));
            HSSFWorkbook wb = new HSSFWorkbook(in);
            sId = wb.getSheet(FIRST_SHEET).getLastRowNum() + 1;
            wb.close();
        }catch(IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (in != null) {
                    in.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void initPostContentDB(){
        BufferedOutputStream out = null;
        try{
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet(FIRST_SHEET);
            HSSFRow row = sheet.createRow(0);
            HSSFCell cell;
            cell = row.createCell(POSTCONTENT_INFO.ID);
            cell.setCellValue("id");
            cell = row.createCell(POSTCONTENT_INFO.TITLE);
            cell.setCellValue("title");
            cell = row.createCell(POSTCONTENT_INFO.CONTENT);
            cell.setCellValue("content");
            cell = row.createCell(POSTCONTENT_INFO.CREATE_TIME);
            cell.setCellValue("createTime");
            cell = row.createCell(POSTCONTENT_INFO.AUTHORID);
            cell.setCellValue("authorId");
            out = new BufferedOutputStream(new FileOutputStream(FILE_PATH));
            wb.write(out);
            out.flush();
        }catch(IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (out != null) {
                    out.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static boolean add(PostContent postContent){
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(FILE_PATH));
            HSSFWorkbook wb = new HSSFWorkbook(in);
            HSSFSheet sheet = wb.getSheet(FIRST_SHEET);
            HSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
            initId();
            row.createCell(POSTCONTENT_INFO.ID)
                    .setCellValue(String.valueOf(sId));
            row.getCell(POSTCONTENT_INFO.ID).setCellType(CellType.STRING);
            row.createCell(POSTCONTENT_INFO.TITLE)
                    .setCellValue(postContent.getTitle());
            row.getCell(POSTCONTENT_INFO.TITLE).setCellType(CellType.STRING);
            row.createCell(POSTCONTENT_INFO.CONTENT)
                    .setCellValue(postContent.getContent());
            row.getCell(POSTCONTENT_INFO.CONTENT).setCellType(CellType.STRING);
            row.createCell(POSTCONTENT_INFO.CREATE_TIME)
                    .setCellValue(postContent.getCreateTime());
            row.getCell(POSTCONTENT_INFO.CREATE_TIME).setCellType(CellType.STRING);
            row.createCell(POSTCONTENT_INFO.AUTHORID)
                    .setCellValue(postContent.getAuthorId());
            row.getCell(POSTCONTENT_INFO.AUTHORID).setCellType(CellType.STRING);
            out = new BufferedOutputStream(new FileOutputStream(FILE_PATH));
            wb.write(out);
            wb.close();
            sId ++;
            return true;
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public static String queryAllContents(){
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(FILE_PATH));
            HSSFWorkbook wb = new HSSFWorkbook(in);
            HSSFSheet sheet = wb.getSheet(FIRST_SHEET);
            StringBuilder sb = new StringBuilder();
            int num = sheet.getLastRowNum();
            for(int i = 1 ; i <= num ; i ++) {
                HSSFRow row = sheet.getRow(i);
                try {
                    sb.append("||  帖子编号：" +
                            row.getCell(POSTCONTENT_INFO.ID));
                    sb.append("\t||  帖子题目：");
                    sb.append(row.getCell(POSTCONTENT_INFO.TITLE));
                    sb.append("\t||  正文：");
                    sb.append(row.getCell(POSTCONTENT_INFO.CONTENT));
                    sb.append("\t||  创建时间：");
                    sb.append(row.getCell(POSTCONTENT_INFO.CREATE_TIME));
                    sb.append("\t||  作者编号：");
                    sb.append(row.getCell(POSTCONTENT_INFO.AUTHORID));
                    sb.append("\t------------------------------\t");
                }catch (Exception e){
                    System.out.println("有一条空记录！");
                }
            }
            wb.close();
            return sb.toString();
        }catch(IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (in != null) {
                    in.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public static boolean deleteContent(String contentId){
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(FILE_PATH));
            HSSFWorkbook wb = new HSSFWorkbook(in);
            HSSFSheet sheet = wb.getSheet(FIRST_SHEET);
            int numId = Integer.valueOf(contentId);
            HSSFRow row = sheet.getRow(numId);
            sheet.removeRow(row);
            out = new BufferedOutputStream(new FileOutputStream(FILE_PATH));
            wb.write(out);
            wb.close();
            return true;
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void main(String[] args){
        initPostContentDB();
//        PostContent postContent = new PostContent();
//        postContent.setTitle("third title");
//        postContent.setmCreateTime("2019-12-25");
//        postContent.setContent("third content");
//        postContent.setmAuthorId("3");
//        System.out.println(add(postContent));
//        System.out.println(queryAllContents());
//        System.out.println(deleteContent("1"));
    }
}
