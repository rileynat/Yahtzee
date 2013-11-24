package eecs285.proj4.rileynat;

import java.util.Date;

public class ServerTest
{

   public final static String IPADDRESS = "192.168.1.124";

   public static void main(String[] args)
   {
      // TODO Auto-generated method stub

      ClientServerSocket theServer;
      String recvdStr;
      theServer = new ClientServerSocket("127.0.0.1", 45000);
      theServer.startServer();
      recvdStr = theServer.recvString();
      System.out.println("Recevied message from client: " + recvdStr);
      theServer.sendString("Back at ya client");

      Date currentDate = new Date();
      long timestamp_long = currentDate.getTime();
      int timestamp = (int) timestamp_long & 0xFFFFFFFF;
      // System.out.print(timestamp);

      // System.out.print('\n');
      // theServer.sendInt(timestamp);
      // theServer.sendInt(~(timestamp & 0xF0F0F0F0));
      // theServer.sendInt(timestamp & 0x0F0F0F0F);

      int starting = theServer.waitForInt();
      System.out.print(starting);
   }

}
