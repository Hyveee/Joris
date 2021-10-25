package Joris;

import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.Delay;

public class Strategy {
	
	Agent joris = new Agent();
	
	public Strategy ()
	{
	}
	
	public void avanceVersPalet(){
	
		float distanceTravel = joris.getDistance(); 
		joris.pilot.travel(distanceTravel);
		if (joris.touche() == 1) {
			joris.pilot.stop();
			this.recupPalet();
		}
	}
	
	public void recupPalet() {
		if(joris.getpinceFerme() == false) {
			joris.pinceFermeture();
		}	
	
	}

	public void ramenerPaletZone() {
		
		while (joris.getColorID() != 0 && joris.getDistance() > 0.15 ) {
			joris.pilot.forward();
		}
		joris.pilot.stop();
		if (joris.getColorID() == 0) {
			joris.pinceOuverture();
			/*strat suivante
			 * exemple : reculer puis reperage()
			 */
		}
		else if(joris.getDistance()<0.15) {
			ramenerPaletZone();
		}
	}
	
	public void changerDeDirection() {
		while(joris.getDistance() > 0.33 && joris.touche() == 0) {
			joris.pilot.forward();
		}
		joris.pilot.stop();
		if (joris.touche() == 1)
			recupPalet();
		else if (joris.getDistance() < 0.33) {
			Delay.msDelay(500);
			if(joris.getDistance() < 0.33) {
				//reperage(); à coder
			}
			else {
				changerDeDirection();
			}
		}		
	}
	
	public Agent getJoris() {
		return joris;
	}
	
	public static void main(String[] args) {
		Agent joris = new Agent();
		joris.g.drawString(""+(joris.getColorID()), 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
		Delay.msDelay(10000);
	}
}