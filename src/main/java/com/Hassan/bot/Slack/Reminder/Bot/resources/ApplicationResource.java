package com.Hassan.bot.Slack.Reminder.Bot.resources;

import com.Hassan.bot.Slack.Reminder.Bot.domain.Message;
import com.Hassan.bot.Slack.Reminder.Bot.services.SlackMsgSenderThreaderService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.MediaType;
import java.time.format.DateTimeFormatter;
import org.springframework.web.bind.annotation.*;
import java.time.*;
import java.util.List;
import java.util.Map;

@RestController
public class ApplicationResource {
    @Value("${webhook.url}")
    private String webhookUrl;
    @Autowired
    private MessageRepoResource MessageRepoResource;

    @PostMapping(path = "/send-private-message", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void sendPrivateMessage(@RequestBody Map<String, String> requestData) {
        String slackUser = requestData.get("slackUser");
        String message = requestData.get("message");
        String reminderTimeString = requestData.get("reminderTime");
        LocalDateTime reminderTime = LocalDateTime.parse(reminderTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        LocalDateTime reminderTimeWithSeconds = reminderTime.withSecond(0);
        String payload = String.format("{\"channel\": \"%s\", \"text\": \"%s\", \"datetime\": \"%s\"}", slackUser, message, reminderTimeWithSeconds);
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now, reminderTimeWithSeconds);
        long seconds = duration.getSeconds();
        Message message1 = new Message(message, slackUser, reminderTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), reminderTime.format(DateTimeFormatter.ofPattern("HH:mm")));
        MessageRepoResource.save(message1);
        SlackMsgSenderThreaderService slackMsgSenderThreaderService = new SlackMsgSenderThreaderService(payload, seconds * 1000, webhookUrl, message1, MessageRepoResource);
        slackMsgSenderThreaderService.start();
    }

    @GetMapping("/get-all-messages")
    public String getAllMessages() {
        return MessageRepoResource.findAll().toString();
    }

    @GetMapping("/get-message-by-userid")
    public List<Message> getMessageByUserId(@RequestParam String slackUser) {
        return MessageRepoResource.findByslack_user_id(slackUser);
    }
}
