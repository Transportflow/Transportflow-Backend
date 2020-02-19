package online.transportflow.backend;

import static spark.Spark.*;

public class Server {
    public static void main(String... args) {
        staticFiles.location("/public");
    }
}
