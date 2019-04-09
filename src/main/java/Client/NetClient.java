package Client;

import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import Client.ChatClient;

import com.google.gson.stream.JsonWriter;
import Server.ChatServerInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import rmi.User;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

public class NetClient {
    public static void main(String[] args) throws IOException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1");//Ссылка на удаленный реестр,порт не передается, но может
        ChatServerInterface stub = (ChatServerInterface) registry.lookup("ChatServer"); //Сслыка на удаленный объект
        Scanner scanner = new Scanner(System.in);
        ChatClient client = null;
        User user1 = null;
        File file = new File("./src/main/resources/user.json");
        Gson gson = new GsonBuilder().create();
        Reader reader = new FileReader(file);
        user1 = gson.fromJson(reader, User.class);
        if (user1 == null) {
            System.out.print("Enter nickname:");
            String name = scanner.nextLine().trim().toLowerCase();
            System.out.print("Enter information about yourself:");
            String info = scanner.nextLine().trim().toLowerCase();
            User user = new User(name, info);
            String userString = gson.toJson(user);
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(userString);
            }catch (IOException e){
                System.out.println("Something go wrong");
            }
            client = new ChatClient(user, stub);
        } else {
            System.out.println("You are already registered");
            /*String userString = gson.toJson(user);
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(userString);
            }catch (IOException e){
                System.out.println("Something go wrong");
            }*/ //Если вдруг потом будет функция изменения информации или имени
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

