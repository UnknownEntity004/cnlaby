import java.io.*;
import java.net.*;

public class UDPFileTransfer {
    public static void main(String[] args) {
        // Sender and receiver port numbers
        int senderPort = 5501;
        int receiverPort = 5501;

        // Sender and receiver host names
        String senderHost = "localhost";
        String receiverHost = "localhost";

        // Create a DatagramSocket for sender and receiver
        try (DatagramSocket senderSocket = new DatagramSocket();
             DatagramSocket receiverSocket = new DatagramSocket(receiverPort)) {

            // Sender
            new Thread(() -> {
                try {
                    // Read files and send them
                    sendFile("script_received.txt", senderHost, senderPort, senderSocket);
                    sendFile("19_AssignmentNo.10_CN.txt", senderHost, senderPort, senderSocket);
                    sendFile("audio_cn.mp3", senderHost, senderPort, senderSocket);
                    sendFile("Video_cn.mp4", senderHost, senderPort, senderSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Receiver
            new Thread(() -> {
                try {
                    // Receive files
                    receiveFile("Script.txt", receiverSocket);
                    receiveFile("19_AssignmentNo.10_CN.txt", receiverSocket);
                    receiveFile("audio_cn.mp3", receiverSocket);
                    receiveFile("Video_cn.mp4", receiverSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to send file
    private static void sendFile(String filename, String host, int port, DatagramSocket socket) throws IOException {
        File file = new File(filename);
        byte[] buffer = new byte[1024];
        DatagramPacket packet;
        FileInputStream fis = new FileInputStream(file);

        // Send file content
        while (fis.read(buffer) != -1) {
            packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(host), port);
            socket.send(packet);
        }

        // Notify end of file transfer
        packet = new DatagramPacket(new byte[0], 0, InetAddress.getByName(host), port);
        socket.send(packet);

        fis.close();
    }

    // Method to receive file
    private static void receiveFile(String filename, DatagramSocket socket) throws IOException {
        File file = new File(filename);
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        DatagramPacket packet;

        // Receive file content
        while (true) {
            packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            if (packet.getLength() == 0) break; // End of file transfer
            fos.write(buffer, 0, packet.getLength());
        }

        fos.close();
    }
}
