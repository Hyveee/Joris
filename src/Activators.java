import java.io.File;

import javax.sound.sampled.Port;

import lejos.hardware.BrickFinder;
import lejos.hardware.Sound;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

import lejos.hardware.port.MotorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;
import lejos.hardware.motor.Motor;

public class Activators {
	private GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();
	private RegulatedMotor rDroite = new EV3LargeRegulatedMotor(MotorPort.B);
	private RegulatedMotor rGauche = new EV3LargeRegulatedMotor(MotorPort.C);
	
	private static Wheel wheel1 = WheeledChassis.modelWheel(Motor.B, 56.0).offset(-58);
	private static Wheel wheel2 = WheeledChassis.modelWheel(Motor.C, 56.0).offset(58);
	private static Chassis chassis = new WheeledChassis(new Wheel[]{wheel1, wheel2}, WheeledChassis.TYPE_DIFFERENTIAL); 
	private static MovePilot pilot = new MovePilot(chassis);

	private boolean pinceFerme = false;

	/**
	 * Fait avancer le robot pendant 2 secondes
	 */
	public void avancer1() {
		
		Motor.B.forward();
		Motor.C.forward();
		Delay.msDelay(2000);
		Motor.B.stop();		
		Motor.C.stop();
	}
	
	/**
	 * Fait avancer le robot sur -2000 centimetres
	 */
	public void avancer2() {
		
		pilot.setLinearSpeed(2000); // cm per second (vitesse)
		pilot.setLinearAcceleration(500);//acceleration
		pilot.travel(-2000);         // cm (distance)
		
	}
	
	/**
	 * Fait avancer le robot sur i centimetres
	 * @param i Distance en centimetre
	 */
	public void avancer(int i) {

		pilot.setLinearSpeed(2000); // cm per second (vitesse)
		pilot.setLinearAcceleration(500);//acceleration
		pilot.travel(i);         // cm (distance)

	}
	
	/**
	 * Fait pivoter le robot de 90 degrés vers la gauche
	 */
	public void tournerMoins90() {
		//tourne le robot de 90° vers la gauche
		 pilot.rotate(-90);        
	}
	
	/**
	 * Fait pivoter le robot de 90 degrés vers la droite
	 */
	public void tournerPlus90() {
		//tourne le robot de 90° vers la droite
		pilot.rotate(90);
	}
	
	/**
	 * Fait pivoter le robot de 180 degrés vers la gauche
	 */
	public void tournerMoins180(){
		//tourne le robot de 180° vers la gauche
		pilot.rotate(-180);
	}
	
	/**
	 * Fait pivoter le robot de 180 degrés vers la droite
	 */
	public void tournerPlus180() {
		//tourne le robot de 180° vers la droite
		pilot.rotate(180);
	}
	
	/**
	 * Fait tourner le robot de i degrès vers la gauchge si la valeur est négative et vers la droite si la valeur est positive
	 * @param i Un entier entre -180 et 180
	 */
	public void tourner(int i) {
		//tourne le robot d'un angle entre -180 et 180
		short rotate;
		
		rotate=(short) (i % 360);
		if (0<=rotate && rotate<=180) {
			pilot.rotate(rotate);
		}
		else { 
			pilot.rotate(rotate-360);
		}
		
	}	
	
	/**
	 * Ouvre les pinces du robot si elles ne sont pas déja ouvertes.
	 */
	public void pinceOuverture(boolean etat) {
		if (etat==false)
			Motor.A.rotate(180);
		
		else System.out.println("pince deja ouverte");
	}
	
	/**
	 * Ferme les pinces du robot si elles ne sont pas déja fermées.
	 */
	public void pinceFermeture(boolean etat) {
		if (etat==true)
			Motor.A.rotate(-180);
		
		else System.out.println("pince deja fermé");
	}
	

	/**
	 * 
	 * @return écran actuel
	 */
	public GraphicsLCD getG() {
		return g;
	}

	/**
	 * 
	 * @param g nouvel écran à utiliser
	 */
	public void setG(GraphicsLCD g) {
		this.g = g;
	}

	/**
	 * 
	 * @return Moteur droit actuel
	 */
	public RegulatedMotor getrDroite() {
		return rDroite;
	}

	/**
	 * 
	 * @param rDroite Moteur a utiliser pour le moteur droit
	 */
	public void setrDroite(RegulatedMotor rDroite) {
		this.rDroite = rDroite;
	}

	/**
	 * 
	 * @return Moteur gauche actuel
	 */
	public RegulatedMotor getrGauche() {
		return rGauche;
	}

	/**
	 * 
	 * @param rGauche Moteur a utiliser pour le moteur gauche
	 */
	public void setrGauche(RegulatedMotor rGauche) {
		this.rGauche = rGauche;
	}

	/**
	 * 
	 * @return Roue actuelle associée au moteur droit
	 */
	public Wheel getWheel1() {
		return wheel1;
	}

	/**
	 * 
	 * @param wheel1 Roue a utiliser a droite
	 */
	public void setWheel1(Wheel wheel1) {
		this.wheel1 = wheel1;
	}

	/**
	 * 
	 * @return Roue actuelle associée au moteur gauche
	 */
	public Wheel getWheel2() {
		return wheel2;
	}

	/**
	 * 
	 * @param wheel2 Roue a utiliser a gauche
	 */
	public void setWheel2(Wheel wheel2) {
		this.wheel2 = wheel2;
	}

	/**
	 * 
	 * @return Chassis actuel
	 */
	public Chassis getChassis() {
		return chassis;
	}

	/**
	 * 
	 * @param chassis Nouveau chassis
	 */
	public void setChassis(Chassis chassis) {
		this.chassis = chassis;
	}

	/**
	 * 
	 * @return Pilot actuel
	 */
	public MovePilot getPilot() {
		return pilot;
	}

	/**
	 * 
	 * @param pilot nouveau pilot
	 */
	public void setPilot(MovePilot pilot) {
		this.pilot = pilot;
	}
	
	/**
	 * 
	 * @return 1 si les pinces sont fermées, 0 sinon
	 */
	public boolean isPinceFermee() {
		return pinceFerme;
	}

	/**
	 * 
	 * @param pinceFermee boolean, 1 pour signifier que les pinces sont fermées, 0 sinon
	 */
	public void setPinceFermee(boolean pinceFermee) {
		this.pinceFerme = pinceFermee;
	}

}