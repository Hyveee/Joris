package Joris;
import java.util.ArrayList;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.ColorDetector;
import lejos.robotics.ColorIdentifier;

public class ColorSensor implements ColorDetector, ColorIdentifier
{
	 private EV3ColorSensor	sensor;
	 private float[]		sample;

    	/**
     	* Initialise le ColorSensor
     	* @param port Port utilisé pour le capteur de couleur.
     	*/
	public ColorSensor(Port port)
	{
		sensor = new EV3ColorSensor(port);
		setColorIdMode();
		setFloodLight(false);
	}

	/**
	 * Met le capteur en mode ColorId
	 */
	public void setColorIdMode()
	{
		sensor.setCurrentMode("ColorID");
		sample = new float[sensor.sampleSize()];
	}
	
	/**
	 * Retourne un entier correspondant a la couleur détectée
	 * @return Int. Couleur détectée.
	 */
	@Override
	public int getColorID()
	{
		sensor.fetchSample(sample, 0);
		
		return (int) sample[0];
	}

	/**
	 * Retourne la valeur de la couleur détectée en mode RGB, pour l'instant pas utilisee
	 */
	@Override
	public Color getColor()
	{
		return null;
	}


	/**
	 * Eteint ou allume la lampe intégrée au ColorSensor.
	 * @param true pour allumer la lampe, false pour l'eteindre.
	 */
	public void setFloodLight(boolean on)
	{
		sensor.setFloodlight(on);
	}

}