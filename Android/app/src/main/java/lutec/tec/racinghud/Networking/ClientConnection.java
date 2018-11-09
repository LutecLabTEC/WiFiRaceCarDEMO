package lutec.tec.racinghud.Networking;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import lutec.tec.racinghud.ControlData;

public class ClientConnection extends Thread {

    private final String TAG = "ClientConnection";

    private BufferedInputStream inputStream = null;
    private BufferedOutputStream outputStream = null;
    private Socket socket;
    private int clientId;


    public ClientConnection(Socket socket) {
        super("ServerThread");
        this.socket = socket;
        this.clientId = -1;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();

        try {
            Log.d(TAG, "Server up and running.");
            inputStream = new BufferedInputStream(socket.getInputStream());
            outputStream = new BufferedOutputStream(socket.getOutputStream());

            byte[] buff = new byte[256];

            // read msg
            while (inputStream.read(buff) != -1) {
                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                for (String line; (line = r.readLine()) != null; ) {
                    Log.d(TAG, "READ: " + line);
                    String[] msgData = line.split(":");
                        switch (msgData[0]) {
                            case "2": {
                                Log.d("Client2", "READ: " + line);
                                this.clientId = 0;
                                ControlData.getInstance().setSpeed(Float.valueOf(msgData[1]));
                                break;
                            }
                        }
                }
            }


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                    outputStream = null;
                }

                if (inputStream != null) {
                    inputStream.close();
                    inputStream = null;
                }

                if (socket != null) {
                    socket.close();
                    socket = null;
                }

            } catch (IOException e) {

            }

        }

    }

    public void sendMsg(String msg) {
        try {
            msg += "\0\n\r";
            outputStream.write(msg.getBytes());
            outputStream.flush();
            Log.d(TAG, "sendMsg: " + msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public int getClientId() {
        return clientId;
    }
}