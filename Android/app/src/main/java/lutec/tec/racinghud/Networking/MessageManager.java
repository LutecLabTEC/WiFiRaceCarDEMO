package lutec.tec.racinghud.Networking;

import lutec.tec.racinghud.ControlData;

public class MessageManager {

    //private static MessageManager instance;

    public MessageManager() {

    }

    /*public static MessageManager getInstance() {
        if (instance == null) instance = new MessageManager();
        return instance;
    }*/

    public void manageMessage(String msg) {
        String[] msgData = msg.split(":");
        switch (msgData[0]) {
            case "2": {
                ControlData.getInstance().setSpeed(Float.valueOf(msgData[1]));
                break;
            }
        }
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
}
