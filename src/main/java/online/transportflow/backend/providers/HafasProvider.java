package online.transportflow.backend.providers;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import online.transportflow.backend.objects.*;
import online.transportflow.backend.objects.location.Stop;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HafasProvider implements Provider {
    private String baseUrl;
    @Expose(serialize = true)
    public String regionName;
    private String language;
    @Expose(serialize = true)
    public String image;
    @Expose(serialize = true)
    public String textColor;

    @Expose(serialize = true)
    public List<Product> products;
    @Expose(serialize = false)
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");

    public HafasProvider(String baseUrl, String regionName, String language, String image, String textColor, List<Product> products) {
        this.baseUrl = baseUrl;
        this.regionName = regionName;
        this.textColor = textColor;
        this.products = products;
        this.image = image;
        this.language = language;
    }

    @Override
    public String getRegionName() {
        return regionName;
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public Stop[] searchLocation(String query, int results, boolean stops, boolean addresses, boolean poi) {
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

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(response, Stop[].class);
    }

    /*
    @Override
    public List<Location> searchLocation(Coordinates coordinates, int radius, int results, boolean stops, boolean poi) {
        if (!stops && !poi) {
            stops = true;
        }

        String response = HttpRequest.get(baseUrl +
                "/stops/nearby?latitude=" + coordinates.getLatitude() +
                "&longitude=" + coordinates.getLongitude() +
                "&distance=" + radius +
                "&results=" + results +
                "&stops=" + stops +
                "&poi=" + poi +
                "&language=" + language).body();

        return parseStopResponse(response);
    }

    private List<Location> parseStopResponse(String responseString) {
        JSONArray response = new JSONArray(responseString);
        List<Location> result = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            result.add(parseLocation(response.getJSONObject(i)));
        }
        return result;
    }

    private LocationType getType(JSONObject obj) {
        if (obj.getString("type").equals("location")) {
            try {
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
