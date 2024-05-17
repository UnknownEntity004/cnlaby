import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000); // Server listens on port 5000
            System.out.println("Server waiting for client connection...");
            Socket socket = serverSocket.accept(); // Wait for client connection

            // Open streams for communication
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Read message from client
            String clientMessage = in.readLine();
            System.out.println("Client: " + clientMessage);

            // Send response to client
            out.println("Hello from Server!");

            // Close streams and socket
            in.close();
            out.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}