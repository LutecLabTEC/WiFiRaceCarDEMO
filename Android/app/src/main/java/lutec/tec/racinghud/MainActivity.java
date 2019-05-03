package lutec.tec.racinghud;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.anastr.speedviewlib.AwesomeSpeedometer;
import com.github.anastr.speedviewlib.ProgressiveGauge;

import lutec.tec.racinghud.Networking.SocketServer;

public class MainActivity extends AppCompatActivity {

    private static RelativeLayout activityBaseLayout;

    static AwesomeSpeedometer speedometer;
    static ProgressiveGauge progressiveGaugeLeft;
    static ProgressiveGauge progressiveGaugeRight;

    private ImageView mImageView;
    private ImageView   blueLightLeft, blueLightLeft1, blueLightRight, blueLightRight1;
    private ImageView   redLightLeft, redLightLeft1, redLightRight, redLightRight1;


    RelativeLayout layout_joystick;
    JoyStickClass js;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityBaseLayout = findViewById(R.id.Activity_Base_Layout);

        speedometer = findViewById(R.id.speedView);
        progressiveGaugeLeft = findViewById(R.id.progressiveGaugeLeft);
        progressiveGaugeRight = findViewById(R.id.progressiveGaugeRight);

        //mImageView = (ImageView) findViewById(R.id.f_lambo2);

        blueLightLeft = new ImageView(getBaseContext());
        blueLightLeft.setImageResource(R.drawable.blue_light);
        blueLightLeft.setScaleX(0.125f);
        blueLightLeft.setScaleY(0.125f);
        blueLightLeft.setX(-145.0f);
        blueLightLeft.setY(-20.0f);
        activityBaseLayout.addView(blueLightLeft);

        blueLightLeft1 = new ImageView(getBaseContext());
        blueLightLeft1.setImageResource(R.drawable.blue_light);
        blueLightLeft1.setScaleX(0.0625f);
        blueLightLeft1.setScaleY(0.0625f);
        blueLightLeft1.setX(-150.0f);
        blueLightLeft1.setY(-35.0f);
        activityBaseLayout.addView(blueLightLeft1);

        blueLightRight = new ImageView(getBaseContext());
        blueLightRight.setImageResource(R.drawable.blue_light);
        blueLightRight.setScaleX(0.125f);
        blueLightRight.setScaleY(0.125f);
        blueLightRight.setX(125.0f);
        blueLightRight.setY(-20.0f);
        activityBaseLayout.addView(blueLightRight);

        blueLightRight1 = new ImageView(getBaseContext());
        blueLightRight1.setImageResource(R.drawable.blue_light);
        blueLightRight1.setScaleX(0.0625f);
        blueLightRight1.setScaleY(0.0625f);
        blueLightRight1.setX(135.0f);
        blueLightRight1.setY(-35.0f);
        activityBaseLayout.addView(blueLightRight1);

        redLightLeft = new ImageView(getBaseContext());
        redLightLeft.setImageResource(R.drawable.red_light);
        redLightLeft.setScaleX(0.15f);
        redLightLeft.setScaleY(0.15f);
        redLightLeft.setX(840.0f);
        redLightLeft.setY(-20.0f);
        activityBaseLayout.addView(redLightLeft);

        redLightLeft1 = new ImageView(getBaseContext());
        redLightLeft1.setImageResource(R.drawable.red_light);
        redLightLeft1.setScaleX(0.15f);
        redLightLeft1.setScaleY(0.15f);
        redLightLeft1.setX(800.0f);
        redLightLeft1.setY(-20.0f);
        activityBaseLayout.addView(redLightLeft1);

        redLightRight = new ImageView(getBaseContext());
        redLightRight.setImageResource(R.drawable.red_light);
        redLightRight.setScaleX(0.15f);
        redLightRight.setScaleY(0.15f);
        redLightRight.setX(1035.0f);
        redLightRight.setY(-20.0f);
        activityBaseLayout.addView(redLightRight);

        redLightRight1 = new ImageView(getBaseContext());
        redLightRight1.setImageResource(R.drawable.red_light);
        redLightRight1.setScaleX(0.15f);
        redLightRight1.setScaleY(0.15f);
        redLightRight1.setX(1070.0f);
        redLightRight1.setY(-20.0f);
        activityBaseLayout.addView(redLightRight1);


        // move to 50 Km/s
        //speedometer.speedTo(50);
        progressiveGaugeLeft.speedTo(50);
        speedometer.setWithTremble(false);

        SocketServer socketServer = new SocketServer();

        SpeedUpdater speedUpdater = new SpeedUpdater();
        speedUpdater.execute();

        layout_joystick = (RelativeLayout) findViewById(R.id.layout_joystick);

        js = new JoyStickClass(getApplicationContext()
                , layout_joystick, R.drawable.llitlecircle);

        js.setStickSize(125, 125);
        js.setLayoutSize(250, 250);
        js.setLayoutAlpha(150);
        js.setStickAlpha(100);
        js.setOffset(50);
        js.setMinimumDistance(25);

        layout_joystick.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {

                js.drawStick(arg1);
                if (arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {


                    int direction = js.get8Direction();
                    if (direction == JoyStickClass.STICK_UP) {
                    } else if (direction == JoyStickClass.STICK_UPRIGHT) {
                    } else if (direction == JoyStickClass.STICK_RIGHT) {
                    } else if (direction == JoyStickClass.STICK_DOWNRIGHT) {
                    } else if (direction == JoyStickClass.STICK_DOWN) {
                    } else if (direction == JoyStickClass.STICK_DOWNLEFT) {
                    } else if (direction == JoyStickClass.STICK_LEFT) {
                    } else if (direction == JoyStickClass.STICK_UPLEFT) {
                    } else if (direction == JoyStickClass.STICK_NONE) {
                    }
                } else if (arg1.getAction() == MotionEvent.ACTION_UP) {

                }
                return true;
            }
        });
    }

    private static class SpeedUpdater extends AsyncTask<Void, Float, String> {

        @Override
        protected void onPreExecute() {

        }

        /* (non-Javadoc)
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @SuppressWarnings("InfiniteLoopStatement")
        @Override
        protected String doInBackground(Void... params) {
            while (true) publishProgress(ControlData.getInstance().getSpeed());
        }

        @Override
        protected void onProgressUpdate(Float... values) {
            super.onProgressUpdate(values);
            speedometer.speedTo(values[0]);
        }
    }
}
