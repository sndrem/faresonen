package sim.tv2.no.tippeligaen.fotballxtra.scanner;

import java.util.Scanner;

public class InputScanner {
	
	private Scanner input;

	public InputScanner() {
		input = new Scanner(System.in);
	}
	
	public String input() {
		System.out.println("Ønsker du faresonen for tippeligaen eller obosligaen?\nØnsker du for begge kan du skrive \"begge\".");
		System.out.println("Ønsker du å avslutt så skriv \"avslutt\"");
		String response = input.nextLine();
		return response;
	}

}
