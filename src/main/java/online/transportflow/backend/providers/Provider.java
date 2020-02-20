package online.transportflow.backend.providers;

import online.transportflow.backend.objects.Monitor;
import online.transportflow.backend.objects.Stop;
import online.transportflow.backend.objects.UpcomingStop;

import java.util.Date;
import java.util.List;

public interface Provider {

    String regionName = null;

    List<Stop> searchStop(String input, int results, boolean stops, boolean addresses, boolean poi);
    List<Stop> searchStop(long[] pos1, long[] pos2, int results, boolean stops, boolean addresses, boolean poi);

    Monitor getDepartures(String stopId, Date when, int duration);
    List<UpcomingStop> getNextStops(String tripId, String lineName, String currentStop);



}
