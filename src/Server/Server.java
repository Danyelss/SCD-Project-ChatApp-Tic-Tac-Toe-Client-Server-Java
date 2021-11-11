package Server;

import Logic.Logic;

public class Server {

    public void transmitData(String string) {
        System.out.print("Server: " + string);
    }

    public Server() {
        Logic logic = new Logic(this);

        logic.getData("@a");

    //   logic.getData("$01");
      //  logic.getData("$00");
        logic.getData("$02");
        logic.getData("$12");

    }

    public static void main(String[] args) {
        Server server = new Server();
    }

}
