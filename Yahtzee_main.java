package eecs285.proj4.Yahtzee;

import javax.swing.JFrame;



public class Yahtzee_main {
   
   public final static String IPADDRESS = "192.168.1.125";
   public final static String FXBIPADDRESS = "67.194.113.232";
   
   
	public static void main(String [] args){
	   boolean success = false;
	      String string = "";
	      String recvdStr = "";
	      ClientServerSocket client;
	      client = new ClientServerSocket(IPADDRESS, 45548);
	      client.startClient();
	      while (!success) {
	         PlayerNamesDialog namesDialog = new PlayerNamesDialog(new JFrame(),
	               "Player Names Input");
	         string = namesDialog.getName();
	         if (string.trim().length() > 0) {
	            success = true;
	         }
	      }
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
	      for (int i = 0; i < numPlayers; i++) {
	         recvdStr = client.recvString();
	         System.out.println("Received player name from server: " + recvdStr);
	      }
	      
		String [] players = {string, "Patrick", "Squidward", "Mr Krabs"};
		Yahtzee_GUI gui = new Yahtzee_GUI(4, 1000, players,"SpongeBob");
		gui.pack();		
		gui.setVisible(true);
		gui.setDefaultCloseOperation(
		JFrame.EXIT_ON_CLOSE);
	}
}
//