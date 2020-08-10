package online.transportflow.backend.providers.regions;

import online.transportflow.backend.objects.Product;
import online.transportflow.backend.providers.DefaultLogos;

// https://images.unsplash.com/photo-1542344807-157f76301e8a?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=3067&q=80
public class RmvProvider {
    public InsaProvider(List<Product> products) {
        super("https://rmv.transportflow.online",
                "Rhein-Main (RMV)",
                "de",
                "https://images.unsplash.com/photo-1542344807-157f76301e8a?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=3067&q=80",
                "white",
                products);
    }

    public static List<Product> getProviderProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("nationalExpress", "ICE", DefaultLogos.getInterCityExpress()));
        products.add(new Product("national", "IC/EC", DefaultLogos.getInterCity()));
        products.add(new Product("regional", "RE/RB", DefaultLogos.getRegional()));
        products.add(new Product("suburban", "S-Bahn", DefaultLogos.getSuburban()));
        products.add(new Product("tram", "Tram", DefaultLogos.getTram()));
        products.add(new Product("bus", "Bus", DefaultLogos.getBus()));
        return products;
    }
}
