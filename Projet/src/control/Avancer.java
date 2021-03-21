/**
 * Cette classe gere l'avancee automatique de la piste
 */

package control;

import model.Etat;
import model.Piste;
import view.Affichage;

public class Avancer extends Thread{
	/** Temps de latence entre deux décallages en millisecondes
	 * @warning modifier coeffChute si on modifie*/ //TODO
	public static int t = 50;
	
	/** La piste que l'on va faire defiler */
	public Piste p;
	
	/** L'affichage que l'on doit mettre a jour*/
	public Affichage a;
	
	/** L'etat sur lequel on va influer*/
	public Etat e;
	
	/** L'instance actuelle de Control*/
	public Control c;
	
	/** Si le jeu est en train de tourner ou pas*/
	public boolean running;
	
	/** La classer Accelerer affilié au jeu actuel*/
	public Accelerer acc;
	
	/**
	 * Constructeur Avancer
	 * Affecte les parametres passes aux
	 * constantes de la classe. Et s'ajoute aux attributs de la classe Accelerer
	 * On affecte aussi une vitesse de base à l'etat
	 * 
	 * @param Etat etat, l'etat sur lequel on va influer
	 * @param Piste pi, la piste que l'on fera defiler
	 * @param Affiachage aff, l'affichage auquel on demandera des mises a jour
	 * @param Control c, l'instance de control du jeu
	 * @param Accelerer acc, l'instance d'Accelerer du jeu
	 */
	public Avancer(Etat etat, Piste pi, Affichage aff, Control c, Accelerer acc) {
		this.e = etat;
		this.p = pi;
		this.a = aff;
		this.c = c;
		acc.setAvancer(this);
		e.vitesse = 5.;
	}
	
	/**
	 * Methode run
	 * Boucle infini qui va appeler setPos de la classe Piste
	 * toutes les t millisecondes. 
	 */
	@Override
	public void run() {
		running = true;
		while(running) {
		      try {
		    	  p.setPos();
		    	  e.passePC();
		    	  a.revalidate(); //On force le dessin pour eviter les ralentissements
		  		  a.repaint();
		  		  if(e.testPerdu()) {
		  			  terminate(); //Si on perd on appelle terminate pour arreter le jeu entier
		  		  }
		    	  Thread.sleep(t); 
	    	  }
		      catch (Exception e) { e.printStackTrace(); 
		      }
		}
	}
	
	/**
	 * methode terminate
	 * Cette methode va arreter le jeu entier, que ce soit les threads et les inputs,
	 * et demander l'affichage de l'ecran de fin
	 */
	public void terminate() {
		if(running) {
			running = false; //stop le thread de Avancer
			c.running = false; //stop les inputs souris
			
			/**TODO implementer les terminate des autres threads*/
			/*vol.terminate(); //stop le thread de Voler
			a.endingscreen(); //demande l'affichage de l'ecran de fin*/
		}
	}
}
