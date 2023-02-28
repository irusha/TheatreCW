import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Theatre {
    static int[] row1;
    static int[] row2;
    static int[] row3;

    static boolean BOOKING_MODE = true;
    static boolean CANCELLING_MODE = false;

    public static void main(String[] args) {
        System.out.println("Welcome to the New Theatre");
        row1 = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        row2 = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        row3 = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};


        boolean flag = true;
        while (flag) {
            System.out.println("""
                    -------------------------------------------------
                    Please select an option:\s
                    1) Buy a ticket\s
                    2) Print seating area\s
                    3) Cancel ticket\s
                    4) List available seats\s
                    5) Save to file\s
                    6) Load from file\s
                    7) Print ticket information and total price\s
                    8) Sort tickets by price\s
                     0) Quit\s
                    -------------------------------------------------
                    """);
            switch (getPositiveInt("Enter option:", true)) {
                case 1:
                    buy_ticket();
                    break;
                case 0:
                    flag = false;
                    break;
                case 2:
                    print_seating_area();
                    break;
                case 3:
                    cancel_ticket();
                    break;
                case 4:
                    available_seats();
                    break;
                case 5:
                    save();
                    break;
                case 6:
                    load();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void print_seating_area() {
        System.out.println("""
                     ***********\s
                     *  STAGE  *\s
                     ***********\s
                """);

        System.out.print("    ");
        for (int i = 0; i < row1.length; i++) {
            if (i == row1.length / 2) {
                System.out.print(" ");
            }
            printSeat(row1[i]);
        }
        System.out.println();
        System.out.print("  ");
        for (int i = 0; i < row2.length; i++) {
            if (i == row2.length / 2) {
                System.out.print(" ");
            }
            printSeat(row2[i]);
        }
        System.out.println();
        for (int i = 0; i < row3.length; i++) {
            if (i == row3.length / 2) {
                System.out.print(" ");
            }
            printSeat(row3[i]);
        }
        System.out.println();
    }

    static void printSeat(int seatVal) {
        if (seatVal == 0) {
            System.out.print("O");
        } else {
            System.out.print("X");
        }
    }

    static int getPositiveInt(String message, String tryAgainMessage) {
        System.out.println(message);
        Scanner sc = new Scanner(System.in);

        String strInt;

        do {
            strInt = sc.nextLine();
            try {
                int parsedInt = Integer.parseInt(strInt);
                if (parsedInt > 0) {
                    return parsedInt;
                } else {
                    System.out.println(tryAgainMessage);
                }
            } catch (Exception e) {
                System.out.println(tryAgainMessage);
            }
        }

        while (true);
    }

    static void available_seats() {
        System.out.print("Seats available in row 1: ");

        ArrayList<Integer> tempArray = new ArrayList<>();
        for (int i = 0; i < row1.length; i++) {
            if (row1[i] == 0) {
                tempArray.add(i + 1);
            }
        }
        System.out.println(arrayToString(tempArray) + ".");

        tempArray = new ArrayList<>();
        System.out.print("Seats available in row 2: ");
        for (int i = 0; i < row2.length; i++) {
            if (row2[i] == 0) {
                tempArray.add(i + 1);
            }
        }
        System.out.println(arrayToString(tempArray) + ".");

        tempArray = new ArrayList<>();
        System.out.print("Seats available in row 3: ");
        for (int i = 0; i < row3.length; i++) {
            if (row3[i] == 0) {
                tempArray.add(i + 1);
            }
        }
        System.out.println(arrayToString(tempArray) + ".");
    }

    static String arrayToString(ArrayList<Integer> list) {
        StringBuilder retStr = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            retStr.append(list.get(i));
            if (i != list.size() - 1) {
                retStr.append(", ");
            }
        }
        return retStr.toString();
    }

    static String arrayToString(int[] list) {
        StringBuilder retStr = new StringBuilder();
        for (int i = 0; i < list.length; i++) {
            retStr.append(list[i]);
            if (i != list.length - 1) {
                retStr.append(", ");
            }
        }
        return retStr.toString();
    }

    static void cancel_ticket() {
        int seatRow = getSeatRow();
        int seat = getSeat(seatRow, CANCELLING_MODE);
        int[] rowArray;
        if (seat != -1) {
            switch (seatRow) {
                case 1:
                    rowArray = row1;
                    break;
                case 2:
                    rowArray = row2;
                    break;
                case 3:
                    rowArray = row3;
                    break;
                default:
                    rowArray = new int[0];
                    break;
            }
            rowArray[seat - 1] = 0;
            System.out.printf("Cancelled ticket %s from row %s\n", seat, seatRow);
        }


    }

    static void save() {
        File file = new File("src\\data.txt");

        try {
            if (file.createNewFile()) {
                System.out.println("File created");
            }

            else {
                System.out.println("File overwritten");
            }
        } catch (IOException e) {
            System.out.println("Unable to create a file");
        }

        try{
            FileWriter writer = new FileWriter(file);
            writer.write(String.format("Row 1 - %s\n", arrayToString(row1)));
            writer.write(String.format("Row 2 - %s\n", arrayToString(row2)));
            writer.write(String.format("Row 3 - %s\n", arrayToString(row3)));
            writer.close();
            System.out.println("Successfully wrote data to the file");

        }
        catch (IOException e) {
            System.out.println("Created file isn't available.");
            throw new RuntimeException(e);
        }

    }

    static int[] strArrayToIntArray(String[] array) {
        int[] tempArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            tempArray[i] = Integer.parseInt(array[i]);
        }
        return tempArray;
    }

    static boolean rowLengthCheck(int[] array,  int row) {
        switch (row) {
            case 1:
                return array.length == 12;
            case 2:
                return array.length == 16;
            case 3:
                return array.length == 20;
            default:
                return false;
        }
    }
    static void load() {
        File arrayFile = new File("src/data.txt");

        try {
            Scanner reader = new Scanner(arrayFile);

            String row1DataStr = reader.nextLine().split(" - ")[1];
            int[] rowTemp = strArrayToIntArray(row1DataStr.split(", "));

            if (rowLengthCheck(rowTemp, 1)) {
                row1 = rowTemp;
            }
            else {
                throw new FileNotFoundException("Invalid File");
            }

            String row2DataStr = reader.nextLine().split(" - ")[1];
            rowTemp = strArrayToIntArray(row2DataStr.split(", "));

            if (rowLengthCheck(rowTemp, 2)) {
                row2 = rowTemp;
            }
            else {
                throw new FileNotFoundException("Invalid File");
            }

            String row3DataStr = reader.nextLine().split(" - ")[1];
            rowTemp = strArrayToIntArray(row3DataStr.split(", "));

            if (rowLengthCheck(rowTemp, 3)) {
                row3 = rowTemp;
            }
            else {
                throw new FileNotFoundException("Invalid File");
            }
            System.out.println("Successfully loaded the file");
            reader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    static int getPositiveInt(String message, boolean includesZero) {
        Scanner sc = new Scanner(System.in);

        String strInt;

        do {
            System.out.println(message);
            strInt = sc.nextLine();
            try {
                int parsedInt = Integer.parseInt(strInt);
                if (includesZero) {
                    if (parsedInt >= 0) {
                        return parsedInt;
                    } else {
                        System.out.println();
                    }
                } else {
                    if (parsedInt > 0) {
                        return parsedInt;
                    } else {
                        System.out.println();
                    }
                }
            } catch (Exception e) {
                System.out.println();
            }
        }

        while (true);
    }

    static int getSeatRow() {

        while (true) {
            int tempSeat = getPositiveInt("Please Enter the seat row", "Please enter a positive integer");

            switch (tempSeat) {
                case 1:
                case 2:
                case 3:
                    return tempSeat;
                default:
                    System.out.println("Invalid seat row. Please try again.");
                    break;
            }
        }

    }

    static int getSeat(int selectedSeatRow, boolean mode) {

        while (true) {
            int tempSeat = getPositiveInt("Please Enter the seat", "Please enter a positive integer");

            switch (selectedSeatRow) {
                case 1:
                    if (tempSeat > 12) {
                        System.out.println("Enter a seat number between 1 - 12");
                    } else {
                        if (mode) {
                            if (row1[tempSeat - 1] == 0) {
                                return tempSeat;
                            } else {
                                System.out.println("Seat already booked");
                            }
                        } else {
                            if (row1[tempSeat - 1] == 0) {
                                System.out.println("Seat isn't booked right now");
                                return -1;
                            } else {
                                return tempSeat;
                            }
                        }
                    }
                    break;
                case 2:
                    if (tempSeat > 16) {
                        System.out.println("Enter a seat number between 1 - 16");
                    } else {
                        if (mode) {
                            if (row2[tempSeat - 1] == 0) {
                                return tempSeat;
                            } else {
                                System.out.println("Seat already booked");
                            }
                        } else {
                            if (row2[tempSeat - 1] == 0) {
                                System.out.println("Seat isn't booked right now");
                                return -1;
                            } else {
                                return tempSeat;
                            }
                        }
                    }
                    break;
                case 3:
                    if (tempSeat > 20) {
                        System.out.println("Enter a seat number between 1 - 20");
                    } else {
                        if (mode) {
                            if (row3[tempSeat - 1] == 0) {
                                return tempSeat;
                            } else {
                                System.out.println("Seat already booked");
                            }
                        } else {
                            if (row3[tempSeat - 1] == 0) {
                                System.out.println("Seat isn't booked right now");
                                return -1;
                            } else {
                                return tempSeat;
                            }
                        }
                    }
                    break;
            }
        }
    }

    static void buy_ticket() {
        int selectedSeatRow = getSeatRow();
        int selectedSeat = getSeat(selectedSeatRow, BOOKING_MODE);

        System.out.printf("Booked seat %s from row 1\n", selectedSeat);

        int[] rowArray;
        switch (selectedSeatRow) {
            case 1:
                rowArray = row1;
                break;
            case 2:
                rowArray = row2;
                break;
            case 3:
                rowArray = row3;
                break;
            default:
                rowArray = new int[0];
                break;
        }
        rowArray[selectedSeat - 1] = 1;
    }
}