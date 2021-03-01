/**
 * Classe Affichage heritee de JPanel, elle affiche l'etat.
 */

package view;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

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

	/**La piste du jeu*/
	public Piste p;
	
	/**L'image qui representara  le joueur*/
	public BufferedImage image;
	
	/** 
	 * Constructeur Affichage
	 * On donne la taille ideale de notre fenetre puis
	 * on affecte l'etat passe en argument a la variable.
	 * On affecte aussi l'image choisie a la variable appropriee 
	 * 
	 * @param Etat e : l'etat que notre fenetre va afficher
	 */
	public Affichage(Etat e) {
		setPreferredSize(new Dimension(xS, yS)); //On rentre les dimensions voulues pour la fenetre
		try {
			image = ImageIO.read(new File("vehicule.png")); //On charge l'image appelee vehicule.png
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.print("L'image n'a pas pu etre chargee"); //Si on n'arrive pas a charger l'image, on prévient l'utilisateur
		}
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
		drawDecor(etat.decor, g);
		colorDecor(etat.decor, g);
		writeTemps(g);		//On ecrit apres tous les dessins et coloriages afin que le texte soit par dessus
		writeVitesse(g);
		writeScore(g);
		g.drawOval(etat.xC, etat.yC, etat.w, etat.h); //techniquement on devrait pouvoir avoir une methode sans parametre...
		//g.drawImage(image, etat.xC, etat.yC, null); //On commente/decommente pour avoir d'affiché l'image ou pas. TODO: dimensions(centrage) et transparence
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
	 * Methode drawDecor
	 * On dessine les montagnes en fond.
	 * On les decale legerement quand on bouge pour donnerune impression de profondeur
	 * 
	 * @param Point[] dec, les sommets des montagnes du decor
	 * @param Graphics g, le contexte graphique actuel
	 */
	public void drawDecor(Point[] dec, Graphics g) {
		Color temp = g.getColor();
		g.setColor(Color.lightGray);		
		for(int i = 0; i< dec.length; i++) {	
			g.drawLine((int)Math.round(dec[i].x-etat.largeurM/2-0.3*etat.decX), Piste.horizon, (int)Math.round(dec[i].x-0.3*etat.decX), dec[i].y);  //+0.3*etat.decX pour sensation de profondeur
			g.drawLine((int)Math.round(dec[i].x-0.3*etat.decX), dec[i].y, (int)Math.round(dec[i].x+etat.largeurM/2-0.3*etat.decX), Piste.horizon);
		}
		g.setColor(temp); //On remet ensuite le contexte graphique en noir
	}
	
	/**
	 * Methode colorDecor
	 * On remplit les montagnes en fond.
	 * On les decale legerement quand on bouge pour donnerune impression de profondeur
	 * 
	 * @param Point[] dec, les sommets des montagnes du decor
	 * @param Graphics g, le contexte graphique actuel
	 */
	public void colorDecor(Point[] dec, Graphics g) {
		Color temp = g.getColor();
		g.setColor(Color.lightGray);	
		for(int i = 0; i< dec.length; i++) {	
			int[] x = {(int)Math.round(dec[i].x-etat.largeurM/2-0.3*etat.decX), (int)Math.round(dec[i].x-0.3*etat.decX), (int)Math.round(dec[i].x+etat.largeurM/2-0.3*etat.decX)};
			int[] y = {Piste.horizon, dec[i].y, Piste.horizon};
			g.fillPolygon(x, y, x.length);
		}
		g.setColor(temp); //On remet ensuite le contexte graphique en noir
	}
	
	/**
	 * Methode writeTemps
	 * On ecrit le temps restant avant la in du jeu
	 * 
	 * @param Graphics g, le contexte graphique actuel
	 */
	public void writeTemps(Graphics g) {
		g.drawString("Temps restant: ", 700, 725);
		g.drawString(String.format("%d", etat.t.t)+" sec", 700, 750); 
	}
	
	/**
	 * Methode writeVitesse
	 * On ecrit la vitesse actuelle du joueur
	 * 
	 * @param Graphics g, le contexte graphique actuel
	 */
	public void writeVitesse(Graphics g) {
		DecimalFormat df = new DecimalFormat("#.##");
		g.drawString("Vitesse actuelle: ", 100, 725);
		g.drawString(String.format("%s", df.format(etat.vitesse*10))+" km/h", 100, 750);
	}
	
	/**
	 * Methode writeTemps
	 * On ecrit le score actuelle du joueur
	 * 
	 * @param Graphics g, le contexte graphique actuel
	 */
	public void writeScore(Graphics g) {
		DecimalFormat df = new DecimalFormat("#.##");
		g.drawString("Distance parcourue: ", 15, 15);
		g.drawString(String.format("%s", df.format(p.position/100))+" km", 130, 15);
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
