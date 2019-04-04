package rmi;

import Client.ChatClient;

public class PrivateMessage extends Message {
    private ChatClient to;

    public ChatClient getTo() {
        return to;
    }

    public PrivateMessage(String message, String name, ChatClient chatClient) {
        super(message, name);
        this.to = chatClient;
    }
}
