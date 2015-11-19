package de.herzog.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.herzog.db.beans.BinaryBean;
import de.herzog.db.repositories.BinaryRepository;

@WebServlet
public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = 522750674107737313L;
	
	private static HashMap<String, BinaryBean> cache;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (cache == null) {
			cache = new HashMap<String, BinaryBean>();
		}
		
		String imageId = String.valueOf(request.getPathInfo().substring(1));
		System.out.println(imageId);
		BinaryBean image = null;
		if (cache.containsKey(imageId)) {
			image = cache.get(imageId);
		} else {
			BinaryRepository repo = new BinaryRepository();
			image = repo.getByUuid(imageId);
			
			cache.put(imageId, image);
		}
		
		if (image != null) {
			switch (image.getType()) {
			case DOC:
				break;
			case GIF:
				response.setHeader("Content-Type", "image/gif");
				break;
			case JPG:
				response.setHeader("Content-Type", "image/jpeg");
				break;
			case PDF:
				break;
			case PNG:
				response.setHeader("Content-Type", "image/png");
				break;
			default:
				break;			
			}
			
			response.setHeader("Content-Disposition", "inline; filename=\"" + image.getFilename() + "\"");
	
			BufferedOutputStream output = null;
	
			try {
				byte[] input = image.getData(); // Creates
																	// buffered
																	// input stream.
				output = new BufferedOutputStream(response.getOutputStream());
				output.write(input, 0, input.length);
			} finally {
				if (output != null)
					try {
						output.close();
					} catch (IOException logOrIgnore) {
					}
			}
		}
	}
}
