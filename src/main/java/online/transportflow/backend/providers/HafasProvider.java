package online.transportflow.backend.providers;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import online.transportflow.backend.objects.*;
import online.transportflow.backend.objects.location.Stop;
import online.transportflow.backend.providers.HafasUtils.BvgStopDeserializer;
import online.transportflow.backend.providers.HafasUtils.HafasStopDeserializer;

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
                "&poi=" + poi +
                "&language=" + language).body();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Stop.class, new BvgStopDeserializer())
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
                "&poi=" + poi +
                "&language=" + language).body();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Stop.class, new BvgStopDeserializer())
                .setPrettyPrinting()
                .create();
        return List.of(gson.fromJson(response, Stop[].class));
    }

/*
    @Override
    public Monitor getDepartures(String stopId, Date when, int duration) {
        long formattedDate = when.getTime() / 1000;
        String res = HttpRequest.get(baseUrl +
                "/stops/" + stopId + "/departures" +
                "?duration=" + duration +
                "&when=" + formattedDate +
                "&language=" + language).body();
        List<Departure> result = new ArrayList<>();

        JSONArray response = new JSONArray(res);

        for (int i = 0; i < response.length(); i++) {
            JSONObject obj = response.getJSONObject(i);

            String tripId = obj.getString("tripId");
            Location location = parseLocation(obj.getJSONObject("stop"));
            Date departureTime = null;
            try {
                String departureString = obj.get("when") instanceof String ? obj.getString("when") : null;
                if (departureString == null) {
                    departureString = obj.getString("scheduledWhen");
                }
                departureTime = dateFormat.parse(departureString);
            } catch (ParseException e) {
                System.out.println(obj.getString("when"));
                e.printStackTrace();
            }
            String direction = obj.getString("direction");
            JSONObject lineObject = obj.getJSONObject("line");
            final Product[] lineProduct = new Product[1];
            products.forEach(product -> {
                if (product.code.equals(lineObject.getString("product")))
                    lineProduct[0] = product;
            });
            Line line = new Line(lineObject.getString("id"),
                    lineObject.getString("fahrtNr"),
                    lineObject.getString("name"),
                    lineObject.getString("mode"),
                    lineProduct[0],
                    lineObject.has("symbol") ? lineObject.get("symbol").toString() : null,
                    lineObject.getInt("nr"));
            JSONArray jsonRemarks = obj.getJSONArray("remarks");
            ArrayList<Remark> remarks = new ArrayList<>();
            if (jsonRemarks != null) {
                for (int v = 0; v < jsonRemarks.length(); v++) {
                    JSONObject jsonRemark = jsonRemarks.getJSONObject(v);

                    RemarkType remarkType = RemarkType.OTHER;
                    if (jsonRemark.getString("type").equals("hint"))
                        remarkType = RemarkType.HINT;
                    else if (jsonRemark.getString("type").equals("warning"))
                        remarkType = RemarkType.WARNING;

                    remarks.add(new Remark(remarkType, jsonRemark.has("code") ? jsonRemark.getString("code") : null, jsonRemark.has("summary") ? jsonRemark.getString("summary") : null, jsonRemark.getString("text")));
                }
            }
            int delay = obj.get("delay") instanceof Number ? obj.getInt("delay") / 60 : 0;
            String platform = obj.get("platform") instanceof String ? obj.getString("platform") : null;
            Boolean cancelled = obj.has("cancelled") && obj.getBoolean("cancelled");

            result.add(new Departure(tripId, location, departureTime, direction, line, remarks, delay, platform, cancelled));
        }

        Monitor monitor = new Monitor();
        monitor.departures = result;
        monitor.location = result.get(0).stop;

        return monitor;
    }

    @Override
    public List<UpcomingStop> getNextStops(String tripId, String lineName, String currentStop) {
        String res = HttpRequest.get(baseUrl +
                "/stops/" + stopId + "/departures" +
                "?duration=" + duration +
                "&when=" + formattedDate +
                "&language=" + language).body();

        return new ArrayList<>();
    }

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
