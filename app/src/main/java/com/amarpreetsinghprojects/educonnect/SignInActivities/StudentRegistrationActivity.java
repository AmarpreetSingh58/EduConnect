package com.amarpreetsinghprojects.educonnect.SignInActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amarpreetsinghprojects.educonnect.Constants;
import com.amarpreetsinghprojects.educonnect.R;
import com.amarpreetsinghprojects.educonnect.Student;
import com.amarpreetsinghprojects.educonnect.StudentSide.StudentActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentRegistrationActivity extends AppCompatActivity {

    ViewPager studentActivityViewpager;
    Button nextButton;
    String email,username,collegename,course,userID;
    CollegeViewPagerAdapter adapter;
    Boolean nextPressed = false;
    SharedPreferences sharedPreferences;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;
    DatabaseReference dbref;
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("TAG","student registration");
        setContentView(R.layout.activity_student_registration);
        adapter = new CollegeViewPagerAdapter(getSupportFragmentManager());
        ViewPager selectCollegeViewpager = (ViewPager)findViewById(R.id.studentRegidtrationActivityViewpager);
        selectCollegeViewpager.setAdapter(adapter);

        tabLayout = (TabLayout)findViewById(R.id.studentRegidtrationActivityTabLayout);
        tabLayout.setupWithViewPager(selectCollegeViewpager);

        auth = FirebaseAuth.getInstance();
        username = auth.getCurrentUser().getDisplayName();
        email = auth.getCurrentUser().getEmail();
        userID = auth.getCurrentUser().getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        dbref = firebaseDatabase.getReference();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        nextButton = (Button)findViewById(R.id.studentRegidtrationActivitynextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nextPressed == false){
                    nextPressed = true;
                }
                if (nextPressed == true){
                    collegename = sharedPreferences.getString(Constants.COLLEGE_NAME,null);
                    course = sharedPreferences.getString(Constants.COURSE_NAME,null);

                    if ((!(collegename.equals(null)))&&(!(course.equals(null)))){
                        Student s = new Student(username,email,userID,collegename,course);
                        dbref.child(collegename).child(course).child(Constants.STUDENT).child(userID).setValue(s)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(StudentRegistrationActivity.this, "Welcome "+username, Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(StudentRegistrationActivity.this, StudentActivity.class);
                                        i.putExtra(Constants.USER_TYPE,Constants.STUDENT_RC_SIGN_IN);
                                        startActivity(i);
                                    }
                                });
                    }
                }
            }
        });


    }

    class CollegeViewPagerAdapter extends FragmentPagerAdapter{

        public CollegeViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return new StudentRegistrationFragmentColleges();
                case 1: return new StudentRegistrationFragmentCourses();
            }

            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0: return "College";
                case 1: return "Course";
            }
            return null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
