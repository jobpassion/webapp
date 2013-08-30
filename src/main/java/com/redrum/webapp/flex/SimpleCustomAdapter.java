package com.redrum.webapp.flex;

import flex.messaging.services.messaging.adapters.MessagingAdapter;
import flex.messaging.services.MessageService;
import flex.messaging.messages.Message;
import flex.messaging.Destination;

public class SimpleCustomAdapter extends MessagingAdapter {

    public Object invoke(Message message) {
//        MessageService msgService = (MessageService)service;
//        msgService.pushMessageToClients(message, true);
//        msgService.sendPushMessageFromPeer(message, true);
    	
        return null;
    }
}
