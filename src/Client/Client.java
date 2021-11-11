package Client;

import Logic.Logic;

public class Client {

    public void transmitData(String string) {
        System.out.println("Client: " + string);
    }

    public Client() {
        Logic logic = new Logic(this);

    }

    public static void main(String[] args) {
        Client client = new Client();
    }
}
