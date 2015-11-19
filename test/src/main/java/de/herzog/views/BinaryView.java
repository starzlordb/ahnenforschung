package de.herzog.views;

import java.io.BufferedOutputStream;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import de.herzog.controller.person.PersonController;
import de.herzog.db.beans.BinaryBean;

public class BinaryView extends BinaryBean implements AbstractView {

	private static final long serialVersionUID = -7584559369995315557L;

	private PersonController controller;
	
	public String getExtension() {
		String[] splitted = getFilename().split("\\.");
		
		return splitted[splitted.length - 1].toLowerCase();
	}

	public PersonController getController() {
		return controller;
	}

	public void setController(PersonController controller) {
		this.controller = controller;
	}

	public void download() throws Exception {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

		response.reset(); 
		response.setContentType(getType().getMimeType());
		response.setHeader("Content-disposition",
				"attachment; filename=\"" + getFilename() + "\"");
		
		BufferedOutputStream output = null;

		try {
			output = new BufferedOutputStream(response.getOutputStream());

			output.write(getData(), 0, getData().length);
		} finally {
			output.close();
		}

		facesContext.responseComplete();
	}
}
