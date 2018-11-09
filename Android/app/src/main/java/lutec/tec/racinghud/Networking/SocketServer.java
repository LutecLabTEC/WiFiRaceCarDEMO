package lutec.tec.racinghud.Networking;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SocketServer extends Thread {

    private final String TAG = "SERVER";

    private ServerSocket mServer;
    private Map<Integer, ClientConnection> conectionsMap;

    public SocketServer() {
        this.conectionsMap = new HashMap<>();
        this.setDaemon(true);
        this.start();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();

        try {
            mServer = new ServerSocket(8888);
            Log.d(TAG, "Server up and running.");
            while (true) {

                try{
                    Socket newSocket = mServer.accept();
                    Log.d(TAG, "Socket Accepted.");
                    ClientConnection newClientConnection = new ClientConnection(newSocket);
                    newClientConnection.start();
                }
                catch(Exception e){
                    e.printStackTrace();
                    System.out.println("Connection Error");
                }

                //noinspection StatementWithEmptyBody
                /*while (newClientConnection.getClientId() == -1) ;
                if (conectionsMap.containsKey(newClientConnection.getClientId())) {
                    conectionsMap.remove(newClientConnection.getClientId());
                }
                conectionsMap.put(newClientConnection.getClientId(), newClientConnection);*/

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }
}