package online.transportflow.backend;

import static spark.Spark.*;

public class Server {
    public static void main(String... args) {
        staticFiles.location("/public");

        get("/hello", (req, res) -> "Transportflow API v0.1");

        before("/api/*", (req, res) -> {
            if (req.queryParams("city") == null) {
                halt(400, "No city specified");
                return;
            }

            String provider = null;
            switch (req.queryParams("city")) {
                case "berlin":
                    provider = "bvg";
            }

            if (provider == null) {
                halt(400, "City not valid");
                return;
            }

            req.attribute("provider", provider);
        });

        get("/api/test", (req, res) -> req.attribute("provider"));
    }
}
