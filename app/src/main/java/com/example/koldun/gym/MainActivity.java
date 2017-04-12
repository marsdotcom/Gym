package com.example.koldun.gym;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Chronometer;
import android.os.SystemClock;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,OnLoadCompleteListener {

    Chronometer mChronometer;
    boolean running;
    Button mButton;
    SoundPool sp;
    int soundIdShot;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        sp.setOnLoadCompleteListener(this);

        try {
            soundIdShot = sp.load(this, R.raw.wav, 1);
        }catch (Exception o)  {
            Toast.makeText(this, "Нажата кнопка ОК", Toast.LENGTH_LONG).show();
        }


        running = false;
        mChronometer = (Chronometer) findViewById(R.id.chronometer2);
        mChronometer.setFormat("%s");

        mButton = (Button)findViewById(R.id.button);
        mButton.setOnClickListener(this);

        mTextView = (TextView) findViewById(R.id.textView);



        mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {

                long elapse =  (SystemClock.elapsedRealtime()- mChronometer.getBase())/1000;
                float volume = ((float)elapse)/60;

                mTextView.setText(Long.toString(elapse));

                if (elapse == 40)
                    sp.play(soundIdShot, volume, volume, 0, 0, 1);
                if (elapse == 50)
                    sp.play(soundIdShot, volume, volume, 0, 0, 1);
                if (elapse == 60)
                    sp.play(soundIdShot, volume, volume, 0, 0, 1);

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "7777", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if(running = !running){
            mChronometer.setBase(SystemClock.elapsedRealtime());
            mChronometer.start();
            mButton.setText("Stop");
            sp.play(soundIdShot, 1, 1, 0, 0, 1);

        }else{
            mButton.setText("Start");
            mChronometer.stop();

        }

    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

    }
}
