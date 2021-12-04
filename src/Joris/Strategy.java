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
	 * Lance le premier coup en appelant avanceVersPalet()
	 */
	public void premierCoup() {

		this.avanceVersPalet(); 
	}

	/**
	 * Permet au robot d avancer vers le palet jusqu a ce que le touchSensor soit enfonce
	 * la methode fait appel ensuite a recupPalet() si le capteur de touche a ete enfonce ou fait appel a reperage2() si le 
	 * robot a avance vers un palet mais que le capteur na pas été enfonce
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
				
			}
			joris.getPilot().stop();
			reperage2();

		}
		
	}

	/**
	 * Permet au robot de fermer ses pinces apres avoir touche un palet 
	 * la methode fait appel ensuite a ramenerPaletZone()
	 * 
	 */
	public void recupPalet() {
		joris.pinceFermeture(true);
		this.ramenerPaletZone();
	}


	/**
	 * Permet au robot d emmener le palet entre ses pinces dans la zone d en but, 
	 * le robot lache le palet puis recule et fait demi tour
	 * la méthode fait appel ensuite a reperage2() 
	 * 
	 */
	public void ramenerPaletZone() {
		if (premier) {
			joris.getMoteurDroit().resetTachoCount();
			joris.setTachoCountRD();
			joris.tourner(17);
			joris.setTachoCountRD();
			joris.setBoussole(joris.getBoussole() + joris.getTachoCountRD());
			
		}
		joris.directionLigneBlanche();
		joris.getPilot().forward();
		while (joris.getColorID() != 6 && joris.getDistance() > 0.15) {

		}
		joris.getPilot().stop();
		joris.pinceOuverture(true);
		Delay.msDelay(2000);
		if (joris.getDistance() > 0.3) {
			joris.pinceFermeture(true);
			joris.getPilot().forward();
			while (joris.getColorID() != 6 && joris.getDistance() > 0.15) {

			}
			joris.getPilot().stop();
			
		}
		
		joris.pinceOuverture();
		joris.setTachoCountMP();
		joris.getPilot().backward();
		Delay.msDelay(1000);
		joris.pinceFermeture(true); 
		Delay.msDelay(1000);
		joris.getMoteurDroit().resetTachoCount();
		joris.setTachoCountRD();
		joris.tourner(130);
		System.out.println("boussole" + joris.getBoussole());
		joris.setTachoCountRD();
		System.out.println("boussole" + joris.getBoussole());
		joris.setBoussole(joris.getBoussole() + joris.getTachoCountRD());
		System.out.println("boussole" + joris.getBoussole());
		premier = false;
		reperage2();
	}

	/**
	 * Tourne lentement et scanne jusqu a touver un objet significativement plus proche que les deux scans precedents
	 * Si aucun objet n'a été trouvé, une fois qu'un angle de 65° a ete effectue on arrete de scanner, on complete un angle de 120° et on entamne une deuxieme rotation dans le sens inverse
	 * Appelle avanceVersPalet() si un palet est trouvé
	 * 
	 */

	public void reperage2 () {
		
		List <Float> valeurs= new ArrayList<Float> ();
		boolean trouve = false;
		joris.getPilot().setAngularSpeed(17);	
		joris.getMoteurDroit().resetTachoCount();
		joris.setTachoCountRD();
		joris.tourner(65, true);
		while(joris.getPilot().isMoving() && !trouve) {
			float distance = joris.getDistance();
			System.out.println(distance);
			Delay.msDelay(2000);	
			if(distance != Float.POSITIVE_INFINITY) {
				valeurs.add(distance);
				if((valeurs.size()>4) && valeurs.get(valeurs.size()-1) < 2 ) {
					if (valeurs.get(valeurs.size()-3) > valeurs.get(valeurs.size()-2) && valeurs.get(valeurs.size()-3) > valeurs.get(valeurs.size()-1)){
						System.out.println("dans le if");
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

		else {
			joris.getPilot().forward();
			Delay.msDelay(500);
			joris.getPilot().stop();
			reperage2();
			
		}
	}
	
	/**
	 * 
	 * @return l'agent courant
	 */
	public Agent getJoris() {
		return joris;
	}
}