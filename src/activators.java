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

public class activators {
	private GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();
	private RegulatedMotor rDroite = new EV3LargeRegulatedMotor(MotorPort.B);
	private RegulatedMotor rGauche = new EV3LargeRegulatedMotor(MotorPort.C);
	
	private static Wheel wheel1 = WheeledChassis.modelWheel(Motor.B, 56.0).offset(-58);
	private static Wheel wheel2 = WheeledChassis.modelWheel(Motor.C, 56.0).offset(58);
	private static Chassis chassis = new WheeledChassis(new Wheel[]{wheel1, wheel2}, WheeledChassis.TYPE_DIFFERENTIAL); 
	private static MovePilot pilot = new MovePilot(chassis);
	 
	

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
	

	public GraphicsLCD getG() {
		return g;
	}

	public void setG(GraphicsLCD g) {
		this.g = g;
	}

	public RegulatedMotor getrDroite() {
		return rDroite;
	}

	public void setrDroite(RegulatedMotor rDroite) {
		this.rDroite = rDroite;
	}

	public RegulatedMotor getrGauche() {
		return rGauche;
	}

	public void setrGauche(RegulatedMotor rGauche) {
		this.rGauche = rGauche;
	}

	public static Wheel getWheel1() {
		return wheel1;
	}

	public static void setWheel1(Wheel wheel1) {
		activators.wheel1 = wheel1;
	}

	public static Wheel getWheel2() {
		return wheel2;
	}

	public static void setWheel2(Wheel wheel2) {
		activators.wheel2 = wheel2;
	}

	public static Chassis getChassis() {
		return chassis;
	}

	public static void setChassis(Chassis chassis) {
		activators.chassis = chassis;
	}

	public static MovePilot getPilot() {
		return pilot;
	}

	public static void setPilot(MovePilot pilot) {
		activators.pilot = pilot;
	}
}
