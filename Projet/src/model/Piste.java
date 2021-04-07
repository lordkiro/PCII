/**Cette classe gere la piste*/
package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("serial")
public class Piste  extends ArrayList<Point>{
		
		/** "Seed" du random */
		public static final Random rand = new Random();
		
		/** Modele dans lequel sera la piste */
		public Etat e;
		
		/** Largeur de la fenetre */
		public static int xS = 800;
		
		/** Hauteur de la fenetre */
		public static int yS = 800;
		
		/** la position sur la piste */
		public int position;
		
		/** Valeur d'incrementation de la valeur position
		 * @warning modifier coeffChute si on modifie */
		public int increPos = 2;

		/**Le nombre de points visibles en même temps sur la piste*/
		public static int nbPVisibles = 5;
		
		/**La hauteur de l'horizon dans la fenetre*/
		public static int horizon = 150; 
		
		/** Ecart entre deux points */
		public static int ecart = (xS-horizon)/nbPVisibles;
		
		/**Distance max en x entre deux points*/
		public static int maxDiffX = 150;
		
		/** Interval minimum entre deux obstacles*/
		public static int interObs;
		
		/** Intervalle entre deux points de controles. En nombre de segments*/
		public static int interPC;
		
		/** Niveau de difficultée */
		public double diff;
		
		/** Facteur de difficulté max*/
		public double maxdiff = 3.5;
		
		/** Compte de segment créés sans PC*/
		private int decomptePC;
		
		/** Compte de segment créés sans obstacle*/
		private int decompteObs;
		
		/** Liste des numeros de segments des PC*/
		public ArrayList<Integer> pointsControl;
		
		/** Liste des numeros de segments des obstacles*/
		public ArrayList<Point> obstacles;
		
		/**
		 * Constructeur Piste
		 * Affecte l'etat en parametre a l'attribut concerne, puis cree la piste visible quand on ouvre la fenetre
		 * en verifiant que les points ont des positions valides, puis lie lla piste a l'etat et initialise
		 * le score a 0, et les intervalles pour les obstacles et points de control, ainsi que les decomptes. On initialise la difficulté à 1.
		 * 
		 * @param Etat etat, l'etat auquel on va lier la piste
		 */
		public Piste(Etat etat) {
			e = etat;
			
			int i = 0;	//i representera l'indice du dernier point ajoute a la piste
			int x = etat.xC - etat.w/4; //On ferra commencé la piste au centre de l'ovale
			int y = etat.yC+etat.h/2;			//dans un soucis de faisabilite de la piste
			this.add(new Point(x, y));
			while(i < nbPVisibles) {	//On cree des points jusqu'a ce qu'on sorte de la fenetre
				x = rand.nextInt(xS);
				y -= ecart;	
				Point p = new Point(x, y);
				if( x > etat.h/2 && x < xS - etat.h*3/2 && suiteValide(this.get(i), p)){ //Si un point est valide  on l'ajoute, sinon on en cree un autre
					this.add(p);
					i++;
				}
				else {
					y += ecart; //recommencer le point invalide
				}
			}
			e.setPiste(this);//on ajoute la piste a l'etat du jeu
			position = 0; //initialisation du score a 0
			interObs = 10;
			interPC = 7;
			decomptePC = interPC - nbPVisibles;
			decompteObs = interObs - nbPVisibles;
			pointsControl = new ArrayList<Integer>();
			obstacles = new ArrayList<Point>();
			diff = 1;
		}

		/**
		 * Fonction getPiste
		 * Renvoie la liste de points qui forment les segments visibles dans la fenetre, du point de vue de sujet.
		 * Comme cette fonction est appelee a chaque fois que la courbe avance, on en profite pour la faire ajouter des points quand le
		 * dernier point est bientot vu par la fenetre afin d'avoir une impression de ligne infinie.
		 * Quand on ajoute un point, on vérifie si il doit posseder un point de control. Si non, on decremente le decompte.
		 * 
		 * @return Point[] La liste des points de la piste actuel
		 */
		public Point[] getPiste() {
			ArrayList<Point> res = new ArrayList<Point>();
			for(int i = 0; i < this.size(); i++) { 
				Point pi = get(i);
				if(estVisible(new Point(pi.x - e.decX, pi.y + position))) { //Si un point est visible apres l'avancee, on l'ajoute a la liste
					Point pr = new Point(pi.x - e.decX, pi.y + position);//On ajoute le decX (decallage sur l'axe des X par rapport au centre de la piste) pour decaler l'afficahge des points
					Point pr2 = new Point(pi.x+e.w - e.decX, pi.y + position); //Ce qui nous permet d'avoir le point au centre de la fenetre et le reste du monde qui bouge
					res.add(pr);
					res.add(pr2);//La piste est composee de deux lignes paralleles, on represente donc deux fois les lignes avec un decalage (d'ou le +e.w/2 plus haut)
					}
				
				else if(pi.y + position > yS && estVisible(new Point(get(i+1).x - e.decX, get(i+1).y + position))) {/*Si un point est hors de la fenetre a droite, et que le point
				precedent est visible, on a besoin du point hors de la fenetre pour creer le segment qui sera partiellement visible. On l'ajoute donc
				a la liste. De plus si ce point est bientot visible, on ajoute un autre point derriere lui*/
					Point pr = new Point(pi.x- e.decX, pi.y + position);
					Point pr2 = new Point(pi.x+e.w- e.decX, pi.y + position);
					res.add(pr);
					res.add(pr2);
				}
				else if(pi.y + position < horizon
						&& estVisible(new Point(get(i-1).x - e.decX, get(i-1).y + position))) { /*Si un point est hors
				 de la fenetre a gauche et que le suivant est visible, on a besoin du point pour tracer le segment qui sera partiellement visible. On l'ajoute donc a la liste*/
					Point pr = new Point(pi.x- e.decX, horizon);
					Point pr2 = new Point(pi.x +e.w- e.decX, horizon);
					res.add(pr);
					res.add(pr2);
					if(pi.y + e.vitesse*1.5 + position > horizon && i == size()-1) { //on met increPos*1.5 pour regarder un peu plus loin que la prochaine etape
						ajoutePoint();
						if(this.decomptePC - 1 == 0) {  //Si le decompte atteint 0, on ajoute un PC
							pointsControl.add(i+1);
							decomptePC = (int) Math.round(interPC * diff); //Plus le jeu avance, plus les PC seront eloignes pour augmenter la difficulte.
							diff += 0.2; //On incremente sensiblement la difficulte
						}else {
							decomptePC -= 1; //Si le decompte n'atteint pas 0, on le decremente
						}
						
					}
					return res.toArray(new Point[res.size()]); /* Pas besoin de s'embeter a aller plus loin puisqu'on affichera 
					pas les points stockés plus tard si il y en a.*/
				}
			}
			return res.toArray(new Point[res.size()]);
		}
		
