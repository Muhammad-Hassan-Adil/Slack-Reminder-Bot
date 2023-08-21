package com.Hassan.bot.Slack.Reminder.Bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SlackReminderBotApplication {
	public static void main(String[] args) {
		SpringApplication.run(SlackReminderBotApplication.class, args);
	}
}
