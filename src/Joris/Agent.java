package Joris;
import java.io.File;
import java.lang.*;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.BaseMotor;
import lejos.robotics.Encoder;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.RegulatedMotorListener;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;
import lejos.hardware.port.SensorPort;
import lejos.hardware.port.TachoMotorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.BrickFinder;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.*;
import lejos.hardware.port.MotorPort;

public class Agent {
	
	private GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();
	private EV3MediumRegulatedMotor moteurPince = new EV3MediumRegulatedMotor(MotorPort.A);
	private EV3LargeRegulatedMotor moteurDroit = new EV3LargeRegulatedMotor(MotorPort.B);
	private EV3LargeRegulatedMotor moteurGauche = new EV3LargeRegulatedMotor(MotorPort.C);
	private Wheel wheel1 = WheeledChassis.modelWheel(moteurDroit, 56).offset(-58);
	private Wheel wheel2 = WheeledChassis.modelWheel(moteurGauche, 56).offset(58);
	private Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
	private MovePilot pilot = new MovePilot(chassis);	
	private EV3UltrasonicSensor uSSensor = new EV3UltrasonicSensor(SensorPort.S4);
	private EV3ColorSensor cSensor = new EV3ColorSensor(SensorPort.S2);
	private EV3TouchSensor tSensor = new EV3TouchSensor(SensorPort.S3);
	private int tachoCountMP = getMoteurPince().getTachoCount();
	private int tachoCountRD = getMoteurDroit().getTachoCount();
	private boolean pinceFerme = true;
	private int boussole = 0;
	


	public Agent(){
		getMoteurPince().setSpeed(1000);
		pilot.setAngularSpeed(1000);
		moteurDroit.getSpeed();
	}
	
	/**
	 * Ferme la pince si celle-ci sont ouvertes en appelant la methode pinceFermeture(boolean info) 
	 *
	 */
	
	public void pinceFermeture() {
		pinceFermeture(false);
	}
	
	/**
	 * Permet de fermer les pinces
	 * @param info est un boolean, true si on veut que la fermeture des pinces soit asynchrone et false si on veut que la 
	 * fermeture des pinces soit synchrone
	 */
	
	public void pinceFermeture(boolean info) {
		if (pinceFerme == false) {
			setTachoCountMP();
			pinceFerme = true;
			getMoteurPince().rotate(-tachoCountMP, info);			
		}
	}
	
	/**
	 *Ouvre la pince si celle-ci sont fermees en appelant la methode pinceOuverture(boolean info) 
	 *
	 */
	public void pinceOuverture() {
		pinceOuverture(false);
	}
	
	/**
	 * Permet d ouvrir les pinces
	 * @param infoSurLesPincesSiTuVeuxLeFaireEnMemeTempsOuPasTuDecidesBG est un boolean, true si on veut que la fermeture des pinces soit asynchrone et false si on veut que la 
	 * fermeture des pinces soit synchrone
	 */
	public void pinceOuverture(boolean infoSurLesPincesSiTuVeuxLeFaireEnMemeTempsOuPasTuDecidesBG) {
		if (pinceFerme == true){
			getMoteurPince().resetTachoCount();
			setTachoCountMP();
			pinceFerme = false;
			getMoteurPince().rotate(1350, infoSurLesPincesSiTuVeuxLeFaireEnMemeTempsOuPasTuDecidesBG);
		}
	}
	
	/**
	 * Retourne la couleur capt�e par le colorSensor
	 * @return un int qui correspond a la couleur detectee par le colorSensor
	 */
	public int getColorID() {
		SampleProvider sp = this.cSensor.getColorIDMode();
		float[] sample = new float [sp.sampleSize()];
		sp.fetchSample(sample, 0);
		int couleur = (int) sample[0];
		return couleur;
	}
	
	/**
	 * Mesure la distance de l'objet qui se trouve devant le robot par l ultrasonicSensor 
	 * @return un float qui correspond a la distance percue par l ultrasonicSensor
	 */
	public float getDistance() {
		SampleProvider sp = this.getuSSensor().getDistanceMode();
		float[] sample = new float [sp.sampleSize()];
		sp.fetchSample(sample, 0);
		float distance = sample[0];
		return distance;
	}
	
	/**
	 * Faire tourner le robot de i degres de maniere synchrone
	 * @param i est un angle
	 * 
	 */
	public void tourner(int i) {
		tourner(i, false);
	}
	
