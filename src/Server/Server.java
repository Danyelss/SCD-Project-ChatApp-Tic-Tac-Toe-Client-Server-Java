package Server;

import Logic.Logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Server {

    private ServerSocket serverSocket ;
    private Socket clientSocket; // socket used by client to send and recieve data from server
    private BufferedReader in;   // object to read data from socket
    private PrintWriter out;     // object to write data into socket
    private Scanner sc = new Scanner(System.in); // object to read data from user's keybord

    private SocketChannel client;

    private Logic logic;

    public Sender getSender() {
        return sender;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    private Sender sender;

    private Receiver receiver;

    public boolean isExist() {
        return exist;
    }

    private boolean exist = false;

    public void clientClose() {
        try {
            System.out.println("Server out of service");

            this.out.close();

            this.clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Server() {

        this.exist = true;

        this.logic = new Logic(this);

        try {
            this.serverSocket = new ServerSocket(8089);
            this.clientSocket = serverSocket.accept();

            this.out = new PrintWriter(clientSocket.getOutputStream());
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            this.sender = new Sender();

            sender.start();

            this.receiver = new Receiver();

            receiver.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class Sender extends Thread {
        private CyclicBarrier cyclicBarrier;
        private String message;

        public void transmitData(String message) {
            this.message = message;

            System.out.println("Server will transmit: " + message );


            try {
                this.cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void run() {
            while (true) {

                try {
                    this.cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }

                out.println(message);
                out.flush();
            }
        }

        public Sender() {
            this.cyclicBarrier = new CyclicBarrier(2);
        }
    }

    public class Receiver extends Thread {
        private String message;

        public void receiveMessage(String message) {
            logic.getData(message);
        }

        @Override
        public void run() {
            try {

                message = in.readLine();

                System.out.println("Server got: " + message);

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                while (message != null) {

                    receiveMessage(message);

                    message = in.readLine();
                }

                clientClose();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public Receiver() {
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
    }

}