package com.Hassan.bot.Slack.Reminder.Bot.services;

import com.Hassan.bot.Slack.Reminder.Bot.domain.Message;
import com.Hassan.bot.Slack.Reminder.Bot.resources.MessageRepoResource;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class SlackMsgSenderThreaderService extends Thread{
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
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(payload, headers);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(webhookUrl, httpEntity, String.class);
            messageRepoResource.delete(message1);
//            ApplicationResource.deleteDBMessage(message1, messageRepoResource);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
