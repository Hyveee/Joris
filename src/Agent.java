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

public class Agent {
	static Activators activators = new Activators();
	static Sensors sensors = new Sensors();
	
	public static void auto() {
		 activators.avancer1();
		 if (sensors.getDistance()<=30) {
			 System.out.println("distance mur proche");
			 if (sensors.getDistance()<=20) {
				 activators.moteurStop();
			 } else if (sensors.touche()==true) {
				 activators.pinceFermeture();
			 }
	 
		 }
	}
		
	
	public static void main(String[] args) {
		
		//test 
		//tournerMoins90() ; //ok
		//tournerPlus90() ; //ok
		//tournerMoins180(); //ok
		//tournerPlus180(); //ok
		//tourner(3000); //ok
		//activators.avancer2();
		auto();
		
		
		
		/*	

			//rDroite.rotate(360);
			//SensorModes sensor = new EV3UltrasonicSensor('4');

			SampleProvider distance= sensor.getMode("Distance");
			//rGauche.rotate(360,true);
			//Delay.msDelay(-360);
			
			 
			
			 pilot.setLinearSpeed(5000); // cm per second
			 pilot.setLinearAcceleration(100);
			 pilot.travel(50);         // cm
			 pilot.rotate(-90);        // degree clockwise
			 pilot.travel(-50,true);  //  move backward for 50 cm
			 while(pilot.isMoving())Thread.yield(); //att finis bouge
			 pilot.rotate(-90);
			 pilot.stop();
			
			/*g.drawString("Noe le supra BG", 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
			Delay.msDelay(500);
			
			rDroite.close();
			rGauche.close();
			*/

			//Sound.playSample(new File("ressource/imperial_march.wav"),Sound.VOL_MAX);
				
		
	}

}
