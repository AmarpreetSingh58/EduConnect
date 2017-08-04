package com.amarpreetsinghprojects.educonnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amarpreetsinghprojects.educonnect.Onboarding.CustomIntro;
import com.amarpreetsinghprojects.educonnect.SignInActivities.RegisterAsActivity;
import com.amarpreetsinghprojects.educonnect.Teacher.TeacherActivity;
import com.amarpreetsinghprojects.educonnect.chatMessages.ChatMessages;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences firsttime = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if (firsttime.getInt(Constants.COMPLETED_ONBOARDING_PREF,0) == 0);
        {
            // first Launch show onboaarding
            startActivity(new Intent(this, CustomIntro.class));
            finish();
        }

        // Initialize Firebase variables
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser == null && firsttime.getInt(Constants.COMPLETED_ONBOARDING_PREF,0)!= 0){
            //Not signed in start registration activity
            startActivity(new Intent(this, RegisterAsActivity.class));
            finish();
        }

        final int usertype = firsttime.getInt(Constants.USER_TYPE,Constants.DEFAULT_RC_SIGN_IN);


        if ((firebaseUser != null)&&(usertype != Constants.DEFAULT_RC_SIGN_IN)){

            Intent i;

            if (usertype == Constants.TEACHER_RC_SIGN_IN){
                i = new Intent(MainActivity.this, TeacherActivity.class);
                i.putExtra(Constants.USER_TYPE,usertype);
                startActivity(i);
            }
            if (usertype == Constants.STUDENT_RC_SIGN_IN){
                i = new Intent(MainActivity.this,ChatMessages.class);
                i.putExtra(Constants.USER_TYPE,usertype);
                startActivity(i);
            }
            finish();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
