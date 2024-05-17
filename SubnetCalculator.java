import java.util.Scanner;

public class SubnetCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the IP address in CIDR notation (e.g., 192.168.1.0/24):");
        String cidrInput = scanner.nextLine();

        String[] parts = cidrInput.split("/");
        String ipAddress = parts[0];
        int subnetMaskLength = Integer.parseInt(parts[1]);

        String[] ipAddressParts = ipAddress.split("\\.");
        int[] ipParts = new int[4];
        for (int i = 0; i < 4; i++) {
            ipParts[i] = Integer.parseInt(ipAddressParts[i]);
        }

        // Calculate subnet mask
        int[] subnetMask = calculateSubnetMask(subnetMaskLength);

        // Print subnet mask
        System.out.print("Subnet mask: ");
        for (int i = 0; i < 4; i++) {
            System.out.print(subnetMask[i]);
            if (i < 3) {
                System.out.print(".");
            }
        }
        System.out.println();
    }

    public static int[] calculateSubnetMask(int subnetMaskLength) {
        int[] subnetMask = new int[4];
        for (int i = 0; i < 4; i++) {
            int fullOctets = subnetMaskLength / 8;
            int remainderBits = subnetMaskLength % 8;
            if (remainderBits == 0) {
                subnetMask[i] = (i < fullOctets) ? 255 : 0;
            } else {
                if (i < fullOctets) {
                    subnetMask[i] = 255;
                } else {
                    int mask = 0;
                    for (int j = 0; j < remainderBits; j++) {
                        mask |= (1 << (7 - j));
                    }
                    subnetMask[i] = mask;
                }
            }
        }
        return subnetMask;
    }
}
