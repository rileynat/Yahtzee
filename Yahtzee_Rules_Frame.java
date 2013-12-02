package eecs285.proj4.Yahtzee.Yahtzee;

import java.lang.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;


public class Yahtzee_Rules_Frame extends JTabbedPane {

	public static boolean open = false;
	public JFrame display = new JFrame("Game Info");
	
	public Yahtzee_Rules_Frame()
	{
		super();
		JLabel object_label = new JLabel("<html>Object: Roll dice for scoring "
		+ "combinations, and get the highest total score.<br></html>");
		JLabel summary_label = new JLabel();
		summary_label.setText("<html>Game Summary:\nOn each turn, roll the dice"
		+ "up to three times to get the <br>highest scoring combination for one"
		+ " of the 13 catagories. After you <br>finish rolling click one of the"
		+ "available scores, indicated by a red number. <br>The game will end"
		+ " when all players have filled in their 13 boxes. Scores <br>are "
		+ "totaled and the player with the highest score wins.</html>");
		JLabel name_label = new JLabel();
		name_label.setText("<html>On your turn, you may roll the dice up to 3"
		+ " times, although you may<br> stop and score after your first or "
		+ "second roll. Each roll you may<br> reroll any or all dice you want"
		+ " even if they were locked in previously.</html>");
		JPanel Summary = new JPanel();
		Summary.setLayout(new BoxLayout(Summary, BoxLayout.PAGE_AXIS));
		Summary.add(object_label);
		Summary.add(new JLabel("<html> <br> <html>"));
		Summary.add(summary_label);
		Summary.add(new JLabel("<html> <br> <html>"));
		Summary.add(name_label);
		addTab("Game Summary", Summary);
		JLabel scores_label =  new JLabel();
		scores_label.setText("<html>Ones: Total of ones only<br>"
						   + "Twos: Total of twos only<br>"
						   + "Threes: Total of thress only<br>"
						   + "Fours: Total of fours only<br>"
						   + "Fives: Total of fives only<br>"
						   + "Sixes: Total of sixes only<br>"
						   + "3 of a Kind: Total of all 5 dice<br>"
						   + "4 of a Kind: Total of all 5 dice<br>"
						   + "Full House: 25 points<br>"
						   + "Small Straight: 30 points<br>"
						   + "Large Straight: 40 points<br>"
						   + "YAHTZEE: 50 points<br>"
						   + "Chance: Total of all 5 dice</html>");
		JPanel score_frame = new JPanel();
		score_frame.add(scores_label);
		addTab("Scores", score_frame);
		display.add(this);
		//display.setSize(new Dimension(300, 300));
		display.pack();
	}
	
	public void show_frame()
	{
		open = true;
	}
	public void hide_frame()
	{
		open = false;
	}
}
