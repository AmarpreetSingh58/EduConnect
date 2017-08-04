package com.amarpreetsinghprojects.educonnect.Teacher;

import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.amarpreetsinghprojects.educonnect.AllAdapters.ChatAdapter;
import com.amarpreetsinghprojects.educonnect.ChatContent;
import com.amarpreetsinghprojects.educonnect.Constants;
import com.amarpreetsinghprojects.educonnect.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TeacherClassroomActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbRef;
    String classUID;
    ArrayList<ChatContent> chatArrayList = new ArrayList<>();
    RecyclerView teacherClassrooms;
    ChatAdapter adapter;
    EditText messagebodyinput;
    FloatingActionButton addstudnts,addevents,addbutton,sendmessagebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_classroom);

        Intent i = getIntent();

        classUID = i.getStringExtra(Constants.CLASSROOMS);

        firebaseDatabase = FirebaseDatabase.getInstance();
        dbRef = firebaseDatabase.getReference();

        addbutton = (FloatingActionButton)findViewById(R.id.addFAB);
        addstudnts = (FloatingActionButton)findViewById(R.id.addstudentFAB);
        addevents = (FloatingActionButton)findViewById(R.id.addeventFAB);
        sendmessagebutton = (FloatingActionButton)findViewById(R.id.sendbutton);
        messagebodyinput = (EditText)findViewById(R.id.newMessageETV);
        teacherClassrooms = (RecyclerView)findViewById(R.id.teacherClassroomRecyclerView);
        adapter = new ChatAdapter(chatArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,true);
        teacherClassrooms.setLayoutManager(layoutManager);
        teacherClassrooms.setAdapter(adapter);



        getchat();
    }

    public void sendMessage(){

        final ChatContent newMessage = new ChatContent(messagebodyinput.getText().toString());

        dbRef.child(Constants.CLASSROOMS).child(classUID).getRef()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dbRef.child(Constants.CLASSROOMS)
                                .child(classUID)
                                .child(Constants.CHATS)
                                .child(newMessage.getTimestamp())
                        .setValue(newMessage);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void getchat(){
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.child(Constants.CLASSROOMS)
                        .child(classUID)
                        .child(Constants.CHATS)
                        .getChildren();

                chatArrayList.clear();

                for (DataSnapshot ds: dataSnapshotIterable){
                    ChatContent chatContent = ds.getValue(ChatContent.class);
                    chatArrayList.add(chatContent);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
