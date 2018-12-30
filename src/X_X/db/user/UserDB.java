package X_X.db.user;


import X_X.model.user.User;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class UserDB {
    private static final String FILE_PATH =
            "C:\\JavaWorkspace\\X_X\\src\\X_X\\db\\user\\user.xls";
    private static final String FIRST_SHEET = "users";
    private static int sId = 1;

    private static void initId(){
        try {
            FileInputStream fis = new FileInputStream(FILE_PATH);
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            sId = wb.getSheet(FIRST_SHEET).getLastRowNum() + 1;
            wb.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public interface USER_INFO{
        public static final int ID = 0;
        public static final int NAME = 1;
        public static final int PHONE = 2;
        public static final int PWD = 3;
        public static final int AGE = 4;
        public static final int SEX = 5;
        public static final int TOKEN = 6;
        public static final int EXPIRE_TIME = 7;
    }

    public static void initUserDB(){
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(FIRST_SHEET);
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        cell = row.createCell(USER_INFO.ID);
        cell.setCellValue("userId");
        cell = row.createCell(USER_INFO.NAME);
        cell.setCellValue("name");
        cell = row.createCell(USER_INFO.PHONE);
        cell.setCellValue("phone");
        cell = row.createCell(USER_INFO.PWD);
        cell.setCellValue("pwd");
        cell = row.createCell(USER_INFO.AGE);
        cell.setCellValue("age");
        cell = row.createCell(USER_INFO.SEX);
        cell.setCellValue("sex");
        cell = row.createCell(USER_INFO.TOKEN);
        cell.setCellValue("token");
        cell = row.createCell(USER_INFO.EXPIRE_TIME);
        cell.setCellValue("expireTime");

        try{
            FileOutputStream fos = new FileOutputStream(FILE_PATH);
            wb.write(fos);
            fos.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static User queryUserInfoById(String userId){
        try{
            User user = new User();
            FileInputStream fis = new FileInputStream(FILE_PATH);
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheet(FIRST_SHEET);
            HSSFRow row = sheet.getRow(Integer.valueOf(userId));
            user.setName(row.getCell(USER_INFO.NAME).getStringCellValue());
            user.setPhone(row.getCell(USER_INFO.PHONE).getStringCellValue());
            user.setSex(row.getCell(USER_INFO.SEX).getStringCellValue());
            user.setAge(row.getCell(USER_INFO.AGE).getStringCellValue());
            wb.close();
            return user;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static User queryUserInfoByPhone(String phone) {
        try {
            FileInputStream fis = new FileInputStream(FILE_PATH);
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheet(FIRST_SHEET);
            int rowNum = sheet.getLastRowNum();
            for(int i = 1; i <= rowNum ; i ++){
                HSSFRow temp_row = sheet.getRow(i);
                if(temp_row.getCell(USER_INFO.PHONE).
                        getStringCellValue().equals(phone)){
                    String name = temp_row.getCell(USER_INFO.NAME)
                            .getStringCellValue();
                    String age = temp_row.getCell(USER_INFO.AGE)
                            .getStringCellValue();
                    String sex = temp_row.getCell(USER_INFO.SEX)
                            .getStringCellValue();
                    String pwd = temp_row.getCell(USER_INFO.PWD)
                            .getStringCellValue();
                    User user = new User();
                    user.setSex(sex);
                    user.setPhone(phone);
                    user.setName(name);
                    user.setAge(age);
                    user.setPwd(pwd);
                    return user;
                }
            }
            wb.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static boolean addUser(User user) {
        try {
            FileInputStream fis = new FileInputStream(FILE_PATH);
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheet(FIRST_SHEET);
            HSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
            initId();
            row.createCell(USER_INFO.ID).setCellValue(sId);
            row.getCell(USER_INFO.ID).setCellType(CellType.STRING);
            row.createCell(USER_INFO.NAME).setCellValue(user.getName());
            row.getCell(USER_INFO.NAME).setCellType(CellType.STRING);
            row.createCell(USER_INFO.PHONE).setCellValue(user.getPhone());
            row.getCell(USER_INFO.PHONE).setCellType(CellType.STRING);
            row.createCell(USER_INFO.PWD).setCellValue(user.getPwd());
            row.getCell(USER_INFO.PWD).setCellType(CellType.STRING);
            row.createCell(USER_INFO.AGE).setCellValue(user.getAge());
            row.getCell(USER_INFO.AGE).setCellType(CellType.STRING);
            row.createCell(USER_INFO.SEX).setCellValue(user.getSex());
            row.getCell(USER_INFO.SEX).setCellType(CellType.STRING);
            row.createCell(USER_INFO.TOKEN).setCellValue(user.getToken());
            row.getCell(USER_INFO.TOKEN).setCellType(CellType.STRING);
            sId ++;
            FileOutputStream out = new FileOutputStream(FILE_PATH);
            wb.write(out);
            wb.close();
            return true;
        }catch(IOException e){
            e.printStackTrace();
        }
        return false;
    }

    public static String getUserIdByToken(String token){
        try{
            FileInputStream fis = new FileInputStream(FILE_PATH);
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheet(FIRST_SHEET);
            int lastNum = sheet.getLastRowNum();
            for(int i = 1 ; i <= lastNum ; i ++){
                HSSFRow tempRow = sheet.getRow(i);
                if(tempRow.getCell(USER_INFO.TOKEN).
                        getStringCellValue().equals(token)){
                    return tempRow.getCell(USER_INFO.ID).getStringCellValue();
                }
            }
            wb.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String getUserIdByPhone(String phone) {
        try {
            FileInputStream fis = new FileInputStream(FILE_PATH);
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheet(FIRST_SHEET);
            int lastNum = sheet.getLastRowNum();
            for(int i = 1; i <= lastNum ; i ++){
                HSSFRow tempRow = sheet.getRow(i);
                if(tempRow.getCell(USER_INFO.PHONE).
                        getStringCellValue().equals(phone)){
                    return tempRow.getCell(USER_INFO.ID).getStringCellValue();
                }
            }
            wb.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static String getUserNameById(String userId){
        try{
            FileInputStream fis = new FileInputStream(FILE_PATH);
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheet(FIRST_SHEET);
            int id = Integer.valueOf(userId);
            wb.close();
            return sheet.getRow(id).getCell(USER_INFO.NAME).
                    getStringCellValue();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static boolean updateName(String userId, String newName) {
        try {
            FileInputStream fis = new FileInputStream(FILE_PATH);
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheet(FIRST_SHEET);
            int id = Integer.valueOf(userId);
            if(id <= sheet.getLastRowNum()) {
                sheet.getRow(id).getCell(USER_INFO.NAME)
                        .setCellValue(newName);
                sheet.getRow(id).getCell(USER_INFO.NAME)
                        .setCellType(CellType.STRING);

                FileOutputStream fos = new FileOutputStream(FILE_PATH);
                wb.write(fos);
                wb.close();
                return true;
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updatePwd(String userId, String newPwd) {
        try {
            FileInputStream fis = new FileInputStream(FILE_PATH);
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheet(FIRST_SHEET);
            int id = Integer.valueOf(userId);
            if(id <= sheet.getLastRowNum()) {
                sheet.getRow(id).getCell(USER_INFO.PWD)
                        .setCellValue(newPwd);
                sheet.getRow(id).getCell(USER_INFO.PWD)
                        .setCellType(CellType.STRING);
                FileOutputStream fos = new FileOutputStream(FILE_PATH);
                wb.write(fos);
                wb.close();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updataToken(String userId, String newToken){
        try {
            FileInputStream fis = new FileInputStream(FILE_PATH);
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheet(FIRST_SHEET);
            int id = Integer.valueOf(userId);
            if(id <= sheet.getLastRowNum()){
                sheet.getRow(id).getCell(USER_INFO.TOKEN)
                        .setCellType(CellType.STRING);
                sheet.getRow(id).getCell(USER_INFO.TOKEN)
                        .setCellValue(newToken);
                FileOutputStream fos = new FileOutputStream(FILE_PATH);
                wb.write(fos);
                wb.close();
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static String getExpireTime(String token){
        try {
            FileInputStream fis = new FileInputStream(FILE_PATH);
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheet(FIRST_SHEET);
            int lastNum = sheet.getLastRowNum();
            for(int i = 1 ; i <= lastNum ; i ++){
                HSSFRow tempRow = sheet.getRow(i);
                if(tempRow.getCell(USER_INFO.TOKEN).
                        getStringCellValue().equals(token)){
                    return tempRow.getCell(USER_INFO.EXPIRE_TIME)
                            .getStringCellValue();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static boolean updataExpireTime
            (String userId, String expireTime){
        try{
            FileInputStream fis = new FileInputStream(FILE_PATH);
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheet(FIRST_SHEET);
            int id = Integer.valueOf(userId);
            sheet.getRow(id).createCell(USER_INFO.EXPIRE_TIME);
            HSSFCell cell = sheet.getRow(id).getCell(USER_INFO.EXPIRE_TIME);
            cell.setCellValue(expireTime);
            cell.setCellType(CellType.STRING);
            FileOutputStream fos = new FileOutputStream(FILE_PATH);
            wb.write(fos);
            wb.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args){
//        initUserDB();
//        User user = new User();
//        user.setPwd("kid123");
//        user.setAge("18");
//        user.setName("kid");
//        user.setPhone("18844544708");
//        user.setSex("woman");
//        user.setToken("1234567");
//        addUser(user);
//        String phone = "14208987623";
//        System.out.println(getUserId(phone));
//        System.out.println(updatePwd("4", "yuan123"));
//        System.out.println(updateName("4", "yuan"));
        updataExpireTime("2", "2018-12-31 06:30:16");
    }
}
