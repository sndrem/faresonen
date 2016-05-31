package sim.tv2.no.tippeligaen.fotballxtra.parser;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
	public Weather parseUrl(String url) {
		Weather weather = null;
		try {
			this.document = saxBuilder.build(url);
			Element root = this.document.getRootElement();
			String location = this.document.getRootElement().getChild("location").getChildText("name");
			
			
			Element forecast = root.getChild("forecast");
			Element elem = forecast.getChild("tabular").getChildren().get(0);
			SimpleDateFormat dateFormat = new SimpleDateFormat(XMLParser.DATE_FORMAT);

			String fromTime = elem.getAttributeValue("from");
			String toTime = elem.getAttributeValue("to");
			Double windSpeed = Double.parseDouble(elem.getChild("windSpeed").getAttributeValue("mps"));
			String windExplanation = elem.getChild("windSpeed").getAttributeValue("name");
			Double degrees = Double.parseDouble(elem.getChild("temperature").getAttributeValue("value"));
			String symbol = elem.getChild("symbol").getAttributeValue("var");
			String symbolText = elem.getChild("symbol").getAttributeValue("name");
			try {
				weather = new Weather(location, dateFormat.parse(fromTime).toString(), dateFormat.parse(toTime).toString(), degrees, windSpeed, windExplanation, symbol, symbolText);
			} catch(ParseException e) {
				e.printStackTrace();
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
		return weather;
	}
	

}
