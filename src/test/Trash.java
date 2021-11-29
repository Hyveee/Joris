package test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lejos.utility.Delay;

public void reperage(boolean demi) {

		List <Float> valeurs= new ArrayList<Float> ();	

		List <Float> valeursRecherche= new ArrayList<Float> ();

		List <Float> valeursIgnore= new ArrayList<Float> ();


		boolean dansIgnore;

		joris.getuSSensor().getDistanceMode();
		//joris.getPilot().setAngularSpeed(50);
		if(demi) {
			joris.tourner(120, true);
		} else {
			joris.tourner(390, true);
		}

		while(joris.getPilot().isMoving()) {
			float distance = joris.getDistance();
			valeurs.add(distance);
			Delay.msDelay(1);
		}
		for(int i =0; i<valeurs.size(); i++) {
			if (valeurs.get(i) < 0.30) {
				valeursRecherche.remove(valeurs.get(i));

			}

		}

		float plusPetiteValeur = 6;

		//recherche de la valeur la plus petite
		plusPetiteValeur = Collections.min(valeurs);
		valeursRecherche.addAll(valeurs);
		while (differencierMurPalet(valeurs, plusPetiteValeur, valeurs.indexOf(plusPetiteValeur)) == false) {
			//valeursIgnore.add(plusPetiteValeur);
			//plusPetiteValeur = 10;
			/*for(int i =0; i<valeurs.size(); i++) {
				dansIgnore = false;
				for (int j = 0; j < valeursIgnore.size(); j++) {
					if(valeursIgnore.get(j) == valeurs.get(i)) {
						System.out.println("dans ignore");
						dansIgnore = true;
					} 
				}	
				if (plusPetiteValeur > valeurs.get(i) && !dansIgnore) {
					System.out.println("nouvelle plus petite valeur");
					plusPetiteValeur = valeurs.get(i);

				}

			}*/
			valeursRecherche.remove(plusPetiteValeur);
			plusPetiteValeur = Collections.min(valeursRecherche);
		}
		if (demi) {
			float tourner = -(120 - ((float) valeurs.indexOf(plusPetiteValeur))/(float) valeurs.size()*120);
			joris.tourner((int)tourner );

		} else { 
			float tourner = ((float) valeurs.indexOf(plusPetiteValeur))/(float) valeurs.size()*360;
			joris.tourner((int)tourner );
		} 



		//joris.getG().drawString("" +(plusPetiteValeur)+" "+ tourner, 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
		//	joris.getG().drawString(""+valeurs.size()+" "+valeurs.indexOf(plusPetiteValeur), 0, 22, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
		Delay.msDelay(3000);

		avanceVersPalet();


		/*	float distanceActuelle=joris.getDistance();
		float distancePrecedente;
		float distanceMin=255;
		while(count3 < 500000 ) {


			/*distancePrecedente=distanceActuelle;
			distanceActuelle=joris.getDistance();
			//if(distancePrecedente-distanceActuelle>0.1) {
				if(distanceActuelle<distanceMin) {
					distanceMin=distanceActuelle;
					d = distancePrecedente;
				}


			//}
			count3++;
		}

		for (int i = 0; i < valeurs.size(); i++) {
			count2++;
			if (valeurs.get(i) < distanceMin) {
				distanceMin = valeurs.get(i);
				count++;
			}
		}
		 */

	}*/




/**
 * 
 * @param i 
 * @param plusPetiteValeur 
 * @param valeurs 
 * @param distance
 * @return
 */
public boolean differencierMurPalet(List<Float> valeurs, float plusPetiteValeur, int index) {

	List <Float> valnew= new ArrayList<Float> () ;
	valnew.addAll(valeurs);

	/*float distanceGauche;
	float distanceDroite;


	joris.tourner(-30);
	distanceGauche = joris.getDistance();
	if (Math.abs(distance - distanceGauche) <=  0.1) {
		joris.tourner(-90);
		return false;
	}
Delay.msDelay(1000);

	joris.tourner(60);
	distanceDroite = joris.getDistance();
	if (Math.abs(distance - distanceDroite) <=  0.1) {
		joris.tourner(90);
		return false;
	}
	Delay.msDelay(1000);

	joris.tourner(-30);*/

	float distanceDiff = (float) 0.5;
	int diffV = 10;
	if(plusPetiteValeur >0.7) {
		diffV = 5;
		distanceDiff = (float) 0.8;
	}
	if(plusPetiteValeur >1.5) {
		diffV = 3;
		distanceDiff = (float) 0.10;
	}

	int taille = valeurs.size();

	for(int i =0; i<16; i++) {
		valnew.add(valeurs.get(i)); //ajoute a la fin de la liste les 10 premieres valeurs 
		valnew.add(0, valeurs.get(taille-i-1)); // ajoute au debut de la liste les 10 derniers valeurs

	}
	index = index+15;
	float valAvant;
	float valApres;
	//try {
	System.out.println("avantIf");
	valAvant = valnew.get(index+diffV);
	valApres = valnew.get(index-diffV);
	System.out.println(valAvant+ " " + plusPetiteValeur + "  " + valApres + "  " + index);

	if (valApres > plusPetiteValeur) {
		System.out.println("premierIf");


		if(Math.abs(valApres-plusPetiteValeur)<distanceDiff) {
			System.out.println("deuxiemeIf");

			//System.out.println(valeurs.get(index-diffV)+ " " + plusPetiteValeur + "  " + valeurs.get(index+diffV));
			//Delay.msDelay(1);
			return true;
		}
	}
	//} catch (Exception e) {



	/*
		System.out.println("catch");
		System.out.println(e.getMessage());
		int val = Integer.parseInt(e.getMessage());
		if (val < 0) {
			valAvant = Math.abs(valeurs.get(index+diffV));
			valApres = Math.abs(valeurs.get(valeurs.size()+val));
			if (valAvant> plusPetiteValeur && valApres> plusPetiteValeur) {

				System.out.println("monnouveausuperif");
				if( valAvant <distanceDiff && valApres <distanceDiff) {

					//System.out.println(valeurs.get(index-diffV)+ " " + plusPetiteValeur + "  " + valeurs.get(index+diffV));
					Delay.msDelay(5000);
					return true;
				}
			}
		} else {
			valAvant = Math.abs(valeurs.get(val-valeurs.size()));
			valApres = Math.abs(valeurs.get(index-diffV))  ;
			if (valAvant > plusPetiteValeur && valApres> plusPetiteValeur) {

				System.out.println("monnouveausuperif2");
				if( valAvant<distanceDiff && valApres<distanceDiff) {

					//System.out.println(valeurs.get(index-diffV)+ " " + plusPetiteValeur + "  " + valeurs.get(index+diffV));
					Delay.msDelay(5000);
					return true;
				}
			}
		}

	 */
	//}
	return false;

}

/*public void reperage() {
	reperage(false);
}*/