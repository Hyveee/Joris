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

public class Activators {
	private GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();
	private RegulatedMotor rDroite = new EV3LargeRegulatedMotor(MotorPort.B);
	private RegulatedMotor rGauche = new EV3LargeRegulatedMotor(MotorPort.C);
	
	private Wheel wheel1 = WheeledChassis.modelWheel(Motor.B, 56.0).offset(-58);
	private Wheel wheel2 = WheeledChassis.modelWheel(Motor.C, 56.0).offset(58);
	private Chassis chassis = new WheeledChassis(new Wheel[]{wheel1, wheel2}, WheeledChassis.TYPE_DIFFERENTIAL); 
	private MovePilot pilot = new MovePilot(chassis);

	public void avancer1() {
		
		Motor.B.forward();
		Motor.C.forward();
		Delay.msDelay(2000);
		Motor.B.stop();		
		Motor.C.stop();
	}
	
	public void avancer2() {
		
		pilot.setLinearSpeed(2000); // cm per second (vitesse)
		pilot.setLinearAcceleration(500);//acceleration
		pilot.travel(-2000);         // cm (distance)
		
	}
	
	public void tournerMoins90() {
		//tourne le robot de 90° vers la gauche
		 pilot.rotate(-90);        
	}
	
	public void tournerPlus90() {
		//tourne le robot de 90° vers la droite
		pilot.rotate(90);
	}
	
	public void tournerMoins180(){
		//tourne le robot de 180° vers la gauche
		pilot.rotate(-180);
	}
	
	public void tournerPlus180() {
		//tourne le robot de 180° vers la droite
		pilot.rotate(180);
	}
	
	public void tourner(int i) {
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

	public Wheel getWheel1() {
		return wheel1;
	}

	public void setWheel1(Wheel wheel1) {
		this.wheel1 = wheel1;
	}

	public Wheel getWheel2() {
		return wheel2;
	}

	public void setWheel2(Wheel wheel2) {
		this.wheel2 = wheel2;
	}

	public Chassis getChassis() {
		return chassis;
	}

	public void setChassis(Chassis chassis) {
		this.chassis = chassis;
	}

	public MovePilot getPilot() {
		return pilot;
	}

	public void setPilot(MovePilot pilot) {
		this.pilot = pilot;
	}

}
