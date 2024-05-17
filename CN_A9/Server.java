import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        final int PORT = 5501;
        
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started. Waiting for a client...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress());

            // Say Hello
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

            String clientMessage = reader.readLine();
            System.out.println("Client: " + clientMessage);
            writer.println("Hello from server!");

            // File Transfer
            InputStream fileStream = clientSocket.getInputStream();
            FileOutputStream fileOutput = new FileOutputStream("19_AssignmentNo.8_CN.txt");
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileStream.read(buffer)) != -1) {
                fileOutput.write(buffer, 0, bytesRead);
            }

            System.out.println("File received successfully.");

            fileOutput.close();
            fileStream.close();
            reader.close();
            writer.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
