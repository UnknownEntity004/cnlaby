import java.util.Scanner;

public class HammingCodeM {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the ASCII code (7/8 bits) as a binary string: ");
        String input = scanner.nextLine();

        if (!input.matches("[01]{7,8}")) {
            System.out.println("Invalid input! Please enter a 7 or 8 bits binary string.");
            return;
        }

        String hammingCode = generateHammingCode(input);
        System.out.println("Original Data: " + input);
        System.out.println("Hamming Code: " + hammingCode);

        System.out.print("Enter the received Hamming code: ");
        String receivedCode = scanner.nextLine();

        String correctedCode = correctHammingCode(receivedCode);
        System.out.println("Corrected Code: " + correctedCode);

        scanner.close();
    }

    // Generate Hamming code for the given input
    private static String generateHammingCode(String input) {
        int[] dataBits = new int[input.length()];
        for (int i = 0; i < input.length(); i++) {
            dataBits[i] = input.charAt(i) - '0';
        }

        int r = calculateRedundantBits(dataBits.length);
        int[] hammingCode = new int[dataBits.length + r];

        // Place data bits and initialize parity bits to 0
        int j = 0;
        for (int i = 0; i < hammingCode.length; i++) {
            if (isPowerOfTwo(i + 1)) {
                hammingCode[i] = 0; // Placeholder for redundant bit
            } else {
                hammingCode[i] = dataBits[j++];
            }
        }

        // Calculate parity bits
        for (int i = 0; i < r; i++) {
            int k = (int) Math.pow(2, i);
            hammingCode[k - 1] = calculateParityBit(hammingCode, k);
        }

        StringBuilder result = new StringBuilder();
        for (int bit : hammingCode) {
            result.append(bit);
        }
        return result.toString();
    }

    // Calculate the number of redundant bits needed
    private static int calculateRedundantBits(int dataBitsLength) {
        int r = 0;
        while (Math.pow(2, r) < dataBitsLength + r + 1) {
            r++;
        }
        return r;
    }

    // Check if a number is a power of two
    private static boolean isPowerOfTwo(int n) {
        return (n & (n - 1)) == 0;
    }

    // Calculate the parity bit for the given position
    private static int calculateParityBit(int[] hammingCode, int position) {
        int parity = 0;
        for (int i = position; i <= hammingCode.length; i++) {
            if ((i & position) == position) {
                parity ^= hammingCode[i - 1];
            }
        }
        return parity;
    }

    // Correct the Hamming code and return the corrected code
    private static String correctHammingCode(String receivedCode) {
        int[] hammingCode = new int[receivedCode.length()];
        for (int i = 0; i < receivedCode.length(); i++) {
            hammingCode[i] = receivedCode.charAt(i) - '0';
        }

        int r = calculateRedundantBits(hammingCode.length - Integer.bitCount(hammingCode.length));
        int errorPosition = 0;

        for (int i = 0; i < r; i++) {
            int k = (int) Math.pow(2, i);
            int parity = calculateParityBit(hammingCode, k);
            if (parity != 0) {
                errorPosition += k;
            }
        }

        if (errorPosition != 0) {
            hammingCode[errorPosition - 1] ^= 1;
            System.out.println("Error detected and corrected at position: " + errorPosition);
        } else {
            System.out.println("No error detected.");
        }

        StringBuilder result = new StringBuilder();
        for (int bit : hammingCode) {
            result.append(bit);
        }
        return result.toString();
    }
}