package rmi;

import Client.ChatClient;
import Client.ChatClientIF;

public class PrivateMessage extends Message {
    private ChatClientIF to;

    public ChatClientIF getTo() {
        return to;
    }

    public PrivateMessage(String message, String name, ChatClientIF chatClient) {
        super(message, name);
        this.to = chatClient;
    }
}
