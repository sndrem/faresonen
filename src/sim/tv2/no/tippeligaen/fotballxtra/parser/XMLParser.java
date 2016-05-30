package sim.tv2.no.tippeligaen.fotballxtra.parser;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import sim.tv2.no.tippeligaen.fotballxtra.weather.Weather;


public class XMLParser {
	
	private Document document;
	private SAXBuilder saxBuilder;
	private static final String DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss";
	
	public XMLParser() {
		saxBuilder = new SAXBuilder();
	}
	
	/**
	 * Method to parse a xml-response from a url
	 * @param url
	 * @return a list of weather objects
	 */
	public List<Weather> parseUrl(String url) {
		List<Weather> weatherList = new ArrayList<Weather>();
		try {
					
			this.document = saxBuilder.build(url);
			Element root = this.document.getRootElement();
			String location = this.document.getRootElement().getChild("location").getChildText("name");
			
			
			Element forecast = root.getChild("forecast");
			for(Element elem : forecast.getChild("tabular").getChildren()) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(XMLParser.DATE_FORMAT);

				String fromTime = elem.getAttributeValue("from");
				String toTime = elem.getAttributeValue("to");
				Double windSpeed = Double.parseDouble(elem.getChild("windSpeed").getAttributeValue("mps"));
				String windExplanation = elem.getChild("windSpeed").getAttributeValue("name");
				Double degrees = Double.parseDouble(elem.getChild("temperature").getAttributeValue("value"));
				int symbolNumber = Integer.parseInt(elem.getChild("symbol").getAttributeValue("number"));
				String symbolText = elem.getChild("symbol").getAttributeValue("name");
				try {
					weatherList.add(new Weather(location, dateFormat.parse(fromTime).toString(), dateFormat.parse(toTime).toString(), degrees, windSpeed, windExplanation, symbolNumber, symbolText));
 				} catch(ParseException e) {
 					e.printStackTrace();
 				}
			}
			
//			XMLOutputter output = new XMLOutputter();
//			output.output(this.document, System.out);
//			
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return weatherList;
	}
	

}
