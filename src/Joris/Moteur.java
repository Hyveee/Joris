package Joris;

import javax.sound.sampled.Port;

import lejos.hardware.BrickFinder;
import lejos.hardware.Sound;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
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
import lejos.robotics.navigation.MovePilot.*;



public class Moteur extends MovePilot{
	
	EV3LargeRegulatedMotor moteurDroit;
	EV3LargeRegulatedMotor moteurGauche;
	

//	static final BaseRegulatedMotor[] Moteurs = new BaseRegulatedMotor[] {moteurGauche};
	private  MovePilot pilot;

	
	public Moteur() {
		super(56,135,new EV3LargeRegulatedMotor(Brick.get),new EV3LargeRegulatedMotor(MotorPort.C));
		moteurDroit = new EV3LargeRegulatedMotor(MotorPort.B);
		moteurGauche = new EV3LargeRegulatedMotor(MotorPort.C);
		
		// TODO Auto-generated constructor stub
	}

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
	

}
