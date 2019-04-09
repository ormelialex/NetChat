package Client;

import Server.ChatServerInterface;
import rmi.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ChatClientIF extends Remote {//Added

    void retrieveMessage(String message) throws RemoteException;//получать сообщения других пользователей

    void connect() throws RemoteException;

    void disconnect() throws RemoteException;

    void send() throws RemoteException;

    String getName() throws RemoteException;

    String getInfo() throws RemoteException;

    ChatServerInterface getChatServer() throws RemoteException;
}