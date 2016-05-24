package sim.tv2.no.tippeligaen.fotballxtra.utilities;

public class TeamUtilities {
	
	public static String convertTeamToAbbreviation(String teamName) {
		if(teamName.equalsIgnoreCase("Rosenborg")) {
			return "RBK";
		} else if(teamName.equalsIgnoreCase("Molde")) {
			return "MFK";
		} else if(teamName.equalsIgnoreCase("Odd")) {
			return "ODD";
		} else if(teamName.equalsIgnoreCase("Strømsgodset")) {
			return "SIF";
		} else if(teamName.equalsIgnoreCase("Haugesund") || teamName.equalsIgnoreCase("FK Haugesund")) {
			return "FKH";
		} else if(teamName.equalsIgnoreCase("Viking")) {
			return "VIK";
		} else if(teamName.equalsIgnoreCase("Brann")) {
			return "BRA";
		} else if(teamName.equalsIgnoreCase("Lillestrøm")) {
			return "LSK";
		} else if(teamName.contains("Sarpsborg")) {
			return "S08";
		} else if(teamName.equalsIgnoreCase("Sogndal")) {
			return "SOG";
		} else if(teamName.equalsIgnoreCase("Tromsø")) {
			return "TIL";
		} else if(teamName.equalsIgnoreCase("Aalesund")) {
			return "AaFK";
		} else if(teamName.equalsIgnoreCase("Bodø/Glimt")) {
			return "B/G";
		} else if(teamName.equalsIgnoreCase("Vålerenga")) {
			return "VIF";
		} else if(teamName.equalsIgnoreCase("Stabæk")) {
			return "STB";
		} else if(teamName.equalsIgnoreCase("Start")) {
			return "STA";
		} else {
			return null;
		}
	}

}
