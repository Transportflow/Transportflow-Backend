package online.transportflow.backend.providers;

import online.transportflow.backend.objects.Coordinates;
import online.transportflow.backend.objects.Location;
import org.junit.jupiter.api.Test;

import java.util.List;

class BvgProviderTest {
    // !! Not actual unit test. Just testing the output from the providers !!

    @Test
    public void locationSearchByCoordinates() {
        BvgProvider bvg = new BvgProvider(BvgProvider.getProducts());

        List<Location> results = bvg.searchLocation(new Coordinates(52.52595, 13.368928), 500, 5, true, false);
        bvg.printResultsToConsole(results);
    }

    @Test
    public void locationSearchByText() {
        BvgProvider bvg = new BvgProvider(BvgProvider.getProducts());

        List<Location> results = bvg.searchLocation("Hauptbahnhof", 5, true, false, false);
        bvg.printResultsToConsole(results);
    }
}