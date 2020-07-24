package online.transportflow.backend.providers.regions;

import online.transportflow.backend.objects.Product;
import online.transportflow.backend.providers.DefaultLogos;
import online.transportflow.backend.providers.HafasProvider;

import java.util.ArrayList;
import java.util.List;

public class DbProvider extends HafasProvider {
    public DbProvider(List<Product> products) {
        super("https://db.transportflow.online",
                "Deutsche Bahn",
                "de",
                "https://images.unsplash.com/photo-1586718252210-03428225427a?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1953&q=80",
                "white",
                products);
    }

    public static List<Product> getProviderProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("nationalExpress", "ICE", DefaultLogos.getInterCityExpress()));
        products.add(new Product("national", "IC/EC", DefaultLogos.getInterCity()));
        products.add(new Product("regionalExp", "RE", DefaultLogos.getTrain()));
        products.add(new Product("regional", "RB", DefaultLogos.getRegional()));
        products.add(new Product("suburban", "S-Bahn", DefaultLogos.getSuburban()));
        products.add(new Product("bus", "Bus", DefaultLogos.getBus()));
        products.add(new Product("ferry", "Fähre", DefaultLogos.getFerry()));
        products.add(new Product("subway", "U-Bahn", DefaultLogos.getSubway()));
        products.add(new Product("tram", "Tram", DefaultLogos.getTram()));
        products.add(new Product("taxi", "Taxi", DefaultLogos.getTaxi()));
        return products;
    }
}