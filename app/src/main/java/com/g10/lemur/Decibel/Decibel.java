package com.g10.lemur.Decibel;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.g10.lemur.Accelerometer.Accelerometer;
import com.g10.lemur.Altimeter.Altimeter;
import com.g10.lemur.Help.Help;
import com.g10.lemur.MainActivity;
import com.g10.lemur.R;
import com.g10.lemur.Settings.Settings;
import com.g10.lemur.Vision.Vision;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;
import java.util.jar.Manifest;

public class Decibel extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    NavigationView navigationView;

    // DECIBEL/VOLUME STUFF
    TextView currentValueTextView;
    TextView highValueTextView;
    TextView lowValueTextView;

    private int currentVolume = 0;
    private int highestVolume = 0;
    private int lowestVolume = 1000;

    MediaRecorder mRecorder = null;
    Thread runner;
    private static double mEMA = 0.0;
    static final private double EMA_FILTER = 0.6;

    final Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateGraphView();
            updateTextView();
        };
    };
    final Handler mHandler = new Handler();



    // GRAPH STUFF
    GraphView mGraph;
    LineGraphSeries<DataPoint> series;
    long activityCreateTime;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decibel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.menuSound);

        currentValueTextView = (TextView) findViewById(R.id.currentValueText);
        highValueTextView = (TextView) findViewById(R.id.highValueText);
        lowValueTextView = (TextView) findViewById(R.id.lowValueText);


        mGraph = (GraphView) findViewById(R.id.graph);
        loadGraphView();

        final TextView mDetailedViewText = (TextView) findViewById(R.id.smallTextView);
        mDetailedViewText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Resetting Values",
                        Toast.LENGTH_SHORT).show();
                highestVolume = 0;
                lowestVolume = 1000;
                highValueTextView.setText("");
                lowValueTextView.setText("");
            }
        });


        if(runner == null){
            runner = new Thread(){
                public void run(){
                    while (runner != null){
                        try{
                            Thread.sleep(100);
                            //Log.i("DecibelThread", "Tock");   // For debug purposes
                        } catch (InterruptedException e){ }
                        mHandler.post(updater);
                    }
                }
            };
            runner.start();
            Log.d("DecibelThread", "start runner()");
        }
        activityCreateTime = System.currentTimeMillis();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                          String permissions[], int[] grantResults){
        switch (requestCode){
            case 1:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // Permission granted
                    startRecorder();
                }
                else{
                    // Permission denied
                    Toast.makeText(Decibel.this, "Permission denied to use microphone",
                            Toast.LENGTH_LONG);
                }
            }
        }
    }

    private void updateGraphView(){


        series.appendData(newDatapoint(currentVolume), true, 100);
        mGraph.onDataChanged(true, false);
    }
    private DataPoint newDatapoint(int y)
    {
        double timeSince = System.currentTimeMillis() - activityCreateTime;
        return new DataPoint(timeSince, y);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);

        navigationView.setCheckedItem(R.id.menuSound);
    }

    @Override
    public void onResume(){
        super.onResume();
        navigationView.setCheckedItem(R.id.menuSound);

        startRecorder();
        // Doesn't work atm...
        //ActivityCompat.requestPermissions(Decibel.this, new String[]{"android.permission.RECORD_AUDIO"},1);
    }
    @Override
    public void onPause(){
        super.onPause();
        stopRecorder();
    }
    public void startRecorder() {
        if(mRecorder == null) {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            mRecorder.setOutputFile("/dev/null");
            try {
                mRecorder.prepare();
            } catch (java.io.IOException ioe) {
                android.util.Log.e("[Monkey]", "IOException: " +
                        android.util.Log.getStackTraceString(ioe));
            } catch (java.lang.SecurityException e) {
                android.util.Log.e("[Monkey]", "SecurityException: " +
                        android.util.Log.getStackTraceString(e));
            }
            try {
                mRecorder.start();
            } catch (Exception e) {
                android.util.Log.e("[Monkey]", "SecurityException: " +
                        android.util.Log.getStackTraceString(e));
            }
        }

        // mEMA = 0.0;
    }
    public void stopRecorder(){
        if(mRecorder != null){
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }
    public void updateTextView(){
        int volume = soundDb();

        if(volume<0)
            return;

        if(volume>highestVolume){
            highValueTextView.setText(volume + " dB");
            highestVolume = volume;
        }

        if(volume<lowestVolume){
            lowValueTextView.setText(volume + " dB");
            lowestVolume = volume;
        }

        currentValueTextView.setText(volume + " dB");
        currentVolume = volume;
    }

    // This is hard to calculate. Basically can't be done perfectly unless you have real calibrated Decibel equipment to use as reference.
    public int soundDb(){
        double amp = 20 * Math.log10(getAmplitude() / 0.1);
        return  (int)amp;
    }

    public double getAmplitude() {
        if (mRecorder != null)
            return  (mRecorder.getMaxAmplitude());
        else
            return 0;

    }
    public double getAmplitudeEMA() {
        double amp =  getAmplitude();
        mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) * mEMA;
        return mEMA;
    }

    private void loadGraphView(){

        series = new LineGraphSeries<DataPoint>();
        mGraph.addSeries(series);
        mGraph.setTitle("Graph");
        mGraph.getGridLabelRenderer().setHorizontalAxisTitle("Time");
        mGraph.getViewport().setXAxisBoundsManual(true);
        mGraph.getViewport().setMinX(0);
        mGraph.getViewport().setMaxX(10000);
        mGraph.getGridLabelRenderer().setNumHorizontalLabels(4);

        mGraph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter()
        {
            @Override
            public String formatLabel(double value, boolean isValueX)
            {
                if (isValueX) {
                    DecimalFormat df = new DecimalFormat("#.#");
                    return df.format(value/1000)+"s";
                } else {
                    return super.formatLabel(value, isValueX);
                }
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        } else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        Intent intent;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menuHome)
        {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.menuVision)
        {
            // Go to Google Vision
            intent = new Intent(this, Vision.class);
            startActivity(intent);
        }
        else if (id == R.id.menuAlti)
        {
            // Go to altimeter
            intent = new Intent(this, Altimeter.class);
            startActivity(intent);
        }
        else if (id == R.id.menuAcc)
        {
            // Go to accelerometer
            intent = new Intent(this, Accelerometer.class);
            startActivity(intent);
        }
        else if (id == R.id.menuSound)
        {
            // Stay here
        }
        else if (id == R.id.menuHelp)
        {
            // Go to help
            intent = new Intent(this, Help.class);
            startActivity(intent);
        }
        else if (id == R.id.menuSettings)
        {
            // Go to Settings
            intent = new Intent(this, Settings.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
