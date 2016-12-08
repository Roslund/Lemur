package com.g10.lemur.Vision;

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

import com.g10.lemur.Accelerometer.Accelerometer;
import com.g10.lemur.Altimeter.Altimeter;
import com.g10.lemur.Decibel.Decibel;
import com.g10.lemur.Help.Help;
import com.g10.lemur.MainActivity;
import com.g10.lemur.R;
import com.g10.lemur.Settings.Settings;

public class Vision extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.menuVision);
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
            // Stay here
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

}
