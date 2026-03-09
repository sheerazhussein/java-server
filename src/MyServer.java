import java.io.*;
import java.net.*;

public class MyServer {
    public static void main(String[] args) {
        int port = 8080;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is waiting on port " + port + "...");

            while (true) {
                // Wait for a client to connect
                try (Socket socket = serverSocket.accept()) {
                    System.out.println("Client connected!");

                    // Send a simple message back to the client
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("HTTP/1.1 200 OK\r\n\r\nHello! Your Java server is working.");
                }
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}
