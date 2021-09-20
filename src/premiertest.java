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




public class premiertest {
	
	GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();
	RegulatedMotor rDroite = new EV3LargeRegulatedMotor(MotorPort.B);
	RegulatedMotor rGauche = new EV3LargeRegulatedMotor(MotorPort.C);
	
	static Wheel wheel1 = WheeledChassis.modelWheel(Motor.B, 56.0).offset(-58);
	static Wheel wheel2 = WheeledChassis.modelWheel(Motor.C, 56.0).offset(58);
	static Chassis chassis = new WheeledChassis(new Wheel[]{wheel1, wheel2}, WheeledChassis.TYPE_DIFFERENTIAL); 
	static MovePilot pilot = new MovePilot(chassis);
	 
	

	void avancer1() {
		
		Motor.B.forward();
		Motor.C.forward();
		Delay.msDelay(2000);
		Motor.B.stop();		
		Motor.C.stop();
	}
	
	static void avancer2() {
		
		pilot.setLinearSpeed(2000); // cm per second (vitesse)
		pilot.setLinearAcceleration(500);//acceleration
		pilot.travel(-2000);         // cm (distance)
		
	}
	
	public static void tournerMoins90() {
		//tourne le robot de 90° vers la gauche
		 pilot.rotate(-90);        
	}
	
	static void tournerPlus90() {
		//tourne le robot de 90° vers la droite
		pilot.rotate(90);
	}
	
	static void tournerMoins180(){
		//tourne le robot de 180° vers la gauche
		pilot.rotate(-180);
	}
	
	static void tournerPlus180() {
		//tourne le robot de 180° vers la droite
		pilot.rotate(180);
	}
	
	static void tourner(int i) {
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
	
	 
	public static void main(String[] args) {
		
		//test 
		//tournerMoins90() ; //ok
		//tournerPlus90() ; //ok
		//tournerMoins180(); //ok
		//tournerPlus180(); //ok
		//tourner(3000); //ok
		avancer2();
		
		
		
		
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
