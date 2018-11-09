package lutec.tec.racinghud;

public class ControlData {

    private static ControlData instance;

    private static volatile float speed = 0;

    private ControlData(){
    }

    public static ControlData getInstance(){
        if(instance == null) instance = new ControlData();
        return instance;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        ControlData.speed = speed;
    }
}
