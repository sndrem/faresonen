package sim.tv2.no.tippeligaen.fotballxtra.weather;

public class Weather {
	
	private String fromTime, toTime, windExplanation, symbolName;
	private Double degrees, wind;

	public Weather(String fromTime, String toTime, Double degrees, Double wind, String windExplanation, int symbolNumber, String symbolName) {
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.setDegrees(degrees);
		this.setWind(wind);
		setWindExplanation(windExplanation);
		setSymbolName(symbolName);
	}
	
	public String toString() {
		String info = "Værvarsel fra " + this.fromTime + " til " + this.toTime + "\n"
				+ "Det er meldt " + this.degrees + " celcius og det skal blåse " + this.wind + " mps. Det tilsvarer " + this.windExplanation;
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
	 * @return the symbolName
	 */
	public String getSymbolName() {
		return getSymbolName();
	}

	/**
	 * @param symbolName the symbolName to set
	 */
	public void setSymbolName(String symbolName) {
		this.symbolName = symbolName;
	}
}
