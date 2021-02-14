/**Cette classe gerera le chronometre*/
package model;

public class Temps{

	/**L'etat du jeu*/
	Etat e;
	
	/**Le temps restant*/
	public int t;
	
	/**Le temps donné au debut du jeu*/
	public int baseT = 40000;

	/**Le temps donné a chaque checkpoint*/
	public int tsup = 20000;
	
	/**
	 * Constructeur Temps
	 * On effecte l'etat a l'attribut concerne, initialise le temps et raccorde cette instance a celle de l'etat
	 * 
	 * @param Etat et, l'etat du jeu
	 */
	public Temps(Etat et) {
		e = et;
		t = baseT;
		e.setTemps(this);
	}
	/**
	 * Methode addTime
	 * ajoute le temps a chaque passage dans un checkpoint
	 */
	public void addTime() {
		this.t += tsup;
	}
	
	/**
	 * Methode decreaseT
	 * le temps passe... (on decroit d'une seconde le temps)
	 */
	public void decreaseT() {
		this.t -= 1000;
	}
	
	/**
	 * Fonction testPerdu
	 * on return si le temps est inferieur ou egal a 0
	 */
	public boolean testPerdu() {
		return t <= 0.;
	}
}
