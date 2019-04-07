package Client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import Server.ChatServerInterface;
import rmi.Message;
import rmi.User;

public class NetClient {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1");//Ссылка на удаленный реестр,порт не передается, но может
        ChatServerInterface stub = (ChatServerInterface) registry.lookup("ChatServer"); //Сслыка на удаленный объект
        System.out.print("Введите имя:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine().trim().toLowerCase();
        System.out.print("Введите информацию о себе:");
        String info = scanner.nextLine().trim().toLowerCase();
        User user = new User(name, info);
        ChatClient client = new ChatClient(user, stub);
        System.out.println("To connect to the server, use 'connect'\nTo disconnect to the server, use the 'disconnect'\n" +
                "To exist the chat, use 'exist'\nTo send message, use 'send'");
        while (true) {
            String command = scanner.nextLine().trim().toLowerCase();
            switch (command) {
                case "exist":
                    client.disconnect();
                    System.exit(0);
                    break;
                case "connect":
                    client.connect();
                    break;
                case "disconnect":
                    client.disconnect();
                    break;
                case "send":
                    client.send();
                    break;
            }
        }
    }
}

