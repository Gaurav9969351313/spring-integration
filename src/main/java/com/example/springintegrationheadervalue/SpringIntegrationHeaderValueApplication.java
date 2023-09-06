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

        // sendMessage(inputChannel, "odd", Integer.toString(3));
        // sendMessage(inputChannel, "even", Integer.toString(2));

		sendMessage(inputChannel, "json", "{\"name\":\"John\", \"age\":30}");
        sendMessage(inputChannel, "xml", "<person><name>Mary</name><age>25</age></person>");

        // Send an unknown payload
        sendMessage(inputChannel, "unknown", "Some other data");
	}

	private static void sendMessage(MessageChannel channel, String headerValue, String payload) {
        Message<String> message = MessageBuilder
                .withPayload(payload)
                .setHeader("number", headerValue)
				.setHeader("msgType", "MT910")
				// .setHeader("payload", headerValue)
				.setHeader("payload", checkPayloadType(payload))
                .build();
        channel.send(message);
    }

	public static boolean isJSON(String input) {
		return input.trim().startsWith("{") && input.trim().endsWith("}");
	}
	
	public static boolean isXML(String input) {
		return input.trim().startsWith("<") && input.trim().endsWith(">");
	}

	public static String checkPayloadType(String payload) {
		if (isJSON(payload)) {
			return PayloadType.JSON.name().toLowerCase();
		} else if (isXML(payload)) {
			return PayloadType.XML.name().toLowerCase();
		} else
		 	return PayloadType.UNKNOWN.name().toLowerCase();
    }

}
