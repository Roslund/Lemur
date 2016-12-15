package com.g10.lemur.Accelerometer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

import com.g10.lemur.Altimeter.Altimeter;
import com.g10.lemur.Decibel.Decibel;
import com.g10.lemur.Help.Help;
import com.g10.lemur.MainActivity;
import com.g10.lemur.R;
import com.g10.lemur.Settings.Settings;
import com.g10.lemur.Vision.Vision;

public class Accelerometer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SensorEventListener
{
    NavigationView navigationView;
    private TextView XaxisText, YaxisText, ZaxisText;
    private Sensor accSensor;
    private SensorManager SM;
    private double lessFloatX;
    private double lessFloatY;
    private double lessFloatZ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Set the current activity as marked in the menu
        navigationView.setCheckedItem(R.id.menuAcc);

        //Create sensor manager
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);

        accSensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SM.registerListener(this,accSensor,SensorManager.SENSOR_DELAY_UI);

        //Assign TextViews for accelerometer data
        XaxisText = (TextView)findViewById(R.id.XDataText);
        YaxisText = (TextView)findViewById(R.id.YDataText);
        ZaxisText = (TextView)findViewById(R.id.ZDataText);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);

        navigationView.setCheckedItem(R.id.menuAcc);
    }
    @Override
    public void onPause(){
        super.onPause(); //always call superclass method first

            SM.unregisterListener(this,accSensor);
    }
    @Override
    public void onResume(){
        super.onResume();

        navigationView.setCheckedItem(R.id.menuAcc);

        SM.registerListener(this,accSensor,SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onBackPressed()
    {
        // Physical back button pressed

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
        // Right sub-menu.
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            // Stay here
        }
        else if (id == R.id.menuSound)
        {
            // Go to decibel
            intent = new Intent(this, Decibel.class);
            startActivity(intent);
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
    //funktion som rundar av till *int precision* antal decimaler
    //Ex Math.pow(10,1) = 10^1, Math.round(9.83827 * 10) = 98, 98/10 = 9.8
    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        lessFloatX = round(sensorEvent.values[0],1);
        lessFloatY = round(sensorEvent.values[1],1);
        lessFloatZ = round(sensorEvent.values[2],1);
        XaxisText.setText(String.valueOf(lessFloatX));
        YaxisText.setText(String.valueOf((lessFloatY)));
        ZaxisText.setText(String.valueOf((lessFloatZ)));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //Not in use
    }
}
