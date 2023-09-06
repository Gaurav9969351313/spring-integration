package com.example.springintegrationheadervalue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.router.HeaderValueRouter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

// @Configuration
public class IntConfig {

    @Bean
    public MessageChannel inputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel oddChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel evenChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "inputChannel")
    public HeaderValueRouter headerValueRouter() {
        HeaderValueRouter router = new HeaderValueRouter("number");

        router.setChannelMapping("odd", "oddChannel");
        router.setChannelMapping("even", "evenChannel");

        return router;
    }

    @Bean
    @ServiceActivator(inputChannel = "oddChannel")
    public MessageHandler oddMessageHandler() {
        return message -> {
            System.out.println("Received odd number: " + message.getPayload());
            System.out.println("MsgType header: " + message.getHeaders().get("msgType"));
            System.out.println("=========================================================================");
        };
    }

    @Bean
    @ServiceActivator(inputChannel = "evenChannel")
    public MessageHandler evenMessageHandler() {
        return message -> {
            System.out.println("Received even number: " + message.getPayload());
            System.out.println("MsgType header: " + message.getHeaders().get("msgType"));
            System.out.println("=========================================================================");
        };
    }
}