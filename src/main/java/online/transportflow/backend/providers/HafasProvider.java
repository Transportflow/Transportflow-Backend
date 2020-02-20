package online.transportflow.backend.providers;

import com.github.kevinsawicki.http.HttpRequest;
import online.transportflow.backend.objects.Monitor;
import online.transportflow.backend.objects.Stop;
import online.transportflow.backend.objects.UpcomingStop;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HafasProvider implements Provider {
    private String baseUrl;
    private String regionName;

    public HafasProvider(String baseUrl, String regionName) {
        this.baseUrl = baseUrl;
        this.regionName = regionName;
    }

    @Override
    public List<Stop> searchStop(String input, int results, boolean stops, boolean addresses, boolean poi) {
        String response = HttpRequest.get(baseUrl + "/locations?query=" + input).body();

        List<Stop> result = new ArrayList<>();

        JSONArray arr = new JSONArray(response);
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            JSONObject location = obj.getJSONObject("location");
            Stop stop = new Stop(obj.getString("id"), obj.getString("name"), regionName, new ArrayList<>(), location.getLong("latitude"), location.getLong("longitude"));
            result.add(stop);
        }

        return result;
    }

    @Override
    public List<Stop> searchStop(long[] pos1, long[] pos2, int results, boolean stops, boolean addresses, boolean poi) {
        return new ArrayList<>();
    }

    @Override
    public Monitor getDepartures(String stopId, Date when, int duration) {
        return null;
    }

    @Override
    public List<UpcomingStop> getNextStops(String tripId, String lineName, String currentStop) {
        return new ArrayList<>();
    }
}
