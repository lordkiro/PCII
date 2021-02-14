package main;

import javax.swing.JFrame;

import control.Accelerer;
import control.Avancer;
import control.Control;
import control.Deplacer;
import model.Etat;
import model.Piste;
import model.Temps;
import view.Affichage;

public class Main {
	public static void main(String[] args) {
		Etat Modele = new Etat();
		Affichage Vue = new Affichage(Modele);
		Deplacer Deplacer = new Deplacer(Modele, Vue);
		Control Controleur = new Control(Modele, Vue, Deplacer);
		Piste P = new Piste(Modele);
		Accelerer Acc = new Accelerer(Modele, Vue);
		Avancer Avancer = new Avancer(Modele, P, Vue, Controleur, Acc);
		@SuppressWarnings("unused")
		Temps Montre = new Temps(Modele);
		Vue.setPiste(P);
		Acc.setAvancer(Avancer);
		
		JFrame test = new JFrame("Projet");
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.add(Vue);
		test.pack();
		test.addKeyListener(Controleur);
		test.setVisible(true);
		
		Deplacer.start();
		Avancer.start();
	}
}
