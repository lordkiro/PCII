
import javax.swing.JFrame;

import control.Avancer;
import control.Control;
import control.Voler;
import model.Etat;
import model.Parcours;
import view.Affichage;

public class Main {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Etat Modele = new Etat();
		Affichage Vue = new Affichage(Modele);
		Voler Vol = new Voler(Modele, Vue);
		Control Controleur = new Control(Modele, Vue);
		Parcours Parcours = new Parcours(Modele);
		Avancer Progression = new Avancer(Modele, Parcours, Vue, Controleur, Vol);
		Vue.setParcours(Parcours);
		
		JFrame test = new JFrame("TP1");
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.add(Vue);
		test.pack();
		test.setVisible(true);
		
		Vol.start();
		Progression.start();
	}
}
