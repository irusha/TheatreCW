public class Ticket {
    private int row;
    private int seat;
    private float price;
    private Person person;

    public int getRow() {
        return row;
    }

    public int getSeat() {
        return seat;
    }

    public float getPrice() {
        return price;
    }

    public Ticket(int row, int seat, float price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    void print() {
        System.out.printf("""
                Person name: %s
                Person surname: %s
                Person email: %s
                Row: %s
                Seat: %s
                Price: %s
                """,
                person.getName(), person.getSurname(), person.getEmail(), row, seat, price);
    }
}
