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

public class UltraSonicSensor {
	
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
		sp.fetchSample(sample, 0);

   		return sample[0];
	}
	
	/**
	* Renvoie true si le capteur pression est enfonce
	* @return un boolean
	*/
	/*public  boolean touche() {
		SampleProvider sp=touch.getMode("touche");
		float [] sample = new float[sp.sampleSize()];
		sp.fetchSample(sample, 0);

		//return true si c'est touch�
	       if (sample[0] == 0)
	           return false;
	       else
	           return true;
		
	}*/

}
