/**
 * Classe Etat qui represente l'etat du model que l'on affiche
 */

package model;

public class Etat {
	
	/** Hauteur d'un saut*/
	public static int jumpH = 10;

	/**Coordonée sur l'axe x*/
	public static int xC = 60;
	
	/**Coordonée sur l'axe y*/
	public static int yC = 200;
	
	/**Largeur de l'ovale*/
	public static int w = 40;
	
	/**Hauteur (ou longueur) de l'ovale*/
	public static int h = 180;

	/** 
	 * Methode jump
	 * si la l'ovale "sort de la fenetre" apres son saut,
	 * il est mis collé au bord superieur de la fenetre.
	 * Sinon sa hauteur est augmentee de la hauteur d'un saut. 
	 */
	public void jump() {
		if(yC - jumpH < 0) { //On verifie si on sort de la fenetre
			yC = 0;
		}
		else {
			yC -= jumpH;
		}
	}
	
	/**
	 * Fonction getHauteur
	 * Renvoie la hauteur RELATIVE a la fenetre 
	 * (le (0,0) de la fenetre etant en haut a gauche, 
	 * quand on descend dans la fenetre, yC augmente)
	 *
	 * @return : yC la coordonee de la hauteur de la figure.
	 */
	public int getHauteur() {
		return yC;
	}
}
