package de.herzog.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.herzog.util.Helper;

@ManagedBean(name = Messages.MANAGED_BEAN_NAME)
@SessionScoped
public class Messages extends AbstractController {

	public static final String MANAGED_BEAN_NAME = "Messages";
	
	private static final long serialVersionUID = -7367220556294131763L;

	private List<String> messages;
	
	public static void append(String message) {
		Messages msg = Helper.getManagedBean(Messages.class);
		
		List<String> messages = msg.getMessages();
		
		if (messages == null) {
			messages = new ArrayList<String>();
		}
		
		messages.add(0, message);
		
		msg.setMessages(messages);
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
}
