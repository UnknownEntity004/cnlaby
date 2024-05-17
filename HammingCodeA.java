import java.util.Scanner;

public class HammingCodeA {

    // Method to calculate the hamming code
    public static int[] calculateHammingCode(int[] data) {
        int r = 0; // Number of parity bits
        while (Math.pow(2, r) < data.length + r + 1) {
            r++;
        }

        int[] hammingCode = new int[data.length + r];
        int j = 0; // Index for hammingCode array
        int k = 0; // Index for data array

        // Initialize hammingCode with -1 (unset)
        for (int i = 0; i < hammingCode.length; i++) {
            if (i == Math.pow(2, j) - 1) {
                j++;
            } else {
                hammingCode[i] = data[k];
                k++;
            }
        }

        // Calculate parity bits
        for (int i = 0; i < r; i++) {
            int parityIndex = (int) Math.pow(2, i) - 1;
            int count = 0;
            for (int m = parityIndex; m < hammingCode.length; m += (parityIndex + 1) * 2) {
                for (int n = m; n < Math.min(hammingCode.length, m + (parityIndex + 1)); n++) {
                    if (hammingCode[n] == 1) {
                        count++;
                    }
                }
            }
            hammingCode[parityIndex] = count % 2;
        }

        return hammingCode;
    }

    // Method to introduce an error in the hamming code
    public static int[] introduceError(int[] hammingCode) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the position to introduce error (0-indexed): ");
        int errorPosition = scanner.nextInt();
        hammingCode[errorPosition] = hammingCode[errorPosition] == 0 ? 1 : 0;
        return hammingCode;
    }

    // Method to correct the error in the hamming code
    public static int[] correctError(int[] hammingCode) {
        int r = 0;
        while (Math.pow(2, r) < hammingCode.length) {
            r++;
        }
        int errorPosition = 0;
        for (int i = 0; i < r; i++) {
            int parityIndex = (int) Math.pow(2, i) - 1;
            int count = 0;
            for (int m = parityIndex; m < hammingCode.length; m += (parityIndex + 1) * 2) {
                for (int n = m; n < Math.min(hammingCode.length, m + (parityIndex + 1)); n++) {
                    if (hammingCode[n] == 1) {
                        count++;
                    }
                }
            }
            if (count % 2 != 0) {
                errorPosition += parityIndex + 1;
            }
        }
        if (errorPosition != 0) {
            hammingCode[errorPosition - 1] = hammingCode[errorPosition - 1] == 0 ? 1 : 0;
        }
        return hammingCode;
    }

    // Method to display the hamming code
    public static void displayHammingCode(int[] hammingCode) {
        System.out.print("Hamming Code: ");
        for (int i : hammingCode) {
            System.out.print(i);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int choice=0;
        while(choice!=4){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the 7-bit ASCII code: ");
        String input = scanner.nextLine();
        
        if (input.length() != 7) {
            System.out.println("Invalid input! Input should be a 7-bit ASCII code.");
            return;
        }
        
        int[] data = new int[7];
        for (int i = 0; i < 7; i++) {
            data[i] = Character.getNumericValue(input.charAt(i));
        }

        int[] hammingCode = calculateHammingCode(data);
        
        while(choice!=3)
        {System.out.println("1. Introduce Error");
        System.out.println("2. Correct Error");
        System.out.println("3. For exit ");
        System.out.print("Enter your choice: ");
         choice = scanner.nextInt();

        switch (choice) {
            case 1:
                hammingCode = introduceError(hammingCode);
                displayHammingCode(hammingCode);
                break;
            case 2:
                hammingCode = correctError(hammingCode);
                displayHammingCode(hammingCode);
                break;
            case 3:
                System.out.print("Exit\n");
                break;
            case 4:
                System.out.print("Enter the 7-bit ASCII code: ");
                break;
            default:
                System.out.println("Invalid choice");}}
        }
    }
}
