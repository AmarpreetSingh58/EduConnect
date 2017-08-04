package com.amarpreetsinghprojects.educonnect.Teacher;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amarpreetsinghprojects.educonnect.Constants;
import com.amarpreetsinghprojects.educonnect.R;
import com.amarpreetsinghprojects.educonnect.chatMessages.ChatMessages;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TeacherRegisterationActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_registeration);

        Log.d("TAG","teacher activity");

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        dbRef = firebaseDatabase.getReference();

        TextView textView = (TextView)findViewById(R.id.teacherSignedinTV);
        textView.setText("Welcome \n"+user.getDisplayName());

        Teacher teacher = new Teacher(user.getDisplayName(),user.getEmail(),user.getUid());
        dbRef.child(Constants.TEACHER).child(user.getUid()).setValue(teacher)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(TeacherRegisterationActivity.this, "Welcome "+user.getDisplayName(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void takeTonext(View view) {
        Intent intent = new Intent(TeacherRegisterationActivity.this, TeacherActivity.class);
        intent.putExtra(Constants.USER_TYPE,Constants.TEACHER_RC_SIGN_IN);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
