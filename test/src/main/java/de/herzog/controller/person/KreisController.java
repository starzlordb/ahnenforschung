package de.herzog.controller.person;

import java.util.ArrayList;
import java.util.List;

import de.herzog.controller.AbstractController;
import de.herzog.util.converter.EventViewConverter;
import de.herzog.views.person.PersonView;

public class KreisController extends AbstractController {

	private static final long serialVersionUID = -7345225217469710350L;
	
	private int generations = 4;
	private double winkel = 180d;
	

	@SuppressWarnings("unused")
	private void generateKreis(long personId) {
		//data = generatePerson(id, 1);
		List<PersonView> data = new ArrayList<PersonView>();
		//header("Content-Type: image/svg+xml");

		int textWidth = 10;
		String fontFamily = "'Arial'";

		int radien = 100;
		int offset = 10;

		int size = (radien + offset) * generations;

		double startwinkel = 360d - winkel + (winkel - 180d) / 2d;

		StringBuilder kreis = new StringBuilder("<?xml version=\"1.0\"?>");
		kreis.
		//append("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\0" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">)").
		append("<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" ").
		append("width=\"'").append(size * 2).append("'\" ").
		append("height=\"'").append(size * 2).append("'\">");

		for (int generation = 0; generation < generations; generation++) {
			int radius = generation * (offset + radien);

			kreis.append("<circle cx=\"").append(size).append("\" ").append("cy=\"").append(size).append("\" r=\"").append(radius).append("\" ").append("style=\"stroke: #000; fill: #fff; fill-opacity: 0;\" />");
			kreis.append("<circle cx=\"").append(size).append("\" cy=\"").append(size).append("\" ").append("r=\"").append(radius - offset).append("\" ").append("style=\"stroke: #000; fill: #fff; fill-opacity: 0;\" />");

			for (int l = -1; l < Math.pow(2, generation); l++) {
				int nummer = (int) (Math.pow(2, generation) + l);
				if (nummer == Math.pow(2, generation + 1) - 1)
					nummer = (int) Math.pow(2, generation);
				else
					nummer++;

				double phi = startwinkel + winkel / Math.pow(2, generation) * (double) (l + 1);

				double x = (double) size + (double) radius * Math.cos(Math.toRadians(phi));
				double y = (double) size + (double) radius * Math.sin(Math.toRadians(phi));

				double x2 = (double) size + (double) (generation - 1) * (double) (offset + radien) * Math.cos(Math.toRadians(phi));
				double  y2 = (double) size + (double) (generation - 1) * (double) (offset + radien) * Math.sin(Math.toRadians(phi));

				kreis.append("<line x1=\"").append(x2).append("\" ").append("y1=\"").append(y2).append("\" ").append("x2=\"").append(x).append("\" ").append("y2=\"").append(y).append("\" ").append("style=\"stroke: #000;\" />");

				int fontSize = 12;

				String text = "";
				String text2 = "";
				String text3 = "";

				String color = "#000000";
				for (int d = 0; d < data.size(); d++)
				{
					int len = (data.get(d).getNachname() + ", " + data.get(d).getVornamen()).length();
					//fontSize = fontSize < ceil(radiusSegment / len * 1.4) ? fontSize : ceil(radiusSegment / len * 1.4);

					if (data.get(d).getKekule().contains((long) nummer)) {
						text = data.get(d).getNachname() + ", " + data.get(d).getVornamen();

						String[] tmp = text.split("(");
						text = tmp[0];

						text2 = "* ";
						text2 += new EventViewConverter().getAsString(null, null, data.get(d).getGeburt());
						text2 += " " + data.get(d).getGeburt().getLocation();
	
						text3 = "t ";
						text3 += new EventViewConverter().getAsString(null, null, data.get(d).getTod());
						text3 += " " + data.get(d).getTod().getLocation();
			
						color = data.get(d).isMann() ? "#D5D5FF" : "#FFD5D5";
					}

					//fontSize = fontSize < Math.ceil(radiusSegment / text.length() * 1.4) ? fontSize : Math.ceil(radiusSegment / text.length() * 1.4);
					//text = phi.' '.nummer.' '.text;
					text = " " + text;
                    fontSize = (int) Math.ceil(fontSize);
                    color = "#000000";
/*
                    echo '<text x="'.(size).'" y="'.(size - radius / 3).'" style="font-family: '.fontFamily.'; font-size: 12px; stroke:none; fill:'.color.'; t
                    echo '<text x="'.(size).'" y="'.(size).'" style="font-family: '.fontFamily.'; font-size: 9px; stroke:none; fill:'.color.'; text-anchor: mid
                    echo '<text x="'.(size).'" y="'.(size + radius / 3).'" style="font-family: '.fontFamily.'; font-size: 9px; stroke:none; fill:'.color.'; te
*/
                }
			}
        }

        //kreis.app '</svg>';
	}

}
