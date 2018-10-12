package com.example.ankurbhadauria.pedometerusingaccelerometer;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView textViewX,textViewY,textViewZ,textSensitive,textViewSteps;

    private Button buttonReset;

    private SensorManager sensorManager;

    private float acceleration;


    private float previousY,currentY;
    private int numSteps;

    private SeekBar seekBar;
    private  int threshold;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewX=(TextView)findViewById(R.id.textView);
        textViewY=(TextView)findViewById(R.id.textView2);
        textViewZ=(TextView)findViewById(R.id.textView3);


       // lala=MediaPlayer.create(this,R.raw.lalalaaa);
        textViewSteps=(TextView)findViewById(R.id.textView4);
        textSensitive=(TextView)findViewById(R.id.textView5);

        buttonReset=(Button)findViewById(R.id.button);

        seekBar=(SeekBar)findViewById(R.id.seekBar);

        seekBar.setProgress(10);
        seekBar.setOnSeekBarChangeListener(seekBarListener);
        threshold=10;

        textSensitive.setText(threshold+"");

        previousY=0;
        currentY=0;
        numSteps=0;
        acceleration=0.00f;

        enableAccelerometerListeniing();

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numSteps=0;
                textViewSteps.setText(numSteps+"");

            }

        });

    }

    private void enableAccelerometerListeniing() {
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(sensorEventListener,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),sensorManager.SENSOR_DELAY_NORMAL);
    }

    private SensorEventListener sensorEventListener=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x=sensorEvent.values[0];
            float y=sensorEvent.values[1];
            float z=sensorEvent.values[2];
            //final MediaPlayer lala=MediaPlayer.create(MainActivity.this,R.raw.lalalaaa);
            currentY=y;

            if(Math.abs(currentY-previousY)>threshold){
                numSteps++;
                textViewSteps.setText(numSteps+"");
                //lala.start();
            }

            textViewX.setText(x+"\nX");
            textViewY.setText(y+"\nY");
            textViewZ.setText(z+"\nZ");

            previousY=y;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    private SeekBar.OnSeekBarChangeListener seekBarListener=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            threshold=seekBar.getProgress();
            textSensitive.setText(threshold+"");
            /*final MediaPlayer lala=MediaPlayer.create(MainActivity.this,R.raw.lalalaaa);
            if(threshold<8)
                lala.stop();*/
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
