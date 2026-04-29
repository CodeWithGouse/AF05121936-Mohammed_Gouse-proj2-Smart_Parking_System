import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ParkingService s = new ParkingService();

        while(true) {
            System.out.println("\n===== SMART PARKING SYSTEM =====");
            System.out.println("1. View Available Slots");
            System.out.println("2. Park Vehicle");
            System.out.println("3. Remove Vehicle");
            System.out.println("4. View All Vehicles");
            System.out.println("5. Search Vehicle");
            System.out.println("6. Calculate Fee");
            System.out.println("7. Daily Report");
            System.out.println("8. Exit");
            System.out.print("Choose: ");

            int ch = sc.nextInt(); sc.nextLine();

            switch(ch) {
                case 1: s.viewAvailableSlots(); break;
                case 2: s.parkVehicle(sc); break;
                case 3: s.removeVehicle(sc); break;
                case 4: s.viewAllVehicles(); break;
                case 5: s.searchVehicle(sc); break;
                case 6: s.calculateFee(sc); break;
                case 7: s.dailyReport(); break;
                case 8: System.exit(0);
                default: System.out.println("Invalid!");
            }
        }
    }
}
