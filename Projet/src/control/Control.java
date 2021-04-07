/**
 * Classe Control qui implemente KeyListener, elle change l'etat de Deplacer et demande a 
 * l'affichage de se mettre a jour.
 */

package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import model.Etat;
import view.Affichage;

public class Control implements KeyListener {

	/** L'etat e du jeu*/
	public Etat e;
	
	/**l'affiachage a du jeu*/
	public Affichage a;
	
	/** Si le jeu est en train de tourner ou pas*/
	public boolean running;
	
	/**La instance Deplacer du jeu*/
	public Deplacer dep;
	
	/**L'instance de Accelerer du jeu*/
	public Accelerer acc;
	
	/**Constructeur Control
	 * 
	 * On affecte les parametres aux variables de la classes, met le booleen running a true et on focus l'ecoute dans la fenetre de l'affichage
	 * 
	 * @param Etat e, l'etat du jeu
	 * @param Affichage v, l'affichage du jeu
	 * @param Deplacer d, l'instance de Deplacer que l'on va modifier
	 */
	public Control(Etat e, Affichage v, Deplacer d) {
		this.e = e;
		this.a = v;
		this.dep = d;
		v.requestFocusInWindow();  //On affecte le controleur de l'on cree aux Listener de this.a
		running = true;
	}
	
	/**
	 * Methode KeyTyped
	 * 
	 * Pas utilisee
	 */
	@Override
	public void keyTyped(KeyEvent e) {

	}

	/**
	 * Methode keyPressed
	 * 
	 * Si le jeu tourne, on change le booleen correspondant a la touche pressee a true dans la classe Deplacer
	 * 
	 * @param KeyEvent e, l'evenement clavier
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if(running) {
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {     
				this.dep.depD = true;
			}
			else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				this.dep.depG = true;
			}
		}
	}

	/**
	 * Methode keyReleased
	 * 
	 * quand une touche est relachee, on met le booleen correspondant a false dans la classe Deplacer
	 * 
	 * @param KeyEvent e, l'evenement clavier
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			this.dep.depD = false;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			this.dep.depG = false;
		}
	}
	
	/**
	 * Methode terminate
	 * 
	 * set running a faux
	 */
	public void terminate() {
		running = false;
	}

}
