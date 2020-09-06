package online.transportflow.backend.providers.regions;

import online.transportflow.backend.objects.Product;
import online.transportflow.backend.providers.DefaultLogos;
import online.transportflow.backend.providers.HafasProvider;

import java.util.ArrayList;
import java.util.List;

public class RsagProvider extends HafasProvider {
    public RsagProvider(List<Product> products) {
        super("https://rsag.transportflow.online",
                "Rostock (RSAG)",
                "de",
                "https://images.unsplash.com/photo-1566333447602-d814291b97dc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80",
                "white",
                products);
        super.beta = false;
    }

    public static List<Product> getProviderProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("ice", "ICE", DefaultLogos.getInterCityExpress()));
        products.add(new Product("ic-ec", "IC/EC", DefaultLogos.getInterCity()));
        products.add(new Product("long-distance-train", "Zug", DefaultLogos.getTrain()));
        products.add(new Product("regional-train", "RE/RB", DefaultLogos.getRegional()));
        products.add(new Product("s-bahn", "S-Bahn", DefaultLogos.getSuburban()));
        products.add(new Product("bus", "Bus", DefaultLogos.getBus()));
        products.add(new Product("ferry", "Fähre", DefaultLogos.getFerry()));
        products.add(new Product("u-bahn", "U-Bahn", DefaultLogos.getSubway()));
        products.add(new Product("tram", "Tram", DefaultLogos.getTram()));
        products.add(new Product("on-call", "Anruf-Taxi", DefaultLogos.getTaxi()));
        return products;
    }
}