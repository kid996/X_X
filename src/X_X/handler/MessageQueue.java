package X_X.handler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class MessageQueue {

    private BlockingQueue<Message> bq;

    public MessageQueue(int cap){
        bq = new LinkedBlockingDeque<Message>(cap);
    }

    public Message next() throws InterruptedException{
        return bq.take();
    }

    public void enqueue(Message msg) throws InterruptedException{
        bq.put(msg);
    }
}
