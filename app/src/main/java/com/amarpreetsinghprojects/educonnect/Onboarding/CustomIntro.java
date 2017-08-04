package com.amarpreetsinghprojects.educonnect.Onboarding;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;

import com.amarpreetsinghprojects.educonnect.Constants;
import com.amarpreetsinghprojects.educonnect.MainActivity;
import com.amarpreetsinghprojects.educonnect.R;
import com.amarpreetsinghprojects.educonnect.SignInActivities.RegisterAsActivity;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

/**
 * Created by kulvi on 07/22/17.
 */

public class CustomIntro extends AppIntro {

    SharedPreferences mGetPref;
    SharedPreferences.Editor sharedPrefEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGetPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sharedPrefEdit = mGetPref.edit();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTheme(R.style.FullScreenTheme);
        }

        Bitmap bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(),
                R.drawable.teacher_sign_in_logo);
        Palette p = Palette.from(bitmap).generate();
        addSlide(AppIntroFragment.newInstance(getString(R.string.choose_avatar),getString(R.string.teacher_log_in),R.drawable.teacher_sign_in_logo,p.getVibrantColor(Color.parseColor("#B39DDB"))));

        bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(),
                R.drawable.student_sign_in_logo);
        p = Palette.from(bitmap).generate();
        addSlide(AppIntroFragment.newInstance(getString(R.string.choose_avatar),getString(R.string.student_log_in),R.drawable.student_sign_in_logo,p.getVibrantColor(Color.parseColor("#90CAF9"))));

        bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(),
                R.drawable.connect_with_multiple);
        p = Palette.from(bitmap).generate();
        addSlide(AppIntroFragment.newInstance(getString(R.string.connect_with_multiple),getString(R.string.connect_multiple_students),R.drawable.connect_with_multiple,p.getVibrantColor(Color.parseColor("#90CAF9"))));

        addSlide(AppIntroFragment.newInstance(getString(R.string.app_name),getString(R.string.lets_start),R.drawable.icon,Color.parseColor("#90CAF9")));

        setImmersiveMode(true);
        setColorTransitionsEnabled(true);

        showSkipButton(true);
        setProgressButtonEnabled(true);


    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);

        finishOnboarding();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

       finishOnboarding();
    }

    private void finishOnboarding(){
        sharedPrefEdit.putInt(Constants.COMPLETED_ONBOARDING_PREF,1);
        sharedPrefEdit.apply();
        Intent i = new Intent(this, RegisterAsActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
