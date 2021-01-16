import javax.swing.JFrame;

import control.Control;
import model.Etat;
import view.Affichage;

public class Main {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Etat Modele = new Etat();
		Affichage Vue = new Affichage(Modele);
		Control Controleur = new Control(Modele, Vue);
		
		JFrame test = new JFrame("TP1");
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.add(Vue);
		test.pack();
		test.setVisible(true);
	}
}
