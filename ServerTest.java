package eecs285.proj4.Yahtzee;

import java.util.ArrayList;
import java.util.Date;

public class ServerTest
{
   static ArrayList<playerNameAndScore> players;

   public final static String IPADDRESS = "10.0.0.21";
   public final static String FXBIPADDRESS = "67.194.113.232";

   public static void main(String[] args)
   {
      // TODO Auto-generated method stub
      players = new ArrayList<playerNameAndScore>();

      ClientServerSocket theServer;
      String recvdStr;
      theServer = new ClientServerSocket(IPADDRESS, 45000);
      theServer.startServer();
      recvdStr = theServer.recvString();
      System.out.println("Recevied message from client: " + recvdStr);
      theServer.sendString("Back at ya client");

      Date currentDate = new Date();
      long timestamp_long = currentDate.getTime();
      int timestamp = (int) timestamp_long & 0xFFFFFFFF;
      System.out.print(timestamp);

      // System.out.print('\n');
      // theServer.sendInt(timestamp);
      // theServer.sendInt(~(timestamp & 0xF0F0F0F0));
      // theServer.sendInt(timestamp & 0x0F0F0F0F);

      int starting = theServer.waitForInt();
      System.out.print(starting);
   }

   public class playerNameAndScore
   {
      public String name;
      public int score;
   }

}
