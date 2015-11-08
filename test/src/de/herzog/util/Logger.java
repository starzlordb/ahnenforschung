package de.herzog.util;

import java.io.Serializable;
import java.util.Calendar;

import de.herzog.controller.Messages;

public class Logger implements Serializable {
	
	private static final long serialVersionUID = -9083338478470584129L;
	
	private Class<? extends Object> clazz;
	
	public Logger(Class<? extends Object> clazz) {
		this.clazz = clazz;
	}
	
	private String outputPrefix(String type) {
		StringBuilder output = new StringBuilder();
		
		Calendar now = Calendar.getInstance();
		
		String time = (now.get(Calendar.HOUR_OF_DAY) < 10 ? "0" : "") + now.get(Calendar.HOUR_OF_DAY) + ":" + 
		(now.get(Calendar.MINUTE) < 10 ? "0" : "") + now.get(Calendar.MINUTE);
		
		output.append("[" + time + ", ");
		output.append(clazz.getName() + ", ");
		output.append(type + "] ");
		
		return output.toString();
	}
	
	private String createMessage(Object message, boolean showStacktrace) {
		if (message == null) {
			return null;
		}
		
		if (message instanceof Exception && showStacktrace) {
			((Exception) message).printStackTrace();
		}
		
		if (message instanceof Exception) {
			String msg = ((Exception) message).getStackTrace()[0].getLineNumber() + " ; " + message;
			return msg;
		}
		
		return message.toString();
	}
	
	public void info(Object message) {
		log("info", message, false);
	}
	
	private void log(String type, Object message, boolean printStacktrace) {
		String msg = outputPrefix(type) + createMessage(message, printStacktrace);
		
		Messages.append(msg);
		
		System.out.println(msg);
	}
	
	public void debug(Object message) {
		log("debug", message, true);
	}
	
	public void warn(Object message) {
		log("warn", message, false);
	}
	
	public void error(Object message) {
		log("error", message, true);
	}

}
