package online.transportflow.backend.providers.regions;

import de.schildbach.pte.dto.Location;
import de.schildbach.pte.dto.LocationType;
import de.schildbach.pte.dto.SuggestLocationsResult;
import online.transportflow.backend.objects.Product;
import online.transportflow.backend.objects.location.Stop;
import online.transportflow.backend.objects.monitor.Monitor;
import online.transportflow.backend.objects.monitor.UpcomingStopover;
import online.transportflow.backend.providers.PteProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class NvbwProvider extends PteProvider {
    private final de.schildbach.pte.NvbwProvider NvBwProvider;

    public NvbwProvider() {
        super("Badem-Württemberg (Nvbw)", "Badem-Württemberg", "Nvbw", "de", "", "black", PteProvider.PteProductsToTransportflow(new de.schildbach.pte.NvbwProvider().defaultProducts()));

        this.NvBwProvider = new de.schildbach.pte.NvbwProvider();
    }

    @Override
    public List<Product> getProducts() {
        return super.getProducts();
    }

    @Override
    public List<Stop> searchLocation(String query, int results, boolean stops, boolean addresses, boolean poi) {
        try {
            SuggestLocationsResult res = NvBwProvider.suggestLocations(query, Set.of(LocationType.STATION), results);

            List<Stop> stopResult = new ArrayList<>();
            for (Location l : res.getLocations()) {
                stopResult.add(new Stop(l.id, l.name + ", " + l.place, PteLocationToTransportflow(l), l.products != null ? PteProductsToTransportflow(l.products) : new ArrayList<>(), null, null, 0));
            }

            return stopResult;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Stop> searchLocation(double lat, double lng, int radius, int results, boolean stops, boolean poi) {
        return super.searchLocation(lat, lng, radius, results, stops, poi);
    }

    @Override
    public Monitor getDepartures(String stopId, Date when, int duration) throws Exception {
        return super.getDepartures(stopId, when, duration);
    }

    @Override
    public List<UpcomingStopover> getNextStops(String tripId, String lineName, String currentStopId, String when, Date relativeDepartureTime) {
        return super.getNextStops(tripId, lineName, currentStopId, when, relativeDepartureTime);
    }
}
