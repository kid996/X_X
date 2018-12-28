package X_X.handler;

public class Looper {
    private MessageQueue mq;
    public Looper(){
        mq = new MessageQueue(3);
    }

    public void loop(){
        System.out.println("启动loop...");
        while(true){
            try{
                Message msg = mq.next();
                if(msg != null){
                    msg.getTarget().handleMessage(msg);
                }
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public MessageQueue myMQ(){
        return mq;
    }
}
