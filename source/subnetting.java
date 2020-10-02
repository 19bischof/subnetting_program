import asg.cliche.Command;
import asg.cliche.ShellFactory;

import java.io.IOException;
import java.util.Scanner;


public class subnetting {
    private int first_oct = -1;
    private int second_oct = -1;
    private int third_oct = -1;
    private int fourth_oct = -1;
    private int slash_mask = -1;
    private int[] binary_ip = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,};
    private int[] binary_mask = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,};
    private String decimal_ip = "Error: IP-Address wasn't specified !";
    private String decimal_mask = "Error: Subnet wasn't specified !";
    private String decimal_network_address = "Didn't calculate Ranges yet !";
    private String decimal_broadcast_address = "Didn't calculate Ranges yet !";

    private int[] decimal_Byte_To_Binary(int oct) {

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

        }


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
                oct += (int) Math.pow(2.0, (double) 7 - i);

            }
        }
        return oct;
    }

    private boolean is_Not_Valid_Oct(int oct) {
        if (oct < 256 && oct > -1) {
            return false;
        }
        System.out.println("The given Octet is incorrect : " + oct + "\nPlease try again");

        return true;
    }

    @Command(name = "scan_Subnet_Mask_In_Slash", abbrev = "1")//one
    public void scan_Subnet_Mask_In_Slash() {
        Scanner myscan = new Scanner(System.in);
        String input = myscan.next();
        input = input.replace("/", "");
        if (!input.matches("\\d+")) {
            System.out.println("Error: wrong slash_mask: " + input);
            return;
        }
        slash_mask = Integer.parseInt(input);
        if (slash_mask < 0 || slash_mask > 30) {
            System.out.println("Error: wrong slash_mask: " + slash_mask);
            return;
        }
        for (int i = 0; i < 32; i++) {
            if (i < slash_mask) {
                binary_mask[i] = 1;
            } else {
                binary_mask[i] = 0;
            }
        }

        decimal_mask = convert_Binary_32_Bit_To_Decimal(binary_mask);


    }

    @Command(name = "scan_Decimal_IP", abbrev = "2") //two
    public void scan_Decimal_IP() {
        Scanner myscan = new Scanner(System.in);
        do {
            if (myscan.hasNextInt()) {
                first_oct = myscan.nextInt();
            } else {
                System.out.println("Please only input one Octet at a time !");
                myscan.nextLine();
            }

        } while (is_Not_Valid_Oct(first_oct));

        do {
            second_oct = myscan.nextInt();
        } while (is_Not_Valid_Oct(second_oct));

        do {
            third_oct = myscan.nextInt();
        } while (is_Not_Valid_Oct(third_oct));

        do {
            fourth_oct = myscan.nextInt();
        } while (is_Not_Valid_Oct(fourth_oct));
        decimal_ip = (first_oct + "." + second_oct + "." + third_oct + "." + fourth_oct);
        copy_Decimal_IP_to_Binary_IP();
        print_Decimal_IP();
    }

    @Command(name = "print_Decimal_IP", abbrev = "3") //three
    public void print_Decimal_IP() {
        System.out.println(decimal_ip);

    }


    private void copy_Decimal_IP_to_Binary_IP() {
        System.arraycopy(decimal_Byte_To_Binary(first_oct), 0, binary_ip, 0, 8);
        System.arraycopy(decimal_Byte_To_Binary(second_oct), 0, binary_ip, 8, 8);
        System.arraycopy(decimal_Byte_To_Binary(third_oct), 0, binary_ip, 16, 8);
        System.arraycopy(decimal_Byte_To_Binary(fourth_oct), 0, binary_ip, 24, 8);
    }

    @Command(name = "print_Binary_IP", abbrev = "4") //four
    public void print_Binary_IP() {
        if (binary_ip[0] == -1) {
            System.out.println("Error: IP-Address wasn't specified !");
            return;
        }
        for (int i = 0; i < 32; i++) {
            System.out.print(binary_ip[i]);
        }
        System.out.println();
    }

    @Command(name = "print_Binary_Mask", abbrev = "5") //five
    public void print_Binary_Mask() {
        if (binary_mask[0] == -1) {
            System.out.println("Error: Subnet wasn't specified !");
            return;
        }
        for (int i = 0; i < 32; i++) {
            System.out.print(binary_mask[i]);
        }
        System.out.println();
    }

    @Command(name = "print_Decimal_Mask", abbrev = "6") //six
    public void print_Decimal_Mask() {
        System.out.println(decimal_mask);

    }

    private String convert_Binary_32_Bit_To_Decimal(int[] origin) {
        String destination = "";
        int[] cur_bin = new int[8];
        int cur_int;

        System.arraycopy(origin, 0, cur_bin, 0, 8);
        cur_int = binary_Byte_To_Decimal(cur_bin);
        destination += (cur_int) + ".";
        System.arraycopy(origin, 8, cur_bin, 0, 8);
        cur_int = binary_Byte_To_Decimal(cur_bin);
        destination += (cur_int) + ".";
        System.arraycopy(origin, 16, cur_bin, 0, 8);
        cur_int = binary_Byte_To_Decimal(cur_bin);
        destination += (cur_int) + ".";
        System.arraycopy(origin, 24, cur_bin, 0, 8);
        cur_int = binary_Byte_To_Decimal(cur_bin);
        destination += (cur_int);
        return destination;
    }

    @Command(name = "show_Menu", abbrev = "7")//seven
    public void show_Menu() {

        System.out.println("What do you want to do ?");
        System.out.println("1:\tscan_Subnet_Mask_In_Slash()");
        System.out.println("2:\tscan_Decimal_IP()");
        System.out.println("3:\tprint_Decimal_IP()");
        System.out.println("4:\tprint_Binary_IP()");
        System.out.println("5:\tprint_Binary_Mask()");
        System.out.println("6:\tprint_Decimal_Mask()");
        System.out.println("7:\tshow_Menu()");
        System.out.println("8:\tprint_Number_Of_Possible_Hosts()");
        System.out.println("9:\tcalculate_Ranges()");
        System.out.println("10:\tprint_Network_Address()");
        System.out.println("11:\tprint_Broadcast_Address()");


    }

    @Command(name = "print_Number_Of_Possible_Hosts", abbrev = "8")//eight
    public void print_Number_Of_Possible_Hosts() {
        if (slash_mask != -1) {
            System.out.printf("%.0f\n", (Math.pow(2.0, (double) 32 - slash_mask) - 2));
        } else {
            System.out.println("Error: Subnet wasn't specified !");
        }

    }

    @Command(name = "calculate_Ranges", abbrev = "9") //nine
    public void calculate_Ranges() {
        if (slash_mask == -1) {
            System.out.println("Error: Subnet wasn't specified !");

            return;
        }
        if (decimal_ip.equals("no decimal IP")) {
            System.out.println("Error: IP-Address wasn't specified !");

            return;
        }
        //network address
        int[] poly_address = binary_ip;
        for (int i = slash_mask; i < 32; i++) {
            poly_address[i] = 0;
        }
        decimal_network_address = convert_Binary_32_Bit_To_Decimal(poly_address);
        //broadcast address
        poly_address = binary_ip;
        for (int i = slash_mask; i < 32; i++) {
            poly_address[i] = 1;
        }
        decimal_broadcast_address = convert_Binary_32_Bit_To_Decimal(poly_address);
    }

    @Command(name = "print_Network_Address", abbrev = "10")//ten
    public void print_Network_Address() {
        System.out.println(decimal_network_address);

    }

    @Command(name = "print_Broadcast_Address", abbrev = "11") //eleven
    public void print_Broadcast_Address() {
        System.out.println(decimal_broadcast_address);

    }


    public static void main(String[] args) throws IOException {
        ShellFactory.createConsoleShell("7", "", new subnetting())
                .commandLoop();


    }
}