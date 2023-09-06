package com.example.springintegrationheadervalue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.router.HeaderValueRouter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class IntegrationConfig {

    @Bean
    public MessageChannel inputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel jsonChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel xmlChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "inputChannel")
    public HeaderValueRouter headerValueRouter() {
        HeaderValueRouter router = new HeaderValueRouter("payload");
        router.setChannelMapping("json", "jsonChannel");
        router.setChannelMapping("xml", "xmlChannel");
        router.setDefaultOutputChannelName("unknownChannel");
        return router;
    }

    @Bean
    @ServiceActivator(inputChannel = "jsonChannel")
    public MessageHandler jsonMessageHandler() {
        return message -> {
            System.out.println("Received JSON payload: " + message.getPayload());
        };
    }

    @Bean
    @ServiceActivator(inputChannel = "xmlChannel")
    public MessageHandler xmlMessageHandler() {
        return message -> {
            System.out.println("Received XML payload: " + message.getPayload());
            System.out.println("===================================================================");
        };
    }

    @Bean
    @ServiceActivator(inputChannel = "unknownChannel")
    public MessageHandler unknownMessageHandler() {
        return message -> {
            System.out.println("Received unknown payload: " + message.getPayload());
            System.out.println("===================================================================");
        };
    }
}