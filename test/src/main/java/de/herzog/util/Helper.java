package de.herzog.util;

import javax.faces.context.FacesContext;

import de.herzog.controller.AbstractController;

public class Helper {
	private static Logger log = new Logger(Helper.class);
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static <B extends AbstractController> B getManagedBean(Class<B> clazz) {
		try {
			String beanName = clazz.getField("MANAGED_BEAN_NAME").get(null).toString();
			
			FacesContext facesContext = FacesContext.getCurrentInstance();
			
			B controller = (B) facesContext.getApplication().createValueBinding("#{" + beanName + "}").getValue(facesContext);
			
			return controller;
		} catch (Exception e) {
			log.error(e);
		}
		
		return null;
	} 
}
