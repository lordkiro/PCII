/**
 * Classe Affichage heritee de JPanel, elle affiche l'etat.
 */

package view;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;

import control.Control;
import model.Etat;


@SuppressWarnings("serial")
public class Affichage extends JPanel {

	/**La largeur de la fenetre que l'on veut creer*/
	public static final int xS = 600;
	
	/**La hauteur de la fenetre que l'on veut creer*/
	public static final int yS = 400;

	/**L'etat que va afficher notre fenetre*/
	protected final Etat etat;
	
	/** 
	 * Constructeur 
	 * On donne la taille ideale de notre fenetre puis
	 * on affecte l'etat passe en argument a la variable. 
	 * 
	 * @param Etat e : l'etat que notre fenetre va afficher
	 */
	public Affichage(Etat e) {
		setPreferredSize(new Dimension(xS, yS));
		etat = e;
	}

	/**
	 * Methode paint
	 * On appelle la methode paint de JPanel afin de clean la fenetre,
	 * puis on appelle drawOval de Graphics afin de redessiner notre
	 * ovale avec ses nouvelles coordonees.
	 * 
	 * @param Graphics g : le contexte graphique
	 */
	@SuppressWarnings("static-access")
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawOval(etat.xC, etat.yC, etat.w, etat.h);
	}
	
	/**Methode setMouseListener
	 * on ajoute simplement le c en parametre aux Listener de l'affichage
	 * 
	 * @param Control c : le controleur qui veut "ecouter" la souris
	 */
	public void setMouseListener(Control c) {
		addMouseListener(c);
	}
}
