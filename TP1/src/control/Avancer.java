/**
 * Cette classe gere l'avancee automatique de la ligne brisee
 */

package control;

import model.Etat;
import model.Parcours;
import view.Affichage;

public class Avancer extends Thread{
	/** Temps de latence entre deux décallages en millisecondes
	 * @warning modifier coeffChute si on modifie*/
	public static int t = 50;
	
	/** Le parcours que l'on va faire defiler */
	public Parcours p;
	
	/** L'affichage que l'on doit mettre a jour*/
	public Affichage a;
	
	/** L'etat sur lequel on va influer*/
	public Etat e;
	
	/** L'instance actuelle de Control*/
	public Control c;
	
	/** Si le jeu est en train de tourner ou pas*/
	public boolean running;
	
	/** La classer Voler affilié au jeu actuel*/
	public Voler vol;
	
	/**
	 * Constructeur Avancer
	 * Affecte les parametres passes aux
	 * constantes de la classe. Et s'ajoute aux attributs de la classe Voler
	 * 
	 * @param Etat etat, l'etat sur lequel on va influer
	 * @param Parcours parc, le parcours que l'on fera defiler
	 * @param Affiachage aff, l'affichage auquel on demandera des mises a jour
	 * @param Control c, l'instance de control du jeu
	 * @param Voler vol, l'instance de Voler du jeu
	 */
	public Avancer(Etat etat, Parcours parc, Affichage aff, Control c, Voler vol) {
		this.e = etat;
		this.p = parc;
		this.a = aff;
		this.c = c;
		this.vol = vol;
		vol.av = this;
	}
	
	/**
	 * Methode run
	 * Boucle infini qui va appeler setPos de la classe Parcours
	 * toutes les t millisecondes. 
	 */
	@Override
	public void run() {
		running = true;
		while(running) {
		      try {
		    	  p.setPos();
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
	 * Cette methode va arreter le jeu entier, que ce soit les threads et les inputs souris,
	 * et demander l'affichage de l'ecran de fin
	 */
	public void terminate() {
		if(running) {
			running = false; //stop le thread de Avancer
			c.running = false; //stop les inputs souris
			vol.terminate(); //stop le thread de Voler
			a.endingscreen(); //demande l'affichage de l'ecran de fin
		}
	}
}
