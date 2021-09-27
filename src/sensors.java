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

public class sensors {
	
	EV3UltrasonicSensor capteur=new EV3UltrasonicSensor(SensorPort.S4);
	//donne distance en metres de l'objet en face
	public void distance () { 
		capteur.getDistanceMode();//en metres
	}
	
	//donne en boolean s'il détecte d'autres capteurs
	public void autresSensor() { 
		capteur.getListenMode();//boolean
	}
	
	
	EV3ColorSensor color =new EV3ColorSensor(SensorPort.S2);
	//donne l'ID de la couleur
	public void colorId() {
		color.getColorIDMode();
	}
		
	EV3TouchSensor pression=new EV3TouchSensor(SensorPort.S3);
	//détecte si il ya une pression du capteur(unité binaire) 
	public void touche() {
		pression.getTouchMode();
	}

	public EV3UltrasonicSensor getCapteur() {
		return capteur;
	}

	public void setCapteur(EV3UltrasonicSensor capteur) {
		this.capteur = capteur;
	}

	public EV3ColorSensor getColor() {
		return color;
	}

	public void setColor(EV3ColorSensor color) {
		this.color = color;
	}

	public EV3TouchSensor getPression() {
		return pression;
	}

	public void setPression(EV3TouchSensor pression) {
		this.pression = pression;
	}
	
	

}
