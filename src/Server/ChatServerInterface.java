package Server;

import Client.ChatClientIF;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServerInterface extends Remote {//Методы, доступные клиентам для вызова

    void registerChatClient(ChatClientIF chatClient) throws RemoteException;//Регистрация клиента

    void removeChatClient(ChatClientIF chatClient) throws RemoteException;//Удаление клиента

    void broadcastMessage(String message) throws RemoteException;//трансляция сообщений для других клиентов

}