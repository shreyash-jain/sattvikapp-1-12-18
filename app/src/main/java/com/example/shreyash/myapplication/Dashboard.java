package com.example.shreyash.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import utils.Constants;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences sharedpreferences;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);
        TextView name = (TextView)header.findViewById(R.id.main_name);
        TextView id = (TextView)header.findViewById(R.id.main_sattvikId);
        //TODO: CHange name and sattvik id of person from here. Can change image also.
//        String personName = "Abhinav Dangi";
//        String personEmail = "2";
//        name.setText(personName);
//        id.setText(personEmail);

        Fragment myFragment=new FragmentBoard();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame,  myFragment, "MY_FRAGMENT");
        ft.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        FragmentBoard myFragment = (FragmentBoard) getSupportFragmentManager().findFragmentByTag("MY_FRAGMENT");;

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (myFragment != null && myFragment.isVisible()) {
                super.onBackPressed();
            } else {
                Fragment home_fragment=new FragmentBoard();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame,  home_fragment, "MY_FRAGMENT");
                ft.commit();
            }
        }
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.board:
                fragment = new FragmentBoard();
                break;
            case R.id.cancel:
                fragment = new FragmentCancel();
                break;
            case R.id.feedback:
                fragment = new FragmentFeedback();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about) {
            Intent i = new Intent(Dashboard.this, ActivityAbout.class);
            startActivity(i);
        } else if (item.getItemId() == R.id.workers) {
            Intent i = new Intent(Dashboard.this, ActivityWorkers.class);
            startActivity(i);
        } else if(item.getItemId()== R.id.logout){
            AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
            builder.setMessage("Confirm Logout?");
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Delete data from shared preferences
                    sharedpreferences = getSharedPreferences(Constants.MYPREFERENCE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.remove(Constants.NAME);
                    editor.remove(Constants.EMAIL);
                    editor.remove(Constants.PASSWORD);
                    editor.apply();

                    Intent i = new Intent(Dashboard.this,LoginActivity.class);
                    startActivity(i);
                    finishAffinity();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            displaySelectedScreen(item.getItemId());
        }
        return true;
    }
}

//TODO: Change highlight of drawer
//TODO: Add notifications 1.Offline complete 2.Monthly notify 3.Meal Cancellation accept 4.Notice Board