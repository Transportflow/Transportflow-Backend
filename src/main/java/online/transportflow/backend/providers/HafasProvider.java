package online.transportflow.backend.providers;

import com.github.kevinsawicki.http.HttpRequest;
import online.transportflow.backend.objects.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HafasProvider implements Provider {
    private String baseUrl;
    private String regionName;
    private List<Product> products;

    public HafasProvider(String baseUrl, String regionName, List<Product> products) {
        this.baseUrl = baseUrl;
        this.regionName = regionName;
        this.products = products;
    }

    @Override
    public List<Location> searchLocation(String query, int results, boolean stops, boolean addresses, boolean poi) {
        String response = HttpRequest.get(baseUrl +
                "/locations?query=" + query.replaceAll(" ", "-") +
                "&results=" + results +
                "&stops=" + stops +
                "&addresses=" + addresses +
                "&poi=" + poi).body();

        return parseStopResponse(response);
    }

    @Override
    public List<Location> searchLocation(Coordinates coordinates, int radius, int results, boolean stops, boolean poi) {
        String response = HttpRequest.get(baseUrl +
                "/stops/nearby?latitude=" + coordinates.getLatitude() +
                "&longitude=" + coordinates.getLongitude() +
                "&distance=" + radius +
                "&results=" + results +
                "&stops=" + stops +
                "&poi=" + poi).body();

        return parseStopResponse(response);
    }

    private List<Location> parseStopResponse(String responseString) {
        JSONArray response = new JSONArray(responseString);
        List<Location> result = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            JSONObject obj = response.getJSONObject(i);
            LocationType type = getType(obj);
            Coordinates coordinates;
            List<Product> stopProducts = new ArrayList<>();
            String id;
            String name;
            Number distance = null;

            if (type == LocationType.STOP) {
                JSONObject jsonLocation = obj.getJSONObject("location");
                coordinates = new Coordinates(jsonLocation.getDouble("latitude"), jsonLocation.getDouble("longitude"));

                try {
                    distance = obj.getInt("distance");
                } catch (Exception e) {
                    // no distance to stop (nearby method no used)
                }

                JSONObject jsonProducts = obj.getJSONObject("products");
                products.forEach((product -> {
                    if (jsonProducts.getBoolean(product.getCode())) {
                        stopProducts.add(product);
                    }
                }));
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

            result.add(new Location(type, id, name, regionName, stopProducts, coordinates, distance));
        }
        return result;
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

    @Override
    public Monitor getDepartures(String stopId, Date when, int duration) {
        return null;
    }

    @Override
    public List<UpcomingStop> getNextStops(String tripId, String lineName, String currentStop) {
        return new ArrayList<>();
    }

    public void printResultsToConsole(List<Location> results) {
        results.forEach((location -> {
            System.out.print(location.getType().toString() + " | " + location.getName() + " | " + location.getCoordinates().getLatitude() + ", " + location.getCoordinates().getLongitude());
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
