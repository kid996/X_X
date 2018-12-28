package X_X.view;

import X_X.handler.Looper;

public class DequeueThread extends Thread{

    private Looper mLooper;
    public DequeueThread(Looper looper){
        mLooper = looper;
    }

    public void run(){
        mLooper.loop();
        System.out.println("looper is running ...");
    }
}
