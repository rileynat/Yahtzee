package eecs285.proj4.Yahtzee;

import java.util.ArrayList;
import java.util.Date;

//Class that runs the actual server for a Yahtzee game
//contains a main for this purpose
public class YahtzeeServer
{

   static ArrayList<PlayerNameAndScore> players;
   
   //This is the IPAddress used to create the client
   //It should match whatever ip address the server is runnning
   public final static String IPADDRESS = "67.194.117.70";
   public final static String FXBIPADDRESS = "67.194.113.232";

   //Main function for class
   public static void main(String[] args)
   {
      // TODO Auto-generated method stub
      players = new ArrayList<PlayerNameAndScore>();
      int numClients = 3;
      int numRound = 0;

      //This code gets all the clients and stores them for later
      //It also creates the player placeholder players arraylist
      ClientServerSocket theServer;
      String recvdStr;
      theServer = new ClientServerSocket(IPADDRESS, 45548);
      // theServer.startServer();
      for (int i = 0; i < numClients; i++) {
         theServer.getClient();
         recvdStr = theServer.waitForString(i);
         System.out.println("Recevied message from client: " + recvdStr);
         PlayerNameAndScore player = new PlayerNameAndScore();
         player.name = recvdStr;
         player.score = 0;
         players.add(player);
      }
      
      //Sending back the player information
      theServer.sendStringToAll("Number players");
      theServer.sendIntToAll(numClients);

      theServer.sendStringToAll("Client List:");
      for (PlayerNameAndScore player : players) {
         theServer.sendStringToAll(player.name);
      }

      // Date currentDate = new Date();
      // long timestamp_long = currentDate.getTime();
      // int timestamp = (int) timestamp_long & 0xFFFFFFFF;
      // // System.out.print(timestamp);

      theServer.sendStringToAll("Starting name:");
      theServer.sendStringToAll(players.get(0).name);

      int currentPlayer = 0;
      String str = "";
      while (numRound < (13 * numClients)) {
         numRound++;
         str = "";
         while (str.equals("")) {
            str = theServer.waitForString(currentPlayer);
         }

         if (str.contains("Score")) {
            str = theServer.waitForString(currentPlayer);
            String message = theServer.waitForString(currentPlayer);
            int score = theServer.waitForInt(currentPlayer);
            System.out.println(score);
            players.get(currentPlayer).score = score;
            Date currentDate = new Date();
            long start_time = currentDate.getTime();
            while (currentDate.getTime() < start_time + 1000) {
               currentDate = new Date();
               System.out.println("testing times");
            }
            System.out.println(numRound);
            theServer.sendStringToAll("Update Score");
            theServer.sendStringToAll(players.get(currentPlayer).name);
            theServer.sendStringToAll("Player "
                  + players.get(currentPlayer).name + ":\n" + message
                  + " points");
            for (PlayerNameAndScore player : players) {
               theServer.sendIntToAll(player.score);
               System.out.println(" " + score);
            }
            currentPlayer = (currentPlayer + 1) % players.size();
         }

         if (numRound == 13 * numClients) {
            theServer.sendStringToAll("Game Over");
            int iter = 0;
            int max = 0;
            for (int i = 0; i < players.size(); i++) {
               if (players.get(i).score > max) {
                  iter = i;
                  max = players.get(i).score;
               }
            }
            theServer.sendStringToAll(players.get(iter).name);
            theServer.sendIntToAll(max);
            break;
         }
         theServer.sendStringToAll(players.get(currentPlayer).name);
      }

      // System.out.print('\n');
      // theServer.sendInt(timestamp);
      // theServer.sendInt(~(timestamp & 0xF0F0F0F0));
      // theServer.sendInt(timestamp & 0x0F0F0F0F);
      //
      // int starting = theServer.waitForInt();
      // System.out.print(starting);
   }
}
