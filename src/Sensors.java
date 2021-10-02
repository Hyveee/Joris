import java.io.File;

import javax.sound.sampled.Port;

import lejos.hardware.BrickFinder;
import lejos.hardware.Sound;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
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

public class Sensors {
	EV3UltrasonicSensor capteur=new EV3UltrasonicSensor(SensorPort.S1);
	/**
	 * Donne la distance renvoyée par le capteur à ultrasons
	 */
	void distance () { 
		capteur.getDistanceMode();//en metres
	}
	
	/**
	 * Donne un boolean en fonction de si un autre capteur à ultrasons est repéré ou non.
	 */
	void autresSensor() { 
		capteur.getListenMode();//boolean
	}
	
	
	EV3ColorSensor color =new EV3ColorSensor(SensorPort.S2);
	/**
	 * Donne l'ID de la couleur percue
	 */
	void colorId() {
		color.getColorIDMode();
	}
		
	EV3TouchSensor pression=new EV3TouchSensor(SensorPort.S3);
	/**
	 * Détecte si le capteur de pression est enfoncé 
	 */
	void touche() {
		pression.getTouchMode();
	}
	

}
