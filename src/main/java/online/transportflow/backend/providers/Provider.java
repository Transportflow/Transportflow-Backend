package online.transportflow.backend.providers;

import online.transportflow.backend.objects.*;
import online.transportflow.backend.objects.location.Stop;

import java.net.UnknownHostException;
import java.util.List;

public interface Provider {
    String baseUrl = null;
    String regionName = null;
    String image = null;
    String textColor = null;
    List<Product> products = null;

    /**
     * Returns a list of locations for a given query
     *
     * @param query      search string
     * @param results    how many stops are max. returned
     * @param stops      include stops?
     * @param addresses  include addresses?
     * @param poi        include poi's?
     * @return           returns locations
     */
    List<Stop> searchLocation(String query, int results, boolean stops, boolean addresses, boolean poi);


    /*
     * Returns a list of stops for a radius around given coordinates
     *
     * @param coordinates  latitude & longitude for search
     * @param radius       radius around coordinates in meters
     * @param results      how many stops are max. returned
     * @param stops        include stops?
     * @param poi          include poi's?
     * @return             returns locations for given position
     */
    List<Stop> searchLocation(double lat, double lng, int radius, int results, boolean stops, boolean poi);

    /*Monitor getDepartures(String stopId, Date when, int duration);
    List<UpcomingStop> getNextStops(String tripId, String lineName, String currentStop);
*/

    String getRegionName();
    List<Product> getProducts();
}
