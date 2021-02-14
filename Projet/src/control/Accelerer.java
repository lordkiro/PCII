/**TODO*/
package control;

import model.Etat;
import view.Affichage;

public class Accelerer {
	public Avancer av;
	
	public Etat e;
	
	public Affichage a;
		
	public Accelerer(Etat e, Affichage a) {
		this.e = e;
		this.a = a;
	}
	
	public void setAvancer(Avancer av) {
		this.av = av;
	}
}
