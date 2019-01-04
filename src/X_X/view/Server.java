package X_X.view;

import X_X.netUtil.Request;
import X_X.netUtil.Response;
import X_X.handler.Handler;
import X_X.handler.Looper;
import X_X.handler.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * server 服务器对象
 * 提供的功能：
 * 1.启动服务器
 * 2.注册服务器的操作接口
 * @author kid
 */
public class Server {
    private ServerSocket mServerSocket;
    private final int RECEIVE_REQUEST = 1;
    private Handler mHandler = null;
    private CallBack mCallBack;
    private Socket mSocket;

    public interface CallBack{
        public void onRequest(Request request, Response response);
    }

    private static class InstanceHolder{
        public static Server sInstance = new Server();
    }

    public static Server getInstance(){
        return InstanceHolder.sInstance;
    }

    private Server(){
        initServer();
    }

    private void initServer(){
        try {
            mServerSocket = new ServerSocket(8888);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("创建服务器异常");
        }
    }

    //启动服务器
    public void start(CallBack callBack){
        mCallBack = callBack;
        startServer();
    }

    private void startServer(){
        if(mServerSocket == null){
            initServer();
        }
        if(mServerSocket == null){
            System.out.println("启动服务器异常");
            return;
        }
        //1.looper转起来
        Looper looper = new Looper();
        DequeueThread dequeueThread = new DequeueThread(looper);
        dequeueThread.start();
        //2.初始化handler
        initHandler(looper);
        System.out.println("初始化handler。。。");
        if(mHandler == null){
            System.out.println("初始化handler失败");
        }
        //3.接收请求
        while(true){
            String requestStr = null;
            try{
                mSocket = mServerSocket.accept();
                DataInputStream is = new DataInputStream(
                        new BufferedInputStream(mSocket.getInputStream())
                );
                requestStr = is.readUTF();
                        System.out.println("收到一条request: " + requestStr);
                Request request = new Request(requestStr);
//                request.setSocket(socket);
                Message msg = new Message();
                msg.setWhat(RECEIVE_REQUEST);
                msg.setObject(request);
                msg.setTarget(mHandler);
                mHandler.sendMessage(msg);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initHandler(Looper looper){
        if(looper == null){
            System.out.println("looper is null !");
            return;
        }
        mHandler = new Handler(looper){
            public void handleMessage(Message msg){
                if(msg.getWhat() == RECEIVE_REQUEST){
                    Request request = (Request)msg.getObject();
                    Response response = new Response();
                    response.setSocket(mSocket);
                    mCallBack.onRequest(request, response);
//                    DataOutputStream os = null;
//                    try{
//                        if(response != null) {
//                            Socket socket = mSocket;
//                            os = new DataOutputStream(
//                                    new BufferedOutputStream(socket.getOutputStream())
//                            );
//                            System.out.println("准备发送response: " + response.toString());
//                            os.writeUTF(response.toString());
//                            os.flush();
//                        }else {
//                            System.out.println("response is null!");
//                        }
//
//                    }catch(Exception e){
//                        e.printStackTrace();
//                    }finally{
//                        if(os != null){
//                            try{
//                                os.close();
//                            } catch(IOException e){
//                                e.printStackTrace();
//                            }
//                        }
//                    }
                }
            }
        };

    }

}
