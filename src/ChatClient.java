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
        System.out.println("Enter Message");
        System.out.println("For exit enter 0");
        Scanner scanner = new Scanner(System.in);
        boolean swit = true;
        String message;
        while (swit) {
            message = scanner.nextLine();
            try {
                if("0".equals(message.trim())){
                    chatServer.broadcastMessage(name+" went out");
                    chatServer.removeChatClient(this);
                    System.out.println("For join enter 1");
                    swit = false;
                }else {
                    chatServer.broadcastMessage(name + " : " + message);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        while(!swit){
            message = scanner.nextLine();
            System.out.println("For join enter 1");
            if("1".equals(message.trim())){
                try {
                    chatServer.registerChatClient(this);//регистрируем клиента
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                try {
                    chatServer.broadcastMessage(name + " joined ");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                swit = true;
            }
        }
    }
}