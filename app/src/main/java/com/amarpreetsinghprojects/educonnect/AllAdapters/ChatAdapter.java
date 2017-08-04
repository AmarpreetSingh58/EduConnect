package com.amarpreetsinghprojects.educonnect.AllAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amarpreetsinghprojects.educonnect.ChatContent;
import com.amarpreetsinghprojects.educonnect.R;

import java.util.ArrayList;

/**
 * Created by kulvi on 07/31/17.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder>{

    ArrayList<ChatContent> chatContentArrayList;

    public ChatAdapter(ArrayList<ChatContent> chatContentArrayList) {
        this.chatContentArrayList = chatContentArrayList;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (LayoutInflater.from(parent.getContext())).inflate(R.layout.chat_single_item,parent,false);

        return new ChatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        ChatContent chatContent  = chatContentArrayList.get(position);
        holder.chatBody.setText(chatContent.getMessageBody());
        holder.chatTime.setText(chatContent.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return chatContentArrayList.size();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView chatBody,chatTime;
        public ChatViewHolder(View itemView) {
            super(itemView);
            chatBody = (TextView)itemView.findViewById(R.id.messageBodyTV);
            chatTime = (TextView)itemView.findViewById(R.id.messageTimestampTV);
        }
    }
}
