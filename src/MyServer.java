import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyServer {
    public static void main(String[] args) {
        int port = 8080;
        // Creates a pool of 10 threads to handle 10 clients simultaneously
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Advanced Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection from: " + clientSocket.getRemoteSocketAddress());

                // Pass the client to a separate thread
                threadPool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            System.err.println("Server Error: " + e.getMessage());
        }
    }
}


