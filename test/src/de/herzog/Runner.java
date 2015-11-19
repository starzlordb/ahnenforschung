package de.herzog;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class Runner {

	public static void main(String[] args) {
		try {
			Tomcat tomcat = new Tomcat();
		
			tomcat.setPort(8081);
			tomcat.start();
			
			tomcat.getServer().await();
		} catch (LifecycleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
