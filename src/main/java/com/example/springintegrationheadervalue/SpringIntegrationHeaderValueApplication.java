package com.example.springintegrationheadervalue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

@SpringBootApplication
public class SpringIntegrationHeaderValueApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(SpringIntegrationHeaderValueApplication.class, args);

        MessageChannel inputChannel = context.getBean("inputChannel", MessageChannel.class);

        sendMessage(inputChannel, "odd", 1);
        sendMessage(inputChannel, "even", 2);
	}

	private static void sendMessage(MessageChannel channel, String headerValue, int payload) {
        Message<Integer> message = MessageBuilder
                .withPayload(payload)
                .setHeader("number", headerValue)
                .build();
        channel.send(message);
    }

}
