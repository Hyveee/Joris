package Joris;
import java.math.*;
import java.util.ArrayList;
import java.util.List;

import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Strategy {
	
	Agent joris = new Agent();

	public Strategy ()
	{
	}
	
	
	
	public void premierCoup() {
		
		this.avanceVersPalet(); 
		
		//joris.tourner(10);
		//joris.getPilot().travelArc(2*Math.PI*277.5*(37.8/360), 37.8, true);
		
		
	}
	public void avanceVersPalet(){
	
		float distanceTravel = joris.getDistance(); 
		//joris.getG().drawString("" +(distanceTravel), 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
		//Delay.msDelay(2000);
		//joris.getPilot().travel(distanceTravel);
		
		joris.getPilot().forward();
		while(joris.touche() == 0 ) {
			
			
		}
		
		if (joris.touche() == 1) {
			//joris.getG().drawString(" C RENTRE DANS LE IF LOL", 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
			//Delay.msDelay(2000);
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
		
		int boussole = joris.getBoussole();
		joris.getG().drawString(""+boussole, 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
		
		Delay.msDelay(2000);
		
		joris.tourner(-boussole);
		joris.getPilot().forward();
		while (joris.getColorID() != 6 && joris.getDistance() > 0.15) {
			
		}
		Delay.msDelay(500);
		joris.getPilot().stop();
		joris.pinceOuverture();
		
		joris.getPilot().backward();
		Delay.msDelay(1000);
		joris.tourner(180);
			/*strat suivante
			 * exemple : reculer puis reperage()
			 */
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
				//reperage(); � coder
			}
			else {
				changerDeDirection();
			}
		}		
	}
	public boolean differencierMurPalet(float distance) {
		
		float distanceGauche;
		float distanceDroite;
		
		
		joris.tourner(-30);
		distanceGauche = joris.getDistance();
		if (Math.abs(distance - distanceGauche) <=  0.1) {
			joris.tourner(-90);
			return false;
		}
	Delay.msDelay(1000);
		
		joris.tourner(60);
		distanceDroite = joris.getDistance();
		if (Math.abs(distance - distanceDroite) <=  0.1) {
			joris.tourner(90);
			return false;
		}
		Delay.msDelay(1000);
		
		joris.tourner(-30);
		return true;
		
	}
	
	
	public void reperage() {

		List <Float> valeurs= new ArrayList<Float> ();		
		
		
		joris.getuSSensor().getDistanceMode();
		//joris.getPilot().setAngularSpeed(50);
		joris.tourner(390, true);
		while(joris.getPilot().isMoving()) {
			float distance = joris.getDistance();
			valeurs.add(distance);
			Delay.msDelay(1);
		}
		float plusPetiteValeur = 6;
		
		//recherche de la valeur la plus petite
		for(int i =0; i<valeurs.size(); i++) {
			if (plusPetiteValeur > valeurs.get(i)) {
				plusPetiteValeur = valeurs.get(i);
				
			}
		
		}
		
	
		float tourner = ((float) valeurs.indexOf(plusPetiteValeur))/(float) valeurs.size()*360;
		joris.tourner((int)tourner );
		
		differencierMurPalet(plusPetiteValeur);
		
		//joris.getG().drawString("" +(plusPetiteValeur)+" "+ tourner, 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
	//	joris.getG().drawString(""+valeurs.size()+" "+valeurs.indexOf(plusPetiteValeur), 0, 22, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
		Delay.msDelay(3000);
		
		avanceVersPalet();
		
		
	/*	float distanceActuelle=joris.getDistance();
		float distancePrecedente;
		float distanceMin=255;
		while(count3 < 500000 ) {
		
			
			/*distancePrecedente=distanceActuelle;
			distanceActuelle=joris.getDistance();
			//if(distancePrecedente-distanceActuelle>0.1) {
				if(distanceActuelle<distanceMin) {
					distanceMin=distanceActuelle;
					d = distancePrecedente;
				}
				
				
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
*/
		
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