/**TODO*/
package control;


import model.Etat;
import model.Piste;
import model.Temps;
import view.Affichage;

public class Timer extends Thread{
	public Etat e;
	
	public Affichage a;
	
	public Piste p;
	
	public long l = 1000;
	
	public Temps t;
	
	public boolean running;
	
	public Timer(Etat et, Affichage aff, Piste pi, Temps te) {
		e = et;
		a = aff;
		p = pi;
		t = te;
		running = true;
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
		    	  t.decreaseT();
		    	  a.revalidate(); //On force le dessin pour eviter les ralentissements
		  		  a.repaint();
		  		  if(t.testPerdu()) {
		  			  terminate(); //Si on perd on appelle terminate pour arreter le jeu entier
		  		  }
		    	  Thread.sleep(l); 
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
			/*c.running = false; //stop les inputs souris
			vol.terminate(); //stop le thread de Voler
			a.endingscreen(); //demande l'affichage de l'ecran de fin*/
		}
	}
}
