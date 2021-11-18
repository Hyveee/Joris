package Joris;
import java.io.File;
import java.lang.*;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.BrickFinder;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.*;
import lejos.hardware.port.MotorPort;

public class Agent {
	
	private GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();
	private EV3MediumRegulatedMotor moteurPince = new EV3MediumRegulatedMotor(MotorPort.A);
	private EV3LargeRegulatedMotor moteurDroit = new EV3LargeRegulatedMotor(MotorPort.B);
	public EV3LargeRegulatedMotor getMoteurDroit() {
		return moteurDroit;
	}

	private EV3LargeRegulatedMotor moteurGauche = new EV3LargeRegulatedMotor(MotorPort.C);
	private Wheel wheel1 = WheeledChassis.modelWheel(moteurDroit, 56).offset(-58);
	private Wheel wheel2 = WheeledChassis.modelWheel(moteurGauche, 56).offset(58);
	private Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
	private MovePilot pilot = new MovePilot(chassis);
	
	private EV3UltrasonicSensor uSSensor = new EV3UltrasonicSensor(SensorPort.S4);
	private EV3ColorSensor cSensor = new EV3ColorSensor(SensorPort.S2);
	private EV3TouchSensor tSensor = new EV3TouchSensor(SensorPort.S3);
	
	private  boolean pinceFerme = false;
	private int boussole = 0;
	
	public Agent(){
		pilot.setAngularSpeed(1000);
		moteurDroit.getSpeed();
	}
	
	public boolean getpinceFerme() {
		return pinceFerme;
	}
	
	public void pinceFermeture() {
			if (pinceFerme == false) {
				moteurPince.rotate(-1350);
				pinceFerme = true;
			}
	}
	
	public  void pinceOuverture() {
		if (pinceFerme == true) {
			moteurPince.rotate(1350);
			pinceFerme = false;
		}	
	}
	
	public int getColorID() {
		SampleProvider sp = this.cSensor.getColorIDMode();
		float[] sample = new float [sp.sampleSize()];
		sp.fetchSample(sample, 0);
		int couleur = (int) sample[0];
		return couleur;
	}
	
	public float getDistance() {
		SampleProvider sp = this.getuSSensor().getDistanceMode();
		float[] sample = new float [sp.sampleSize()];
		sp.fetchSample(sample, 0);
		float distance = sample[0];
		return distance;
	}
	
	public  void tourner(int i) {
		tourner(i, false);
	}
	
	public  void tourner(int i, boolean asynchroneOuSynchroneAToiDeDecider) {
		//tourne le robot d'un angle entre -180 et 180
		
		boussole= ( boussole+i);
		
		/*
		boussole=(i % 360);
		
		
		if (0<=boussole && boussole<=180) {
			boussole =+ i;
		}
		else { 
			boussole =+ i-360;
		}
		*/
		pilot.rotate(i,asynchroneOuSynchroneAToiDeDecider);
		
	}
	
	public int listen() {
		SampleProvider sp = this.getuSSensor().getListenMode();
		float[] sample = new float [sp.sampleSize()];
		sp.fetchSample(sample, 0);
		int other = (int) sample[0];
		return other;
	}
	
	public int touche() {
		SampleProvider sp = this.tSensor.getTouchMode();
		float[] sample = new float [sp.sampleSize()];
		sp.fetchSample(sample, 0);
		int touch = (int)sample[0];
		return touch;
	}
		
	public GraphicsLCD getG() {
		return g;
	}

	public void setG(GraphicsLCD g) {
		this.g = g;
	}

	public MovePilot getPilot() {
		return pilot;
	}

	public void setPilot(MovePilot pilot) {
		this.pilot = pilot;
	}

	public EV3UltrasonicSensor getuSSensor() {
		return uSSensor;
	}

	public void setuSSensor(EV3UltrasonicSensor uSSensor) {
		this.uSSensor = uSSensor;
	}

	public int getBoussole() {
		return boussole;
	}
}