package eecs285.proj4.rileynat;

public class ClientTest
{
   public final static String IPADDRESS = "67.194.32.122";

   public static void main(String[] args)
   {
      // TODO Auto-generated method stub
      ClientServerSocket theClient;
      String recvdStr;
      theClient = new ClientServerSocket(IPADDRESS, 45542);
      theClient.startClient();
      theClient.sendString("Hello to the server!");
      recvdStr = theClient.recvString();
      System.out.println("Received this message from server: " + recvdStr);

      // int result = theClient.recvInt();
      // System.out.println("timestamp1: " + result);
      // result = theClient.recvInt();
      // System.out.println("timestamp2: " + result);
      // result = theClient.recvInt();
      // System.out.println("timestamp3: " + result);

      // theClient.sendInt(2634);
   }

}
