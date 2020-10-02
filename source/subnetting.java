import asg.cliche.Command;
import asg.cliche.ShellFactory;

import java.io.IOException;
import java.util.Scanner;


public class subnetting {
    private int first_oct;
    private int second_oct;
    private int third_oct;
    private int fourth_oct;
    private int[] binary_ip = new int[32];
    private int[] binary_mask = new int[32];
    private String decimal_ip = "no decimal IP";
    private String decimal_mask = "no decimal Subnet Mask";

    private int[] decimal_Byte_To_Binary(int oct) {
        System.out.println();
        if (oct < 0 || oct > 255) {
            System.out.println("Error: oct wrong : " + oct);
            return new int[0];
        }
        int[] bioct = new int[8];
        for (int i = 0; i < 8; i++) {
            if (oct % 2 == 1) {
                bioct[7 - i] = 1;
                oct--;
            } else {
                bioct[7 - i] = 0;
            }
            oct = oct / 2;
            System.out.println(oct);
        }
        System.out.println("at the end:");
        for (int i = 0; i < 8; i++) {
            System.out.print(bioct[i]);
        }
        System.out.println();
        return bioct;
    }

    private int binary_Byte_To_Decimal(int[] binary) {
        int oct = 0;
        if (binary.length != 8) {
            System.out.println("Error: binary array not 8 bits");
            return -1;
        }

        for (int i = 0; i < 8; i++) {
            if (binary[i] == 1) {
                oct += (int) Math.pow((double) 2, (double) 7 - i);

            }
        }
        return oct;
    }

    private boolean is_not_Valid_oct(int oct) {
        if (oct < 256 && oct > -1) {
            return false;
        }
        System.out.println("The given octett was false : " + oct + "\nPlease try again");
        return true;
    }

    @Command //one
    public void scan_Subnet_Mask_In_Slash() {
        int mask;
        Scanner myscan = new Scanner(System.in);
        String input = myscan.next();
        input = input.replace("/", "");
        System.out.println(input);
        mask = Integer.parseInt(input);
        if (mask < 0 || mask > 32) {
            System.out.println("Error: wrong mask : " + mask);
            myscan.close();

            return;
        }
        for (int i = 0; i < mask; i++) {
            binary_mask[i] = 1;
        }
        System.out.println("the mask is : \n");
        convert_Binary_Mask_To_Decimal();
        System.out.println();
        myscan.close();

    }

    @Command //two
    public void scan_Decimal_IP() {
        Scanner myscan = new Scanner(System.in);
        do {
            first_oct = myscan.nextInt();
        } while (is_not_Valid_oct(first_oct));

        do {
            second_oct = myscan.nextInt();
        } while (is_not_Valid_oct(second_oct));

        do {
            third_oct = myscan.nextInt();
        } while (is_not_Valid_oct(third_oct));

        do {
            fourth_oct = myscan.nextInt();
        } while (is_not_Valid_oct(fourth_oct));
        decimal_ip = (first_oct + "." + second_oct + "." + third_oct + "." + fourth_oct);
        copy_Decimal_IP_to_Binary_IP();
        print_Decimal_IP();
        myscan.close();
    }

    @Command //three
    public void print_Decimal_IP() {
        System.out.println(decimal_ip);
    }

    @Command //four
    public void copy_Decimal_IP_to_Binary_IP() {
        System.arraycopy(decimal_Byte_To_Binary(first_oct), 0, binary_ip, 0, 8);
        System.arraycopy(decimal_Byte_To_Binary(second_oct), 0, binary_ip, 8, 8);
        System.arraycopy(decimal_Byte_To_Binary(third_oct), 0, binary_ip, 16, 8);
        System.arraycopy(decimal_Byte_To_Binary(fourth_oct), 0, binary_ip, 24, 8);
    }

    @Command //five
    public void print_Binary_IP() {
        for (int i = 0; i < 32; i++) {
            System.out.print(binary_ip[i]);
        }
    }

    @Command //six
    public void print_Binary_Mask() {
        for (int i = 0; i < 32; i++) {
            System.out.print(binary_mask[i]);
        }
    }

    @Command //seven
    public void print_Decimal_Mask() {
        System.out.println(decimal_mask);
    }

    private void convert_Binary_Mask_To_Decimal() {
        int[] cur_bin = new int[8];
        int cur_int = 0;
        decimal_mask = "";
        System.arraycopy(binary_mask, 0, cur_bin, 0, 8);
        for (int i = 0; i < 8; i++) {
            System.out.print(cur_bin[i]);
        }
        cur_int = binary_Byte_To_Decimal(cur_bin);
        System.out.println(cur_int);
        decimal_mask += Integer.toString(cur_int) + ".";
        System.arraycopy(binary_mask, 8, cur_bin, 0, 8);
        cur_int = binary_Byte_To_Decimal(cur_bin);
        decimal_mask += Integer.toString(cur_int) + ".";
        System.arraycopy(binary_mask, 16, cur_bin, 0, 8);
        cur_int = binary_Byte_To_Decimal(cur_bin);
        decimal_mask += Integer.toString(cur_int) + ".";
        System.arraycopy(binary_mask, 24, cur_bin, 0, 8);
        cur_int = binary_Byte_To_Decimal(cur_bin);
        decimal_mask += Integer.toString(cur_int);
    }

    @Command //eight
    public void show_Menu() {

        System.out.println("What do you want to do ?");
        System.out.println("\tscan_Subnet_Mask_In_Slash()");
        System.out.println("\tscan_Decimal_IP()");
        System.out.println("\tprint_Decimal_IP()");
        System.out.println("\tprint_Binary_IP()");
        System.out.println("\tprint_Binary_Mask()");
        System.out.println("\tprint_Decimal_Mask()");

    }

    public static void main(String[] args) throws IOException {
        ShellFactory.createConsoleShell("start", "", new subnetting())
                .commandLoop();


    }
}