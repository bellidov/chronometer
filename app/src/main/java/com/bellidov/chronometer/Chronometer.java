package com.bellidov.chronometer;

import android.content.Context;

/**
 * Created by Cesar on 13/01/2017.
 */
public class Chronometer implements Runnable {
    private Context mContext;
    private long startTime;
    private boolean isRunning;

    public Chronometer(Context context) {
        this.mContext = context;
    }

    public void start(){
        startTime = System.currentTimeMillis();
        this.isRunning = true;
    }

    public void stop(){
        this.isRunning = false;
    }

    public void pause(){
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void continueChrono(){

    }

    public boolean isRunning() {
        return isRunning;
    }

    public long getStartTime() {
        return startTime;
    }

    @Override
    public void run() {
        while(isRunning) {
            long since = System.currentTimeMillis() - startTime;

            int seconds = (int) (since / 1000) % 60;
            int minutes = (int) ((since / 60000) % 60);
            int hours = (int) ((since / 3600000));
            int millis = (int) since % 1000;

            ((MainActivity) mContext).updateTimerText(String.format("%02d:%02d:%02d:%03d"
                    , hours, minutes, seconds, millis));

            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String xxx = "finish";
        xxx = "exit";
    }
}
