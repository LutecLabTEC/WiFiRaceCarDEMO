package lutec.tec.racinghud;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.anastr.speedviewlib.AwesomeSpeedometer;

import lutec.tec.racinghud.Networking.SocketServer;

public class MainActivity extends AppCompatActivity {

    static AwesomeSpeedometer speedometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speedometer = findViewById(R.id.speedView);

        // move to 50 Km/s
        //speedometer.speedTo(50);
        speedometer.setWithTremble(false);

        SocketServer socketServer = new SocketServer();

        SpeedUpdater speedUpdater = new SpeedUpdater();
        speedUpdater.execute();
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
