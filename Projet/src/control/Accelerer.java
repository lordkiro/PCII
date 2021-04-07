/**Cette classe gere l'appel regulier de la methode d'acceleration*/
package control;

import model.Etat;
import view.Affichage;

public class Accelerer extends Thread{
	/**L'instance d'avancer du jeu*/
	public Avancer av;
	
	/**L'etat du jeu*/
	public Etat e;

	/**L'affichage du jeu*/
	public Affichage a;

	/**La classe control du jeu*/
	public Control c;

	/**Si le jeu est en train de tourner ou pas*/
	public boolean running;

	/**La frequence a laquelle l'acceleration devra se mettre a jour*/
	public int t = 500;

	/**
	 * Constructeur Accelerer
	 * On affecte les parametres aux attributs de la classe et on affecte l'instane cree a la classe control
	 * 
	 * @param Etat e, l'etat du jeu
	 * @param Affichage a, l'affichage du jeu
	 * @param Control c, l'instance de control du jeu
	 */
	public Accelerer(Etat e, Affichage a, Control c) {
		this.e = e;
		this.a = a;
		this.c = c;
		c.acc = this;
	}

	/** On affecte l'instance a celle d'Avancer passee en parametre
	 * 
	 * @param Avancer av, l'instance a laquelle on veut affecter cette instance
	 */
	public void setAvancer(Avancer av) {
		this.av = av;
	}

	/**
	 * Methode run
	 * Toute les t millisecondes, on appelle la methode accelere de l'etat et on test si on perd
	 */
	@Override
	public void run() {
		running = true;
		while(running) {
			try {
				e.accelere();
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
	 Mets running a false
	 */
	public void terminate() {
		if(running) {
			running = false; //stop le thread de Avancer
		}
	}
}
