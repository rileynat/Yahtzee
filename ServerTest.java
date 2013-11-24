package eecs285.proj4.rileynat;

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

   }

}
