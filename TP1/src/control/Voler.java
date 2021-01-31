/**
 * Cette classe gere la descente automatique de l'ovale. 
 */

package control;

import model.Etat;
import view.Affichage;

public class Voler extends Thread{
	
	/** Temps de latence entre deux descentes en millisecondes
	 * @warning modifier coeffChute si on modifie*/
	public static int t = 50;
	
	/** L'etat que l'on va modifier */
	public Etat e;
	
	/** L'affichage que l'on doit mettre a jour avec moveDown*/
	public Affichage a;
	
	/** Si le jeu est en train de tourner ou pas*/
	public boolean running;
	
	/** L'instance Avancer actuelle*/
	public Avancer av;
	
	/**
	 * Constructeur Voler
	 * Affecte l'etat, l'affichage et l'instance avancer passes
	 *  en parametre aux constantes de la classe.
	 * 
	 * @param Etat etat, l'etat que l'on modifiera
	 * @param Affiachage aff, l'affichage auquel 
	 * on demandera des mises a jour
	 * @param Avancer av, l'instance de Avancer du jeu
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
		  			av.terminate(); //Si l'on detecte que l'on a perdu, on appelle la fonction 
		  							//terminate de avancer qui va stopper tout le programme
		  		  }
		    	  Thread.sleep(t); 
		    	  }
		      catch (Exception e) { e.printStackTrace(); 
		      }
		}
	}
	 /**
	  * methode terminate
	  * Cette methode va simplement arreter ce thread*/
	public void terminate() {
		running = false;
	}
}
