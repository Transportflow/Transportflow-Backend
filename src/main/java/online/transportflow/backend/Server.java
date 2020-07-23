package online.transportflow.backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import online.transportflow.backend.objects.location.Stop;
import online.transportflow.backend.objects.monitor.Monitor;
import online.transportflow.backend.providers.regions.BvgProvider;
import online.transportflow.backend.providers.regions.DbProvider;
import online.transportflow.backend.providers.regions.DvbProvider;
import online.transportflow.backend.providers.Provider;
import online.transportflow.backend.providers.regions.VbbProvider;
import spark.Request;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static spark.Spark.*;

public class Server {

    static List<Provider> providers = new ArrayList<>();

    public static void main(String... args) {
        providers.add(new DbProvider(DbProvider.getProviderProducts()));
        providers.add(new DvbProvider(DvbProvider.getProviderProducts()));
        providers.add(new BvgProvider(BvgProvider.getProviderProducts()));
        providers.add(new VbbProvider(VbbProvider.getProviderProducts()));

        staticFiles.location("/public");

        options("/*",
                (request, response) -> {

                    String accessControlRequestHeaders = request
                            .headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        response.header("Access-Control-Allow-Headers",
                                accessControlRequestHeaders);
                    }

                    String accessControlRequestMethod = request
                            .headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        response.header("Access-Control-Allow-Methods",
                                accessControlRequestMethod);
                    }

                    return "OK";
                });

        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

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

            List<Stop> loc = provider.searchLocation(query,
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
                latitude = Double.parseDouble(req.queryParams("lat"));
                longitude = Double.parseDouble(req.queryParams("lng"));
            } catch (Exception e) {
                halt(400, "Latitude or longitude not specified/not numbers");
            }

            if (latitude == null || longitude == null) {
                return null;
            }

            List<Stop> loc = provider.searchLocation(latitude, longitude,
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

            try {
                Monitor monitor = provider.getDepartures(req.params("stopId"), when, req.queryParams("duration") != null ? Integer.parseInt(req.queryParams("duration")) : 200);
                return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(monitor);
            } catch(Exception e) {
                halt(400, "Haltestelleninformationen aktuell nicht verfügbar");
                return new Monitor(null, null);
            }
        });
    }

    static Provider getProvider(Request req) throws Exception {
        if (req.params("region") == null) {
            throw new Exception("Keine Region ausgewählt.");
        }

        Provider provider = providers.stream().filter(p -> p.getRegionName().toLowerCase().equals(req.params("region").toLowerCase()))
                .findFirst().orElse(null);

        if (provider == null) {
            throw new Exception("Aktuell gewählte Region nicht verfügbar.");
        }

        return provider;
    }
}
