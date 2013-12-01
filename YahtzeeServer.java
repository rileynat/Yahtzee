package eecs285.proj4.Yahtzee;

import java.util.ArrayList;
import java.util.Date;

public class YahtzeeServer
{

   static ArrayList<PlayerNameAndScore> players;

   public final static String IPADDRESS = "10.0.0.47";
   public final static String FXBIPADDRESS = "67.194.113.232";

   public static void main(String[] args)
   {
      // TODO Auto-generated method stub
      players = new ArrayList<PlayerNameAndScore>();
      int numClients = 1;

      ClientServerSocket theServer;
      String recvdStr;
      theServer = new ClientServerSocket(IPADDRESS, 45548);
      // theServer.startServer();
      for (int i = 0; i < numClients; i++) {
         theServer.getClient();
         recvdStr = theServer.waitForString();
         System.out.println("Recevied message from client: " + recvdStr);
         PlayerNameAndScore player = new PlayerNameAndScore();
         player.name = recvdStr;
         player.score = 2;
         players.add(player);
      }

      theServer.sendStringToAll("Number players");
      theServer.sendIntToAll(numClients);

      theServer.sendStringToAll("Client List:");
      for (PlayerNameAndScore player : players) {
         theServer.sendStringToAll(player.name);
      }

      Date currentDate = new Date();
      long timestamp_long = currentDate.getTime();
      int timestamp = (int) timestamp_long & 0xFFFFFFFF;
      // System.out.print(timestamp);

      theServer.sendStringToAll("Starting name:");
      theServer.sendStringToAll(players.get(0).name);

      int currentPlayer = 0;
      String str = "";
      while (true) {
         str = "";
         while (str.equals("")) {
            str = theServer.waitForString();
         }

         if (str.equals("Send Score")) {
            str = theServer.waitForString();
            int score = theServer.waitForInt();
            System.out.println(score);
            players.get(currentPlayer).score = score;
            theServer.sendStringToAll("Update Score");
            for (PlayerNameAndScore player : players) {
               theServer.sendIntToAll(player.score);
               System.out.println(" " + score);
            }
            currentPlayer = (currentPlayer + 1) % players.size();
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
