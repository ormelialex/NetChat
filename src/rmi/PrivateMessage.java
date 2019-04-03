package rmi;

public class PrivateMessage extends Message {
    private String to;

    public String getTo() {
        return to;
    }

    public PrivateMessage(String message, String name, String to) {
        super(message, name);
        this.to = to;
    }
}
