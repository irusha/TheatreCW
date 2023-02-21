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
                                System.out.println("Seat is already booked");
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
                                System.out.println("Seat is already booked");
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
                                System.out.println("Seat is already booked");
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