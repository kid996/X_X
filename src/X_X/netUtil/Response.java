package X_X.netUtil;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Response {
    private int mCode;
    private Object mData;
    private String mMessage;
    private Socket mSocket;

    public static interface Status{
        public final static int SUCCESS = 0;
        public final static int FAILED = 1;
    }
    //为什么要是静态的？
    private static class InstanceHolder {
        public static Response sInstance = new Response();
    }
    //因为这里必须是static 外部才能调用，而这里由于是static，只能调用static类
    public static Response getInstance(){
        return InstanceHolder.sInstance;
    }

    private Response(){
    }

    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        this.mCode = code;
    }

    public Object getData() {
        return mData;
    }

    public void setData(Object data) {
        this.mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }

    public Socket getSocket(){
        return mSocket;
    }

    public void setSocket(Socket socket){
        this.mSocket = socket;
    }

    public void sendResponse(){
        if(mSocket == null){
            System.out.println("socket is null!");
            return;
        }
        DataOutputStream os = null;
        Response response = Response.getInstance();
        try {
            if(response != null) {
                os = new DataOutputStream(
                        new BufferedOutputStream(mSocket.getOutputStream()));
                System.out.println("准备发送response: " + response.toString());
                os.writeUTF(response.toString());
            }else{
                System.out.println("response is null!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(os != null){
                try {
                    os.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{code:");
        sb.append(mCode);
        sb.append(", data:");
        sb.append(mData);
        sb.append(", message:");
        sb.append(mMessage);
        sb.append("}");
        return sb.toString();
    }
}
