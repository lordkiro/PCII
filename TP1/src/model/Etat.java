/**
 * Classe Etat qui represente l'etat du model que l'on affiche
 */

package model;

public class Etat {
	
	/** Hauteur d'un saut*/
	public static int jumpH = 30;
	
	/** Hauteur d'une descente
	 * @warning modifier coeffChute si on modifie*/
	public static int dropH = 6;

	/**Coordonée sur l'axe x*/
	public  int xC = 60;
	
	/**Coordonée sur l'axe y*/
	public  int yC = 550;
	
	/**Largeur de l'ovale*/
	public static int w = 40;
	
	/**Hauteur (ou longueur) de l'ovale*/
	public static int h = 120;
	
	/** Le parcours dans lequel on evolue*/
	public Parcours parc;
	
	/** 
	 * Methode jump
	 * si l'ovale "sort de la fenetre" apres son saut,
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
	 * Methode moveDown
	 * si l'ovale sort de la fenetre lorsqu'il descent, 
	 * il est collé au bord inferieur de la fenetre.
	 * Sinon sa hauteur est baissé de la hauteur d'une descente.
	 * 
	 * @param int yS, hauteur de la fenetre
	 */
	public void moveDown(int yS) {
		if(yC + h  + dropH > yS) {
			yC = yS - h;
		}
		else {
			yC += dropH;
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
	
	/**
	 * Methode setParcours
	 * Affecte le parcours à l'atttribut de la classe
	 */
	public void setParcours(Parcours p) {
		this.parc = p;
	}
	
	
	public boolean testPerdu() {
		int i = parc.getPos() / Parcours.ecart; //indice du point déjà passe part l'ovale
		int j = i+1; //indice du point pas encore passé
		float coeff = (float) (parc.get(j).y - parc.get(i).y)/Parcours.ecart; 
		float ligne = coeff * (parc.getPos()%Parcours.ecart) + parc.get(i).y;
		return (ligne <= yC || ligne >= yC + h);
	}
}
