package Server;

import Client.ChatClient;
import Client.ChatClientIF;
import rmi.Message;
import rmi.PrivateMessage;
import rmi.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatServerInterface extends Remote {//Методы, доступные клиентам для вызова

    void registerChatClient(String name,ChatClientIF chatClient) throws RemoteException;//Регистрация клиента

    void removeChatClient(String name,ChatClientIF chatClient) throws RemoteException;//Удаление клиента

    void broadcastMessage(String message) throws RemoteException;//трансляция сообщений для других клиентов

    void broadcastMessage(Message Msg) throws RemoteException;

    void broadcastMessage(PrivateMessage privateMsg) throws RemoteException;

    List<String> getAllUsers() throws RemoteException;

    ChatClientIF getClient(String name) throws RemoteException;

}