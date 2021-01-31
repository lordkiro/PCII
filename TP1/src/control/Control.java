/**
 * Classe Control qui implemente MouseListener, elle change l'etat et demande a 
 * l'affichage de se mettre a jour.
 */

package control;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import model.Etat;
import view.Affichage;

public class Control implements MouseListener {

	/**L'etat lie au controleur*/
	private final Etat e;
	
	/**L'affichage lie au controleur*/
	private final Affichage a;
	
	/** Si le jeu est en train de tourner ou pas*/
	public boolean running;
	
	/**
	 * Construceur de Control.
	 * Ces derniers sont ensuite affectes aux variables concernees.
	 * Enfin on ajoute le controleur aux MouseListener
	 * de l'affichage.
	 * 
	 * @param Etat e : l'etat auquel on doit rattacher le controleur
	 * @param Affichage a : l'affichage lie 
	 */
	public Control(Etat e, Affichage aff) {
		this.e = e;
		this.a = aff;
		a.setMouseListener(this);  //On affecte le controleur de l'on cree aux Listener de this.a
		running = true;
	}

	/**
	 * Methode mouseClicked
	 * Lors d'un clic souris, on appelle la methode jump du modele,
	 * puis on demande a l'affichage de redessiner la fenetre. Cela seulement si le jeu n'est pas perdu
	 * 
	 * @param MouseEvent e , un evenement de clic souris
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if(running) {
			this.e.jump(); /* "this." pour ne pas creer de confusion avec le parametre*/
			a.repaint();

		}
	}

	/** Methode mousePressed */
	@Override
	public void mousePressed(MouseEvent e) {
	}

	/** Methode mouseReleased */
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	/** Methode mouseEntered */
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	/** Methode mouseExited */
	@Override
	public void mouseExited(MouseEvent e) {
	}
}
