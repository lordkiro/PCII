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
	public static int t = 200;
	
	/** Le parcours que l'on va faire defiler */
	public Parcours p;
	
	/** L'affichage que l'on doit mettre a jour*/
	public Affichage a;
	
	
	public Etat e;
	
	
	public Control c;
	
	
	public boolean running;
	
	
	public Voler vol;
	
	/**
	 * Constructeur Avancer
	 * Affecte le parcours et l'affichage passe en parametre aux
	 * constantes de la classe.
	 * 
	 * @param Parcours parc, le parcours que l'on fera defiler
	 * @param Affiachage aff, l'affichage auquel 
	 * on demandera des mises a jour
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
		  			  terminate();
		  		  }
		    	  Thread.sleep(t); 
		    	  }
		      catch (Exception e) { e.printStackTrace(); 
		      }
		}
	}
	
	public void terminate() {
		if(running) {
			running = false;
			c.running = false;
			vol.terminate();
			a.endingscreen();
		}
	}
}
