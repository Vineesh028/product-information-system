package com.user.portal.service.impl;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.user.portal.model.TextMessage;

import lombok.AllArgsConstructor;

/**
 * Sends messages to user portal UI
 */
@Controller
@AllArgsConstructor
public class WebsocketService {

	private final SimpMessagingTemplate template;

	public void send(TextMessage message) {

		template.convertAndSend("/topic/message", message);

	}
	
	@SendTo("/topic/message")
	public TextMessage broadcastMessage(@Payload TextMessage message) {
		return message;
	}
	
	@MessageMapping("/sendMessage")
	public void receiveMessage(@Payload TextMessage  message) {
		// receive message from client
	}


}
