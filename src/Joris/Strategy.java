package Joris;
import java.math.*;
import java.util.ArrayList;
import java.util.List;

import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Strategy {

	Agent joris = new Agent();

	public Strategy (){
	}

	/**
	 * methode qui fait le premier coup en appelant avanceVersPalet()
	 */
	public void premierCoup() {

		this.avanceVersPalet(); 

		//joris.tourner(10);
		//joris.getPilot().travelArc(2*Math.PI*277.5*(37.8/360), 37.8, true);


	}

	/**
	 * methode qui permet au robot d avancer vers le palet jusqu a ce que le touchSensor soit enfonce
	 * la methode fait appel ensuite a recupPalet()
	 */
	public void avanceVersPalet(){


		joris.pinceOuverture(true);
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

	/**
	 * methode qui permet au robot de fermer ses pinces apres avoir touche un palet 
	 * la methode fait appel ensuite a ramenerPaletZone()
	 */
	public void recupPalet() {
		//joris.getMoteurPince().stop();

		joris.pinceFermeture();

		this.ramenerPaletZone();
	}


	/**
	 * methode qui permet au robot d emmener le palet entre ses pinces dans la zone d en but, 
	 * le robot lache le palet puis recule et fait demi tour
	 * la zone d en but est detectee grace a la ligne blanche presente sur le plateau de jeu
	 * 
	 */
	public void ramenerPaletZone() {

		int boussole = joris.getBoussole();
		joris.getG().drawString(""+boussole, 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);

		Delay.msDelay(2000);

		joris.tourner(-boussole);
		joris.tourner(10); //testtbierhfzjdopzihobfuoijhgvkjkhuoijhugfjdhyftugihjomihlgkfjtdhryfgoiohlgkfjdhgsfjgkhlijmolgkfjdhgsdfguoipfdhgsfqtyuipuytrsegrtykuljhgkfdsrtyuijhgfdshrtyuiljkhgfhdrrtyulijkhvcghfd
		joris.getPilot().forward();
		while (joris.getColorID() != 6 && joris.getDistance() > 0.15) {

		}
		Delay.msDelay(500);
		if (joris.getDistance() > 0.15) {
			joris.getPilot().forward();
			while (joris.getColorID() != 6 && joris.getDistance() > 0.15) {

			}
			Delay.msDelay(500);
		}
		joris.getPilot().stop();
		joris.pinceOuverture();

		joris.getPilot().backward();
		joris.pinceFermeture(true);
		Delay.msDelay(1000);
		joris.tourner(180);
		reperage();
	}

	/**
	 * 
	 */
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
				reperage();
			}
			else {
				changerDeDirection();
			}
		}		
	}

	/**
	 * 
	 * @param i 
	 * @param plusPetiteValeur 
	 * @param valeurs 
	 * @param distance
	 * @return
	 */
	public boolean differencierMurPalet(List<Float> valeurs, float plusPetiteValeur, int index) {

		/*float distanceGauche;
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

		joris.tourner(-30);*/

		int distanceDiff = 5;
		int diffV = 10;
		if(plusPetiteValeur >0.7) {
			diffV = 5;
			distanceDiff = 8;
		}
		if(plusPetiteValeur >1.5) {
			diffV = 3;
			distanceDiff = 10;
		}

		if(Math.abs(valeurs.get(index+diffV)-plusPetiteValeur) <distanceDiff && Math.abs(valeurs.get(index-diffV)-plusPetiteValeur) <distanceDiff) {
			return true;
		}
		return true;

	}

	/**
	 * 
	 */
	public void reperage() {

		List <Float> valeurs= new ArrayList<Float> ();		

		List <Float> valeursIgnore= new ArrayList<Float> ();
		boolean dansIgnore;

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

		while (differencierMurPalet(valeurs, plusPetiteValeur, valeurs.indexOf(plusPetiteValeur)) == false) {
			valeursIgnore.add(plusPetiteValeur);
			plusPetiteValeur = 10;
			for(int i =0; i<valeurs.size(); i++) {
				dansIgnore = false;
				for (int j = 0; j < valeursIgnore.size(); j++) {
					if(valeursIgnore.get(j) == valeurs.get(i)) {
						dansIgnore = true;
					} 
				}	
				if (plusPetiteValeur > valeurs.get(i) && !dansIgnore) {
					plusPetiteValeur = valeurs.get(i);

				}

			}



		}

		float tourner = ((float) valeurs.indexOf(plusPetiteValeur))/(float) valeurs.size()*360;
		joris.tourner((int)tourner );



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