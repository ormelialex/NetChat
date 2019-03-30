import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class NetClient {
    public static void main(String[] args) throws RemoteException, NotBoundException {

        Registry registry = LocateRegistry.getRegistry("127.0.0.1");//Ссылка на удаленный реестр,порт не передается, но может
        ChatServerInterface stub = (ChatServerInterface) registry.lookup("ChatServer");//Сслыка на удаленный объект
        System.out.print("Введите имя:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        new Thread(new ChatClient(name, stub)).start();//added
    }
}

