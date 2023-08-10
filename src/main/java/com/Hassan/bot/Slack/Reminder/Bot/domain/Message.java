package com.Hassan.bot.Slack.Reminder.Bot.domain;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    @Column
    private String text;
    @Column
    private String slack_user_id;
    @Column
    private LocalDateTime datetime;

    public Message() {
    }

    public Message(String text, String slack_handle, LocalDateTime datetime) {
        this.text = text;
        this.slack_user_id = slack_handle;
        this.datetime = datetime;
    }
    public int getID() {
        return ID;
    }
    public String getText() {
        return text;
    }
    public String getSlack_user_id() {
        return slack_user_id;
    }
    public LocalDateTime getTime_min() {
        return datetime;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setSlack_user_id(String slack_handle) {
        this.slack_user_id = slack_handle;
    }

    public void setTime_min(LocalDateTime datetime) {
        this.datetime = datetime;
    }
    @Override
    public String toString() {
        return "Message{" +
                "id=" + ID +
                ", text='" + text + '\'' +
                ", slack_handle='" + slack_user_id + '\'' +
                ", time_min=" + datetime +
                '}';
    }
}
