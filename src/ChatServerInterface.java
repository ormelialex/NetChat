import java.rmi.Remote;
import java.rmi.RemoteException;

interface ChatServerInterface extends Remote {//Методы, доступные клиентам для вызова
    void registerChatClient(ChatClientIF chatClient) throws RemoteException;//Регистрация клиента
    void broadcastMessage(String message) throws RemoteException;//трансляция сообщений для других клиентов

}