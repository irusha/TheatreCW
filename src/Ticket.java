public class Ticket {
    int row;
    int seat;
    int price;
    Person person;

    public Ticket(int row, int seat, int price, Person person) {
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
                person.name, person.surname, person.email, row, seat, price);
    }
}
