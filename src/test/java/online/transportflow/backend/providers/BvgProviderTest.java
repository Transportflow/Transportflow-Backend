package online.transportflow.backend.providers;

import online.transportflow.backend.Utils;
import online.transportflow.backend.objects.Coordinates;
import online.transportflow.backend.objects.Location;
import online.transportflow.backend.objects.Monitor;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

class BvgProviderTest {
    // !! Not actual unit test. Just testing the output from the providers !!

    @Test
    public void locationSearchByCoordinates() {
        BvgProvider bvg = new BvgProvider(BvgProvider.getProducts());

        List<Location> results = bvg.searchLocation(new Coordinates(52.52595, 13.368928), 500, 5, true, false);
        Utils.printLocationsToConsole(results);
    }

    @Test
    public void locationSearchByText() {
        BvgProvider bvg = new BvgProvider(BvgProvider.getProducts());

        List<Location> results = bvg.searchLocation("Finkenkrug Bhf", 5, true, false, false);
        Utils.printLocationsToConsole(results);
    }

    @Test
    public void departureMonitorTest() {
        BvgProvider bvg = new BvgProvider(BvgProvider.getProducts());

        Date departure = new Date();
        departure.setHours(18);

        Monitor m = bvg.getDepartures("900000210011", departure, 300);
    }
}