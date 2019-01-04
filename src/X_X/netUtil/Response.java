package X_X.netUtil;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Response {
    private int mCode;
    private Object mData;
    private String mMessage;
    private Socket mSocket;
    private Response mResponse;

    public Response(){
    }

    public static interface Status{
        public final static int SUCCESS = 0;
        public final static int FAILED = 1;
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

    public void sendResponse(Response response){
        if(mSocket == null){
            System.out.println("socket is null!");
            return;
        }
        DataOutputStream os = null;
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
