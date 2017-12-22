package com.kxy.demo.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxy.demo.model.User;

@Service
public class RabbitTemplateService {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public void sendMesssage(User user) {
		rabbitTemplate.convertAndSend("fannoutQueueExchange", "queryMQ1", user);
	}
}
