package eecs285.proj4.rileynat;

public class ClientTest
{

   public static void main(String[] args)
   {
      // TODO Auto-generated method stub
      ClientServerSocket theClient;
      String recvdStr;
      theClient = new ClientServerSocket("127.0.0.1", 45000);
      theClient.startClient();
      theClient.sendString("Hello to the server!");
      recvdStr = theClient.recvString();
      System.out.println("Received this message from server: " + recvdStr);
   }

}
