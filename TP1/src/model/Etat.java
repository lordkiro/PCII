/**
 * Classe Etat qui represente l'etat du model que l'on affiche
 */

package model;

import java.awt.Point;

public class Etat {
	
	/** Hauteur d'un saut*/
	public static int jumpH = 60;
	
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
	public static int h = 100;
	
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
		float pi = (float) Math.PI;
		int i = parc.getPos() / Parcours.ecart; //indice du point déjà passe part l'ovale
		int j = i+1; //indice du point pas encore passé
		float coeff = (float) (parc.get(j).y - parc.get(i).y)/Parcours.ecart; 
		float ligne = coeff * (parc.getPos()%Parcours.ecart) + parc.get(i).y;
		
		if (ligne >= yC && ligne <= yC + h) {
			return false;
		}
		else {
			System.out.print("else \n");
			Point centre = new Point(xC + h/2, yC - w/2);
			int a = 0;
			boolean flag =false;
			while(!flag && a <13) {
				Point ref = pointEllipse(a/6*pi);
				
				int x1 = ref.x + centre.x;
				int x2 = -ref.x + centre.x;
				
				int y1 = ref.y - centre.y;
				int y2 = -ref.y - centre.y;
				
				float ligne1 = coeff * x1 + parc.get(i).y;
				float ligne2 = coeff * x2 + parc.get(i).y;
				if((ligne1 >= y1 && ligne <= y2) || (ligne2 >= y1 && ligne <= y2)) {
					System.out.print("passé \n");
					return false;
				}
				a++;
			}
			return true;
		}
	}
	
	public Point pointEllipse(float a) {
		float upperx = (float) ((h/2) * (w/2) * Math.cos(a));
		float lower = (float) Math.sqrt(Math.pow(w/2, 2)*Math.pow(Math.cos(a), 2)+ Math.pow(h/2, 2)*Math.pow(Math.sin(a), 2));
		float x = upperx / lower;
		
		float uppery = (float) ((h/2) * (w/2) * Math.sin(a));
		float y = uppery / lower;
		
		Point res = new Point(Math.round(x),Math.round(y));
		return res;
	}
}
