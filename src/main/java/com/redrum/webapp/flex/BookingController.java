package com.redrum.webapp.flex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.messaging.MessageTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BookingController {
	
	private MessageTemplate template;
	
	
	@RequestMapping(value="/bookings")
	public String createBooking(){
		template.send("bookingUpdates", "aaaa");
		return null;
	}
	
	@Autowired
	public void setTemplate(MessageTemplate template) {
		this.template = template;
	}
	
}