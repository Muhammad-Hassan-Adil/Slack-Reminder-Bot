package com.Hassan.bot.Slack.Reminder.Bot.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class SlackMsgSenderThreaderService extends Thread{
    private String payload, webhookUrl;
    private long delay;

    public SlackMsgSenderThreaderService(String payload, long delay, String webhookUrl){
        this.payload = payload;
        this.delay = delay;
        this.webhookUrl = webhookUrl;
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
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
