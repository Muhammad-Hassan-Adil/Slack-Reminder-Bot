package com.Hassan.bot.Slack.Reminder.Bot.services;

import com.Hassan.bot.Slack.Reminder.Bot.domain.Message;
import com.Hassan.bot.Slack.Reminder.Bot.resources.ApplicationResource;
import com.Hassan.bot.Slack.Reminder.Bot.resources.MessageRepoResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.mysql.cj.conf.PropertyKey.logger;

public class SlackMsgSenderThreaderService extends Thread{
    private static final Logger logger = LogManager.getLogger(SlackMsgSenderThreaderService.class.getName());
    private String payload, webhookUrl;
    private long delay;
    private Message message1;
    private MessageRepoResource messageRepoResource;

    public SlackMsgSenderThreaderService(String payload, long delay, String webhookUrl, Message message1, MessageRepoResource messageRepoResource) {
        this.webhookUrl = webhookUrl;
        this.payload = payload;
        this.delay = delay;
        this.message1 = message1;
        this.messageRepoResource = messageRepoResource;
    }
    @Override
    public void run(){
        try {
            Thread.sleep(delay);
            Optional<Message> optionalMessage = messageRepoResource.findById(message1.getID());
            if (optionalMessage.isPresent()) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> httpEntity = new HttpEntity<>(payload, headers);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.postForObject(webhookUrl, httpEntity, String.class);
                messageRepoResource.delete(message1);
                logger.info("Message Sent");
            }
            else {
                logger.info("Message Deleted");
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
