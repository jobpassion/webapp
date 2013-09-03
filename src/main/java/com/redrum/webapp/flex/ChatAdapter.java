package com.redrum.webapp.flex;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;

import com.redrum.webapp.service.ChatService;

import flex.messaging.services.messaging.adapters.MessagingAdapter;
import flex.messaging.services.MessageService;
import flex.messaging.io.amf.ASObject;
import flex.messaging.messages.Message;
import flex.messaging.Destination;

public class ChatAdapter extends flex.messaging.services.messaging.adapters.ActionScriptAdapter {
	
	@Autowired
	private ChatService chatService;

	@Override
	public Object invoke(Message message) {
		// TODO Auto-generated method stub
		ASObject object = (ASObject) message.getBody();
		object.put("timestamp", new Timestamp(System.currentTimeMillis()));
		if("msg".equals(message.getHeader("type"))){
			
		Integer id = chatService.send(message.getClientId().toString(), message.getHeader("to").toString(), object.get("message").toString());
		object.put("id", id);
		}
		return super.invoke(message);
	}

	
    
}
