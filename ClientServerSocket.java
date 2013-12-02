package eecs285.proj4.Yahtzee;

import static java.lang.System.out;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Vector;

//Class to create a server and a socket and send and recieve data back and
//forth

public class ClientServerSocket
{
   private String ipAddr;
   private int portNum;
   private Socket socket;
   private DataOutputStream outData;
   private DataInputStream inData;
   ServerSocket serverSock;
   private ArrayList<ServerClient> clients;

   //Constructor for class that takes in an ipaddress and port
   //all other parts are set to null
   //used by the server and the clients
   public ClientServerSocket(String inIPAddr, int inPortNum)
   {
      ipAddr = inIPAddr;
      portNum = inPortNum;
      inData = null;
      outData = null;
      socket = null;
   }

   //function to begin working a client
   public void startClient()
   {
      try {
         socket = new Socket(ipAddr, portNum);
         socket.setSoTimeout(2000);
         outData = new DataOutputStream(socket.getOutputStream());
         inData = new DataInputStream(socket.getInputStream());
      } catch (IOException ioe) {
         out.println("ERROR: Unable to connect - " + "is the server running?");
         out.printf("error message %s\n", ioe);
         System.exit(10);
      }
   }

   //Function to start the server running -- deprecated
   //Generally not for use except for if there is only one player
   public void startServer()
   {
      ServerSocket serverSock;
      try {
         serverSock = new ServerSocket(portNum);
         out.println("Waiting for client to connect...");
         socket = serverSock.accept();
         // socket.setSoTimeout(10000);
         outData = new DataOutputStream(socket.getOutputStream());
         inData = new DataInputStream(socket.getInputStream());
         out.println("Client connection accepted");
      } catch (IOException ioe) {
         out.println("ERROR: Caught exception starting server");
         System.exit(7);
      }
   }

   //Used multiple times by the server to get and connect each
   //client
   public void getClient()
   {
      if (clients == null) {
         clients = new ArrayList<ServerClient>();
      }

      try {
         if (serverSock == null) {
            serverSock = new ServerSocket(portNum);
         }
         out.printf("Waiting for client to connect...\n");
         socket = serverSock.accept();
         // socket.setSoTimeout(2000);
         ServerClient client = new ServerClient();
         client.outData = new DataOutputStream(socket.getOutputStream());
         client.inData = new DataInputStream(socket.getInputStream());
         clients.add(client);
         out.printf("Client connection accepted\n");
      } catch (IOException ioe) {
         out.println("ERROR: Caught exception getting client");
         System.exit(7);
      }
   }

   //Used by the client only to send a string to the server
   public boolean sendString(String strToSend)

   {
      boolean success = false;
      try {
         outData.writeBytes(strToSend);
         outData.writeByte(0); // send 0 to signal the end of the string
         success = true;
      } catch (IOException e) {
         System.out.println("Caught IOException Writing To Socket Stream!");
         System.exit(-1);
      }
      return (success);
   }

   //server side string sending to all the clients it is connected to
   public boolean sendStringToAll(String strToSend)
   // reserved for the server side
   {
      boolean success = false;
      if (clients == null) {
         return success;
      }
      try {
         for (ServerClient client : clients) {
            client.outData.writeBytes(strToSend);
            client.outData.writeByte(0);
         }
         success = true;
      } catch (IOException e) {
         System.out.println("Caught IOException Writing To Socket Stream!");
         System.exit(-1);
      }
      return (success);
   }

   //Server side recieving string that asks the individual client for a
   //string
   public String waitForString(int currentPlayer)
   // reserved for the server side
   {
      Vector<Byte> byteVec = new Vector<Byte>();
      byte[] byteAry;
      byte recByte;
      String receivedString = "";
      try {
         // socket.setSoTimeout(2000);
         System.out.println("about to hang!!!!");
         recByte = clients.get(currentPlayer).inData.readByte();
         System.out.println("done hanging");
         while (recByte != 0) {
            byteVec.add(recByte);
            recByte = clients.get(currentPlayer).inData.readByte();
         }
         byteAry = new byte[byteVec.size()];
         for (int ind = 0; ind < byteVec.size(); ind++) {
            byteAry[ind] = byteVec.elementAt(ind).byteValue();
         }
         receivedString = new String(byteAry);
         if (receivedString.length() > 0) {
            return receivedString;
         }
      } catch (IOException ioe) {
         out.println("ERROR: waiting for string from socket");
      }
      try {
         socket.setSoTimeout(0);
      } catch (SocketException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return (receivedString);
   }

   //Client side string recieving that asks only its server
   public String recvString()
   // reserved for the client side
   {
      Vector<Byte> byteVec = new Vector<Byte>();
      byte[] byteAry;
      byte recByte;
      String receivedString = "";
      try {
         recByte = inData.readByte();
         while (recByte != 0) {
            byteVec.add(recByte);
            recByte = inData.readByte();
         }
         byteAry = new byte[byteVec.size()];
         for (int ind = 0; ind < byteVec.size(); ind++) {
            byteAry[ind] = byteVec.elementAt(ind).byteValue();
         }
         receivedString = new String(byteAry);
      } catch (IOException ioe) {
         out.println("ERROR: receiving string from socket");
      }
      return (receivedString);
   }

   //Used by the client to get an int from the server and 
   //returns it
   public int recvInt()
   // reserved for the client side
   {
      int recvInt = 0;
      try {
         recvInt = inData.readInt();
      } catch (IOException ioe) {
         out.println("ERROR: receiving int from socket");
         System.exit(10);
      }
      return (recvInt);
   }

   //sends an integer client side to the server
   public void sendInt(int inSendInt)
   // reserved for the client side
   {
      try {
         outData.writeInt(inSendInt);
      } catch (IOException ioe) {
         System.out.println("ERROR: sending int");
         System.exit(11);
      }
   }

   //Used by the server to send ints to all its cleints
   public void sendIntToAll(int inSendInt)
   // reserved for the server side
   {
      try {
         for (ServerClient client : clients) {
            client.outData.writeInt(inSendInt);
         }
      } catch (IOException ioe) {
         System.out.println("ERROR: sending int");
         System.exit(11);
      }
   }

   //Server waiting for an int to be given to it from
   //the correct client
   public int waitForInt(int currentPlayer)
   // reserved for the server side
   {
      int recvInt = 0;
      boolean success = false;
      while (!success) {
         try {
            recvInt = clients.get(currentPlayer).inData.readInt();
            success = true;
         } catch (IOException ioe) {
            success = false;
            // out.println("ERROR: waiting for int from socket");
         }
         if (success) {
            return recvInt;
         }
      }
      // out.printf("recieved int %d\n", recvInt);
      return (recvInt);
   }

   //Inner class used to store the data of an individual client
   //connection for use by the server
   public class ServerClient
   {
      public ServerSocket serverSock;
      public DataOutputStream outData;
      public DataInputStream inData;
   }

}
