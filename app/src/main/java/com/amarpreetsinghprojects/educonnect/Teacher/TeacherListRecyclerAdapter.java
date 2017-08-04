package com.amarpreetsinghprojects.educonnect.Teacher;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amarpreetsinghprojects.educonnect.Classroom;
import com.amarpreetsinghprojects.educonnect.R;

import java.util.ArrayList;

/**
 * Created by kulvi on 07/27/17.
 */

public class TeacherListRecyclerAdapter extends RecyclerView.Adapter<TeacherListRecyclerAdapter.TeacherViewHolder>{

    ArrayList<Classroom> classroomArrayList;
    TeacherOnClickListner listner;

    public TeacherListRecyclerAdapter(ArrayList<Classroom> classroomArrayList, TeacherOnClickListner listner) {
        this.classroomArrayList = classroomArrayList;
        this.listner = listner;
    }

    @Override
    public TeacherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (LayoutInflater.from(parent.getContext())).inflate(R.layout.teacher_classes_list_single_item,parent,false);

        return new TeacherViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TeacherViewHolder holder, int position) {
        holder.onBind(classroomArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return classroomArrayList.size();
    }

    public class TeacherViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public TeacherViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.textview1);
        }

        public void onBind(final Classroom classroom){
            textView.setText(classroom.getClassname());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.onItemClickRecyclerView(classroom);
                }
            });
        }
    }
}
