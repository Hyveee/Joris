import java.io.File;


import javax.sound.sampled.Port;

import lejos.hardware.BrickFinder;
import lejos.hardware.Sound;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.AnalogPort;
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

public class TouchSensor {
	EV3TouchSensor sensor;
	SampleProvider s;
	public TouchSensor(lejos.hardware.port.Port s2) {
		sensor= new EV3TouchSensor(s2);
		s=sensor.getTouchMode();
	}

	
	
	/**
	* Renvoie true si le capteur pression est enfonce
	* @return un boolean
	*/
	public  boolean touche() {
		float [] sample = new float[s.sampleSize()];
		s.fetchSample(sample, 0);
		
	       if (sample[0] == 0)
	           return false;
	       else
	           return true;
		
	}
}
