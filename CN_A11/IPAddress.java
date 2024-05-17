public class IPAddress {

    public static void main(String[] args) {
        String ipAddress = "192.168.1.1"; // Example IP address

        if (isValidIPAddress(ipAddress)) {
            String ipClass = getClassOfIPAddress(ipAddress);
            String ipType = getTypeOfIPAddress(ipAddress);

            System.out.println("IP Address: " + ipAddress);
            System.out.println("Class: " + ipClass);
            System.out.println("Type: " + ipType);
        } else {
            System.out.println("Invalid IP address");
        }
    }

    // Validate the IP address by manually checking each part
    private static boolean isValidIPAddress(String ipAddress) {
        String[] parts = ipAddress.split("\\.");
        if (parts.length != 4) {
            return false;
        }

        for (String part : parts) {
            try {
                int value = Integer.parseInt(part);
                if (value < 0 || value > 255) {
                    return false;
                }
            } catch (NumberFormatException ex) {
                return false;
            }
        }

        return true;
    }

    // Determine the class of the IP address
    private static String getClassOfIPAddress(String ipAddress) {
        int firstOctet = Integer.parseInt(ipAddress.split("\\.")[0]);

        if (firstOctet >= 1 && firstOctet <= 126) {
            return "A";
        } else if (firstOctet >= 128 && firstOctet <= 191) {
            return "B";
        } else if (firstOctet >= 192 && firstOctet <= 223) {
            return "C";
        } else if (firstOctet >= 224 && firstOctet <= 239) {
            return "D";
        } else if (firstOctet >= 240 && firstOctet <= 255) {
            return "E";
        } else {
            return "Unknown";
        }
    }

    // Determine the type of the IP address (public or private)
    private static String getTypeOfIPAddress(String ipAddress) {
        int firstOctet = Integer.parseInt(ipAddress.split("\\.")[0]);
        int secondOctet = Integer.parseInt(ipAddress.split("\\.")[1]);

        // Private IP address ranges
        if ((firstOctet == 10) ||
            (firstOctet == 172 && (secondOctet >= 16 && secondOctet <= 31)) ||
            (firstOctet == 192 && secondOctet == 168)) {
            return "Private";
        } else {
            return "Public";
        }
    }
}