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
	

	private EV3LargeRegulatedMotor moteurGauche = new EV3LargeRegulatedMotor(MotorPort.C);
	private Wheel wheel1 = WheeledChassis.modelWheel(moteurDroit, 56).offset(-58);
	private Wheel wheel2 = WheeledChassis.modelWheel(moteurGauche, 56).offset(58);
	private Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
	private MovePilot pilot = new MovePilot(chassis);
	
	private EV3UltrasonicSensor uSSensor = new EV3UltrasonicSensor(SensorPort.S4);
	private EV3ColorSensor cSensor = new EV3ColorSensor(SensorPort.S2);
	private EV3TouchSensor tSensor = new EV3TouchSensor(SensorPort.S3);
	private int tachoCount = getMoteurPince().getTachoCount();

	
	private  boolean pinceFerme = true;
	private int boussole = 0;
	
	
	
	public Agent(){
		getMoteurPince().setSpeed(1000);
		pilot.setAngularSpeed(1000);
		moteurDroit.getSpeed();
		
	}
	
	public EV3LargeRegulatedMotor getMoteurDroit() {
		return moteurDroit;
	}
	/**
	 * methode qui retourne un boolean si la pince est fermee
	 * @return true si la pince est fermee
	 */
	
	public boolean getpinceFerme() {
		return pinceFerme;
	}
	
	/**
	 *methode sans retour qui ferme la pince si celle-ci est ouverte
	 *la fermeture se fait avec une rotation du moteurPince de -1350
	 */
	public void pinceFermeture() {
		pinceFermeture(false);
	}
	
	public void pinceFermeture(boolean info) {
		if (pinceFerme == false) {
			getMoteurPince().rotate(-tachoCount, info);
			pinceFerme = true;
			System.out.println("Je ferme mes pinces parce que je suis pas une sale merde");
		}
	}
	
	/**
	 *methode sans retour qui ouvre la pince si celle-ci est fermee
	 *l'ouverture se fait avec une rotation du moteurPince de 1350
	 */
	public void pinceOuverture() {
		pinceOuverture(false);
	}
	
	public void pinceOuverture(boolean infoSurLesPincesSiTuVeuxLeFaireEnMemeTempsOuPasTuDecidesBG) {
		/*
		if (pinceFerme == true) {
			moteurPince.rotate(1350,infoSurLesPincesSiTuVeuxLeFaireEnMemeTempsOuPasTuDecidesBG);
			pinceFerme = false;
		}	*/
		if (pinceFerme == true){
			getMoteurPince().resetTachoCount();
			tachoCount = 0;
			getMoteurPince().rotate(1350, infoSurLesPincesSiTuVeuxLeFaireEnMemeTempsOuPasTuDecidesBG);
			pinceFerme = false;
		}
	}
	
	/**
	 * methode qui renvoie un int en faisant appel au colorSensor
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
	 * methode qui permet de mesurer la distance de l'objet qui se trouve devant le robot par l ultrasonicSensor 
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
	 * methode qui permet de faire tourner le robot de i degres de maniere synchrone
	 * @param i est un angle
	 * 
	 */
	public void tourner(int i) {
		tourner(i, false);
	}
	
	/**
	 * methode qui permet de faire tourner le robot grace a la methode rotate de i degres de maniere synchore ou asynchrone
	 * et permet aussi de rajouter langle de rotaion du robot a la boussole
	 * @param i est un angle 
	 * @param asynchroneOuSynchroneAToiDeDecider est un boolean, si c est synchrone (false) et si cest asynchrone(true) 
	 */
	public void tourner(int i, boolean asynchroneOuSynchroneAToiDeDecider) {
		//tourne le robot d'un angle entre -180 et 180
		
		boussole= boussole+i;
		
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
	
	/**
	 * 
	 * @return un int 
	 */
	public int listen() {
		SampleProvider sp = this.getuSSensor().getListenMode();
		float[] sample = new float [sp.sampleSize()];
		sp.fetchSample(sample, 0);
		int other = (int) sample[0];
		return other;
	}
	
	/**
	 * methode qui permet de savoir si le TouchSensor est enfonce ou non par le palet
	 * @return un int : 1 si le capteur est enfonce, 0 sinon
	 */
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
	
	public int getTachoCount() {
		return tachoCount;
	}

	public void setTachoCount() {
		this.tachoCount = this.getMoteurPince().getTachoCount();
	}

	public EV3MediumRegulatedMotor getMoteurPince() {
		return moteurPince;
	}

	public void setMoteurPince(EV3MediumRegulatedMotor moteurPince) {
		this.moteurPince = moteurPince;
	}
}