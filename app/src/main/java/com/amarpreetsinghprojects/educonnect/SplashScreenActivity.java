package com.amarpreetsinghprojects.educonnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amarpreetsinghprojects.educonnect.Onboarding.CustomIntro;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000;
   // public static final String COMPLETED_ONBOARDING_PREF = "completed_onboarding_pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                /*SharedPreferences firsttime = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                if (firsttime.getInt(COMPLETED_ONBOARDING_PREF,0) == 0);
                {
                    // first Launch show onboaarding
                    startActivity(new Intent(SplashScreenActivity.this, CustomIntro.class));
                }*/

                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}

