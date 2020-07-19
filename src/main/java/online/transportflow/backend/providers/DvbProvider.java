package online.transportflow.backend.providers;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import online.transportflow.backend.objects.Product;
import online.transportflow.backend.objects.location.Location;
import online.transportflow.backend.objects.location.Stop;
import online.transportflow.backend.utils.GK4toWGS84;
import org.matsim.api.core.v01.Coord;
import org.matsim.core.utils.geometry.transformations.TransformationFactory;
import org.osgeo.proj4j.ProjCoordinate;
import org.osgeo.proj4j.Registry;
import org.osgeo.proj4j.parser.Proj4Parser;
import org.osgeo.proj4j.proj.Projection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class DvbProvider extends GeneralProvider {
    public DvbProvider(List<Product> products) {
        super("https://webapi.vvo-online.de",
                "Dresden",
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
                .field("regionalOnly", String.valueOf(false))
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
                        0);
                finalStops.add(s);
            });
            return finalStops;
        } catch(Exception e) {
            return null;
        }
    }

    @Override
    public List<Stop> searchLocation(double lat, double lng, int radius, int results, boolean stops, boolean poi) {
        //TODO: Optimise!!!!!
        HttpResponse<JsonNode> response = Unirest.get("http://adwira.wien:7826/toGK4?lat="+lat+"&lng="+lng)
                .asJson();
        JSONObject res = response.getBody().getObject();
        return searchLocation("coord:"+res.get("x")+":"+res.get("y"), results, stops, false, poi);
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
        } catch(Exception e) {
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
