package eecs285.proj4.Yahtzee;

import javax.swing.JFrame;

import java.util.ArrayList;
import java.util.Date;



public class Yahtzee_main {
   
   public final static String IPADDRESS = "10.0.0.47";
   public final static String FXBIPADDRESS = "67.194.113.232";
   public static ArrayList<String> playerNames;
   
	public static void main(String [] args){
	   boolean success = false;
	   boolean serverInUse = true;
	   String myPlayerName = "";
	      String string = "";
	      String recvdStr = "";
	      int numPlayers = 4;
	      ClientServerSocket client;
	      playerNames = new ArrayList<String>();
	      String [] players = new String[4];
	      client = new ClientServerSocket(IPADDRESS, 45548);
	      if (serverInUse) {
	      client.startClient();
	      }
	      while (!success) {
	         PlayerNamesDialog namesDialog = new PlayerNamesDialog(new JFrame(),
	               "Player Names Input");
	         myPlayerName = namesDialog.getName();
	         if (myPlayerName.trim().length() > 0) {
	            success = true;
	         }
	      }
	      if (serverInUse) {
	      client.sendString(myPlayerName);
	      System.out.println("test before recieving string");
	      recvdStr = client.recvString();
	      while (recvdStr == "") {
	         client.sendString(string);
	         recvdStr = client.recvString();
	         System.out.print(recvdStr);
	      }
	      
	      numPlayers = client.recvInt();
	      System.out.println("Received number of players from server: "
	            + numPlayers);
	      recvdStr = client.recvString();
	      System.out.println("Received this message from server: " + recvdStr);
	      for (int i = 0; i < numPlayers && i < 4; i++) {
	         recvdStr = client.recvString();
	         System.out.println("Received player name from server: " + recvdStr);
	         players[i] = recvdStr;
	      }
	      
	      recvdStr = client.recvString();
	      System.out.println("Received this message from server: " + recvdStr);
	      recvdStr = client.recvString();
	      System.out.println("Received this message from server: " + recvdStr);
	      }
	      
		
	      Date cur_date = new Date();
	      long timestamp = cur_date.getTime();
	      int rand = (int) timestamp & 0xFFFFFFFF;
		Yahtzee_GUI gui = new Yahtzee_GUI(numPlayers, rand, players, myPlayerName, client);
		gui.pack();		
		gui.setVisible(true);
		gui.setDefaultCloseOperation(
		JFrame.EXIT_ON_CLOSE);
		if(players[0].equals(myPlayerName)){
		   System.out.println(myPlayerName);
			gui.start_turn();
		}else{		
			gui.get_Server_data();
		}
	}
}
//