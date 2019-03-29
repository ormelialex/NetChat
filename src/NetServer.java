import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class NetServer {

    public static void main(String[] args) throws RemoteException {

        ChatServerInterface ChatServer = new ChatServer();

        try{
            //ChatServerInterface stub = (ChatServerInterface) UnicastRemoteObject.exportObject(ChatServer,0);//RMI сама выберет порт, по которому объект будет принимать вызовы
            Registry registry = LocateRegistry.createRegistry(1099);//создание реестра, по которму клиент получает ссылку на удаленный объект

            registry.bind("ChatServer", ChatServer);//Регистрируем заглушку под именем ChatServer
            System.out.println("bound 'ChatServer'");
        } catch (Throwable e) {
            System.err.println("couldn't bind 'ChatServer' cause "+e.getMessage());
        }

    }
}

