package com.jain.shreyash.myapplication;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class ActivityTeam extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simulateDayNight(/* DAY */ 0);
        Element adsElement = new Element();
        adsElement.setTitle("Advertise with us");

        View aboutPage = new AboutPage(this)
                .isRTL(false)


                .setDescription(Html.fromHtml("<b>"+"•Sattvik Mess A Team•"+"</b>"+"<br><br><p align=\"right\">•Tanish Jain<br>•Shreya Munshi<br>•Himanshu Bhadera<br>•Dhawal Jain<br>•Bhoomik Bhamavat<br>•Ria Signoria<br>•Shreyash Jain<br><br><b></p>"+"•Sattvik App Team•<br><br>"+"</b>"+"•Chavvi Jain<br>•Shreya Munshi<br>•Himanshu Bhadera<br>•Bhoomik Bhamavat<br>•Anant Gowadiya<br>•Abhinav Dangi<br>Shreyash Jain"))
                .setImage(R.drawable.icon2)
                .addItem(new Element().setTitle("Version 2.0"))

                .addGroup("Always ready to help you")
                .addEmail("sattvikmess@gmail.com")
                .addWebsite("http://sattvikmess.com/")



                .addPlayStore("com.jain.shreyash.myapplication")

                .addGitHub("shreyash-jain")
                .addItem(getCopyRightsElement())
                .create();

        setContentView(aboutPage);
    }


    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        final String copyrights = "Sattvik App Team | 2019";
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIconDrawable(R.drawable.about_icon_copy_right);
        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.white);
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityTeam.this, copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }

    void simulateDayNight(int currentSetting) {
        final int DAY = 0;
        final int NIGHT = 1;
        final int FOLLOW_SYSTEM = 3;

        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        if (currentSetting == DAY && currentNightMode != Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        } else if (currentSetting == NIGHT && currentNightMode != Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else if (currentSetting == FOLLOW_SYSTEM) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }
}
