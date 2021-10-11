import java.io.File;

import javax.sound.sampled.Port;

import lejos.hardware.BrickFinder;
import lejos.hardware.Sound;
import lejos.hardware.lcd.GraphicsLCD;
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

public class Agent {
	
	private UltraSonicSensor  us;
	private Activators ac;
	private TouchSensor ts;
	
	public Agent() {
		Agent agent = new Agent();

		
		this.us = new UltraSonicSensor(SensorPort.S4);
		this.ac = new Activators(MotorPort.B,MotorPort.C);
		this.ts = new TouchSensor(SensorPort.S2);
	}
	
	public UltraSonicSensor getUltrasonicSensor() {
		return this.us;
		
	}
	
	public Activators getActivator() {
		return this.ac;
		
	}
	
	public TouchSensor getTouchSensor() {
		return this.ts;
		
	}
	
	public void reperage() {
		float i = 10000;
		Thread t1 = new Thread() {
			public void run() {
				ac.tourner(360);
			}
		};
		t1.start();
		while (t1.isAlive()) {
			float dist = us.getDistance();
			if (dist<i) {
				i = dist;
			}
		}		
		t1.start();
		while (t1.isAlive()) {
			float dist = us.getDistance();
			if (dist==i) {
				t1.interrupt();
				break;
			}
		}
	}
	
	public void differencierMurPalet() {
		
	}
	
	public void differencierRobot() {
		
	}
	
	public void avanceVersPalet(){
		
		if (this.getTouchSensor().touche() == true) {
			this.ac.moteurStop();
			recupPalet();
		}
	}
	
	public void recupPalet() {
			if(this.getActivator().isPinceFermee()== false) {
				this.getActivator().pinceFermeture();
			}	
		
	}
	
	public void ramenerPaletZone() {
		
	}
	
	public void changerDeDirection() {
		
	}
	
	
	public static void auto() {
		Agent agent = new Agent();
		 agent.getActivator().avancer1();
		
		 if (agent.getUltrasonicSensor().getDistance()<=0.30 ) {
			 
			 System.out.println("distance mur proche");
			 if (agent.getUltrasonicSensor().getDistance()<=0.20) 
				agent.getActivator().moteurStop();

			 else if (agent.getTouchSensor().touche()==true) {
				 agent.getActivator().pinceFermeture();
				 agent.getActivator().moteurStop();

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
