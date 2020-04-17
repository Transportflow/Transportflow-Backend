package online.transportflow.backend.providers;

import online.transportflow.backend.objects.*;

import java.util.Date;
import java.util.List;

public interface Provider {
    String baseUrl = null;
    String regionName = null;
    String image = null;
    String textColor = null;
    List<Product> products = null;

    /**
     * Returns a list of stops for a given query
     *
     * @param query      search string
     * @param results    how many stops are max. returned
     * @param stops      include stops?
     * @param addresses  include addresses?
     * @param poi        include poi's?
     */
    List<Location> searchLocation(String query, int results, boolean stops, boolean addresses, boolean poi);

    /**
     * Returns a list of stops for a radius around given coordinates
     *
     * @param coordinates  latitude & longitude for search
     * @param radius       radius around coordinates in meters
     * @param results      how many stops are max. returned
     * @param stops        include stops?
     * @param poi          include poi's?
     */
    List<Location> searchLocation(Coordinates coordinates, int radius, int results, boolean stops, boolean poi);

    Monitor getDepartures(String stopId, Date when, int duration);
    List<UpcomingStop> getNextStops(String tripId, String lineName, String currentStop);

    String getRegionName();
    List<Product> getProducts();
}
