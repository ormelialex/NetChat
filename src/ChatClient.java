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
        chatServer.broadcastMessage(name + " joined ");
    }

    @Override
    public void retrieveMessage(String message) throws RemoteException {
        System.out.println(message);
    }

    @Override // считывает сообщение и отображает его всем пользователям
    public void run() {
        System.out.println("Введите сообщение");
        System.out.println("Для выхода введите 0");
        Scanner scanner = new Scanner(System.in);
        boolean swit = true;
        String message;
        while (swit) {
            message = scanner.nextLine();
            try {
                if(message.toString().trim().equals("0")){
                    chatServer.broadcastMessage(name+" went out");
                    chatServer.removeChatClient(this);
                    swit = false;
                }else {
                    chatServer.broadcastMessage(name + " : " + message);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}