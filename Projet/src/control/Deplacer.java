/** Classe Deplacer qui etend Thread. Elle permet d'avoir un deplacement fluide */
package control;

import model.Etat;
import view.Affichage;

public class Deplacer extends Thread{

	/**L'etat e du jeu*/
	public Etat e;

	/**L'affichage a du jeu*/
	public Affichage a;

	/**Si on appuie sur une des touches de deplacement a gauche*/
	public boolean depG;

	/**Si on appuie sur une des touches de deplacement a droite*/
	public boolean depD;

	/** Si le jeu est en train de tourner ou pas*/
	public boolean running;
	
	/**Latence entre deux deplacement en milliseconde*/
	public int l = 50;

	/**Constructeur Deplacer
	 * 
	 * on affecte les parametres aux attributs de la classe et on initialise depG et depD a false
	 * 
	 * @param Etat e, l'etat du jeu
	 * @param Affichage a, l'affichage du jeu
	 */
	public Deplacer(Etat e, Affichage a) {
		this.e = e;
		this.a = a;
		depG = false;
		depD = false;
	}
	/**
	 * Methode run
	 * 
	 * On initialise running a true
	 * Si le jeu est en train de tourner, quand depG ou (exclusif) depD sont a true, on se deplace dans la direction correspondante.
	 * On passe dans la boucle while toutes les l millisecondes, on demande une mise a jour de l'affichage a chaque fois.
	 * Grace a cela on se deplace de maniere reguliere.
	 */
	public void run() {
		running = true; //Quand le jeu demarre, on initialise running a true
		while(running) {
			try {
				if(depG && !depD) { 
					e.moveL();
				}					//Pour ces deux conditions, on utilise le ou exclusif
				if(depD && !depG) {
					e.moveR();
				}
				a.repaint();	//On demande a l'affichage de se mettre a jour
				Thread.sleep(l); 
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**TODO 
	 * methode terminate
	 */
}
