package eecs285.proj4.keyserja;

import javax.swing.JFrame;

public class Yahtzee_main {
	public static void main(String [] args){
		String [] players = {"SpongeBob", "Patrick", "Squidward", "Mr Krabs"};
		Yahtzee_GUI gui = new Yahtzee_GUI(4, 1000, players);
		gui.pack();		
		gui.setVisible(true);
		gui.setDefaultCloseOperation(
		JFrame.EXIT_ON_CLOSE);
	}
}
