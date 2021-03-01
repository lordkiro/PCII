/**Cette classe represente l'etat du jeu*/
package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;


public class Etat {
	
	/** "Seed" du random */
	public static final Random rand = new Random();
	
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
	public double vitesse;

	public double vitesseM = 12.;

	/**L'acceleration du vehicule en jeu  (non implemente/utilise)*/
	public double acceleration;

	/**L'aceleration quand on est sur la piste. (coefficient)*/
	public double maxAcc = 1.002;

	/**Les mesures de la fenetre*/
	public int xS= 800;
	public int yS = 800;

	/**La piste du jeu*/
	public Piste p;

	/**L'instance de Temps du jeu (non implemente/utilise)*/
	public Temps t;

	/**Les sommets des montagnes du decor*/
	public Point[] decor;
	
	/**Nombre de montagne dans le decor (arbitraire)*/
	public int nbMontagnes = 7;
	
	/**Ecart minimal entre deux sommets*/
	public int ecart = 80;
	
	/**Largeur d'une montagne dans le decor*/
	public int largeurM = 90;
	
	/**
	 * Constructeur Etat
	 * on initialise simplement decX a 0, et on cree le decor
	 */
	public Etat() {
		decX = 0;
		decor = setDecor();
	}

	/**
	 * Methode moveR
	 * 
	 * On decalle le decors et la piste de la valeur d'un pas sur le cote, vers la droite
	 */
	public void moveR() {
		decX += decalX;
	}

	/**
	 * Methode moveL
	 * 
	 * On decalle le decors et la piste de la valeur d'un pas sur le cote, vers la gauche
	 */
	public void moveL() {
		decX -= decalX;
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

	/**
	 * Foncton potAcc
	 * On calcule l'acceleration du joueur en fonction de sa position.
	 * Si on est sur la piste, on est sur la piste, on renvoie le coefficient d'acceleration max,
	 * sinon on renvoie un coefficient inferieur a 1 positif pour le ralentissement
	 * 
	 * @return double, le coefficient d'acceleration du modele dans sa positon actuelle
	 */
	public double potAcc() {
		int i = p.getPos() / Piste.ecart; //indice du point déjà passe part l'ovale
		int j = i+1; //indice du point pas encore passé
		double coeff = (p.get(j).x - p.get(i).x)/Piste.ecart; 	/**TODO not working*/
		for(int i1 = -w/2; i1 <= w/2; i1+=3) {
			double ligne = coeff * ((p.getPos()%Piste.ecart) + i1) + p.get(i).x; // On calcule l'ordonnée a chaque abscisse
			if(ligne+decX >= xC-w/2 && ligne+decX+w/2 <= xC+w) {//w/2 est la largeur de la route
				System.out.print("acc \n");
				return maxAcc;
			}
		}
		System.out.print("dec \n");
		return 1.; //Pour le debuggage on ne ralenti pas hors de la piste
	}

	/**
	 * Methode accelerer
	 * On calcule l'acceleration potentielle du joueur, puis si cela le fait aller plus vite
	 * que la vitesse max du vehicule, on n'accelere pas, sinon on modifie la vitesse.
	 */
	public void accelere() {
		double acc = potAcc();
		if(vitesse*acc >= vitesseM) {
			vitesse = vitesseM;
		}else{
			vitesse *= acc;
		}
	}
	
	/**
	 * Methode setDecor
	 * On pose des points de facon legerement aleatoirepour donner des montagnes en fond. On fait quand meme en sorte que les montagnes ne se chevauchent pas
	 * 
	 * @return Point[] les sommets des montagnes
	 */
	public Point[] setDecor() {
		ArrayList<Point> res = new ArrayList<Point>();
		int i = 0;	//i representera l'indice du dernier point ajoute a la piste
		int x = 100; //On ferra commencé la piste au centre de l'ovale
		int y = rand.nextInt(Piste.horizon);			//dans un soucis de faisabilite de la piste
		res.add(new Point(x, y));
		while(i < nbMontagnes) {	//On cree des points jusqu'a ce qu'on sorte de la fenetre
			x += rand.nextInt(ecart)+largeurM;
			y = rand.nextInt(Piste.horizon);	
			Point p = new Point(x, y);
			res.add(p);
			i++;
		}
		return res.toArray(new Point[res.size()]);
	}
}
