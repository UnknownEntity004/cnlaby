import java.util.Scanner;

public class CRCErrorDetection {

    // CRC-8 polynomial: x^8 + x^2 + x + 1 (binary representation)
    private static final String POLYNOMIAL = "100000111";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the ASCII code (7/8 bits) as a binary string: ");
        String input = scanner.nextLine();

        if (!input.matches("[01]{7,8}")) {
            System.out.println("Invalid input! Please enter a 7 or 8 bits binary string.");
            return;
        }

        // Compute the CRC code
        String crc = computeCRC(input, POLYNOMIAL);
        System.out.println("Original Data: " + input);
        System.out.println("CRC Code: " + crc);

        // Form the transmitted message
        String transmittedMessage = input + crc;
        System.out.println("Transmitted Message: " + transmittedMessage);

        // Simulate receiving the message
        System.out.print("Enter the received message: ");
        String receivedMessage = scanner.nextLine();

        if (!receivedMessage.matches("[01]{15,16}")) {
            System.out.println("Invalid received message! Please enter a 15 or 16 bits binary string.");
            return;
        }

        // Check the received message for errors
        if (isValidMessage(receivedMessage, POLYNOMIAL)) {
            System.out.println("No error detected in the received message.");
        } else {
            System.out.println("Error detected in the received message.");
        }

        scanner.close();
    }

    private static String computeCRC(String input, String polynomial) {
        StringBuilder paddedInput = new StringBuilder(input);
        for (int i = 0; i < polynomial.length() - 1; i++) {
            paddedInput.append('0');
        }
        String dividend = paddedInput.toString();

        for (int i = 0; i <= dividend.length() - polynomial.length(); i++) {
            if (dividend.charAt(i) == '1') {
                for (int j = 0; j < polynomial.length(); j++) {
                    char bit = dividend.charAt(i + j);
                    char polyBit = polynomial.charAt(j);
                    char resultBit = (bit == polyBit) ? '0' : '1';
                    dividend = dividend.substring(0, i + j) + resultBit + dividend.substring(i + j + 1);
                }
            }
        }

        return dividend.substring(dividend.length() - (polynomial.length() - 1));
    }

    private static boolean isValidMessage(String receivedMessage, String polynomial) {
        for (int i = 0; i <= receivedMessage.length() - polynomial.length(); i++) {
            if (receivedMessage.charAt(i) == '1') {
                for (int j = 0; j < polynomial.length(); j++) {
                    char bit = receivedMessage.charAt(i + j);
                    char polyBit = polynomial.charAt(j);
                    char resultBit = (bit == polyBit) ? '0' : '1';
                    receivedMessage = receivedMessage.substring(0, i + j) + resultBit + receivedMessage.substring(i + j + 1);
                }
            }
        }

        String remainder = receivedMessage.substring(receivedMessage.length() - (polynomial.length() - 1));
        return remainder.equals("00000000".substring(0, polynomial.length() - 1));
    }
}