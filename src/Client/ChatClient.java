package Client;

import Server.ChatServerInterface;
import rmi.Message;
import rmi.PrivateMessage;
import rmi.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatClient extends UnicastRemoteObject implements ChatClientIF {
    private ChatServerInterface chatServer;
    private String name;
    private String info;
    private boolean connected = false;

    public ChatClient(User user, ChatServerInterface chatServer) throws RemoteException { //Принимает клиента и сервер
        this.name = user.getName();
        this.info = user.getInfo();
        this.chatServer = chatServer;
        //chatServer.registerChatClient(this);//регистрируем клиента
    }

    public String getName() {
        return name;
    }

    @Override
    public void retrieveMessage(String message) throws RemoteException {
        System.out.println(message);
    }

    @Override
    public void connect() throws RemoteException {
        if (connected) {
            System.out.println("You are already connected");
        } else {
            chatServer.registerChatClient(this);//регистрируем клиента
            chatServer.broadcastMessage(this.name + " joined ");
            connected = true;
        }
    }

    @Override
    public void disconnect() throws RemoteException {
        if (connected) {
            chatServer.broadcastMessage(this.name + " went out");
            chatServer.removeChatClient(this);
            connected = false;
        } else {
            System.out.println("You are already disconnected");
        }
    }

    @Override
    public void send() throws RemoteException {
        if (connected) {
            System.out.println("For private message enter 'private'\nFor public message enter 'public'");
            Scanner sc = new Scanner(System.in);
            String choose = sc.nextLine().trim();
            System.out.print("Enter message : ");
            switch (choose) {
                case "public":
                    Message msg = new Message(sc.nextLine().trim(), this.name);
                    chatServer.broadcastMessage(msg.getFrom() + " : " + msg.getMessage());
                    break;
                case "private":
                    String message = sc.nextLine().trim();
                    System.out.println("Enter the recipient of the message");
                    String to = sc.nextLine().trim();
                    ChatClient chatClient = chatServer.getClient(to);
                    PrivateMessage privateMsg = new PrivateMessage(message, this.name, chatClient);
                    chatServer.broadcastPrivateMessage(privateMsg);
                    //logic
                    break;
            }
        } else {
            System.out.println("Firstly, connect to chat");
        }
    }
}