package Client;

import rmi.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatClientIF extends Remote {//Added

    void retrieveMessage(String message) throws RemoteException;//получать сообщения других пользователей

    void connect() throws RemoteException;

    void disconnect() throws RemoteException;

    void send(Message message) throws RemoteException;
}