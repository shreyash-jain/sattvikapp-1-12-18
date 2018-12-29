package com.example.shreyash.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;

public class IntroActivity extends MaterialIntroActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableLastSlideAlphaExitTransition(false);




        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.firstpage)
                        .buttonsColor(R.color.colorAccent)
                        .image(R.drawable.pic1)
                        .title("\n"+"\n"+"Daily Menu and Feedback")
                        .description("\n"+"Know what's cooking before hand" +"\n"+
                                " and rate it so that we improve" +"\n"+"our qualty and taste")
                        .build()
              );

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.secondpage)
                .buttonsColor(R.color.yellowvla)
                .image(R.drawable.cancel)
                .title("\n"+"\n"+"Meals Cancellation")
                .description("\n"+"Cancel your meals to your convenience"+"\n"+"before holidays or an outing just"+"\n"+"tell us 12 hours before")
                .build()



        );

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.thirdpage)
                .buttonsColor(R.color.colorAccent)
                .image(R.drawable.bills)
                .title("\n"+"\n"+"Track Bills")
                .description("\n"+"Track your daily expenses"+"\n"+"and get a monthly estimate so "+"\n"+" that you can redeem maximum refund")
                .build()



        );

    }
    @Override
    public void onFinish() {
        super.onFinish();
        Intent i = new Intent(IntroActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}
