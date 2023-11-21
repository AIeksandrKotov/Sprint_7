public class CourierCredentials {
    private String login;
    private String password;
    private String firstName;
    private int id;


    public CourierCredentials(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public CourierCredentials(String login) {
        this.login = login;
    }

    public CourierCredentials() {
    }

    public String getLogin() {
        return login;
    }

    public int getId() {
        return id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setId(int id) {
        this.id = id;
    }
}