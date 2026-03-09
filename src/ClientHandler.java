import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler implements Runnable {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            // Read the first line of the request (e.g., "GET /index.html HTTP/1.1")
            String requestLine = in.readLine();
            if (requestLine == null) return;

            String[] tokens = requestLine.split(" ");
            String path = tokens[1];

            // Simple Routing Logic
            String responseBody;
            if (path.equals("/")) {
                responseBody = "<h1>Welcome to the Home Page</h1>";
            } else if (path.equals("/status")) {
                responseBody = "<h1>Server is Running Smoothly</h1>";
            } else {
                responseBody = "<h1>404 Not Found</h1>";
            }

            // Standard HTTP Response Headers
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html");
            out.println("Content-Length: " + responseBody.length());
            out.println(); // Blank line between headers and body
            out.print(responseBody);
            out.flush();

        } catch (IOException e) {
            System.err.println("Handler Error: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}