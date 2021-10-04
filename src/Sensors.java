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
	private SensorModes ultrasound=null;
	private SensorModes color=null;
	private SensorModes touch=null;
	
	/**
	* Initialise les ports utilisés par les différents capteurs
	*/
	public Sensors() {	
		ultrasound=new EV3UltrasonicSensor(SensorPort.S4);
		color=new EV3ColorSensor(SensorPort.S2);
		touch=new EV3TouchSensor(SensorPort.S3);
	}
	
	
	/**
	* Renvoie la distance mesurée par le capteur
	* @return Une distance en metre
	*/
	public float getDistance() {
		SampleProvider s=ultrasound.getMode("distance");
		float[] sample=new float[s.sampleSize()];
		s.fetchSample(sample, 0);

   		return sample[0];
	}

}
