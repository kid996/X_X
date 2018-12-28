package X_X.handler;

public class Message {
    private Object mObject;
    private Handler mTarget;
    private int mWhat;

    public int getWhat() {
        return mWhat;
    }

    public void setWhat(int what) {
        this.mWhat = what;
    }

    public Object getObject() {
        return mObject;
    }

    public void setObject(Object object) {
        this.mObject = object;
    }



    public Handler getTarget() {
        return mTarget;
    }

    public void setTarget(Handler target) {
        this.mTarget = target;
    }


}
