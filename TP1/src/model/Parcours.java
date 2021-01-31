/**
 * Cette classe crée la ligne brisee
 */

package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;


@SuppressWarnings("serial")
public class Parcours extends ArrayList<Point>{
	
	/** "Seed" du random */
	public static final Random rand = new Random();
	
	/** Ecart entre deux points */
	public static int ecart = 75;
	
	/** Modele dans lequel sera la ligne brisee */
	public Etat e;
	
	/** Largeur de la fenetre */
	public static int xS = 600;
	
	/** Hauteur de la fenetre */
	public static int yS = 600;
	
	/** Position de la ligne */
	public int position;
	
	/** Valeur d'incrementation de la valeur position
	 * @warning modifier coeffChute si on modifie */
	public int increPos = 2;

	/** Le coefficient de la droite de "chute libre" de l'ovale (déterminée par calcul, a modifier si on change les parametres)*/
	public int coeffChute = 3; //Le coefficient est positif car 0,0 est en haut a gauche de la fenetre
	
	/**
	 * Constructeur Parcours
	 * Affecte l'etat en parametre a l'attribut concerne, puis cree le parcours visible quand on ouvre la fenetre
	 * en verifiant que les points ont des positions valides, puis lie lle parcours a l'etat et initialise
	 * le score a 0.
	 * 
	 * @param Etat etat, l'etat auquel on va lier le parcours
	 */
	public Parcours(Etat etat) {
		e = etat;
		
		int i = 0;	//i representera l'indice du dernier point ajoute au parcours
		int x = etat.xC + Etat.w/2; //On ferra commencé le parcours au centre de l'ovale
		int y = etat.yC;			//dans un soucis de faisabilite du parcours
		this.add(new Point(x, y));
		while(x < xS) {	//On cree des points jusqu'a ce qu'on sorte de la fenetre
			y = rand.nextInt(yS);
			x += ecart;	
			Point p = new Point(x, y);
			if(suiteValide(this.get(i), p) && y > Etat.h/2 && y < yS - Etat.h/2){ //Si un point est valide (cf suitevalide) on l'ajoute, sinon on en cree un autre
				//On veut aussi que le point que l'on crée soit à une bonne distance des bords inférieur et supérieur de la fenêtre, afin de faciliter la phase de jeu.
				this.add(p);
				i++; //On incremente l'indice du dernier point de la liste puisqu'on vient d'en rajouter un
			}
			else {
				x -= ecart; //recommencer le point invalide
			}
		}
		etat.setParcours(this);//on ajoute le parcours a l'etat du jeu
		position = 0; //initialisation du score a 0
	}

	/**
	 * Fonction getParcours
	 * Renvoie la liste de points qui forment les segments visibles dans la fenetre.
	 * Comme cette fonction est appelee a chaque fois que la courbe avance, on en profite pour la faire ajouter des points quand le
	 * dernier point est bientot vu par la fenetre afin d'avoir une impression de ligne infinie
	 * 
	 * @return Point[] La liste des points du parcours actuel
	 */
	public Point[] getParcours() {
		ArrayList<Point> res = new ArrayList<Point>();
		for(int i = 0; i < this.size(); i++) { 
			Point pi = get(i);
			if(estVisible(new Point(pi.x - position, pi.y))) { //Si un point est visible apres l'avancee, on l'ajoute a la liste
				Point pr = new Point(pi.x - position, pi.y);
				res.add(pr);
				}
			else if(pi.x - position < 0 && estVisible(new Point(get(i+1).x - position, get(i+1).y))) { /*Si un point est hors
			 de la fenetre a gauche et que le suivant est visible, on a besoin du point pour tracer le segment qui sera partiellement visible. On l'ajoute donc a la liste*/
				Point pr = new Point(pi.x - position, pi.y);
				res.add(pr);
			}
			else if(pi.x - position > xS && estVisible(new Point(get(i-1).x - position, get(i-1).y))) {/*Si un point est hors de la fenetre a droite, et que le point
			precedent est visible, on a besoin du point hors de la fenetre pour creer le segment qui sera partiellement visible. On l'ajoute donc
			a la liste. De plus si ce point est bientot visible, on ajoute un autre point derriere lui*/
				Point pr = new Point(pi.x - position, pi.y);
				res.add(pr);
				if(pi.x - increPos*1.5 - position <= xS && i == size()-1) { //on met increPos*1.5 pour regarder un peu plus loin que la prochaine etape
					ajoutePoint();
				}
				return res.toArray(new Point[res.size()]); /* Pas besoin de s'embeter a aller plus loin puisqu'on affichera 
				pas les points stockés plus tard si il y en a.*/
			}
		}
		return res.toArray(new Point[res.size()]);
	}
	
	/**
	 * Fonction getPos
	 * Renvoie le score (position par rapport au depart) sur le parcours actuel
	 * 
	 * @return int La position
	 */
	public int getPos() {
		return position;
	}
	
	/**
	 * Methode setPos
	 * incremente la position (score) de la constante increPos
	 */
	public void setPos() {
		position += increPos;
	}
	
	/**
	 * Fonction estVisible
	 * Renvoie si le point en argument est visible ou non
	 * 
	 * @param Point p, le point que l'on veut tester
	 * @return boolean , Si le point est visible ou non
	 */
	public boolean estVisible(Point p) {
		return p.x <= xS && p.x  >= 0;
	}
	
	/**
	 * Methode ajoutePoint
	 * Ajoute un point valide apres le dernier point du parcours
	 */
	public void ajoutePoint() {
		int i = size()-1; //i represente l'indice du dernier point dans le tableau
		int x = get(i).x + ecart;
		boolean flag = false;
		Point p = null;
		while (flag==false) { // tant que la suite de points n'est pas valide, on cree un nouveau point
			int y = rand.nextInt(yS);
			p = new Point(x, y);
			if(suiteValide(this.get(i), p) && y > Etat.h/2 && y < yS - Etat.h/2){ //Si la suite du dernier point dans le tableau avec le point que l'on cree est valide, on sort de la boucle
				//On veut aussi que le point que l'on crée soit à une bonne distance des bords inférieur et supérieur de la fenêtre, afin de faciliter la phase de jeu.
				flag = true;
			}
		}
		add(p);
	}
	
	/** 
	 * Fonction suiteValide
	 * Renvoie si l'ordonnée du point suivant est sous la droite de chute libre partant du point precedent
	 * 
	 * @param Point p1, le point precedent
	 * @param Point p2, le point que l'on test
	 * @return boolean si le resultat est sous la droite ou pas
	 */
	public boolean suiteValide(Point p1, Point p2) {
		return (p1.y + ecart * this.coeffChute > p2.y);
	}
}
