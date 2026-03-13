import java.util.*;

public class ParkingLotManagement {

    static class ParkingSpot {
        String licensePlate;
        long entryTime;

        ParkingSpot(String plate) {
            this.licensePlate = plate;
            this.entryTime = System.currentTimeMillis();
        }
    }

    static int SIZE = 500;
    static ParkingSpot[] table = new ParkingSpot[SIZE];


    public static int hash(String plate) {
        return Math.abs(plate.hashCode()) % SIZE;
    }


    public static void parkVehicle(String plate) {

        int index = hash(plate);
        int probes = 0;

        while (table[index] != null) {
            index = (index + 1) % SIZE;
            probes++;
        }

        table[index] = new ParkingSpot(plate);

        System.out.println("Assigned spot #" + index +
                " (" + probes + " probes)");
    }


    public static void exitVehicle(String plate) {

        int index = hash(plate);

        while (table[index] != null) {

            if (table[index].licensePlate.equals(plate)) {

                long duration = System.currentTimeMillis() - table[index].entryTime;

                double hours = duration / 3600000.0;
                double fee = hours * 5;

                table[index] = null;

                System.out.println("Spot #" + index + " freed");
                System.out.println("Duration: " + hours + " hours");
                System.out.println("Fee: $" + fee);
                return;
            }

            index = (index + 1) % SIZE;
        }

        System.out.println("Vehicle not found");
    }


    public static void getStatistics() {

        int occupied = 0;

        for (ParkingSpot spot : table) {
            if (spot != null) occupied++;
        }

        double occupancy = (occupied * 100.0) / SIZE;

        System.out.println("Occupancy: " + occupancy + "%");
        System.out.println("Occupied Spots: " + occupied);
        System.out.println("Total Spots: " + SIZE);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter vehicle plate to park: ");
        String plate = sc.nextLine();

        parkVehicle(plate);

        System.out.print("Enter vehicle plate to exit: ");
        String exitPlate = sc.nextLine();

        exitVehicle(exitPlate);

        getStatistics();

        sc.close();
    }
}
