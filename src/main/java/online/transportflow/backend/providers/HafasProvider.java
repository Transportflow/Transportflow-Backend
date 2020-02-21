package online.transportflow.backend.providers;

import com.github.kevinsawicki.http.HttpRequest;
import online.transportflow.backend.objects.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
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
    public List<Location> searchLocation(String query, int results, boolean stops, boolean addresses, boolean poi) {
        String response = HttpRequest.get(baseUrl +
                "/locations?query=" + query.replaceAll(" ", "-") +
                "&results=" + results +
                "&stops=" + stops +
                "&addresses=" + addresses +
                "&poi=" + poi).body();

        List<Location> result = new ArrayList<>();

        JSONArray arr = new JSONArray(response);
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            LocationType type = getType(obj);
            Coordinates coordinates;
            String id;
            String name;

            if (type == LocationType.STOP) {
                JSONObject jsonLocation = obj.getJSONObject("location");
                coordinates = new Coordinates(jsonLocation.getDouble("latitude"), jsonLocation.getDouble("longitude"));
            } else {
                coordinates = new Coordinates(obj.getDouble("latitude"), obj.getDouble("longitude"));
            }

            if (type == LocationType.ADDRESS) {
                id = null;
                name = obj.getString("address");
            } else {
                id = obj.getString("id");
                name = obj.getString("name");
            }

            result.add(new Location(type, id, name, regionName, new ArrayList<>(), coordinates));
        }

        return result;
    }

    @Override
    public List<Location> searchLocation(Coordinates coordinates, int radius, int results, boolean stops, boolean poi) {
        String response = HttpRequest.get(baseUrl + "/nearby").body();
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

    private LocationType getType(JSONObject obj) {
        if (obj.getString("type").equals("location")) {
            try  {
                // if location is no poi, poi is undefined
                obj.getBoolean("poi");
                return LocationType.POI;
            } catch (JSONException e) {
                return LocationType.ADDRESS;
            }
        }
        return LocationType.STOP;
    }
}
