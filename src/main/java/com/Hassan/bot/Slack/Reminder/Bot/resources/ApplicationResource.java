package com.Hassan.bot.Slack.Reminder.Bot.resources;
import com.Hassan.bot.Slack.Reminder.Bot.domain.Message;
import com.Hassan.bot.Slack.Reminder.Bot.services.SlackMsgSenderThreaderService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


@RestController
public class ApplicationResource {
    @Value("${webhook.url}")
    private String webhookUrl;
    @Autowired
    private MessageRepoResource MessageRepoResource;

    @PostMapping("/send-private-message")
    public String sendPrivateMessage(@RequestParam String slackUser, String message, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime reminderTime) {
        LocalDateTime reminderTimeWithSeconds = reminderTime.withSecond(0);
        String payload = String.format("{\"channel\": \"%s\", \"text\": \"%s\", \"datetime\": \"%s\"}", slackUser, message, reminderTimeWithSeconds);
        Message message1 = new Message(message, slackUser, reminderTimeWithSeconds);
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now, reminderTimeWithSeconds);
        long seconds = duration.getSeconds();
        MessageRepoResource.save(message1);
        SlackMsgSenderThreaderService slackMsgSenderThreaderService = new SlackMsgSenderThreaderService(payload, seconds * 1000, webhookUrl);
        slackMsgSenderThreaderService.start();
        MessageRepoResource.delete(message1);
        return "Message saved";
    }
    @GetMapping("/get-all-messages")
    public String getAllMessages() {
        return MessageRepoResource.findAll().toString();
    }

    @GetMapping("/get-message-by-userid")
    public List<Message> getMessageByUserId(@RequestParam String slackUser) {
        return MessageRepoResource.findBySlack_user_id(slackUser);
    }
}
