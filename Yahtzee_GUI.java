package eecs285.proj4.Yahtzee

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URISyntaxException;

import javax.security.auth.kerberos.KerberosKey;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class Yahtzee_GUI extends JFrame {
	private JButton roll_button;
	private JToggleButton [] dice_buttons;
	private Scorecard playerScorecard;
	private Dice dice;
	private JLabel[] scores;
	private ImageIcon[] dice_pictures;
	private Yahtzee_Listener listener;
	private JButton [] score_buttons;
	private JLabel [] score_labels;
	private JPanel [] player_panels;
	private JLabel [] player_score_labels;
		
	private final Border BLACKLINE = BorderFactory.createLineBorder(Color.black);
	private final Border REDLINE= BorderFactory.createLineBorder(Color.red);
	
	public Yahtzee_GUI(int num_players, int seed, String [] players) {
		//Main Window
		super("YAHTZEE");
		setLayout(new BorderLayout());
		listener = new Yahtzee_Listener();
		
		//dice 
		roll_button = new JButton("Roll Dice");
		dice_buttons = new JToggleButton[5];
		dice = new Dice(seed);
		scores = new JLabel[5];
		dice_pictures = new ImageIcon[6];
		//score
		score_buttons = new JButton[13];
		score_buttons[0] = new JButton("Ones");
		score_buttons[1] = new JButton("Twos");
		score_buttons[2] = new JButton("Threes");
		score_buttons[3] = new JButton("Fours");
		score_buttons[4] = new JButton("Fives");
		score_buttons[5] = new JButton("Sixes");
		score_buttons[6] = new JButton("3 of a Kind");
		score_buttons[7] = new JButton("4 of a Kind");
		score_buttons[8] = new JButton("Full House");
		score_buttons[9] = new JButton("Small Staight");
		score_buttons[10] = new JButton("Large Staight");
		score_buttons[11] = new JButton("Chance");
		score_buttons[12] = new JButton("Yahtzee!");
		score_labels = new JLabel[14];
		for(int i=0;i<14;i++){
			score_labels[i] = new JLabel();
			score_labels[i].setHorizontalAlignment(SwingConstants.CENTER);
		}
		//players
		player_panels = new JPanel[num_players];
		player_score_labels = new JLabel[num_players];
		
		//retrieve dice pictures
	   File myDir = null;
	   File [] fileList;
	   
	    try
	    {
	      myDir = new File(getClass().getClassLoader().getResource(
	             "eecs285/proj4/keyserja/Dice_pictures").toURI());
	    }
	    catch (URISyntaxException uriExcep)
	    {
	      System.out.println("Caught a URI syntax exception");
	      System.exit(4); //Just bail for simplicity in this project
	    }

	    fileList = myDir.listFiles();
	    
	    for(int i = 0; i < 6; i++) {
	    	dice_pictures[i] = new ImageIcon(fileList[i].toString());
	    }

	    
		//*******************************************************
	    
		//Dice Panel
		JPanel dice_panel = new JPanel(new BorderLayout());
		JPanel dice_pic_panel = new JPanel(new FlowLayout());
		for(int i = 0; i < 5; i++){
			dice_buttons[i]= new JToggleButton();
			dice_buttons[i].setBackground(Color.white);
			dice_pic_panel.add(dice_buttons[i]);
			dice_buttons[i].addActionListener(listener);
		}
		
		dice_panel.add(dice_pic_panel, BorderLayout.NORTH);
		JPanel rollPanel = new JPanel(new FlowLayout());
		rollPanel.add(roll_button);
		dice_panel.add(rollPanel, BorderLayout.SOUTH);
		
		add(dice_panel, BorderLayout.SOUTH);
		update_dice();
		roll_button.addActionListener(listener);

		//players panels
		JPanel top_panel = new JPanel(new GridLayout(1, num_players));
		player_panels = new JPanel[num_players];
		player_score_labels = new JLabel[num_players];
		
		for(int i=0; i < num_players; i++){
			player_panels[i]= new JPanel( new FlowLayout());
			player_panels[i].setBorder(
					BorderFactory.createTitledBorder(BLACKLINE, players[i]));
			player_score_labels[i] = new JLabel("0");
			player_panels[i].add(player_score_labels[i]);
			top_panel.add(player_panels[i]);
		}
		add(top_panel, BorderLayout.NORTH);
		
		//scoreboard panel
		JPanel score_panel = new JPanel(new GridLayout(4, 7));
		for(int i = 0; i < 6; i++) {
			score_panel.add(score_buttons[i]);
		}
		JLabel bonus_label = new JLabel("Bonus", SwingConstants.CENTER);
		score_panel.add(bonus_label);
		for(int i = 0; i < 6; i++) {
			score_panel.add(score_labels[i]);
		}
		score_panel.add(score_labels[13]);
		
		for(int i = 6; i < 13; i++) {
			score_panel.add(score_buttons[i]);
		}		
		for(int i=6; i<13; i++){
			score_panel.add(score_labels[i]);
		}
		add(score_panel, BorderLayout.CENTER);

		
		
	}
	
	public class Yahtzee_Listener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == roll_button){
				update_labels();
				roll_dice();
			}else{
				dice_buttons_pressed(e);
			}
		}
	}
	
	private void roll_dice(){
		dice.roll();
		update_dice();
	}
	
	private void dice_buttons_pressed(ActionEvent e){
		for(int i=0;i<5;i++){
			if(e.getSource() == dice_buttons[i]){
				dice.toggle_lock_die(i);
			}
		}
	}
	
	public void update_dice(){
		int dice_value;
		for(int i=0;i<5;i++){
			dice_value = dice.get_die_value(i)-1;
			dice_buttons[i].setIcon(dice_pictures[dice_value]);
		}
	}
	
	public void update_labels(){
		for(int i = 0; i < 14; i++){
			score_labels[i].setText(Integer.toString(i));
		}
	}
	
}
