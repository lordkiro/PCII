/**
 * Cette classe gere la descente automatique de l'ovale. 
 */

package control;

import model.Etat;
import view.Affichage;

public class Voler extends Thread{
	
	/** Temps de latence entre deux descentes en millisecondes
	 * @warning modifier coeffChute si on modifie*/
	public static int t = 200;
	
	/** L'etat que l'on va modifier */
	public Etat e;
	
	/** L'affichage que l'on doit mettre a jour avec moveDown*/
	public Affichage a;
	
	
	public boolean running;
	
	
	public Avancer av;
	
	/**
	 * Constructeur Voler
	 * Affecte l'etat et l'affichage passe en parametre aux
	 * constantes de la classe.
	 * 
	 * @param Etat etat, l'etat que l'on modifiera
	 * @param Affiachage aff, l'affichage auquel 
	 * on demandera des mises a jour
	 */
	public Voler(Etat etat, Affichage aff) {
		this.e = etat;
		this.a = aff;
	}
	
	/**
	 * Methode run
	 * Boucle infini qui va appeler moveDown de la classe Etat
	 * toutes les t millisecondes. 
	 */
	@Override
	public void run() {
		running = true;
		while(running) {
		      try {
		    	  e.moveDown(Affichage.yS);
		    	  a.revalidate(); //On force le dessin pour eviter les ralentissements
		  		  a.repaint();
		  		  if(e.testPerdu()) {
		  			av.terminate();
		  		  }
		    	  Thread.sleep(t); 
		    	  }
		      catch (Exception e) { e.printStackTrace(); 
		      }
		}
	}
	
	public void terminate() {
		running = false;
	}
}
