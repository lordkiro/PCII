/**
 * Classe Affichage heritee de JPanel, elle affiche l'etat.
 */

package view;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import control.Control;
import model.Etat;
import model.Parcours;


@SuppressWarnings("serial")
public class Affichage extends JPanel {

	/**La largeur de la fenetre que l'on veut creer*/
	public static final int xS = 600;
	
	/**La hauteur de la fenetre que l'on veut creer*/
	public static final int yS = 600;

	/**L'etat que va afficher notre fenetre*/
	protected final Etat etat;
	
	/**Le parcours dans lequel on evolue*/
	public Parcours parcours;
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
	 * On appelle aussi les methodes drawParcours et drawScore definient plus bas.
	 * 
	 * @param Graphics g : le contexte graphique
	 */
	@SuppressWarnings("static-access")
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawOval(etat.xC, etat.yC, etat.w, etat.h);
		drawParcours(parcours.getParcours(), g);
		drawScore(g);
	}
	
	/**Methode setMouseListener
	 * on ajoute simplement le c en parametre aux Listener de l'affichage
	 * 
	 * @param Control c : le controleur qui veut "ecouter" la souris
	 */
	public void setMouseListener(Control c) {
		addMouseListener(c);
	}
	
	/**
	 * Methode setParcours
	 * Lie le parcours dans lequel on a evoluer a l'attribut concerne
	 */
	public void setParcours(Parcours p) {
		parcours = p;
	}
	
	/**
	 * Methode drawParcours
	 * Dessine le parcours en rouge
	 * 
	 * @param Point[] p, la liste de points a afficher
	 * @param Graphics g, le contexte graphique
	 */
	public void drawParcours(Point[] p, Graphics g) {
		Color temp = g.getColor();
		g.setColor(Color.red);		//Pour qu'il ressorte plus, on choisi d'afficher le parcours en rouge
		for(int i = 1; i< p.length; i++) {
			g.drawLine(p[i-1].x, p[i-1].y, p[i].x, p[i].y);
		}
		g.setColor(temp); //On remet ensuite le contexte graphique en noir
	}
	
	/**
	 * Methode drawScore
	 * On affiche le score dans un rectangle blanc en haut a gauche de la fenetre
	 * 
	 * @param Graphics g, le contexte graphique
	 */
	public void drawScore(Graphics g) {
		g.clearRect(110, 0, 80, 30); //On libere un rectangle ou l'on pourra ecrire sans que la ligne brisee ne gene la lecture
		g.drawString(String.format("%d", parcours.getPos()), 110, 17); 
	}
	
	public void endingscreen() {
		JOptionPane.showMessageDialog(this, String.format("Votre score est de %d", this.parcours.getPos()), "Perdu!", JOptionPane.INFORMATION_MESSAGE);
	}
}
