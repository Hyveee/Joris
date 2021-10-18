package Joris;
import java.io.File;

import javax.sound.sampled.Port;

import lejos.hardware.BrickFinder;
import lejos.hardware.Sound;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.TachoMotorPort;
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

public class Activator {
	private GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();
	private RegulatedMotor rDroite ;
	private RegulatedMotor rGauche ;
	private RegulatedMotor pinces;
	
	private  Wheel wheel1;
	private  Wheel wheel2;
	private  Chassis chassis; 
	private  MovePilot pilot;
	//WheeledChassis.modelWheel(Motor.B, 56.0).offset(-58)
	//WheeledChassis.modelWheel(Motor.C, 56.0).offset(58)

	private  boolean pinceFerme = false;
	
	 public boolean isPinceFerme() {
		return pinceFerme;
	}

	final BaseRegulatedMotor[] Moteur = new BaseRegulatedMotor[] {Motor.C};

	 public Activator(){
		 
	 }

	 /** 
	  * constructeur
	  */
	 public Activator(lejos.hardware.port.Port b, lejos.hardware.port.Port c, lejos.hardware.port.Port d) {
		 rDroite= new EV3LargeRegulatedMotor(b);
		 rGauche= new EV3LargeRegulatedMotor(c);
		 pinces= new EV3LargeRegulatedMotor(d);
		 chassis = new WheeledChassis(new Wheel[]{wheel1, wheel2}, WheeledChassis.TYPE_DIFFERENTIAL); 
		 pilot = new MovePilot(chassis);
	}

	/**
	 * Fait avancer le robot pendant 2 secondes
	 */

	public void avancer1() {
		Motor.B.setSpeed(800);
		Motor.C.setSpeed(800);
		
		Motor.B.forward();
		Motor.C.forward();
		Delay.msDelay(500);
		
	}
	
	public void moteurStop() {
		
		Motor.B.synchronizeWith(Moteur);
		Motor.B.startSynchronization();
		Motor.B.stop();		
		Motor.C.stop();
		Motor.B.endSynchronization();
	}

		
	/**
	 * Fait avancer le robot sur i centimetres
	 * @param i Distance en centimetre
	 */
	public void avancer(int i) {

		pilot.setLinearSpeed(800); // cm per second (vitesse)
		pilot.setLinearAcceleration(500);//acceleration
		pilot.travel(i);         // cm (distance)

	}
	
	public void forwardWithAngle() {
		
	}
	
	
	
	/**
	 * Fait tourner le robot de i degrès vers la gauchge si la valeur est négative et vers la droite si la valeur est positive
	 * @param i Un angle en degr�s 
	 */
	public  void tourner(int i) {
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
	public  void pinceOuverture() {
		if (pinceFerme==true) {
			pinces.rotate(180);
			pinceFerme = false;
		}
		
		else System.out.println("pince deja ouverte");
	}
	
	/**
	 * Ferme les pinces du robot si elles ne sont pas déja fermées.
	 */
	public  void pinceFermeture() {
		if (pinceFerme==false) {
			pinces.rotate(-180);
			pinceFerme = true;
		}
		
		
		else System.out.println("pince deja fermé");
	}

	

	
	
	
	

}
