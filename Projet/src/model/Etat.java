/**Cette classe represente l'etat du jeu*/
package model;

public class Etat {
	
	/**La valeur d'un "pas" sur le cote*/
	public int decalX = 10;
	
	/**la largeur de l'ovale*/
	public int w = 100;
	
	/**la heuteur de l'ovale*/
	public int h = 60;
	
	/**La position sur l'axe des abcisses de l'ovale*/
	public int xC = 350;
	
	/**La position sur l'axe des ordonnees de l'ovale*/
	public int yC = 600;
	
	/**Le decalage sur l'axe des X par rapport au "centre" du jeu*/
	public int decX;
	
	/**La vitesse du vehicule en jeu  (non implemente/utilise)*/
	public int vitesse;
	
	/**L'acceleration du vehicule en jeu  (non implemente/utilise)*/
	public float acceleration;
	
	/**Les mesures de la fenetre*/
	public int xS= 800;
	public int yS = 800;
	
	/**La piste du jeu*/
	public Piste p;
	
	/**L'instance de Temps du jeu (non implemente/utilise)*/
	public Temps t;
	
	/**
	 * Constructeur Etat
	 * on initialise simplement decX a 0
	 */
	public Etat() {
		decX = 0;
	}
	
	/**
	 * Methode moveR
	 * 
	 * On decalle le decors et la piste de la valeur d'un pas sur le cote, vers la droite
	 */
	public void moveR() {
		if(xC + decalX + w + decX> xS) {
			decX = xS - w;
		}
		else {
			decX += decalX;
		}
	}
	
	/**
	 * Methode moveL
	 * 
	 * On decalle le decors et la piste de la valeur d'un pas sur le cote, vers la gauche
	 */
	public void moveL() {
		if(xC - decalX + decX < 0) {
			decX = 0;
		}
		else {
			decX -= decalX;
		}
	}

	/**
	 * Methode setPiste
	 * Affecte la piste à l'atttribut de la classe
	 */
	public void setPiste(Piste piste) {
		this.p = piste;	
	}
	
	/**
	 * Methode setTemps
	 * Affecte le Timer à l'atttribut de la classe 
	 */
	public void setTemps(Temps temps) {
		this.t = temps;	
	}

	/**
	 * Fonction testPerdu
	 * Ici on renverra true si l'une des conditions de defaite est remplie (non implemente/utilise)
	 * 
	 * TODO
	 */
	public boolean testPerdu() {
		return t.testPerdu(); //temporaire, il y en aura d'autres
	}
}
