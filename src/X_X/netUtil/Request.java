package X_X.netUtil;

import net.sf.json.JSONObject;

import java.net.Socket;

public class Request {
    private String action;
    private String data;
    private Socket socket;

    public Request(){

    }

    public Request(String requestStr){
        JSONObject requestJson = JSONObject.fromObject(requestStr);
        action = String.valueOf(requestJson.get("action"));
        data = requestJson.get("data").toString();
    }
    
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{action:");
        sb.append(action);
        sb.append(":data:");
        sb.append(data);
        return sb.toString();
    }
}
