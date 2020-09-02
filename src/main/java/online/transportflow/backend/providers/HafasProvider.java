package online.transportflow.backend.providers;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import online.transportflow.backend.objects.*;
import online.transportflow.backend.objects.location.Stop;
import online.transportflow.backend.objects.monitor.Monitor;
import online.transportflow.backend.objects.monitor.Stopover;
import online.transportflow.backend.objects.monitor.UpcomingStopover;
import online.transportflow.backend.providers.HafasUtils.HafasStopDeserializer;
import online.transportflow.backend.providers.HafasUtils.HafasStopoverDeserializer;
import online.transportflow.backend.providers.HafasUtils.HafasUpcomingStopDeserializer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HafasProvider extends GeneralProvider {
    public HafasProvider(String baseUrl, String regionName, String language, String image, String textColor, List<Product> products) {
        super(baseUrl, regionName, language, image, textColor, products);
    }

    @Override
    public List<Stop> searchLocation(String query, int results, boolean stops, boolean addresses, boolean poi) {
        if (!stops && !addresses && !poi) {
            stops = true;
        }

        String response = HttpRequest.get(baseUrl +
                "/locations?query=" + query.replaceAll(" ", "-") +
                "&results=" + results +
                "&stops=" + stops +
                "&addresses=" + addresses +
                "&language=" + language).body();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Stop.class, new HafasStopDeserializer(super.products))
                .setPrettyPrinting()
                .create();
        return List.of(gson.fromJson(response, Stop[].class));
    }

    @Override
    public List<Stop> searchLocation(double latitude, double longitude, int radius, int results, boolean stops, boolean poi) {
        if (!stops && !poi) {
            stops = true;
        }

        String response = HttpRequest.get(baseUrl +
                "/stops/nearby?latitude=" + latitude +
                "&longitude=" + longitude +
                "&distance=" + radius +
                "&results=" + results +
                "&stops=" + stops +
                "&language=" + language).body();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Stop.class, new HafasStopDeserializer(super.products))
                .setPrettyPrinting()
                .create();
        return List.of(gson.fromJson(response, Stop[].class));
    }

    @Override
    public Monitor getDepartures(String stopId, Date when, int duration) {
        long formattedDate = when.getTime() / 1000;
        String response = HttpRequest.get(baseUrl +
                "/stops/" + stopId + "/departures" +
                "?duration=" + duration +
                "&when=" + formattedDate +
                "&language=" + language).body();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Stopover.class, new HafasStopoverDeserializer(super.products, when.getTime()))
                .setPrettyPrinting()
                .create();
        List<Stopover> stopovers = List.of(gson.fromJson(response, Stopover[].class));
        return new Monitor(stopovers.get(0).stop, stopovers);
    }


    @Override
    public List<UpcomingStopover> getNextStops(String tripId, String lineName, String currentStopId, String when, Date relativeDepartureTime) {
        HttpResponse<JsonNode> response = Unirest.get(baseUrl +
                "/trips/" + tripId.replaceAll("\\|", "%7C").replaceAll("#", "%23") +
                "?lineName=" + lineName +
                "&stopovers=" + true +
                "&language=" + language).asJson();

        String upcomingStopovers = response.getBody().getObject().get("stopovers").toString();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(UpcomingStopover.class, new HafasUpcomingStopDeserializer(super.products))
                .setPrettyPrinting()
                .create();
        List<UpcomingStopover> nextStops = new ArrayList<>(List.of(gson.fromJson(upcomingStopovers, UpcomingStopover[].class)));
        nextStops.forEach(nextStop -> {
            nextStop.generateRelatives(relativeDepartureTime);
        });

        int removeIndex = 0;
        for (int i = 0; i < nextStops.size(); i++) {
            try {
                if (Integer.parseInt(nextStops.get(i).relativeArrival.replace("'", "").replace("h", "")) > 0 && removeIndex == 0)
                    removeIndex = i++;
            } catch(Exception ignore) {}
        }
        for (int i = 0; i < removeIndex; i++) {
            nextStops.remove(0);
        }

        return nextStops;
    }

    /*
    private Location parseLocation(JSONObject obj) {
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

        return new Location(type, id, name, regionName, stopProducts, coordinates, distance);
    }
    */
}
