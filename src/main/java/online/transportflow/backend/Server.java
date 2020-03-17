package online.transportflow.backend;

import com.google.gson.Gson;
import online.transportflow.backend.objects.Location;
import online.transportflow.backend.providers.BvgProvider;
import online.transportflow.backend.providers.Provider;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static spark.Spark.*;

public class Server {
    public static void main(String... args) {
        staticFiles.location("/public");

        get("/hello", (req, res) -> "Transportflow API v0.1");

        before("/api/*", (req, res) -> {
            if (req.queryParams("region") == null) {
                halt(400, "No region specified");
                return;
            }

            Provider provider = null;
            switch (req.queryParams("region").toLowerCase()) {
                case "berlin":
                case "brandenburg":
                    provider = new BvgProvider(BvgProvider.getProducts());
                    break;
            }

            if (provider == null) {
                halt(400, "Region not valid");
                return;
            }

            res.type("application/json");
            req.attribute("provider", provider);
        });

        get("/api/locations", (req, res) -> {
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
    }
}
