package com.foo;

import java.util.List;

import flex.messaging.FlexContext;
import flex.messaging.client.FlexClient;
import flex.messaging.client.FlexClientOutboundQueueProcessor;
import flex.messaging.messages.Message;

public class YourProcessor extends FlexClientOutboundQueueProcessor {

	@Override
	public void add(List<Message> outboundQueue, Message message) {
		// TODO Auto-generated method stub
		FlexClient flexClient = getFlexClient();
		flexClient.getAttributeNames().hasMoreElements();
		super.add(outboundQueue, message);
	}
	
	
	
	

}
