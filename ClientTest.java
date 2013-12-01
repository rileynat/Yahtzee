package eecs285.proj4.Yahtzee;

public class ClientTest
{
   public final static String IPADDRESS = "192.168.1.125";
   public final static String FXBIPADDRESS = "67.194.113.232";

   public static void main(String[] args)
   {
      // TODO Auto-generated method stub
      ClientServerSocket theClient;
      String recvdStr;
      theClient = new ClientServerSocket(IPADDRESS, 45548);
      theClient.startClient();
      theClient.sendString("Hello to the server!");

      // int result = theClient.recvInt();
      // System.out.println("timestamp1: " + result);
      // result = theClient.recvInt();
      // System.out.println("timestamp2: " + result);
      // result = theClient.recvInt();
      // System.out.println("timestamp3: " + result);

      // theClient.sendInt(2634);

      System.out.println("test before recieving string");
      recvdStr = theClient.recvString();
      while (recvdStr == "") {
         theClient.sendString("Hello to the server! test");
         recvdStr = theClient.recvString();
         System.out.print(recvdStr);
      }
      System.out.println("Received this message from server: " + recvdStr);
      int numPlayers = theClient.recvInt();
      System.out.println("Received number of players from server: "
            + numPlayers);

      recvdStr = theClient.recvString();
      System.out.println("Received this message from server: " + recvdStr);
      for (int i = 0; i < numPlayers; i++) {
         recvdStr = theClient.recvString();
         System.out.println("Received player name from server: " + recvdStr);
      }

      recvdStr = theClient.recvString();
      System.out.println("Received this message from server: " + recvdStr);
      recvdStr = theClient.recvString();
      System.out.println("Received this message from server: " + recvdStr);

      if (recvdStr.equals("Hello to the server!")) {
         for (long i = 0; i < 5807; i++) {
            System.out.print('G');
         }
         theClient.sendString("Send Score");
         theClient.sendInt(40);
      }

      recvdStr = "";
      while (recvdStr == "") {
         recvdStr = theClient.recvString();
      }
      System.out.println("Received this message from server: " + recvdStr);
      int score;
      for (int i = 0; i < numPlayers; i++) {
         score = theClient.recvInt();
         System.out.println("Received player score from server: " + score);
      }
   }

}
