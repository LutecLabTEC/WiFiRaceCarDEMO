package lutec.tec.racinghud.Networking;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketClient extends Thread {
    private Socket mSocket;
    private BufferedOutputStream outputStream;
    private static final String TAG = "socket";
    private String mIP = "192.168.0.16";
    private int mPort = 8888;

    public SocketClient(String ip, int port) {
        mIP = ip;
        mPort = port;
        start();
    }

    public SocketClient() {
        start();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();
        try {
            mSocket = new Socket();
            Log.d("ANAL", "SocketCreated");
            mSocket.connect(new InetSocketAddress(mIP, mPort), 10000); // hard-code server address
            Log.d("ANAL", "SocketInstanciatted");
            BufferedOutputStream outputStream = new BufferedOutputStream(mSocket.getOutputStream());
            BufferedInputStream inputStream = new BufferedInputStream(mSocket.getInputStream());
            Log.d("ANAL", "OutputANAL");
            //sendMsg("HOLA");
            Log.d("ANAL", "Run");
        } catch (Exception e) {
            // TODO Auto-generated catch block
//			e.printStackTrace();
            Log.e(TAG, e.toString());
        }
        finally {
            try {
                mSocket.close();
                mSocket = null;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void sendMsg(String msg){
        try {
            outputStream.write(msg.getBytes());
            outputStream.flush();
            Log.d("ANAL", "sendMsg: "+msg);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void close() {
        if (mSocket != null) {
            try {
                mSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}