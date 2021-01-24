/**
 * Cette classe gere l'avancee automatique de la ligne brisee
 */

package control;

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
	
	/**
	 * Constructeur Avancer
	 * Affecte le parcours et l'affichage passe en parametre aux
	 * constantes de la classe.
	 * 
	 * @param Parcours parc, le parcours que l'on fera defiler
	 * @param Affiachage aff, l'affichage auquel 
	 * on demandera des mises a jour
	 */
	public Avancer(Parcours parc, Affichage aff) {
		this.p = parc;
		this.a = aff;
	}
	
	/**
	 * Methode run
	 * Boucle infini qui va appeler setPos de la classe Parcours
	 * toutes les t millisecondes. 
	 */
	@Override
	public void run() {
		while(true) {
		      try {
		    	  p.setPos();
		    	  a.revalidate(); //On force le dessin pour eviter les ralentissements
		  		  a.repaint();
		    	  Thread.sleep(t); 
		    	  }
		      catch (Exception e) { e.printStackTrace(); 
		      }
		}
	}
}