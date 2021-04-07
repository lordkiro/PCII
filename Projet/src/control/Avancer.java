/**
 * Cette classe gere l'avancee automatique de la piste
 */

package control;

import model.Etat;
import model.Piste;
import view.Affichage;

public class Avancer extends Thread{
	/** Temps de latence entre deux décallages en millisecondes*/ 
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
	
	/** La classer Accelerer affiliée au jeu actuel*/
	public Accelerer acc;
	
	/** La classe Deplacer affiliée au jeu*/
	public Deplacer d;
	
	/**L'instance de timer du jeu*/
	public Timer ti;
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
	 * @param Deplacer d, l'instance de Deplacer
	 * @param Timer t, l'instance de Timer
	 */
	public Avancer(Etat etat, Piste pi, Affichage aff, Control c, Accelerer acc, Deplacer d, Timer t) {
		this.e = etat;
		this.p = pi;
		this.a = aff;
		this.c = c;
		this.acc = acc;
		acc.setAvancer(this);
		e.vitesse = 5.;
		this.d =d;
		this.ti = t;
		ti.setAv(this);
	}
	
	/**
	 * Methode run
	 * Boucle infini qui va appeler setPos de la classe Piste
	 * On en profite pour verifier si l'on passe par un point de controle
	 * toutes les t millisecondes. 
	 */
	@Override
	public void run() {
		running = true;
		while(running) {
		      try {
		    	  p.setPos();
		    	  e.passePC();
		    	  e.percuteObs();
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
	 * 
	 * Cette methode va arreter le jeu entier, que ce soit les threads et les inputs,
	 * 
	 */
	public void terminate() {
		if(running) {
			running = false; //stop le thread de Avancer
			c.terminate(); //stop les inputs souris
			acc.terminate(); //On stop les autres threads
			d.terminate();
			ti.terminate();
		}
	}
}
