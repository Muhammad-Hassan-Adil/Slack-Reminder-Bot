package com.Hassan.bot.Slack.Reminder.Bot.resources;

import com.Hassan.bot.Slack.Reminder.Bot.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepoResource extends JpaRepository<Message, Integer>{
    List<Message> findBySlack_user_id(String slack_user_id);
}
