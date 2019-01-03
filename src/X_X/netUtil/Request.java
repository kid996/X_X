package X_X.netUtil;

import net.sf.json.JSONObject;

public class Request {
    private String mAction;
    private String mData;

    public Request(){

    }

    public Request(String requestStr){
        JSONObject requestJson = JSONObject.fromObject(requestStr);
        mAction = String.valueOf(requestJson.get("action"));
        mData = requestJson.get("data").toString();
    }
    
    public String getAction() {
        return mAction;
    }

    public void setAction(String action) {
        this.mAction = action;
    }
    public String getData() {
        return mData;
    }

    public void setData(String data) {
        this.mData = data;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{action:");
        sb.append(mAction);
        sb.append(":data:");
        sb.append(mData);
        return sb.toString();
    }
}
