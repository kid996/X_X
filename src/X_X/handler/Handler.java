package X_X.handler;

public abstract class Handler {
    private MessageQueue mq;

    public Handler(Looper looper) {
        mq = looper.myMQ();
    }

    public void sendMessage(Message msg){
        try {
            if (mq == null) {
                System.out.println("mq is null");
            }
            mq.enqueue(msg);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public abstract void handleMessage(Message msg);

}
