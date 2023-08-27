package com.Hassan.bot.Slack.Reminder.Bot.domain;
import jakarta.persistence.*;

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
    private String date;
    @Column
    private String time;

    public Message() {
    }

    public Message(String text, String slack_handle, String date, String time) {
        this.text = text;
        this.slack_user_id = slack_handle;
        this.date = date;
        this.time = time;
    }
    public int getID() { return ID;}
    public String getText() {
        return text;
    }
    public String getSlack_user_id() {
        return slack_user_id;
    }
    public String getDate() {
        return date;
    }
    public String getTime() { return time; }
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setSlack_user_id(String slack_handle) {
        this.slack_user_id = slack_handle;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public void setTime(String time) { this.time = time; }
    public String toString() {
        return "[" +
                "{ \"ID\": " + ID + ", \"Message\": \"" + text +
                "\", \"Slack Handle\": \"" + slack_user_id + "\", \"Date\": \"" +
                date + "\", \"Time\": \"" + time + "\" }" +
                "]";
    }
}
