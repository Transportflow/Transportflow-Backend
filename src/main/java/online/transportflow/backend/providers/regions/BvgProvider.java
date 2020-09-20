package online.transportflow.backend.providers.regions;

import online.transportflow.backend.objects.Product;
import online.transportflow.backend.providers.DefaultLogos;
import online.transportflow.backend.providers.HafasProvider;

import java.util.ArrayList;
import java.util.List;

public class BvgProvider extends HafasProvider {
    public BvgProvider(List<Product> products) {
        super("https://bvg.transportflow.online",
                "Berlin (BVG)",
                "Berlin",
                "BVG",
                "de",
                "https://images.unsplash.com/photo-1572083272166-b2a0855fcbb3?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80",
                "white",
                products);
    }

    public static List<Product> getProviderProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("suburban", "S-Bahn", DefaultLogos.getSuburban()));
        products.add(new Product("subway", "U-Bahn", DefaultLogos.getSubway()));
        products.add(new Product("tram", "Straßenbahn", DefaultLogos.getTram()));
        products.add(new Product("bus", "Bus", DefaultLogos.getBus()));
        products.add(new Product("ferry", "Fähre", DefaultLogos.getFerry()));
        products.add(new Product("express", "IC/ICE", DefaultLogos.getTrain()));
        products.add(new Product("regional", "RE/RB", DefaultLogos.getRegional()));
        return products;
    }
}
