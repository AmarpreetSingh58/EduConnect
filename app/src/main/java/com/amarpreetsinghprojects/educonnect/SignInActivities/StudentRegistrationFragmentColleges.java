package com.amarpreetsinghprojects.educonnect.SignInActivities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

public class StudentRegistrationFragmentColleges extends Fragment {

    ListView collegeListView;
    FloatingSearchView collegeFloatingSearchView;
    String collegename=null;
    ArrayAdapter<String> collegelistAdapter;
    DatabaseReference dbref;
    ArrayList<String> collegesList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_student_registration_college,container,false);
        collegeListView = (ListView) v.findViewById(R.id.select_college_listView);
        collegeFloatingSearchView = (FloatingSearchView)v.findViewById(R.id.select_collegeFloatingsearch);

        collegesList = new ArrayList<>();

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> dataSnapshots = dataSnapshot.child("Colleges").getChildren();
                for (DataSnapshot ds: dataSnapshots){
                    String college = ds.getKey();
                    collegesList.add(college);
                }

                collegelistAdapter = new ArrayAdapter<String>(container.getContext(),android.R.layout.simple_list_item_1,collegesList);
                collegeListView.setAdapter(collegelistAdapter);
                collegeFloatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
                    @Override
                    public void onSearchTextChanged(String oldQuery, String newQuery) {
                        collegelistAdapter.getFilter().filter(newQuery);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(container.getContext(), "Database read error pls try again later", Toast.LENGTH_SHORT).show();
            }
        });
        
        
        collegeFloatingSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                // TODO: 07/27/17  
            }
        });
        
        
        collegeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: 07/27/17
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(container.getContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.COLLEGE_NAME,collegelistAdapter.getItem(position));
                editor.apply();
            }
        });

        return v;
    }


}
