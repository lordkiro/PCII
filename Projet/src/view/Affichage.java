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
import java.awt.event.KeyListener;

import control.Control;
import model.Etat;
import model.Piste;



@SuppressWarnings({ "serial", "unused" })
public class Affichage extends JPanel {

	/**La largeur de la fenetre que l'on veut creer*/
	public static final int xS = 800;

	/**La hauteur de la fenetre que l'on veut creer*/
	public static final int yS = 800;

	/**L'etat que va afficher notre fenetre*/
	protected final Etat etat;

	public Piste p;
	
	
	/** 
	 * Constructeur Affichage
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
	 * On appelle aussi les methodes drawParcours,  drawScore(non implemente/utilise) definient plus bas, colorPiste, color Ground et drawHorizon.
	 * 
	 * @param Graphics g : le contexte graphique
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Point[] points = p.getPiste();
		drawPiste(points, g);
		colorPiste(points, g);
		colorGround(points, g);
		drawHorizon(g);
		colorSky(g);
		g.drawOval(etat.xC, etat.yC, etat.w, etat.h); //techniquement on devrait pouvoir avoir une methode sans parametre...
	}
	
	/**
	 * Methode drawPiste
	 * Dessine la piste en rouge
	 * 
	 * @param Point[] p, la liste de points a afficher
	 * @param Graphics g, le contexte graphique
	 */
	public void drawPiste(Point[] p, Graphics g) {
		Color temp = g.getColor();
		g.setColor(Color.red);		//Pour qu'il ressorte plus, on choisi d'afficher la piste en rouge
		for(int i = 2; i< p.length; i++) {	//La piste est composee de deux lignes paralleles, on represente donc deux fois les lignes avec un decalage
			g.drawLine(p[i-2].x, p[i-2].y, p[i].x, p[i].y); 
		}
		g.setColor(temp); //On remet ensuite le contexte graphique en noir
	}
	
	/**
	 * Methode colorPiste
	 * Remplis la piste en rouge
	 * 
	 * @param Point[] p, la liste de points a afficher
	 * @param Graphics g, le contexte graphique
	 */
	public void colorPiste(Point[] p, Graphics g) {
		Color temp = g.getColor();
		g.setColor(Color.red); //Comme precedement on sauvegarde la couleur du contexte graphique, puis on la change a rouge
		for(int i = 3; i < p.length; i+=2) {
			int[] x = {p[i-3].x,p[i-2].x, p[i].x, p[i-1].x}; //On remplie les parallelograme formes par les 4 points des deux segments paralleles deux a deux
			int[] y = {p[i-3].y,p[i-2].y, p[i].y, p[i-1].y};
			g.fillPolygon(x, y, x.length);
		}
		g.setColor(temp);
	}
	
	/**
	 * Methode colorGround
	 * Remplit ce qui n'est pas la piste en vert
	 * 
	 * @param Point[] p, la liste de points a afficher
	 * @param Graphics g, le contexte graphique
	 */
	public void colorGround(Point[] p, Graphics g) {
		Color temp = g.getColor();
		g.setColor(Color.green);//Comme precedement on sauvegarde la couleur du contexte graphique, puis on la change a vert
		for(int i = 3; i < p.length; i+=2) {
			int[] x = {0, p[i-3].x,  p[i-1].x, 0};	//On prend les points qui sont aux extremites hors de la piste (a l'horizon a gauche et a droite, en bas de la fenetre a droite et a gauche)
			int[] y = {p[i-3].y,p[i-3].y, p[i-1].y, p[i-1].y};
			int[] x2 = {p[i-2].x,xS, xS, p[i].x};
			int[] y2 = {p[i-2].y,p[i-2].y, p[i].y, p[i].y};
			g.fillPolygon(x, y, x.length);
			g.fillPolygon(x2, y2, x2.length);
		}
		g.setColor(temp);
	}
	
	/**
	 * Methode colorSky
	 * Remplit le ciel en vert
	 * 
	 * @param Graphics g, le contexte graphique
	 */
	public void colorSky(Graphics g) {
		Color temp = g.getColor();
		g.setColor(Color.cyan);//Comme precedement on sauvegarde la couleur du contexte graphique, puis on la change a vert
		int[] x = {0, 0,  xS, xS};	//On prend les points qui sont aux extremites hors de la piste (a l'horizon a gauche et a droite, en bas de la fenetre a droite et a gauche)
		int[] y = {0, Piste.horizon, Piste.horizon, 0};
		g.fillPolygon(x, y, x.length);
		g.setColor(temp);
	}
	
	/**
	 * Methode drawHorizon
	 * on dessine l'horizon
	 * 
	 * @param Graphics g, le contexte graphique
	 */
	public void drawHorizon(Graphics g) {
		g.drawLine(0, Piste.horizon, xS, Piste.horizon);
	}
	
	
	/**
	 * Methode setPiste
	 * Lie le parcours dans lequel on a evoluer a l'attribut concerne
	 * 
	 * @param Piste p, la piste a lier
	 */
	public void setPiste(Piste p) {
		this.p = p;
	}
}
