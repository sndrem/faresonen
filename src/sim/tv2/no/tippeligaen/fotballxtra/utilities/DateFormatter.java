package sim.tv2.no.tippeligaen.fotballxtra.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {
	
	public static SimpleDateFormat oldFormat, newFormat;
	private final static String oldPattern = "dd.MM.yyyy";
	private final static String newPattern = "E, dd.MM.YYYY";

	public DateFormatter() {
		// TODO Auto-generated constructor stub
		
	}
	
	public static String formatDate(String dateString) {
		DateFormatter.oldFormat = new SimpleDateFormat(oldPattern, Locale.getDefault());
		DateFormatter.newFormat = new SimpleDateFormat(newPattern, Locale.getDefault());
		try {
			Date date = DateFormatter.oldFormat.parse(dateString);
			return DateFormatter.newFormat.format(date) + " - ";
		} catch (ParseException exe) {
			exe.printStackTrace();
		}
		return "";
	}

}
