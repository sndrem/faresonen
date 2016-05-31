package sim.tv2.no.tippeligaen.fotballxtra.weather;

public class Weather {
	
	private String fromTime, toTime, windExplanation, symbol, location;
	private Double degrees, wind;

		
	public Weather(String location, String fromTime, String toTime, Double degrees, Double wind, String windExplanation, String symbol, String symbolName) {
		setLocation(location);
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.setDegrees(degrees);
		this.setWind(wind);
		setWindExplanation(windExplanation);
		setSymbol(symbol);
	}
	
	public String toString() {
		String info = "<p>Værvarsel for " + this.location + "</p>"
				+ "<p>Det er meldt " + this.degrees + "&#8451; og det skal blåse " + this.wind + " m/s. Det tilsvarer " + this.windExplanation + "</p>"
				+ "<image src=\"file:sym/b38/" + this.symbol + ".png\">";
		return info;
	}

	/**
	 * @return the fromTime
	 */
	public String getFromTime() {
		return fromTime;
	}

	/**
	 * @param fromTime the fromTime to set
	 */
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	/**
	 * @return the toTime
	 */
	public String getToTime() {
		return toTime;
	}

	/**
	 * @param toTime the toTime to set
	 */
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	/**
	 * @return the degrees
	 */
	public Double getDegrees() {
		return degrees;
	}

	/**
	 * @param degrees the degrees to set
	 */
	public void setDegrees(Double degrees) {
		this.degrees = degrees;
	}

	/**
	 * @return the wind
	 */
	public Double getWind() {
		return wind;
	}

	/**
	 * @param wind the wind to set
	 */
	public void setWind(Double wind) {
		this.wind = wind;
	}

	/**
	 * @return the windExplanation
	 */
	public String getWindExplanation() {
		return windExplanation;
	}

	/**
	 * @param windExplanation the windExplanation to set
	 */
	public void setWindExplanation(String windExplanation) {
		this.windExplanation = windExplanation;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}
