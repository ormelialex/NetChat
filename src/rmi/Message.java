package rmi;

public class Message {
    private String message;
    private String from;

    public Message(String message, String name) {
        this.message = message;
        this.from = name;
    }

    public String getMessage() {
        return message;
    }

    public String getFrom() {
        return from;
    }
}
