package com.jain.shreyash.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jain.shreyash.myapplication.R;
import com.jain.shreyash.utils.Constants;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences sharedPreferences;
    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        if(!isInternetPresent){
            showAlertDialog(Dashboard.this, "No Internet Connection",
                    "You don't have internet connection.", false);
        }






        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);
        TextView name = header.findViewById(R.id.main_name);
        TextView email = header.findViewById(R.id.main_EmailId);
        //TODO: CHange name and sattvik id of person from here. Can change image also.
        sharedPreferences = getSharedPreferences(Constants.MY_PREFERENCE, Context.MODE_PRIVATE);
        String user_email = sharedPreferences.getString(Constants.email,"");
        String user_name = sharedPreferences.getString(Constants.name,"");
//        String personName = "AbhinString personEmail = s;
        name.setText(user_name);
        email.setText(user_email);

        Fragment myFragment=new FragmentBoard();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame,  myFragment, "MY_FRAGMENT");
        ft.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        FragmentBoard myFragment = (FragmentBoard) getSupportFragmentManager().findFragmentByTag("MY_FRAGMENT");

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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
                    //TODO: Delete data from shared preferences
                    sharedPreferences = getSharedPreferences(Constants.MY_PREFERENCE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(Constants.name);
                    editor.remove(Constants.email);
                    editor.remove(Constants.pin);
                    editor.remove(Constants.isactive);
                    editor.apply();

                    //Deleting internal storage
                    Dashboard.this.deleteFile("CancelData");

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
    @Override
    public void onResume(){
        super.onResume();

        Intent intent = getIntent();

        String frag = intent.getExtras().getString("EXTRA");
       // String wname =intent.getExtras().getString("WEXTRA");

        switch(frag){

            case "openFragment":
                Fragment fragment1=new FragmentFeedback();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment1).commit();
                Bundle bundle = new Bundle();
                bundle.putInt("pass", 0);
               // bundle.putString("wname",wname);
                fragment1.setArguments(bundle);
                break;
        }
    }
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon((status) ? R.drawable.ic_signal_wifi_off_black_24dp : R.drawable.ic_signal_wifi_off_black_24dp);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}

//TODO: Add back button on each page
//TODO: Change highlight of drawer
//TODO: Add notifications 1.Offline complete 2.Monthly notify 3.Meal Cancellation accept 4.Notice Board