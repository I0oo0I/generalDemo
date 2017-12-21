package com.kxy.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsOperations;
import org.springframework.stereotype.Service;

import com.kxy.demo.model.User;

@Service
public class AlertServiceImpl implements AlertService{

	//JmsTemplate 实现了 JmsOperations，这里注入的实际是 jmsTemplate 的bean
	private JmsOperations jmsOperations;
	
	@Autowired
	public AlertServiceImpl(JmsOperations jmsOperations) {
		this.jmsOperations = jmsOperations;
	}

	@Override
	public void sendUserMessage(User user) {
		//设置目的地 user.queue
//		jmsOperations.send("user.queue", new MessageCreator() {
//			@Override
//			public Message createMessage(Session session) throws JMSException {
//				return session.createObjectMessage(user);
//			}
//		});
		
		//更简单的操作,若jmsTemplate指定了默认的目的地，这个user.queue参数就不需要了
		//JmsOperations, 内置了消息转换器  MappingJacksonMessageConverter， MappingJackson2MessageConverter（json）
		//MarshallingMessageConverter(xml), SimpleMessageConverter(默认, 其他)
		//这个消息转换器可以在xml中配置的
		jmsOperations.convertAndSend("user.queue", user);
	}

}
