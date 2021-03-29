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
	public double maxAcc = 0.1;

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
	
	/** Largeur d'une montagne dans le decor*/
	public int largeurM = 90;
	
	/** Vitesse perdue lors d'une collision*/
	public double vCol = 2.5;
		
	/** Liste des checkpoints passes*/
	public ArrayList<Point> PC;
	
	/** Constante de ralentissement que l'on multipliera à la distance entre le vehicule et la piste*/
	public double slowCst = -0.0005;
	
	/**
	 * Constructeur Etat
	 * on initialise simplement decX a 0, et on cree le decor
	 */
	public Etat() {
		decX = 0;
		decor = setDecor();
		PC = new ArrayList<Point>();
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
	 * 
	 * on renvoie si la vitesse ou le temps restant est nul.
	 * 
	 * @return boolean, si on a perdu ou pas
	 */
	public boolean testPerdu() {
		return t.testPerdu() || vitesseNulle(); 
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
		double coeff = (p.get(i).x - p.get(j).x)/Piste.ecart; 	/**TODO not working*/
		double decalDroite = 0;
		double decalGauche = 0;
		for(int i1 = -w/2; i1 <= w/2; i1+=3) {
			double ligne = coeff * ((p.getPos()%Piste.ecart) + i1) + p.get(i).x; // On calcule l'ordonnée a chaque abscisse
			decalDroite = ligne-decX -(xC-w);
			decalGauche = xC+w*2 -(ligne-decX+w);
			if(decalDroite >= 0 && decalGauche >= 0) {//w est la largeur de la route
				return maxAcc;
			}
		}
		return -Math.max(coeff * ((p.getPos()%Piste.ecart)) + p.get(i).x-decX -(xC-w), xC+w*2 -(coeff * ((p.getPos()%Piste.ecart)) + p.get(i).x-decX+w)); 
	}
	
	/**
	 * Fonction passePC
	 * 
	 * incremente le temps si on passe dans un checkpoint
	 */
	public void passePC() {
		Point[] PCV = p.getPC();
		for(Point point : PCV) {   //On effectue les tests pour chaque point dans la liste des points visibles.
			/*System.out.print((point.y - yC - h/2 <= h) + "\n");
			System.out.print((point.y + 10 - yC >= 0) + "\n");
			System.out.print((point.x - (decX) + 2*w >=  xC) + "\n");
			if 	(!(point.x - (decX) + 2*w >=  xC)){
				System.out.print(point.x + " - ");
				System.out.print(decX  + " + ");
				System.out.print( 2*w + " >= ");
				System.out.print( xC + "\n");
			}
			System.out.print((point.x - (decX-w/4) - w/2 <= xC + 2*w) + "\n");
			if 	(!(point.x - (decX-w/4) <= xC + 2*w)){
				System.out.print(point.x + " - ");
				System.out.print(decX-w/4  + " <= ");
				System.out.print( 2*w + " + ");
				System.out.print( xC + "\n");
			}
			System.out.print("Passe pas? \n");*/
			if(point.y - yC - h/2- Math.round(vitesse) <= h && point.y + 10 - yC >= 0 && point.x - (decX) + 2*w  >=  xC && point.x - (decX/*-w/4*/) /*- w/2*/<= xC + 2*w ) {
				//Point P = new Point(point.x - decX, point.y - p.getPos());	//On soustraie decX et la pos pour avoir les coordonnees du point par rapport au depart.
				//System.out.print("Si");
				t.addTime();
				p.pointsControl.remove(0);
				/*if(!PC.contains(P)) {  //parfois le temps s'ajoute plusieurs fois. PB d'enregistrement des points?
						t.addTime();
						PC.add(P);
						System.out.print("et la");
				}*/
			}
			if(point.y > yS) {
				p.pointsControl.remove(0);
			}
		}
	}
	
	
	/**
	 * Methode accelerer
	 * On calcule l'acceleration potentielle du joueur, puis si cela le fait aller plus vite
	 * que la vitesse max du vehicule, on n'accelere pas, sinon on modifie la vitesse.
	 */
	public void accelere() {
		double acc = potAcc();
		if(acc > 0.) {	
			if(vitesse+acc >= vitesseM) {
				vitesse = vitesseM;
			}else{
				vitesse += acc;
			}
		}else {
			if(vitesse - acc * slowCst <= 0) {
				vitesse = 0;
			}else{
				vitesse -= acc * slowCst;
			}
		}
	
	}
	
	/**
	 * Fonction percuteObs
	 * 
	 * incremente le temps si on percute un obstacle
	 */
	public void percuteObs() {
		Point[] ObsV = p.getObs();
		for(Point point : ObsV) {
			if(point.y - yC - h/2- Math.round(vitesse) <= h && point.y + 10 - yC >= 0 && point.x - (decX) + 2*w  >=  xC && point.x - (decX/*-w/4*/) /*- w/2*/<= xC + 2*w ) {
				p.obstacles.remove(0);
				collision();
			}
			if(point.y > yS) {
				p.obstacles.remove(0);
			}
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
	
	/**
	 * Methode collision
	 * 
	 * on reduit la vitesse lors d'une collision
	 */
	public void collision() {
		if ((vitesse - vCol) >0) {
			vitesse -= vCol;
		}else {
			vitesse = 0.;
		}
	}
	
	/**
	 * Fonction vitesseNulle
	 * 
	 * On renvoie si la vitesse est nulle ou negative
	 * 
	 * @return boolean, si la vitesse est nulle ou negative
	 */
	public boolean vitesseNulle() {
		return vitesse <= 0.;
	}
}
