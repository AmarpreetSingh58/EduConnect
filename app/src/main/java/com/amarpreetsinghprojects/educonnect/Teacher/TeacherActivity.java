package com.amarpreetsinghprojects.educonnect.Teacher;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amarpreetsinghprojects.educonnect.Classroom;
import com.amarpreetsinghprojects.educonnect.Constants;
import com.amarpreetsinghprojects.educonnect.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeacherActivity extends AppCompatActivity {

    RecyclerView teacherClassesRecyclerView;
    FloatingActionButton addclass;
    Classroom classroom;
    TextView emptyText;
    ArrayList<Classroom> classroomArrayList = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbRef;
    FirebaseAuth auth;
    FirebaseUser user;
    TeacherListRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        setTheme(R.style.teacherUI);

        teacherClassesRecyclerView = (RecyclerView)findViewById(R.id.teacherActivityrecyclerView);
        emptyText = (TextView)findViewById(R.id.teacherActivityemptyTV);
        addclass = (FloatingActionButton)findViewById(R.id.teacherActivityaddclassFAB);

        firebaseDatabase = FirebaseDatabase.getInstance();
        dbRef = firebaseDatabase.getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        adapter = new TeacherListRecyclerAdapter(classroomArrayList, new TeacherOnClickListner() {
            @Override
            public void onItemClickRecyclerView(Classroom classroom) {
                Intent i = new Intent(TeacherActivity.this,TeacherClassroomActivity.class);
                i.putExtra(Constants.CLASSROOMS,classroom.getClassuid());
                startActivity(i);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TeacherActivity.this,LinearLayoutManager.VERTICAL,false);
        teacherClassesRecyclerView.setLayoutManager(layoutManager);
        teacherClassesRecyclerView.setAdapter(adapter);

        getClassnames();

        if (classroomArrayList.size() == 0){
            emptyText.setText("No Classes Yet.");
        }
        else{
            emptyText.setVisibility(View.GONE);
        }

        final CardView dialogCardview = (CardView)getLayoutInflater().inflate(R.layout.teacheractivity_addclass_alertdialog,null);
        addclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(TeacherActivity.this)
                        .setTitle("Add Class")
                        .setView(dialogCardview)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText classnameeditText = (EditText)dialogCardview.findViewById(R.id.addclassnameETV);

                                addclasstoFirebase(classnameeditText.getText().toString());
                            }
                        }).show();
            }
        });

    }

    public void addclasstoFirebase(String classname){
        final String classroomuid = classname.concat(user.getUid());
        final Classroom addroom = new Classroom(classname,classroomuid);

        dbRef.child(Constants.CLASSROOMS).child(classroomuid).setValue(addroom)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        dbRef.child(Constants.TEACHER)
                                .child(user.getUid())
                                .child(Constants.CLASSROOMS)
                                .child(classroomuid)
                                .setValue(addroom)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(TeacherActivity.this, "Class added successfully", Toast.LENGTH_SHORT).show();
                                        getClassnames();
                                    }
                                });
                    }
                });
    }

    public void getClassnames(){
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> dataSnapshots = dataSnapshot.child(Constants.TEACHER)
                        .child(user.getUid())
                        .child(Constants.CLASSROOMS)
                        .getChildren();
                classroomArrayList.clear();

                for (DataSnapshot ds: dataSnapshots){
                    classroom = ds.getValue(Classroom.class);
                    classroomArrayList.add(classroom);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
