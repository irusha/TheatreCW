import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Theatre {
    static int[] row1;
    static int[] row2;
    static int[] row3;

    static ArrayList<Ticket> tickets = new ArrayList<>();
    final static boolean BOOKING_MODE = true;
    final static boolean CANCELLING_MODE = false;

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
                case 1 -> buy_ticket();
                case 0 -> flag = false;
                case 2 -> print_seating_area();
                case 3 -> cancel_ticket();
                case 4 -> available_seats();
                case 5 -> save();
                case 6 -> load();
                case 7 -> show_tickets_info();
                case 8 -> sort_tickets();
                default -> System.out.println("Invalid option. Please try again.");
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

    /**
     * Prints the seat on the terminal based on it's booked status
     * @param seatVal the seat that is to be checked
     */
    static void printSeat(int seatVal) {
        if (seatVal == 0) {
            System.out.print("O");
        } else {
            System.out.print("X");
        }
    }

    /**
     * @param message the message that is asked from the user to enter an int
     * @param tryAgainMessage the message that is shown to the user when they enter a wrong input
     * @return the int input by the user
     */
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

    /**
     * @param message the message that is asked from the user to enter a float
     * @param tryAgainMessage the message that is shown to the user when they enter a wrong input
     * @return the float input by the user
     */
    static float getPositiveFloat(String message, String tryAgainMessage) {
        System.out.println(message);
        Scanner sc = new Scanner(System.in);

        String strFloat;

        do {
            strFloat = sc.nextLine();
            try {
                float parsedInt = Float.parseFloat(strFloat);
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

    /**
     * will take an ArrayList of type integer
     *
     * @param list the ArrayList
     * @return a formatted string with the array elements
     */
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

    /**
     * will take an ArrayList of type integer
     * @param list the integer array
     * @return a formatted string with the array elements
     */
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
            rowArray = switch (seatRow) {
                case 1 -> row1;
                case 2 -> row2;
                case 3 -> row3;
                default -> new int[0];
            };
            rowArray[seat - 1] = 0;
            int ticketIndex = getTicketIndex(seatRow, seat, tickets);
            if (ticketIndex != -1) {
                tickets.remove(ticketIndex);
            }
            System.out.printf("Cancelled ticket %s from row %s\n", seat, seatRow);
            //System.out.println(tickets.size());
        }
    }

    /*static void sort_tickets() {
        for (int i = 0; i < tickets.size(); i++) {
            for (int j = i + 1; j < tickets.size(); j++) {
                if (tickets.get(i).getPrice() > tickets.get(j).getPrice()) {
                    Ticket tempTicket = tickets.get(i);
                    tickets.set(i, tickets.get(j));
                    tickets.set(j, tempTicket);
                }
            }
        }
        show_tickets_info();
    }*/

    static void sort_tickets() {
        ArrayList<Ticket> tempArray = new ArrayList<>();
        ArrayList<Ticket> copiedArray = new ArrayList<>(tickets);
//        Ticket currentSmallest = copiedArray.get(0);

        while (copiedArray.size() != 0) {
            int smallestNumberIndex = 0;
            for (int i = 0; i < copiedArray.size(); i++) {
                if (copiedArray.get(i).getPrice() <= copiedArray.get(smallestNumberIndex).getPrice()) {
                    smallestNumberIndex = i;
                }
            }
            tempArray.add(copiedArray.get(smallestNumberIndex));
            copiedArray.remove(smallestNumberIndex);
        }

        tickets = tempArray;
        show_tickets_info();
    }

    /**
     * Get the index of the ticket by searching tickets with row number and seat number
     * @param row the row number
     * @param seat the seat number
     * @param ticketArray the array of tickets that needs to be searched
     * @return index of the ticket
     */
    static int getTicketIndex(int row, int seat, ArrayList<Ticket> ticketArray) {
        for (int i = 0; i < ticketArray.size(); i++) {
            if (ticketArray.get(i).getRow() == row && ticketArray.get(i).getSeat() == seat) {
                return i;
            }
        }

        return -1;
    }

    /**
     * get email from the user
     * @return the email entered by the user
     */
    static String emailInput() {
        Scanner sc = new Scanner(System.in);
        String email = "";
        do {
            if (!email.equals("") && !emailFormatCheck(email)) {
                System.out.println("Email is in wrong format. Please try again.");
            }
            email = sc.nextLine();
        }
        while (!emailFormatCheck(email));
        return email;
    }

    /**
     * check whether the email is in the correct format
     * @param email email needs to be checked
     * @return true if the format is correct and vice versa
     */
    static boolean emailFormatCheck(String email) {
        if (email.contains("@")) {
            try {
                if (email.split("@")[1].split("\\.").length == 2) {
                    return true;
                } else {
                    return false;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    static void save() {
        File file = new File("src\\data.txt");

        try {
            if (file.createNewFile()) {
                System.out.println("File created");
            } else {
                System.out.println("File overwritten");
            }
        } catch (IOException e) {
            System.out.println("Unable to create a file");
        }

        try {
            FileWriter writer = new FileWriter(file);
            writer.write(String.format("Row 1 - %s\n", arrayToString(row1)));
            writer.write(String.format("Row 2 - %s\n", arrayToString(row2)));
            writer.write(String.format("Row 3 - %s\n", arrayToString(row3)));
            writer.close();
            System.out.println("Successfully wrote data to the file");

        } catch (IOException e) {
            System.out.println("Created file isn't available.");
            throw new RuntimeException(e);
        }

    }

    /**
     * Converts an array of strings to an array of integers
     * @param array the array that is needed to be converted
     * @return the integer array
     */
    static int[] strArrayToIntArray(String[] array) {
        int[] tempArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            tempArray[i] = Integer.parseInt(array[i]);
        }
        return tempArray;
    }

    /**
     * Checks the length of the given array is equal to the given seat row
     * @param array the array that needs to be checked
     * @param row
     * @return true if the array is equal to the given seat row and vice versa
     */
    static boolean rowLengthCheck(int[] array, int row) {
        return switch (row) {
            case 1 -> array.length == 12;
            case 2 -> array.length == 16;
            case 3 -> array.length == 20;
            default -> false;
        };
    }

    static void load() {
        File arrayFile = new File("src/data.txt");

        try {
            Scanner reader =   new Scanner(arrayFile);

            String row1DataStr = reader.nextLine().split(" - ")[1];
            int[] rowTemp = strArrayToIntArray(row1DataStr.split(", "));

            if (rowLengthCheck(rowTemp, 1)) {
                row1 = rowTemp;
            } else {
                throw new FileNotFoundException("Invalid File");
            }

            String row2DataStr = reader.nextLine().split(" - ")[1];
            rowTemp = strArrayToIntArray(row2DataStr.split(", "));

            if (rowLengthCheck(rowTemp, 2)) {
                row2 = rowTemp;
            } else {
                throw new FileNotFoundException("Invalid File");
            }

            String row3DataStr = reader.nextLine().split(" - ")[1];
            rowTemp = strArrayToIntArray(row3DataStr.split(", "));

            if (rowLengthCheck(rowTemp, 3)) {
                row3 = rowTemp;
            } else {
                throw new FileNotFoundException("Invalid File");
            }
            System.out.println("Successfully loaded the file");
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Invalid file. Failed to load.");
        }

    }

    /**
     * Gets a positive integer from the user
     * @param message shows a message before getting the input
     * @param includesZero true: user can input numbers from 0, false: user can only input numbers from 1
     * @return the valid number inputted by the user
     */
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

    static void show_tickets_info() {
        float i = 0;

        for (Ticket ticket : tickets) {
            ticket.print();
            System.out.println();
            i += ticket.getPrice();
        }

        System.out.println("Total value of the tickets: " + i);
    }

    /**
     * Get the seat row from the user
     * @return the seat row
     */
    static int getSeatRow() {

        while (true) {
            int tempSeat = getPositiveInt("Please Enter the seat row", "Please enter a positive integer");

            switch (tempSeat) {
                case 1, 2, 3 -> {
                    return tempSeat;
                }
                default -> System.out.println("Invalid seat row. Please try again.");
            }
        }

    }

    /**
     * Get a valid seat number from a user
     * @param selectedSeatRow is used to check the valid seat range
     * @param mode BOOKING_MODE will check whether the seat is already booked and
     *             CANCELLING_MODE will check whether a seat is already empty
     * @return the seat number
     */
    static int getSeat(int selectedSeatRow, boolean mode) {

        while (true) {
            int tempSeat = getPositiveInt("Please Enter the seat", "Please enter a positive integer");

            switch (selectedSeatRow) {
                case 1 -> {
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
                }
                case 2 -> {
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
                }
                case 3 -> {
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
                }
            }
        }
    }

    /**
     * Gets a non-empty string from the user
     * @param message prints this message before getting the input
     * @return the user input
     */
    static String getString(String message) {
        System.out.println(message);
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();

        while (str.equals("")) {
            System.out.println("Please enter a valid string");
            str = sc.nextLine();
        }

        return str;
    }

    static void buy_ticket() {
        int selectedSeatRow = getSeatRow();
        int selectedSeat = getSeat(selectedSeatRow, BOOKING_MODE);

        String personName = getString("Please enter the name of the person");
        String surname = getString("Please enter the surname of the person");
        System.out.println("Please enter the email of the person");
        String email = emailInput();

        Person person = new Person(personName, surname, email);

        int[] rowArray = switch (selectedSeatRow) {
            case 1 -> row1;
            case 2 -> row2;
            case 3 -> row3;
            default -> new int[0];
        };

        float price = getPositiveFloat("Please enter the price of the ticket", "Invalid amount");

        Ticket ticket = new Ticket(selectedSeatRow, selectedSeat, price, person);

        tickets.add(ticket);

        rowArray[selectedSeat - 1] = 1;
        System.out.printf("Booked seat %s from row 1\n", selectedSeat);
    }
}