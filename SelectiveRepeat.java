import java.util.*;

class Sender {
    private int windowSize;
    private List<Packet> packets;
    private int base;
    private int nextSeqNum;

    public Sender(int windowSize) {
        this.windowSize = windowSize;
        this.packets = new ArrayList<>();
        this.base = 0;
        this.nextSeqNum = 0;
    }

    public void sendData() {
        while (base < packets.size()) {
            for (int i = base; i < Math.min(base + windowSize, packets.size()); i++) {
                // Simulate sending packet
                System.out.println("Sending packet with sequence number: " + packets.get(i).getSequenceNumber());
            }

            // Simulate waiting for ACKs
            try {
                Thread.sleep(2000); // Simulate network delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Update base based on ACKs received
            base = Math.max(base, nextSeqNum);
        }
    }

    public void addPacket(String data) {
        packets.add(new Packet(nextSeqNum, data));
        nextSeqNum++;
    }

    public List<Packet> getPackets() {
        return packets;
    }
}


public class SelectiveRepeat {
    public static void main(String[] args) {
        Sender sender = new Sender(3); // Window size of 3
        Receiver receiver = new Receiver(3); // Window size of 3

        // Simulate data transmission
        sender.addPacket("Packet 1");
        sender.addPacket("Packet 2");
        sender.addPacket("Packet 3");
        sender.addPacket("Packet 4");
        sender.addPacket("Packet 5");

        // Simulate sending data from sender to receiver
        sender.sendData();

        // Simulate receiving data at receiver
        for (Packet packet : sender.getPackets()) {
            receiver.receiveData(packet);
        }
    }
}
