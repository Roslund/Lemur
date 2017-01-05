package com.g10.lemur.Help;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.g10.lemur.Accelerometer.Accelerometer;
import com.g10.lemur.Altimeter.Altimeter;
import com.g10.lemur.Decibel.Decibel;
import com.g10.lemur.MainActivity;
import com.g10.lemur.R;
import com.g10.lemur.Settings.Settings;
import com.g10.lemur.Speedometer.Speedometer;
import com.g10.lemur.Vision.Vision;

public class Help extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    NavigationView navigationView;

    // Used to remember the state of the TextViews (true = text displayed, false = no text displayed)
    private boolean helpLemurState;
    private boolean helpAccelerometerState;
    private boolean helpAltimeterState;
    private boolean helpDecibelState;
    private boolean helpVisionState;

    TextView helpLemur;
    TextView helpLemurText;

    TextView helpAccelerometer;
    TextView helpAccelerometerText;

    TextView helpAltimeter;
    TextView helpAltimeterText;

    TextView helpDecibel;
    TextView helpDecibelText;

    TextView helpVision;
    TextView helpVisionText;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
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
        navigationView.setCheckedItem(R.id.menuHelp);

        helpLemurState = true;
        helpAccelerometerState = false;
        helpAltimeterState = false;
        helpDecibelState = false;
        helpVisionState = false;

        // Get textViews that need to be changed
        helpLemur = (TextView)findViewById(R.id.helpLemur);
        helpLemurText = (TextView)findViewById(R.id.helpLemurText);

        helpAccelerometer = (TextView)findViewById(R.id.helpAccelerometer);
        helpAccelerometerText = (TextView)findViewById(R.id.helpAccelerometerText);

        helpAltimeter = (TextView)findViewById(R.id.helpAltimeter);
        helpAltimeterText = (TextView)findViewById(R.id.helpAltimeterText);

        helpDecibel = (TextView)findViewById(R.id.helpDecibel);
        helpDecibelText = (TextView)findViewById(R.id.helpDecibelText);

        helpVision = (TextView)findViewById(R.id.helpVision);
        helpVisionText = (TextView)findViewById(R.id.helpVisionText);

        helpLemur.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpLemurChangeState();
            }
        });
        helpAccelerometer.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpAccelerometerChangeState();
            }
        });
        helpAltimeter.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpAltimeterChangeState();
            }
        });
        helpDecibel.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpDecibelChangeState();
            }
        });
        helpVision.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpVisionChangeState();
            }
        });
    }

    public void helpLemurChangeState(){
        if(helpLemurState){
            // Remove text
            helpLemur.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.ic_white_uparrow,0);
            helpLemurText.setVisibility(helpLemurText.GONE);

        }
        else{
            // Show text
            helpLemur.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.ic_white_downarrow,0);
            helpLemurText.setVisibility(helpLemurText.VISIBLE);
        }
        helpLemurState = !helpLemurState;

    }
    public void helpAccelerometerChangeState(){
        if(helpAccelerometerState){
            // Remove text
            helpAccelerometer.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.ic_white_uparrow,0);
            helpAccelerometerText.setVisibility(helpAccelerometerText.GONE);
        }
        else{
            // Show text
            helpAccelerometer.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.ic_white_downarrow,0);
            helpAccelerometerText.setVisibility(helpAccelerometerText.VISIBLE);
        }
        helpAccelerometerState = !helpAccelerometerState;

    }
    public void helpAltimeterChangeState(){
        if(helpAltimeterState){
            // Remove text
            helpAltimeter.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.ic_white_uparrow,0);
            helpAltimeterText.setVisibility(helpAltimeterText.GONE);
        }
        else{
            // Show text
            helpAltimeter.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.ic_white_downarrow,0);
            helpAltimeterText.setVisibility(helpAltimeterText.VISIBLE);
        }
        helpAltimeterState = !helpAltimeterState;

    }
    public void helpDecibelChangeState(){
        if(helpDecibelState){
            // Remove text
            helpDecibel.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.ic_white_uparrow,0);
            helpDecibelText.setVisibility(helpDecibelText.GONE);
        }
        else{
            // Show text
            helpDecibel.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.ic_white_downarrow,0);
            helpDecibelText.setVisibility(helpDecibelText.VISIBLE);
        }
        helpDecibelState = !helpDecibelState;

    }
    public void helpVisionChangeState(){
        if(helpVisionState){
            // Remove text
            helpVision.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.ic_white_uparrow,0);
            helpVisionText.setVisibility(helpVisionText.GONE);
        }
        else{
            // Show text
            helpVision.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.ic_white_downarrow,0);
            helpVisionText.setVisibility(helpVisionText.VISIBLE);
        }
        helpVisionState = !helpVisionState;

    }

    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);

        navigationView.setCheckedItem(R.id.menuHelp);
    }

    @Override
    public void onResume(){
        super.onResume();

        navigationView.setCheckedItem(R.id.menuHelp);
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
        else if (id == R.id.menuSpeed)
        {
            // Go to speedometer
            intent = new Intent(this, Speedometer.class);
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
            // Go to decibel
            intent = new Intent(this, Decibel.class);
            startActivity(intent);
        }
        else if (id == R.id.menuHelp)
        {
            // Stay here
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
