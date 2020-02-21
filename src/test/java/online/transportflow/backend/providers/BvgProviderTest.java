package online.transportflow.backend.providers;

import online.transportflow.backend.objects.Location;
import org.junit.jupiter.api.Test;

import java.util.List;

class BvgProviderTest {

    // !! Not actual unit test. Just testing the output from the providers !!
    @Test
    public void locationSearchByText() {
        BvgProvider bvg = new BvgProvider();

        List<Location> results = bvg.searchLocation("U Pankstraße", 10, true, false, true);
        results.forEach((location -> {
            System.out.println(location.getType().toString() + " | " + location.getName() + " | " + location.getCoordinates().getLatitude() + ", " + location.getCoordinates().getLongitude());
        }));
    }
}