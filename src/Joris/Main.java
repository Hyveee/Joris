package Joris;
import lejos.hardware.Button;

public class Main {
	public static void main(String[] args) {
		Strategy strat = new Strategy();
		Button.ENTER.waitForPress();
		strat.reperage2();
		//strat.reperage();
	}
	
	
	
}
