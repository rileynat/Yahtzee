package eecs285.proj4.Yahtzee;

import javax.swing.JFrame;
import java.util.ArrayList;



public class Yahtzee_main {
   
   public final static String IPADDRESS = "67.194.30.199";
   public final static String FXBIPADDRESS = "67.194.113.232";
   public static ArrayList<String> playerNames;
   
	public static void main(String [] args){
	   boolean success = false;
	   boolean serverInUse = false;
	      String string = "";
	      String recvdStr = "";
	      ClientServerSocket client;
	      playerNames = new ArrayList<String>();
	      String [] players = {"SpongeBob", "Patrick", "Squidward", "Mr Krabs"};
	      client = new ClientServerSocket(IPADDRESS, 45548);
	      if (serverInUse) {
	      client.startClient();
	      }
	      while (!success) {
	         PlayerNamesDialog namesDialog = new PlayerNamesDialog(new JFrame(),
	               "Player Names Input");
	         string = namesDialog.getName();
	         if (string.trim().length() > 0) {
	            success = true;
	         }
	      }
	      if (serverInUse) {
	      client.sendString(string);
	      System.out.println("test before recieving string");
	      recvdStr = client.recvString();
	      while (recvdStr == "") {
	         client.sendString(string);
	         recvdStr = client.recvString();
	         System.out.print(recvdStr);
	      }
	      
	      int numPlayers = client.recvInt();
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
	      
		
		Yahtzee_GUI gui = new Yahtzee_GUI(4, 1000, players, recvdStr);
		gui.pack();		
		gui.setVisible(true);
		gui.setDefaultCloseOperation(
		JFrame.EXIT_ON_CLOSE);
	}
}
//