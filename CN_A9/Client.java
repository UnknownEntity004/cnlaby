import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        final String SERVER_IP = "127.0.0.1";
        final int PORT = 5501;

        try {
            Socket socket = new Socket(SERVER_IP, PORT);

            // Say Hello
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            writer.println("Hello from client!");
            String serverResponse = reader.readLine();
            System.out.println("Server: " + serverResponse);

            // Send text file
            sendFile(socket, "Script.txt");

            // Send audio file
            sendFile(socket, "audio_cn.mp3");

            // Send video file
            sendFile(socket, "Video_cn.mp4");

            writer.close();
            reader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendFile(Socket socket, String fileName) throws IOException {
        OutputStream fileStream = socket.getOutputStream();
        FileInputStream fileInput = new FileInputStream(fileName);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileInput.read(buffer)) != -1) {
            fileStream.write(buffer, 0, bytesRead);
        }

        System.out.println("File " + fileName + " sent successfully.");

        fileInput.close();
    }
}
