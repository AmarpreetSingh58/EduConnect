package com.amarpreetsinghprojects.educonnect.SignInActivities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.amarpreetsinghprojects.educonnect.Colleges;
import com.amarpreetsinghprojects.educonnect.Constants;
import com.amarpreetsinghprojects.educonnect.R;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by kulvi on 07/27/17.
 */

public class StudentRegistrationFragmentCourses extends Fragment {

    ListView courseListView;
    FloatingSearchView courseFloatingSearchView;
    String collegename=null,coursename = null;
    ArrayAdapter<String> courselistAdapter;
    DatabaseReference dbref;
    ArrayList<String> coursesList;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_student_registration_courses,container,false);
        courseListView = (ListView)v.findViewById(R.id.select_course_listView);
        courseFloatingSearchView = (FloatingSearchView)v.findViewById(R.id.select_courseFloatingsearch);
        coursesList = new ArrayList<>();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(container.getContext());
        collegename = sharedPreferences.getString(Constants.COLLEGE_NAME,null);

        if (!(collegename.equals(null))){

            dbref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> dataSnapshots = dataSnapshot.child("Colleges").child(collegename).getChildren();
                    for (DataSnapshot ds: dataSnapshots){
                        String name = ds.getKey();
                        coursesList.add(name);
                    }

                    courselistAdapter = new ArrayAdapter<String>(container.getContext(),android.R.layout.simple_list_item_1,coursesList);
                    courseListView.setAdapter(courselistAdapter);
                    courseFloatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
                        @Override
                        public void onSearchTextChanged(String oldQuery, String newQuery) {
                            courselistAdapter.getFilter().filter(newQuery);
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(container.getContext(), "Database read error pls try again later", Toast.LENGTH_SHORT).show();
                }
            });

            courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    editor = sharedPreferences.edit();
                    editor.putString(Constants.COURSE_NAME,courselistAdapter.getItem(position));
                    editor.apply();
                }
            });

        }
        else {
            Toast.makeText(container.getContext(), "Please sellect a college first", Toast.LENGTH_SHORT).show();
        }


        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