	/**
	 * Permet de faire tourner le robot grace a la methode rotate de i degres de maniere synchrone ou asynchrone
	 * et permet aussi de rajouter langle de rotation du robot a la boussole
	 * @param i est un angle 
	 * @param asynchroneOuSynchroneAToiDeDecider est un boolean, si c est asynchrone (true) et si cest synchrone(false) 
	 */
	public void tourner(int i, boolean asynchroneOuSynchroneAToiDeDecider) {
		
		pilot.rotate(i,asynchroneOuSynchroneAToiDeDecider);
		
	}
	
	/**
	 * Retrouve la direction de la ligne blanche d'en but en fonction de la boussole
	 */
	
	public void directionLigneBlanche() {
		pilot.setAngularSpeed(200);
		this.boussole = boussole%780;		
		if (0 <= boussole && boussole <= 390) {
			moteurDroit.resetTachoCount();
			System.out.println("boussole3 : " + boussole);
			this.getPilot().rotate(-(boussole/2), false);
			setTachoCountRD();
			boussole =  boussole + this.getTachoCountRD();
			System.out.println("boussole4 : " + boussole);
		}
		else if (390 < boussole && boussole <= 780) {
			moteurDroit.resetTachoCount();
			System.out.println("boussole3 : " + boussole);
			this.getPilot().rotate(((780-boussole)/2), false);
			setTachoCountRD();
			boussole =  boussole + this.getTachoCountRD();
			System.out.println("boussole4 : " + boussole);
		}
		else if (-390 <= boussole && boussole < 0) {
			moteurDroit.resetTachoCount();
			System.out.println("boussole3 : " + boussole);
			this.getPilot().rotate(-(boussole/2), false);
			setTachoCountRD();
			boussole =  boussole + this.getTachoCountRD();
			System.out.println("boussole4 : " + boussole);
		}
		else if (-780 <= boussole && boussole < -390) {
			moteurDroit.resetTachoCount();
			System.out.println("boussole3 : " + boussole);
			this.getPilot().rotate(((780 +boussole +60)/2), false);
			setTachoCountRD();
			boussole =  boussole + this.getTachoCountRD();
			System.out.println("boussole4 : " + boussole);
		}
		
		this.boussole = 0;
	}
	
	/**
	 *  Ecoute si un autre capteur ultrasonic fonctionne a proximite
	 * @return int : si le capteur a trouv� un autre robot ou non 
	 */
	public int listen() {
		SampleProvider sp = this.getuSSensor().getListenMode();
		float[] sample = new float [sp.sampleSize()];
		sp.fetchSample(sample, 0);
		int other = (int) sample[0];
		return other;
	}
	
	/**
	 * Permet de savoir si le TouchSensor est enfonce ou non par le palet
	 * @return un int : 1 si le capteur est enfonce, 0 sinon
	 */
	public int touche() {
		SampleProvider sp = this.tSensor.getTouchMode();
		float[] sample = new float [sp.sampleSize()];
		sp.fetchSample(sample, 0);
		int touch = (int)sample[0];
		return touch;
	}

	/**
	 * 
	 * @return MovePilot pilote courant
	 */
	public MovePilot getPilot() {
		return pilot;
	}

	/**
	 * 
	 * @return EV3UltrasonicSensor ultrasonicSensor courant
	 */
	public EV3UltrasonicSensor getuSSensor() {
		return uSSensor;
	}

	/**
	 * 
	 * @return int valeur sctock�e dans la boussole
	 */
	public int getBoussole() {
		return boussole;
	}
	
	/**
	 * Recupere la valeur du tachoCount du moteur utilise par les pinces
	 */
	public void setTachoCountMP() {
		this.tachoCountMP = this.getMoteurPince().getTachoCount();
	}
	
	/**
	 * 
	 * @return EV3LargeRegulatedMotor moteur utilise pour la roue droite
	 */
	public EV3LargeRegulatedMotor getMoteurDroit() {
		return moteurDroit;
	}
	
	/**
	 * 
	 * @return EV3MediumRegulatedMotor moteur utilise pour les pinces
	 */
	public EV3MediumRegulatedMotor getMoteurPince() {
		return moteurPince;
	}
	
	/**
	 * 
	 * @return int valeur du tachoCount associee a la roue droite
	 */
	public int getTachoCountRD() {
		return tachoCountRD;
	}

	/**
	 * Recupere la valeur du tachoCount du moteur utilise par la roue droite
	 */
	public void setTachoCountRD() {
		this.tachoCountRD = this.getMoteurDroit().getTachoCount();
	}
	
	/**
	 * 
	 * @param boussole int valeur a remplacer pour la variable boussole
	 */
	public void setBoussole(int boussole) {
		this.boussole = boussole;
	}
	
}