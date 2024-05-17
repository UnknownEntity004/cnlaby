import java.util.Scanner;

public class IP {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter IP Address (xxx.xxx.xxx.xxx format): ");
        String ip = scanner.nextLine();

        // Splitting the IP address into octets
        String[] octets = ip.split("\\.");

        if (octets.length != 4) {
            System.out.println("Invalid IP Address format");
            return;
        }

        try {
            // Parsing octets to integers
            int firstOctet = Integer.parseInt(octets[0]);

            // Finding IP class
            char ipClass;
            if (firstOctet >= 1 && firstOctet <= 126) {
                ipClass = 'A';
            } else if (firstOctet >= 128 && firstOctet <= 191) {
                ipClass = 'B';
            } else if (firstOctet >= 192 && firstOctet <= 223) {
                ipClass = 'C';
            } else if (firstOctet >= 224 && firstOctet <= 239) {
                ipClass = 'D';
            } else if (firstOctet >= 240 && firstOctet <= 255) {
                ipClass = 'E';
            } else {
                System.out.println("Invalid IP Address range");
                return;
            }

            // Checking if IP is private or public
            boolean isPrivate = false;
            if (firstOctet == 10 || (firstOctet == 172 && Integer.parseInt(octets[1]) >= 16 && Integer.parseInt(octets[1]) <= 31) || (firstOctet == 192 && Integer.parseInt(octets[1]) == 168)) {
                isPrivate = true;
            }

            // Displaying the results
            System.out.println("IP Class: " + ipClass);
            System.out.println("Type: " + (isPrivate ? "Private" : "Public"));
        } catch (NumberFormatException e) {
            System.out.println("Invalid IP Address format");
        }
    }
}
