/**Cette classe gere le temps dans le jeu*/
package control;


import model.Etat;
import model.Piste;
import model.Temps;
import view.Affichage;

public class Timer extends Thread{
	/**L'etat du jeu*/
	public Etat e;
	
	/**L'affichage du jeu*/
	public Affichage a;
	
	/**La piste du jeu*/
	public Piste p;
	
	/**La frequence a laquelle le timer se mettra a jour*/
	public long l = 1000; //1sec
	
	/**L'instance de Temps du jeu*/
	public Temps t;
	
	/**Si le jeu est en train de tourner ou pas*/
	public boolean running;
	
	
	/**L'instance d'avancer du jeu*/
	public Avancer av;
	
	/**
	 * Constructeur Timer
	 * 
	 * On affecte les parametres aux attributs de la classe et on met running a true.
	 * 
	 * @param Etat et, l'etat du jeu
	 * @param Affichage aff, l'affichage du jeu
	 * @param Piste pi, la piste du jeu
	 * @param Temps te, l'instance temps du jeu
	 */
	public Timer(Etat et, Affichage aff, Piste pi, Temps te) {
		e = et;
		a = aff;
		p = pi;
		t = te;
	}
	

	
	/**
	 * Methode run
	 * Boucle infini qui va appeler decrease de la classe Temps, apres quoi on reaffiche le nouveau temps
	 * toutes les secondes. 
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
		running = false;
		if(av.running) {
			av.terminate();	
		}
	}
	
	/**
	 * Methode setAv
	 * 
	 * Lie l'instance d avancer du jeu
	 */
	public void setAv(Avancer av) {
		this.av = av;
	}
}
