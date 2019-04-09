package rmi;

public class User {
    private String name;
    private String info;

    public User(String name, String info) {
        this.name = name;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }
}
