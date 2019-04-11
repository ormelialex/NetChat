package Server;

import Client.ChatClient;
import Client.ChatClientIF;
import rmi.Message;
import rmi.PrivateMessage;
import rmi.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class ChatServer extends UnicastRemoteObject implements ChatServerInterface { //Добавлено
    private ConcurrentMap<String,ChatClientIF> chatClients;

    protected ChatServer() throws RemoteException {
        chatClients = new ConcurrentHashMap<>();//инициализация всех пользоавтелей
    }

    @Override
    public void registerChatClient(String name,ChatClientIF chatClient) throws RemoteException {
        this.chatClients.put(name,chatClient);//Добавление пользователя в лист пользователей
        ChatServerInterface chatServ = chatClient.getChatServer();
        chatServ.broadcastMessage(chatClient.getName() + " joined");
    }

    @Override
    public void removeChatClient(String name,ChatClientIF chatClient) throws RemoteException {
        this.chatClients.remove(chatClient,name);
        ChatServerInterface chatServ = chatClient.getChatServer();
        chatServ.broadcastMessage(chatClient.getName() + " went out");
    }

    @Override
    public void broadcastMessage(String message) throws RemoteException {
        for(ConcurrentMap.Entry<String,ChatClientIF> entry : chatClients.entrySet()){
            entry.getValue().retrieveMessage(message);
        }
    }

    @Override
    public void broadcastMessage(Message msg) throws RemoteException {
        for(ConcurrentMap.Entry<String,ChatClientIF> entry : chatClients.entrySet()){
            entry.getValue().retrieveMessage("[PUBLIC]" + msg.getFrom() + " : " + msg.getMessage());
        }
    }

    public void broadcastMessage(PrivateMessage privateMessage) throws RemoteException {
        ChatClientIF recipient = chatClients.get(privateMessage.getTo());
        if(recipient != null) {
            recipient.retrieveMessage("[PRIVATE]"+"From "+ privateMessage.getFrom() + " to " + privateMessage.getTo().getName()+" : " + privateMessage.getMessage());
        }
    }

    @Override
    public List<String> getAllUsers() throws RemoteException {
        ArrayList<String> users = new ArrayList<>();
        for(ConcurrentMap.Entry<String,ChatClientIF> entry : chatClients.entrySet()){
            users.add(entry.getKey());
        }
        return users;
    }

    public ChatClientIF getClient(String name) throws RemoteException {
        for(ConcurrentMap.Entry<String,ChatClientIF> entry : chatClients.entrySet()){
            if(entry.getKey().equals(name)){ return entry.getValue();}
        }
        return null;
    }
}