		/**
		 * Fonction getPC
		 * 
		 * @return Point[] Les points le plus a gauche de chaque PC visibles
		 */
		
		public Point[] getPC() {
			ArrayList<Point> res = new ArrayList<Point>();
			for(int i : this.pointsControl) {
				Point p = this.get(i);
				if(estVisible(new Point(p.x - e.decX, p.y + position))) {
					res.add(new Point(p.x-e.w/2-e.decX, p.y+position)); //Comme precedement, on ajoute le point aux coordonnees ou on veut le voir
				}
			}
			return res.toArray(new Point[res.size()]);
		}
		
		/**
		 * Fonction getObs
		 * 
		 * @return Point[] Les points le plus a gauche de chaque obstacles visibles
		 */
		
		public Point[] getObs() {
			ArrayList<Point> res = new ArrayList<Point>();
			for(Point p : this.obstacles) {
				if(estVisible(new Point(p.x - e.decX, p.y + position))) {
					res.add(new Point(p.x-e.w/2-e.decX, p.y+position)); //Comme precedement, on ajoute le point aux coordonnees ou on veut le voir
				}
			}
			return res.toArray(new Point[res.size()]);
		}
		
		/**
		 * Fonction getPos
		 * Renvoie le score (position par rapport au depart) sur la piste actuel
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
			position += Math.round(e.vitesse);
		}
		
		/**
		 * Fonction estVisible
		 * Renvoie si le point en argument est visible ou non
		 * 
		 * @param Point p, le point que l'on veut tester
		 * @return boolean , Si le point est visible ou non
		 */
		public boolean estVisible(Point p) {
			return p.y <= yS && p.y >= horizon;
		}
		
		/**
		 * Methode ajoutePoint
		 * Ajoute un point valide apres le dernier point de la piste
		 * 
		 * A chaque point que l'on ajoute, on verifie si un obstacle se situera sur le segment entre le dernier et le point que l'on va creer.
		 */
		public void ajoutePoint() {
			int i = size()-1; //i represente l'indice du dernier point dans le tableau
			int y = get(i).y - ecart;
			boolean flag = false;
			Point p = null;
			while (flag==false) { // tant que la suite de points n'est pas valide, on cree un nouveau point
				int x = rand.nextInt(yS);
				p = new Point(x, y);
				if( x > e.h/2 && x < xS - e.h*3/2 && suiteValide(this.get(i), p)){ //Si la suite du dernier point dans le tableau avec le point que l'on cree est valide, on sort de la boucle
					//On veut aussi que le point que l'on crée soit à une bonne distance des bords inférieur et supérieur de la fenêtre, afin de faciliter la phase de jeu.
					flag = true;
				}
			}
			add(p);
			if(this.decompteObs - 1 == 0) { //Si il est temps de mettre un point, on en ajoute un.
				int xleft = rand.nextInt(175)-p.x;
				obstacles.add(new Point (xleft, p.y+ rand.nextInt(ecart))); //Le point le plus a gauche d'obstacle est placé de facon aleatoire sur le troncon de route
				decompteObs = (int) Math.round(interObs / (diff+1)); 
			}else {
				decompteObs -= 1;
			}
		}
		
		/** 
		 * Fonction suiteValide
		 * Renvoie si l'abscisse du point suivant est assez proche de l'abscisse du point precedent.
		 * 
		 * @param Point p1, le point precedent
		 * @param Point p2, le point que l'on test
		 * @return boolean le resultat 
		 */
		public boolean suiteValide(Point p1, Point p2) {
			return (Math.abs(p1.x - p2.x) < maxDiffX);
		}
}
