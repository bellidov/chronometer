package com.bellidov.chronometer;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {

    private TextView chronoText;
    private Button startBtn;
    private Button pauseBtn;
    private Chronometer chronometer;
    private Context context;
    private Thread threadChrono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.context = this;
        chronoText = (TextView) findViewById(R.id.txtChrono);
        startBtn = (Button) findViewById(R.id.btnStart);
        pauseBtn = (Button) findViewById(R.id.btnPause);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(startBtn.getText().toString()){
                    case "START":
                        if(chronometer == null) {
                            startBtn.setText("STOP");
                            //instantiate the chronometer
                            chronometer = new Chronometer(context);
                            //run the chronometer on a separate thread
                            threadChrono = new Thread(chronometer);
                            threadChrono.start();

                            //start the chronometer!
                            chronometer.start();
                        }
                        break;
                    case "STOP":
                        startBtn.setText("START");
                        chronometer.stop();
                        try {
                            threadChrono.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        threadChrono.interrupt();
                        threadChrono = null;
                        updateTimerText(String.format("%02d:%02d:%02d:%03d"
                                , 0, 0, 0, 0));
                        chronometer = null;
                        break;
                }


            }
        });

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronoText.setText("Pios!");
            }
        });
    }
    public void updateTimerText(final String timeAsText) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chronoText.setText(timeAsText);
            }

        });
    }
}
