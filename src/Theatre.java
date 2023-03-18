import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Theatre {
    static int[] row1;
    static int[] row2;
    static int[] row3;

    static ArrayList<Ticket> tickets = new ArrayList<>();
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

    /**
     *
     * @param message
     * @param tryAgainMessage
     * @return
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
     * @param list is the ArrayList
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

    static void sort_tickets() {
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
    }

    static int getTicketIndex(int row, int seat, ArrayList<Ticket> ticketArray) {
        for (int i = 0; i < ticketArray.size(); i++) {
            if (ticketArray.get(i).getRow() == row && ticketArray.get(i).getSeat() == seat) {
                return i;
            }
        }

        return -1;
    }

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

    static boolean emailFormatCheck(String email) {
        if (email.contains("@")) {
            try {
                if (email.split("@")[1].split("\\.").length == 2) {
                    return true;
                }
                else {
                    return false;
                }
            }
            catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }
        }

        else {
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

    static int[] strArrayToIntArray(String[] array) {
        int[] tempArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            tempArray[i] = Integer.parseInt(array[i]);
        }
        return tempArray;
    }

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
            Scanner reader = new Scanner(arrayFile);

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
        int i = 0;

        for (Ticket ticket : tickets) {
            ticket.print();
            System.out.println();
            i += ticket.getPrice();
        }

        System.out.println("Total value of the tickets: " + i);
    }

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

    static void buy_ticket() {
        int selectedSeatRow = getSeatRow();
        int selectedSeat = getSeat(selectedSeatRow, BOOKING_MODE);

        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the name of the person");
        String personName = sc.nextLine();
        System.out.println("Please enter the surname of the person");
        String surname = sc.nextLine();
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