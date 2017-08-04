package com.amarpreetsinghprojects.educonnect.SignInActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

import com.amarpreetsinghprojects.educonnect.Constants;
import com.amarpreetsinghprojects.educonnect.R;
import com.amarpreetsinghprojects.educonnect.Teacher.TeacherRegisterationActivity;
import com.amarpreetsinghprojects.educonnect.Utility;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ResultCodes;

import java.util.Arrays;

public class RegisterAsActivity extends AppCompatActivity {


    SharedPreferences getPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as);

        getPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.teacher_sign_in_logo);
        Palette p = Palette.from(bitmap).generate();
        CardView teacherSignInCardView = (CardView)findViewById(R.id.teacher_sign_in_cardView);
        teacherSignInCardView.setBackgroundColor(p.getDominantColor(Color.parseColor("#CE93D8")));

        bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.student_sign_in_logo);
        p = Palette.from(bitmap).generate();
        CardView studentSignInCardView = (CardView)findViewById(R.id.student_sign_in_cardView);
        studentSignInCardView.setBackgroundColor(p.getDominantColor(Color.parseColor("#B2EBF2")));


        teacherSignInCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentSignIn(R.drawable.teacher_sign_in_logo,R.style.teacherUI, Constants.TEACHER_RC_SIGN_IN);
            }
        });

        studentSignInCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentSignIn(R.drawable.teacher_sign_in_logo,R.style.teacherUI,Constants.STUDENT_RC_SIGN_IN);
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private void studentSignIn(int logo, int style, int RC_SIGN_IN){
        if (Utility.isNetworkAvailable(getApplicationContext())){
            startAuthentication(RC_SIGN_IN,style,logo);
        }
        else {
            CoordinatorLayout coordinatorLayout = new CoordinatorLayout(this);
            Snackbar snackbar = Snackbar.make(coordinatorLayout,"Network not availabele",Snackbar.LENGTH_LONG)
                    .setAction("Settings", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                }
            });

            snackbar.show();
        }
    }

    private void startAuthentication(int RC_SIGN_IN,int theme,int logo){

        Log.d("TAG","Authentication Started");
        startActivityForResult(AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(
                                Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                        new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                        .setIsSmartLockEnabled(false)
                        .setTheme(theme)
                        .setLogo(logo)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("TAG","Authentication done starting activities");

        CoordinatorLayout coordinatorLayout = new CoordinatorLayout(this);

        if (requestCode == Constants.TEACHER_RC_SIGN_IN || requestCode == Constants.STUDENT_RC_SIGN_IN){

            Log.d("TAG","Authentication done starting activities 1");

            if (resultCode == ResultCodes.OK) {
                SharedPreferences.Editor sharedPrefEdit = getPref.edit();
                sharedPrefEdit.putInt(Constants.USER_TYPE,requestCode);
                sharedPrefEdit.apply();

                if (requestCode == Constants.TEACHER_RC_SIGN_IN) {
                    Log.d("TAG","Authentication done starting teacher activity");
                    Intent i = new Intent(RegisterAsActivity.this, TeacherRegisterationActivity.class);
                    i.putExtra(Constants.USER_TYPE, requestCode);
                    startActivity(i);
                    finish();
                }
                if (requestCode == Constants.STUDENT_RC_SIGN_IN){
                    Log.d("TAG","Authentication done starting student activity");
                    Intent i = new Intent(RegisterAsActivity.this, StudentRegistrationActivity.class);
                    startActivity(i);
                    finish();
                }

            }
            if (resultCode == ResultCodes.CANCELED){
                Snackbar.make(coordinatorLayout,"Operation Cancelled by user",Snackbar.LENGTH_LONG).show();
            }

        }
    }
}
