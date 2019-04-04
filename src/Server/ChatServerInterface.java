package Server;

import Client.ChatClient;
import Client.ChatClientIF;
import rmi.PrivateMessage;
import rmi.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ChatServerInterface extends Remote {//Методы, доступные клиентам для вызова

    void registerChatClient(ChatClientIF chatClient) throws RemoteException;//Регистрация клиента

    void removeChatClient(ChatClientIF chatClient) throws RemoteException;//Удаление клиента

    void broadcastMessage(String message) throws RemoteException;//трансляция сообщений для других клиентов

    void broadcastPrivateMessage(PrivateMessage privateMsg) throws RemoteException;

    ChatClientIF getClient(String name) throws RemoteException;

}