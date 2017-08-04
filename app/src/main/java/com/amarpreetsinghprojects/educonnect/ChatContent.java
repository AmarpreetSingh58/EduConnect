package com.amarpreetsinghprojects.educonnect;

import java.text.SimpleDateFormat;

/**
 * Created by kulvi on 07/31/17.
 */

public class ChatContent {

    String timestamp,messageBody;

    public ChatContent() {
    }

    public ChatContent(String messageBody) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        timestamp = simpleDateFormat.format(System.currentTimeMillis());
        this.messageBody = messageBody;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}
