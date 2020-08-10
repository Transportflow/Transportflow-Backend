package online.transportflow.backend.providers.regions;

import online.transportflow.backend.objects.Product;
import online.transportflow.backend.providers.DefaultLogos;

// https://images.unsplash.com/photo-1542344807-157f76301e8a?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=3067&q=80
public class RmvProvider {
    public RmvProvider(List<Product> products) {
        super("https://rmv.transportflow.online",
                "Rhein-Main (RMV)",
                "de",
                "https://images.unsplash.com/photo-1542344807-157f76301e8a?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=3067&q=80",
                "white",
                products);
    }

    public static List<Product> getProviderProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("express-train", "ICE", DefaultLogos.getInterCityExpress()));
        products.add(new Product("long-distance-train", "IC/EC", DefaultLogos.getInterCity()));
        products.add(new Product("regiona-train", "RE/RB", DefaultLogos.getRegional()));
        products.add(new Product("s-bahn", "S-Bahn", DefaultLogos.getSuburban()));
        products.add(new Product("u-bahn", "U-Bahn", DefaultLogos.getSubway()));
        products.add(new Product("tram", "Straßenbahn", DefaultLogos.getTram()));
        products.add(new Product("watercraft", "Fähre", DefaultLogos.getFerry()));
        products.add(new Product("bus", "Bus", DefaultLogos.getBus()));
        products.add(new Product("ast", "Anruf-Sammel-Taxi", DefaultLogos.getTaxi()));
        products.add(new Product("cable-car", "Seilbahn", "https://www.dvb.de/assets/img/trans-icon/transport-lift.svg"));
        return products;
    }
}
