package online.transportflow.backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import online.transportflow.backend.objects.Coordinates;
import online.transportflow.backend.objects.Location;
import online.transportflow.backend.objects.Monitor;
import online.transportflow.backend.providers.BvgProvider;
import online.transportflow.backend.providers.Provider;
import spark.Request;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static spark.Spark.*;

public class Server {

    static List<Provider> providers = new ArrayList<>();

    public static void main(String... args) {
        providers.add(new BvgProvider(BvgProvider.getProviderProducts()));

        staticFiles.location("/public");

        get("/providers", (req, res) -> {
            res.type("application/json");
            return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(providers);
        });

        before("/:region/*", (req, res) -> {
            Provider provider = null;
            try {
                provider = getProvider(req);
            } catch (Exception e) {
                halt(400, e.getMessage());
            }
            if (provider == null)
                return;

            res.type("application/json");
            req.attribute("provider", provider);
        });

        get("/:region", (req, res) -> {
            Provider provider = null;
            try {
                provider = getProvider(req);
            } catch (Exception e) {
                halt(400, e.getMessage());
            }
            if (provider == null)
                return null;

            res.type("application/json");
            return "{region: \"" + provider.getRegionName() + "\", products:" + new Gson().toJson(provider.getProducts()) + "}";
        });

        get("/:region/locations", (req, res) -> {
            Provider provider = req.attribute("provider");

            String query = req.queryParams("query");
            if (query == null) {
                halt(400, "No query specified");
                return null;
            }

            List<Location> loc = provider.searchLocation(query,
                    req.queryParams("results") != null ? Integer.parseInt(req.queryParams("results")) : 7,
                    req.queryParams("stops") != null && req.queryParams("stops").equals("true"),
                    req.queryParams("address") != null && req.queryParams("address").equals("true"),
                    req.queryParams("pois") != null && req.queryParams("pois").equals("true"));

            return new Gson().toJson(loc);
        });

        get("/:region/nearby", (req, res) -> {
            Provider provider = req.attribute("provider");

            Double latitude = null;
            Double longitude = null;
            try {
                latitude = Double.parseDouble(req.queryParams("latitude"));
                longitude = Double.parseDouble(req.queryParams("longitude"));
            } catch (Exception e) {
                halt(400, "Latitude or longitude not specified/not numbers");
            }

            if (latitude == null || longitude == null) {
                return null;
            }

            List<Location> loc = provider.searchLocation(new Coordinates(latitude, longitude),
                    req.queryParams("radius") != null ? Integer.parseInt(req.queryParams("radius")) : 250,
                    req.queryParams("results") != null ? Integer.parseInt(req.queryParams("results")) : 5,
                    req.queryParams("stops") != null && req.queryParams("stops").equals("true"),
                    req.queryParams("pois") != null && req.queryParams("pois").equals("true"));

            return new Gson().toJson(loc);
        });

        get("/:region/departures/:stopId", (req, res) -> {
            Provider provider = req.attribute("provider");
            Date when = new Date();
            if (req.queryParams("when") != null) {
                when.setTime(Long.parseLong(req.queryParams("when")));
            }

            Monitor monitor = provider.getDepartures(req.params("stopId"), when, req.queryParams("duration") != null ? Integer.parseInt(req.queryParams("duration")) : 250);
            return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(monitor);
        });
    }

    static Provider getProvider(Request req) throws Exception {
        if (req.params("region") == null) {
            throw new Exception("No region specified");
        }

        Provider provider = providers.stream().filter(p -> p.getRegionName().toLowerCase().equals(req.params("region").toLowerCase()))
                .findFirst().orElse(null);

        if (provider == null) {
            throw new Exception("Region not valid");
        }

        return provider;
    }
}
