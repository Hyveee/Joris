package Joris;
import java.math.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Strategy {

	Agent joris = new Agent();
	private boolean premier = true;

	public Strategy (){
	}

	/**
	 * methode qui fait le premier coup en appelant avanceVersPalet()
	 */
	public void premierCoup() {

		this.avanceVersPalet(); 
	}

	/**
	 * methode qui permet au robot d avancer vers le palet jusqu a ce que le touchSensor soit enfonce
	 * la methode fait appel ensuite a recupPalet()
	 * 
	 * FONCTION CHECK, TOUT MARCHE
	 */
	public void avanceVersPalet(){

		// ouvre les pinces, asychrone.
		joris.pinceOuverture(true);
		// on remet le tachoCountRD à 0.
		joris.getMoteurDroit().resetTachoCount();
		joris.setTachoCountRD();
		// Joris avance jusqu'à rencontrer soit un palet, soit un mur
		joris.getPilot().forward();
		while(joris.touche() == 0 && joris.getDistance() > 0.20) {
		}
		if (joris.touche() == 1) {			
			joris.getPilot().stop();
			//retient la distance parcourue
			joris.setTachoCountRD();
			joris.getMoteurPince().stop();
			joris.setTachoCountMP();
			this.recupPalet();
		}

		if (joris.getDistance()< 0.20) {
			joris.getPilot().stop();
			joris.setTachoCountRD();
			joris.getMoteurPince().stop();
			joris.setTachoCountMP();
			joris.pinceFermeture(false);
			joris.getPilot().backward();
			while (joris.getTachoCountRD()> 0) {
				joris.setTachoCountRD();
				joris.setTachoCountRD(2);
			}
			joris.getPilot().stop();
			reperage2();

		}
		reperage2();
	}

	/**
	 * methode qui permet au robot de fermer ses pinces apres avoir touche un palet 
	 * la methode fait appel ensuite a ramenerPaletZone()
	 * 
	 * FONCTION CHECK, TOUT MARCHE
	 */
	public void recupPalet() {
		joris.pinceFermeture(true);
		this.ramenerPaletZone();
	}


	/**
	 * methode qui permet au robot d emmener le palet entre ses pinces dans la zone d en but, 
	 * le robot lache le palet puis recule et fait demi tour
	 * la zone d en but est detectee grace a la ligne blanche presente sur le plateau de jeu
	 * 
	 */
	public void ramenerPaletZone() {
		if (joris.getpinceFerme() != true) {
			joris.pinceFermeture(2700, false);
		}
		if (premier) {
			joris.getMoteurDroit().resetTachoCount();
			joris.setTachoCountRD();
			joris.tourner(25);
			joris.setTachoCountRD();
			joris.setBoussole(joris.getBoussole() + joris.getTachoCountRD());
			joris.getPilot().forward();
			Delay.msDelay(1500);
			joris.getPilot().stop();
		}
		joris.directionLigneBlanche();
		joris.getPilot().forward();
		while (joris.getColorID() != 6 && joris.getDistance() > 0.15) {

		}
		joris.getPilot().stop();
		joris.pinceOuverture(true);
		Delay.msDelay(2000);
		if (joris.getDistance() > 0.3) {
			joris.pinceFermeture(1350, false);
			joris.getPilot().forward();
			while (joris.getColorID() != 6 && joris.getDistance() > 0.15) {

			}
			joris.getPilot().stop();
			joris.pinceOuverture();
		}
		
		joris.setTachoCountMP();
		joris.getPilot().backward();
		joris.pinceFermeture(false);
		Delay.msDelay(500);
		joris.getPilot().stop();
		joris.getMoteurDroit().resetTachoCount();
		joris.setTachoCountRD();
		joris.tourner(270);
		joris.setTachoCountRD();
		joris.setBoussole(joris.getBoussole() + joris.getTachoCountRD());
		premier = false;
		reperage2();
	}

	/**
	 * 
	 */

	public void reperage2 () {
		
		if (joris.getpinceFerme() == false) {
			joris.pinceFermeture();
		}
		List <Float> valeurs= new ArrayList<Float> ();
		boolean trouve = false;
		joris.getPilot().setAngularSpeed(17);	
		joris.getMoteurDroit().resetTachoCount();
		joris.setTachoCountRD();
		joris.tourner(65, true);
		joris.pinceFermeture(2700, true);
		while(joris.getPilot().isMoving() && !trouve) {
			float distance = joris.getDistance();	
			if(distance != Float.POSITIVE_INFINITY) {
				valeurs.add(distance);
				if((valeurs.size()>4) && valeurs.get(valeurs.size()-1) < 2 ) {
					if (valeurs.get(valeurs.size()-3) > valeurs.get(valeurs.size()-2) && valeurs.get(valeurs.size()-3) > valeurs.get(valeurs.size()-1)){
						System.out.println(valeurs.get(valeurs.size()-1));
						joris.getPilot().stop();
						trouve = true;
						joris.setTachoCountRD();
						joris.setBoussole(joris.getBoussole()+joris.getTachoCountRD());
						joris.getPilot().setAngularSpeed(1000);
						avanceVersPalet();
					}
				}
			}
			Delay.msDelay(1);
		}
		valeurs.clear();
		if (!trouve) {
			joris.setTachoCountRD();
			joris.setBoussole(joris.getBoussole()+joris.getTachoCountRD());
			joris.getMoteurDroit().resetTachoCount();
			joris.setTachoCountRD();
			joris.getPilot().setAngularSpeed(1000);
			joris.tourner(65);
			joris.setTachoCountRD();
			joris.setBoussole(joris.getBoussole()+joris.getTachoCountRD());
			joris.getMoteurDroit().resetTachoCount();
			joris.setTachoCountRD();
			joris.getPilot().setAngularSpeed(17);
			joris.tourner(-65, true);
			while(joris.getPilot().isMoving() && !trouve) {
				float distance = joris.getDistance();
				if(distance != Float.POSITIVE_INFINITY) {
					System.out.println(distance);
					valeurs.add(distance);
					System.out.println("dans valeurs : "+ valeurs.get(valeurs.size()-1));
					if(valeurs.size()>4 && valeurs.get(valeurs.size()-1) < 2 ) {
						if (valeurs.get(valeurs.size()-3) > valeurs.get(valeurs.size()-2) && valeurs.get(valeurs.size()-3) > valeurs.get(valeurs.size()-1)){
							trouve = true;
							joris.getPilot().stop();
							joris.setTachoCountRD();
							joris.setBoussole(joris.getBoussole()+joris.getTachoCountRD());
							joris.getPilot().setAngularSpeed(1000);
							avanceVersPalet();
						}
					}
				}
				Delay.msDelay(1);
			}
		}

		else if (!trouve){
			joris.getPilot().forward();
			Delay.msDelay(500);
			joris.getPilot().stop();
			this.reperage2();
			
		}


	}

	public Agent getJoris() {
		return joris;
	}

	public static void main(String[] args) {
	}
}