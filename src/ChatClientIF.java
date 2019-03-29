import java.rmi.Remote;
import java.rmi.RemoteException;

interface ChatClientIF extends Remote {//Added
    void retrieveMessage(String message) throws RemoteException;//получать сообщения других пользователей
}