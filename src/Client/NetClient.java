package Client;

import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import Server.ChatServerInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import rmi.Message;
import rmi.User;

public class NetClient {
    public static void main(String[] args) throws IOException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1");//Ссылка на удаленный реестр,порт не передается, но может
        ChatServerInterface stub = (ChatServerInterface) registry.lookup("ChatServer"); //Сслыка на удаленный объект
        Scanner scanner = new Scanner(System.in);
        ChatClient client= null;
        File file = new File("./resources/user.json");
        Gson gson = new GsonBuilder().create();
        //User user1 = null;
        User user1 = gson.fromJson(FileUtils.readFileToString(file,"UTF-8"),User.class);
        if(user1 == null) {
            System.out.print("Enter nickname:");
            String name = scanner.nextLine().trim().toLowerCase();
            System.out.print("Enter information about yourself:");
            String info = scanner.nextLine().trim().toLowerCase();
            User user = new User(name, info);
            FileUtils.writeStringToFile(file, gson.toJson(user));
            client = new ChatClient(user, stub);
        }else{
            System.out.println("You are already registered");
            FileUtils.writeStringToFile(file, gson.toJson(user1));
            client = new ChatClient(user1, stub);
        }
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

