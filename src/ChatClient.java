import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ChatClient extends UnicastRemoteObject implements ChatClientIF,Runnable {
    private static final long serialVersionUID = 1L;//параметр сериализации
    private ChatServerInterface chatServer;
    private String name = null;

    protected ChatClient(String name, ChatServerInterface chatServer) throws RemoteException { //Принимает имя клиента и сервер
        this.name = name;
        this.chatServer = chatServer;
        chatServer.registerChatClient(this);//регистрируем клиента
    }

    @Override
    public void retrieveMessage(String message) throws RemoteException {
        System.out.println(message);
    }

    @Override // считывает сообщение и отображает его всем пользователям
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String message;
        while (true) {
            message = scanner.nextLine();
            try {
                chatServer.broadcastMessage(name + " : " + message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}