package com.Hassan.bot.Slack.Reminder.Bot.resources;

import com.Hassan.bot.Slack.Reminder.Bot.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepoResource extends JpaRepository<Message, Integer>{
    @Query("SELECT m FROM Message m WHERE m.slack_user_id = :slack_user_id")
    List<Message> findByslack_user_id(String slack_user_id);
}
