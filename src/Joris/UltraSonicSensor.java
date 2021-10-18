package Joris;
import java.io.File;

import javax.sound.sampled.Port;

import lejos.hardware.BrickFinder;
import lejos.hardware.Sound;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.BaseSensor;
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

public class UltraSonicSensor extends BaseSensor{
	
	EV3UltrasonicSensor sensor;
	 private SampleProvider sp;
     private float [] sample;
	/**
	* Initialise les ports utilisés par les différents capteurs
	*/
    public UltraSonicSensor(lejos.hardware.port.Port port)
	{
		sensor = new EV3UltrasonicSensor(port);
		sp = sensor.getDistanceMode();
	    sample = new float[sp.sampleSize()];
	}

	
	/**
	* Renvoie la distance mesurée par le capteur
	* @return Une distance en metre
	*/
	public float getDistance() {
		sp = sensor.getDistanceMode();
		sp.fetchSample(sample, 0);

   		return sample[0];
	}
	
	
	public float scan() {
		sp = sensor.getListenMode();
		sp.fetchSample(sample, 0);
		
		return sample[0];
		
	}
	
	

}
