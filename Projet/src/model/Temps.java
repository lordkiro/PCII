/**Cette classe gerera le chronometre*/
package model;

public class Temps{

	/**L'etat du jeu*/
	Etat e;
	
	/**Le temps restant*/
	public int t;
	
	/**Le temps donné au debut du jeu en secondes*/
	public int baseT = 40;

	/**Le temps donné a chaque checkpoint en secondes*/
	public int tsup = 20;
	
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
	 * 
	 * TEMPORAIRE : tant qu on a pas les checkpoint, si on perd apres une baisse de temps, on apelle addTime
	 */
	public void decreaseT() {
		if(t-1 <= 0) {
			addTime();
		}else{
			this.t -= 1;
		}
	}
	
	/**
	 * Fonction testPerdu
	 * on return si le temps est inferieur ou egal a 0
	 */
	public boolean testPerdu() {
		return t <= 0.;
	}
}
