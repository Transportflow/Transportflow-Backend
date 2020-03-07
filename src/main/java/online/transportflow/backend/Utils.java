package online.transportflow.backend;

import online.transportflow.backend.objects.Location;

import java.util.List;

public class Utils {
    public static void printLocationsToConsole(List<Location> results) {
        results.forEach((location -> {
            System.out.print(location.getType().toString() + " " + location.getId() + " | " + location.getName() + " | " + location.getCoordinates().getLatitude() + ", " + location.getCoordinates().getLongitude());
            if (location.getDistance() != null) {
                System.out.print(" | " + location.getDistance() + "m");
            }
            if (location.getProducts().size() > 0) {
                System.out.print("\n + ");
                location.getProducts().forEach((product -> {
                    System.out.print(product.getName() + " + ");
                }));
                System.out.print("\n");
            }
            System.out.print("\n");
        }));
    }
}
