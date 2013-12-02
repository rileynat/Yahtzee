        package eecs285.proj4.Yahtzee;

        import java.awt.BorderLayout;
        import java.awt.Color;
        import java.awt.Dimension;
        import java.awt.FlowLayout;
        import java.awt.GridLayout;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.awt.event.MouseListener;
        import java.io.File;
        import java.net.URISyntaxException;

        import javax.security.auth.kerberos.KerberosKey;
        import javax.swing.BorderFactory;
        import javax.swing.GroupLayout.Alignment;
        import javax.swing.BoxLayout;
        import javax.swing.ImageIcon;
        import javax.swing.JButton;
        import javax.swing.JFrame;
        import javax.swing.JLabel;
        import javax.swing.JPanel;
        import javax.swing.JToggleButton;
        import javax.swing.SwingConstants;
        import javax.swing.border.Border;
        import javax.xml.stream.events.EndDocument;

        import org.w3c.dom.events.MouseEvent;

        import eecs285.proj4.Yahtzee.Yahtzee_GUI.Yahtzee_Listener;
        //import eecs285.proj4.Yahtzee.Yahtzee_GUI.yahtzee_scorecard;

        @SuppressWarnings("serial")
        public class Yahtzee_GUI extends JFrame {
                private JButton roll_button;
                private JLabel roll_countJLabel;
                private DieButton [] dice_buttons;
                private Scoreboard playerScorecard;
                private Dice dice;
                private ImageIcon[] dice_pictures;
                private Yahtzee_Listener listener;
                private JButton [] score_buttons;
                private JPanel [] player_panels;
                private JLabel [] player_score_labels;
                private int roll_count;
                private int this_player_index;
                private int num_players;
                private String[] player_names;
                private boolean got_bonus;
                private ClientServerSocket client;
                private yahtzee_scorecard card;
                
                private JPanel glass;
                //this is just a test
                
                private final Border BLACKLINE = BorderFactory.createLineBorder(Color.black);
                private final Border REDLINE= BorderFactory.createLineBorder(Color.red);
                private final String ROLL_COUNT_STRING = "Current Roll: ";
                private final Color background_Color = new Color(85, 200,50);
                private final Color button_Color = new Color(200,200,200);
                public Yahtzee_GUI(int _num_players, int seed, String [] players, String in_player_name, ClientServerSocket inClient) {
                        //Main Window
                        super("YAHTZEE");
                        setLayout(new BorderLayout());
                        num_players = _num_players;

                        
                        getContentPane().setBackground(background_Color);
                        listener = new Yahtzee_Listener();
                        Glass_Pane_Listener glass_listener = new Glass_Pane_Listener();
                        roll_count = 0;
                        client = inClient;
                        got_bonus=false;
                        glass = new JPanel();
                        glass.setPreferredSize(getContentPane().getSize());
                        glass.addMouseListener(glass_listener);
                        setGlassPane(glass);
                        glass.setOpaque(false);
                        glass.setVisible(true);
                        //dice 
                        player_names = new String[num_players];
                        
                        for(int i=0;i<num_players;i++){
                                player_names[i]= players[i]; 
                                if(players[i].equals(in_player_name)) this_player_index = i;
                        }
                        card = new yahtzee_scorecard(players[this_player_index]);
                        roll_button = new JButton("Roll Dice");
                        roll_button.setBackground(button_Color);
                        roll_countJLabel = new JLabel(ROLL_COUNT_STRING + (roll_count+1));
                        dice_buttons = new DieButton[5];
                        dice = new Dice(seed);
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
                        for(int i=0; i < 13; i++){
                                score_buttons[i].addActionListener(listener);
                                score_buttons[i].setBackground(button_Color);
                        }
                        
                        //players
                        player_panels = new JPanel[num_players];
                        playerScorecard = new Scoreboard(in_player_name);
                        
                        //retrieve dice pictures
                   File myDir = null;
                   File [] fileList;
                   
                    try
                    {
                      myDir = new File(getClass().getClassLoader().getResource(
                             "eecs285/proj4/Yahtzee/Dice_pictures").toURI());
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
                        dice_panel.setOpaque(false);
                        JPanel dice_pic_panel = new JPanel(new FlowLayout());
                        dice_pic_panel.setOpaque(false);
                        for(int i = 0; i < 5; i++){
                                dice_buttons[i]= new DieButton(dice_pictures, 6);;
                
                                dice_pic_panel.add(dice_buttons[i]);
                                dice_buttons[i].addActionListener(listener);
                        }
                        
                        dice_panel.add(dice_pic_panel, BorderLayout.NORTH);
                        JPanel rollPanel = new JPanel(new FlowLayout());
                        rollPanel.setOpaque(false);
                        rollPanel.add(roll_button);
                        rollPanel.add(roll_countJLabel);
                        dice_panel.add(rollPanel, BorderLayout.SOUTH);
                        
                        add(dice_panel, BorderLayout.SOUTH);
                        update_dice();
                        roll_button.addActionListener(listener);

                        //players panels
                        JPanel top_panel = new JPanel(new GridLayout(1, num_players));
                        top_panel.setOpaque(false);
                        player_panels = new JPanel[num_players];
                        player_score_labels = new JLabel[num_players];
                        
                        for(int i=0; i < num_players; i++){
                                player_panels[i]= new JPanel( new FlowLayout());
                                player_panels[i].setBorder(
                                                BorderFactory.createTitledBorder(BLACKLINE, players[i]));
                                player_score_labels[i] = new JLabel("000");
                                player_score_labels[i].setText(" 0 ");
                                player_panels[i].add(player_score_labels[i]);
                                player_panels[i].setOpaque(false);
                                top_panel.add(player_panels[i]);
                        }
                        add(top_panel, BorderLayout.NORTH);
                        
                        //scoreboard panel
                        JPanel score_panel = new JPanel(new GridLayout(4, 7));
                        score_panel.setOpaque(false);
                        for(int i = 0; i < 6; i++) {
                                score_panel.add(score_buttons[i]);
                        }
                        JLabel bonus_label = new JLabel("Bonus", SwingConstants.CENTER);
                        score_panel.add(bonus_label);

                        
                        for(int i = 6; i < 13; i++) {
                                score_panel.add(score_buttons[i]);
                        }                

                        //add(score_panel, BorderLayout.CENTER);
                        player_panels[0].setBorder(
                                                                        BorderFactory.createTitledBorder(REDLINE, player_names[0]));
                        
                        JLabel leftTempLabel = new JLabel();
                        leftTempLabel.setBackground(new Color(85, 200, 50));
                        leftTempLabel.setOpaque(true);
                        JLabel rightTempLabel = new JLabel();
                        rightTempLabel.setBackground(new Color(85, 200, 50));
                        rightTempLabel.setOpaque(true);
                        JPanel mid = new JPanel(new GridLayout(1, 3));
                        mid.add(leftTempLabel);
                        mid.add(card);
                        mid.add(rightTempLabel);
                        //mid.add(tempLabel);
                        add(mid, BorderLayout.CENTER);
                }
                
                public class Yahtzee_Listener implements ActionListener{
                        public void actionPerformed(ActionEvent e) {
                                if(e.getSource() == roll_button){
                                        if(roll_count <3){
                                                roll_dice();
                                        }
                                        roll_count++;
                                }else{
                                        if(!dice_buttons_pressed(e)){
                                                score_buttons_pressed(e);
                                        }
                                }
                        }
                }
                
                public class Glass_Pane_Listener implements MouseListener{
                                @Override
                                public void mouseClicked(java.awt.event.MouseEvent e) {
                                        // TODO Auto-generated method stub
                                        
                                }

                                @Override
                                public void mouseEntered(java.awt.event.MouseEvent e) {
                                        // TODO Auto-generated method stub
                                        
                                }

                                @Override
                                public void mouseExited(java.awt.event.MouseEvent e) {
                                        // TODO Auto-generated method stub
                                        
                                }

                                @Override
                                public void mousePressed(java.awt.event.MouseEvent e) {
                                        // TODO Auto-generated method stub
                                        
                                }

                                @Override
                                public void mouseReleased(java.awt.event.MouseEvent e) {
                                        // TODO Auto-generated method stub
                                        
                                }
                }
                
                //we need a function to listen for news from the network
                //Then it will update all the scores and whose players turn it is
                //if it is this Gui player turn, it will call start turn.
                
                void get_Server_data(){
                        while(true){
                                String recieved_string = "";
                                while("".equals(recieved_string)){
                                        recieved_string = client.recvString();
                                }
                                if("Update Score".equals(recieved_string)){
                                        for(int i =0; i < player_names.length ; i++){
                                                int score = client.recvInt();
                                                String score_str = Integer.toString(score);
                                                if(score_str.length() ==2){
                                                        score_str+=" ";
                                                }else{
                                                        score_str = " "+ score_str + " ";
                                                }
                                                player_score_labels[i].setText(score_str);
                                        }
                                        
                                        String in_player_turn = client.recvString();
                                        for(int i=0; i < player_names.length; i++){
                                                if(player_names[i].equals(in_player_turn)){
                                                        player_panels[i].setBorder(
                                                                        BorderFactory.createTitledBorder(REDLINE, player_names[i]));
                                                        
                                                        if(i-1 < 0) i = player_names.length;
                                                        player_panels[i-1].setBorder(
                                                                        BorderFactory.createTitledBorder(BLACKLINE, player_names[i-1]));
                                                        break;
                                                }
                                        }
                                        paint(getGraphics());
                                        if(player_names[this_player_index].equals(in_player_turn)){
                                                break;
                                        }
                                }
                        }
                        start_turn();
                        
                }
                
                void start_turn(){
                        //remove the glass panel,
                        //roll the dice, and set the roll button correc.
                        glass.setVisible(false);
                        roll_dice();
                        roll_count=1;
                }
                
                private void roll_dice(){
                        dice.roll();
                        update_dice();
                        int [] possible_scores = playerScorecard.get_possible_scores(
                                        dice.get_dice_values());
                        
                        update_labels(possible_scores);
                }
                
                private boolean dice_buttons_pressed(ActionEvent e){
                        for(int i=0;i<5;i++){
                                if(e.getSource() == dice_buttons[i]){
                                        dice.toggle_lock_die(i);
                                        return true;
                                }
                        }
                        return false;
                }
                
                private void score_buttons_pressed(ActionEvent e){
                		for(int j=0;j<13;j++){
                			if(e.getSource() == card.scores[j]){
                        if(card.scores[j].isEnabled()){
                        		card.scores[j].setEnabled(false);
                                playerScorecard.insert_new_score(j, 
                                                Integer.parseInt(card.scores[j].getText()));
                                /*player_score_labels[this_player_index].setText(
                                                playerScorecard.get_score()+"");*/
                                card.scores[j].setForeground(Color.black);
                                end_turn();
                        }
                			}
                		}
                }
                
                private void update_dice(){
                        int[] dice_values = dice.get_dice_values();
                        int j=0;
                        for(int i=0;i<5;i++){
                                if(dice.is_die_locked(i)==false){
                                        dice_buttons[i].animate(dice_values[i], 12 + (j*12));
                                        j++;
                                }
                        }
                        roll_countJLabel.setText(ROLL_COUNT_STRING + (roll_count+1));
                }
                
                
                private void update_labels(int [] possible_scores){
                        for(int i = 0; i < 13; i++){
                                if(possible_scores[i]!=-1){
                                    card.scores[i].setText(possible_scores[i]+"");
                                    card.scores[i].setForeground(Color.red);
                                }
                        }
                }
                
                private boolean update_check_bonus(){
                        if(playerScorecard.check_bonus()){
                //                player_score_labels[this_player_index].setText(
                        //                        Integer.toString(playerScorecard.get_score()));
                                card.bonusScore.setText("35");
                                return true;
                        }
                        return false;
                }
                
                private void end_turn(){
                        for(int i=0; i<5; i++){
                                if(dice.is_die_locked(i)){
                                        dice_buttons[i].doClick();
                                }
                        }
                        
                        if(got_bonus==false){
                                got_bonus = update_check_bonus();
                        }
                        roll_count=0;
                        //put a glass panel over the UI so that nothing can be touched. 
                        //send back to the network, name and current score.
                        glass.setVisible(true);
                        client.sendString("Send Score");
                        client.sendString(player_names[this_player_index]);
                        client.sendInt(playerScorecard.get_score());
                        get_Server_data();
                }
                
                private class yahtzee_scorecard extends JPanel{

                	public JLabel[] labels = new JLabel[14];
                	public final Border BLACKLINE = BorderFactory.createLineBorder(Color.black);
                	//private final Border GRAYLINE = BorderFactory.createLineBorder(Color.gray);
                	//private final Border ETCHED = new EtchedBorder(Color.black, Color.gray);
                	public final Dimension size = new Dimension(120, 30);
                	private Yahtzee_Listener listener = new Yahtzee_Listener();
                	public JButton[] scores;
                	public JLabel bonusLabel;
                	public JLabel bonusScore;
                	
                	public yahtzee_scorecard(String name)
                	{
                		add(new JLabel("  "));
                		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
                		JPanel choices = new JPanel(new GridLayout(15, 1));
                		labels[0] = new JLabel();
                		labels[1] = new JLabel(" Ones");
                		labels[2] = new JLabel(" Twos");
                		labels[3] = new JLabel(" Threes");
                		labels[4] = new JLabel(" Fours");
                		labels[5] = new JLabel(" Fives");
                		labels[6] = new JLabel(" Sixes"); 
                		labels[7] = new JLabel(" Three of a Kind    ");
                		labels[8] = new JLabel(" Four of a Kind");
                		labels[9] = new JLabel(" Full House");
                		labels[10] = new JLabel(" Small Straight");
                		labels[11] = new JLabel(" Large Straight");
                		labels[12] = new JLabel(" Chance");
                		labels[13] = new JLabel(" Yahtzee!");
                		bonusLabel = new JLabel(" Bonus ");
                		bonusScore = new JLabel("0");
                		bonusLabel.setBorder(BLACKLINE);
                		bonusLabel.setBackground(Color.WHITE);
                		bonusLabel.setOpaque(true);
                		bonusScore.setBorder(BLACKLINE);
                		bonusScore.setBackground(Color.WHITE);
                		bonusScore.setOpaque(true);
                		bonusScore.setHorizontalAlignment(SwingConstants.CENTER);
                		
                		for(int i = 0; i < 14; i++)
                		{
                			labels[i].setBorder(BLACKLINE);
                			labels[i].setBackground(Color.WHITE);
                			labels[i].setOpaque(true);
                			labels[i].setPreferredSize(size);
                			if(i == 7)
                				choices.add(bonusLabel);
                			choices.add(labels[i]);
                		}
                		add(choices);

                		scores = new JButton[13];
              			JPanel player = new JPanel(new GridLayout(15, 1));
              			JLabel p = new JLabel(" " + name + " ");
              			p.setPreferredSize(new Dimension(90, 30));
              			p.setHorizontalAlignment(SwingConstants.CENTER);
              			p.setBorder(BLACKLINE);
              			p.setBackground(Color.WHITE);
              			p.setOpaque(true);
              			player.add(p);
              			//JButton[] scores = new JButton[13];
              			for(int j = 0; j < 13; j++)
              			{
              				scores[j] = new JButton();
              				scores[j].setBackground(Color.white);
              				scores[j].setBorder(BLACKLINE);
              				scores[j].setOpaque(true);
              				scores[j].addActionListener(listener);
              				scores[j].setEnabled(true);
              				if(j == 6)
              					player.add(bonusScore);
              				player.add(scores[j]);
              			}	
              			add(player);
              			setBackground( new Color(85, 200, 50) );
                		add(new JLabel(" "));
                	}
                }
        }
