import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

class ChatServer extends UnicastRemoteObject implements ChatServerInterface { //Добавлено
    private static final long serialVersionUID = 1L;//параметр сериализации
    private List<ChatClientIF> chatClients;

    protected ChatServer() throws RemoteException {
        chatClients = new ArrayList<ChatClientIF>();//инициализация всех пользоавтелей
    }

    @Override
    public synchronized void registerChatClient(ChatClientIF chatClient) throws RemoteException {
        this.chatClients.add(chatClient);//Добавление пользователя в лист пользователей
    }

    @Override
    public void removeChatClient(ChatClientIF chatClient) throws RemoteException {
        this.chatClients.remove(chatClient);
    }

    @Override
    public synchronized void broadcastMessage(String message) throws RemoteException {
        int i = 0;
        while (i < chatClients.size()) {
            chatClients.get(i++).retrieveMessage(message);//Клиенты увидят все сообщения , которые транслировались
        }
    }
}