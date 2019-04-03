package Server;

import Client.ChatClientIF;
import rmi.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

class ChatServer extends UnicastRemoteObject implements ChatServerInterface { //Добавлено
    private List<ChatClientIF> chatClients;

    protected ChatServer() throws RemoteException {
        chatClients = new ArrayList<>();//инициализация всех пользоавтелей
    }

    @Override
    public void registerChatClient(ChatClientIF chatClient) throws RemoteException {
        this.chatClients.add(chatClient);//Добавление пользователя в лист пользователей
    }

    @Override
    public void removeChatClient(ChatClientIF chatClient) throws RemoteException {
        this.chatClients.remove(chatClient);
    }

    @Override
    public void broadcastMessage(String message) throws RemoteException {
        int i = 0;
        while (i < chatClients.size()) {
            chatClients.get(i++).retrieveMessage(message);//Клиенты увидят все сообщения , которые транслировались
        }
    }

    public void getUser(User user){
        //return user;
    }
}