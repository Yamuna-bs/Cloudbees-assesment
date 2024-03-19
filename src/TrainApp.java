import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TrainApp {
    private static Map<String, User> users = new HashMap<>();
    private static Map<Seat, User> seatMap = new HashMap<>();
    private static int nextSeatNumber = 1;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("\nWelcome to the Train Ticketing System!");
            System.out.println("1. Purchase Ticket");
            System.out.println("2. View Receipt");
            System.out.println("3. View Users by Section");
            System.out.println("4. Remove User");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    purchaseTicketFromUser();
                    break;
                case 2:
                    viewReceipt();
                    break;
                case 3:
                    viewUsersBySection();
                    break;
                case 4:
                    removeUser();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
        scanner.close();
    }

    private static void purchaseTicketFromUser() {
        System.out.print("Enter departure city: ");
        String from = scanner.nextLine();

        System.out.print("Enter destination city: ");
        String to = scanner.nextLine();

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        TrainApp.purchaseTicket(from, to, firstName, lastName, email);
    }

    private static void viewReceipt() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        TrainApp.viewReceipt(email);
    }

    private static void viewUsersBySection() {
        System.out.print("Enter section (A or B): ");
        String section = scanner.nextLine().toUpperCase();
        TrainApp.viewUsersBySection(section);
    }

    private static void removeUser() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        TrainApp.removeUser(email);
    }

    public static void purchaseTicket(String from, String to, String firstName, String lastName, String email) {
        User user = new User(firstName, lastName, email);
        Seat seat = assignSeat();
        user.setSeat(seat);
        users.put(email, user);
        seatMap.put(seat, user);
        System.out.println("Ticket purchased successfully.");
    }

    public static void viewReceipt(String email) {
        User user = users.get(email);
        if (user != null) {
            System.out.println("Receipt:");
            System.out.println("From: London");
            System.out.println("To: France");
            System.out.println("User: " + user.getFirstName() + " " + user.getLastName());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Price Paid: $20");
            System.out.println("Seat: Section " + user.getSeat().getSection() + ", Seat " + user.getSeat().getSeatNumber());
        } else {
            System.out.println("User not found.");
        }
    }

    public static void viewUsersBySection(String section) {
        System.out.println("Users in Section " + section + ":");
        for (Map.Entry<Seat, User> entry : seatMap.entrySet()) {
            Seat seat = entry.getKey();
            User user = entry.getValue();
            if (seat.getSection().equals(section)) {
                System.out.println(user.getFirstName() + " " + user.getLastName() + " - Seat " + seat.getSeatNumber());
            }
        }
    }

    public static void removeUser(String email) {
        User user = users.get(email);
        if (user != null) {
            Seat seat = user.getSeat();
            users.remove(email);
            seatMap.remove(seat);
            System.out.println("User removed successfully.");
        } else {
            System.out.println("User not found.");
        }
    }

    private static Seat assignSeat() {
        Seat seat;
        if (nextSeatNumber <= 50) {
            seat = new Seat("A", nextSeatNumber);
        } else {
            seat = new Seat("B", nextSeatNumber - 50);
        }
        nextSeatNumber++;
        return seat;
    }
}