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

class ChatServer extends UnicastRemoteObject implements ChatServerInterface { //Добавлено
    private List<ChatClientIF> chatClients;

    protected ChatServer() throws RemoteException {
        chatClients = new ArrayList<>();//инициализация всех пользоавтелей
    }

    @Override
    public void registerChatClient(ChatClientIF chatClient) throws RemoteException {
        this.chatClients.add(chatClient);//Добавление пользователя в лист пользователей
        ChatServerInterface chatServ = chatClient.getChatServer();
        chatServ.broadcastMessage(chatClient.getName() + " joined");
    }

    @Override
    public void removeChatClient(ChatClientIF chatClient) throws RemoteException {
        this.chatClients.remove(chatClient);
        ChatServerInterface chatServ = chatClient.getChatServer();
        chatServ.broadcastMessage(chatClient.getName() + " went out");
    }

    @Override
    public void broadcastMessage(String message) throws RemoteException {
        int i = 0;
        while (i < chatClients.size()) {
            chatClients.get(i++).retrieveMessage(message);//Клиенты увидят все сообщения , которые транслировались
        }
    }

    @Override
    public void broadcastMessage(Message msg) throws RemoteException {
        int i = 0;
        while (i < chatClients.size()) {
            chatClients.get(i++).retrieveMessage("[PUBLIC]" + msg.getFrom() + " : " + msg.getMessage());//Клиенты увидят все сообщения , которые транслировались
        }
    }

    public void broadcastMessage(PrivateMessage privateMessage) throws RemoteException {
        ChatClientIF recipient = privateMessage.getTo();
        int i = 0;
        while (i < chatClients.size()) {
            if(recipient.equals(chatClients.get(i))) {
                chatClients.get(i).retrieveMessage("[PRIVATE]"+privateMessage.getFrom() + " : "+privateMessage.getMessage());//Клиенты увидят все сообщения , которые транслировались
            }
            i++;
        }
    }

    public ChatClientIF getClient(String name) throws RemoteException {
        for (ChatClientIF chatClient : chatClients
        ) {
            if (chatClient.getName().equals(name)) {
                return chatClient;
            }
        }
        return null;

    }
}