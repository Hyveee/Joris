package Joris;
import java.math.*;
import java.util.ArrayList;

import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Strategy {
	
	Agent joris = new Agent();
	
	public Strategy ()
	{
	}
	
	public void premierCoup() {
		joris.getPilot().rotate(10);
		joris.getPilot().travelArc(2*Math.PI*277.5*(37.8/360), 37.8, true);
		this.avanceVersPalet();
		
	}
	public void avanceVersPalet(){
	
		float distanceTravel = joris.getDistance(); 
		joris.getPilot().travel(distanceTravel);
		if (joris.touche() == 1) {
			joris.getPilot().stop();
			this.recupPalet();
		}
	}
	
	public void recupPalet() {
		if(joris.getpinceFerme() == false) {
			joris.pinceFermeture();
		}	
		this.ramenerPaletZone();
	}

	public void ramenerPaletZone() {
		
		while (joris.getColorID() != 0 && joris.getDistance() > 0.15 ) {
			joris.getPilot().forward();
		}
		joris.getPilot().stop();
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
			joris.getPilot().forward();
		}
		joris.getPilot().stop();
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
	
	public void reperage() {

		int count = 0;
		int count2 = 0;
		int count3 = 0;
		float d = 0;
		ArrayList<Float> valeurs = new ArrayList<Float>();
		joris.getuSSensor().getDistanceMode();
		joris.getPilot().setAngularSpeed(50);
		float distanceActuelle=joris.getDistance();
		float distancePrecedente;
		float distanceMin=255;
		joris.getPilot().rotate(380);// je ne retrouve pas la fonction tourner, je crois qu'on ne l'a plus
		while(count3 < 500000 ) {
			valeurs.add(joris.getDistance());
			
			/*distancePrecedente=distanceActuelle;
			distanceActuelle=joris.getDistance();
			//if(distancePrecedente-distanceActuelle>0.1) {
				if(distanceActuelle<distanceMin) {
					distanceMin=distanceActuelle;
					d = distancePrecedente;
				}
				
				*/
			//}
			count3++;
		}
		
		for (int i = 0; i < valeurs.size(); i++) {
			count2++;
			if (valeurs.get(i) < distanceMin) {
				distanceMin = valeurs.get(i);
				count++;
			}
		}

		joris.getG().drawString("" +(distanceMin)+" "+(count)+" "+(valeurs.size())+" "+count2, 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
		joris.getG().drawString(""+(valeurs.size())+" "+count2, 0, 22, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
		Delay.msDelay(10000);
	}
	
	public Agent getJoris() {
		return joris;
	}
	
	public static void main(String[] args) {
		Agent joris = new Agent();
		joris.getG().drawString(""+(joris.getColorID()), 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
		Delay.msDelay(10000);
	}
}