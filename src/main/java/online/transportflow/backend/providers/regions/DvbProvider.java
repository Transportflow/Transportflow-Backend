package online.transportflow.backend.providers.regions;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;
import online.transportflow.backend.objects.Line;
import online.transportflow.backend.objects.Operator;
import online.transportflow.backend.objects.Product;
import online.transportflow.backend.objects.location.Location;
import online.transportflow.backend.objects.location.Stop;
import online.transportflow.backend.objects.monitor.Monitor;
import online.transportflow.backend.objects.monitor.Stopover;
import online.transportflow.backend.providers.GeneralProvider;
import online.transportflow.backend.utils.GK4toWGS84;
import org.matsim.api.core.v01.Coord;
import org.matsim.core.utils.geometry.transformations.TransformationFactory;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class DvbProvider extends GeneralProvider {
    public DvbProvider(List<Product> products) {
        super("https://webapi.vvo-online.de",
                "Dresden (VVO)",
                "de",
                "https://images.unsplash.com/photo-1589381765173-8b3db2043022?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80",
                "white",
                products);
    }

    public static List<Product> getProviderProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Tram", "Straßenbahn", "https://www.dvb.de/assets/img/trans-icon/transport-tram.svg"));
        products.add(new Product("CityBus", "Bus", "https://www.dvb.de/assets/img/trans-icon/transport-bus.svg"));
        products.add(new Product("IntercityBus", "Regio-Bus", "https://www.dvb.de/assets/img/trans-icon/transport-bus.svg"));
        products.add(new Product("SuburbanRailway", "S-Bahn", "https://www.dvb.de/assets/img/trans-icon/transport-metropolitan.svg"));
        products.add(new Product("Train", "Zug", "https://www.dvb.de/assets/img/trans-icon/transport-train.svg"));
        products.add(new Product("Cableway", "Seil-/Schwebebahn", "https://www.dvb.de/assets/img/trans-icon/transport-lift.svg"));
        products.add(new Product("Ferry", "Fähre", "https://www.dvb.de/assets/img/trans-icon/transport-ferry.svg"));
        products.add(new Product("HailedSharedTaxi", "Anrufsammeltaxi (AST)/ Rufbus", "https://www.dvb.de/assets/img/trans-icon/transport-alita.svg"));
        products.add(new Product("Footpath", "Fussweg", "https://m.dvb.de/img/walk.svg"));
        products.add(new Product("StairsUp", "Treppe aufwärts", "https://m.dvb.de/img/stairs-up.svg"));
        products.add(new Product("StairsDown", "Treppe abwärts", "https://m.dvb.de/img/stairs-down.svg"));
        products.add(new Product("EscalatorUp", "Rolltreppe aufwärts", "https://m.dvb.de/img/escalator-up.svg"));
        products.add(new Product("EscalatorDown", "Rolltreppe abwärts", "https://m.dvb.de/img/escalator-down.svg"));
        products.add(new Product("ElevatorUp", "Fahrstuhl aufwärts", "https://m.dvb.de/img/elevator-up.svg"));
        products.add(new Product("ElevatorDown", "Fahrstuhl abwärts", "https://m.dvb.de/img/elevator-down.svg"));
        products.add(new Product("StayForConnection", "gesicherter Anschluss", "https://m.dvb.de/img/sit.svg"));
        products.add(new Product("PlusBus", "PlusBus", "https://m.dvb.de/img/mot_icons/plusBus.svg"));
        return products;
    }

    @Override
    public List<Stop> searchLocation(String query, int results, boolean stops, boolean addresses, boolean poi) {
        HttpResponse<JsonNode> response = Unirest.post(baseUrl + "/tr/pointfinder")
                .field("query", query)
                .field("limit", String.valueOf(results))
                .field("stopsOnly", String.valueOf(!addresses && !poi))
                .field("regionalOnly", String.valueOf(true))
                .asJson();
        try {
            JSONArray res = new JsonNode(response.getBody().getObject().get("Points").toString()).getArray();
            List<Stop> finalStops = new ArrayList<>();
            res.forEach(stop -> {
                String[] parts = stop.toString().split("\\|");
                Coord coords = TransformationFactory.getCoordinateTransformation("GK4", "WGS84").transform(new Coord(Double.parseDouble(parts[5]), Double.parseDouble(parts[4])));
                Stop s = new Stop(parts[0],
                        parts[3] + (!(parts[2]).equals("") ? ", " + parts[2] : ""),
                        new Location(parts[3], parts[3] + (!(parts[2]).equals("") ? ", " + parts[2] : ""), coords.getX(), coords.getY(), 0),
                        getStopProdcuts(parts[0]),
                        null,
                        null,
                        Integer.parseInt(parts[6]));
                finalStops.add(s);
            });
            return finalStops;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Stop> searchLocation(double lat, double lng, int radius, int results, boolean stops, boolean poi) {
        //TODO: Optimise!!!!!
        HttpResponse<JsonNode> response = Unirest.get("http://adwira.wien:7826/toGK4?lat=" + lat + "&lng=" + lng)
                .asJson();
        JSONObject res = response.getBody().getObject();
        return searchLocation("coord:" + res.get("x").toString().replaceAll("(\\.[^<]+)", "")
                + ":" + res.get("y").toString().replaceAll("(\\.[^<]+)", ""), results, stops, false, poi);
    }

    @Override
    public Monitor getDepartures(String stopId, Date when, int duration) throws Exception {
        HttpResponse<JsonNode> response = Unirest.post(baseUrl + "/dm")
                .field("stopid", stopId)
                .field("limit", String.valueOf(duration))
                .field("time", when.toInstant().truncatedTo(ChronoUnit.MINUTES).toString())
                .field("isarrival", String.valueOf(false))
                .asJson();
        JSONObject res = response.getBody().getObject();
        if (!new JsonNode(res.get("Status").toString()).getObject().get("Code").equals("Ok"))
            throw new Exception();

        Stop stop = new Stop(stopId, res.get("Name") + ", " + res.get("Place"),
                searchLocation(stopId, 1, true, false, false).get(0).getLocation(),
                getStopProdcuts(stopId), null, null, 0);

        JSONArray jsonStopovers = new JsonNode(res.get("Departures").toString()).getArray();
        List<Stopover> stopovers = new ArrayList<>();

        jsonStopovers.forEach(o -> {
            JSONObject jsonStopover = new JsonNode(o.toString()).getObject();

            Line line = new Line();
            line.name = jsonStopover.getString("LineName");
            line.fahrtNr = jsonStopover.getString("Id");
            line.product = getProduct(jsonStopover.getString("Mot"));

            Date plannedWhen = null;
            if (jsonStopover.has("ScheduledTime")) {
                long plannedLong = Long.parseLong(jsonStopover.get("ScheduledTime").toString()
                        .replaceAll("/Date\\(", "").replaceAll("-0000\\)/", ""));
                plannedWhen = new Date(plannedLong);
            }

            Date realtimeWhen = null;
            if (jsonStopover.has("RealTime")) {
                long realtimeLong = Long.parseLong(jsonStopover.get("RealTime").toString()
                        .replaceAll("/Date\\(", "").replaceAll("-0000\\)/", ""));
                realtimeWhen = new Date(realtimeLong);
            }
            if (plannedWhen == null)
                plannedWhen = realtimeWhen;
            else if (realtimeWhen == null)
                realtimeWhen = plannedWhen;

            int delay = (int) ((realtimeWhen.getTime() - plannedWhen.getTime())/60000);

            String platform = "N/A";

            if (jsonStopover.has("Platform"))
                platform = new JsonNode(jsonStopover.get("Platform").toString()).getObject().getString("Name");

            String relativeWhen = (realtimeWhen.getTime() - System.currentTimeMillis()) / 60000 + "'";
            if (Integer.parseInt(relativeWhen.replaceAll("'", "")) > 59)
                relativeWhen = (realtimeWhen.getTime() - System.currentTimeMillis()) / 60000 / 60 + "h";

            var hours = String.valueOf(realtimeWhen.getHours());
            var minutes = String.valueOf(realtimeWhen.getMinutes());
            String clockWhen = ("0" + hours).substring(hours.length()-1) + ":" + ("0" + minutes).substring(minutes.length()-1);

            Stopover stopover = new Stopover(jsonStopover.getString("Id"),
                    jsonStopover.getString("Direction"),
                    line, stop, false, realtimeWhen,
                    plannedWhen, plannedWhen, delay, platform,
                    null, null,
                    relativeWhen,
                    clockWhen, new ArrayList<>());
            stopovers.add(stopover);
        });


        return new Monitor(stop, stopovers);
    }

    private List<Product> getStopProdcuts(String stopId) {
        HttpResponse<JsonNode> response = Unirest.post(baseUrl + "/stt/lines")
                .field("stopid", stopId)
                .asJson();
        try {
            JSONArray res = new JsonNode(response.getBody().getObject().get("Lines").toString()).getArray();
            List<Product> products = new ArrayList<>();
            res.forEach(line -> {
                JSONObject l = new JSONObject(line.toString());
                Product p = getProduct(l.get("Mot").toString());
                AtomicBoolean isRedundant = new AtomicBoolean(false);
                products.forEach(t -> {
                    if (t.img.equals(p.img))
                        isRedundant.set(true);
                });
                if (!isRedundant.get())
                    products.add(p);
            });
            return products;
        } catch (Exception e) {
            return null;
        }
    }

    private Product getProduct(String name) {
        AtomicReference<Product> product = new AtomicReference<>(null);
        getProviderProducts().forEach(p -> {
            if (p.name.equals(name))
                product.set(p);
        });
        return product.get();
    }
}
