package online.transportflow.backend.providers.regions;

import online.transportflow.backend.objects.Product;
import online.transportflow.backend.providers.DefaultLogos;
import online.transportflow.backend.providers.HafasProvider;

import java.util.ArrayList;
import java.util.List;

public class NahshProvider extends HafasProvider {
    public NahshProvider(List<Product> products) {
        super("https://nahsh.transportflow.online",
                "Schleswig-Holstein (NAHSH)",
                "Schleswig-Holstein",
                "NAHSH",
                "de",
                "https://images.unsplash.com/photo-1597231152034-dc2a622f7f83?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80",
                "black",
                products);
        super.beta = false;
    }

    public static List<Product> getProviderProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("nationalExpress", "ICE", DefaultLogos.getInterCityExpress()));
        products.add(new Product("national", "IC/EC", DefaultLogos.getInterCity()));
        products.add(new Product("interregional", "IR", DefaultLogos.getTrain()));
        products.add(new Product("regional", "RE/RB", DefaultLogos.getRegional()));
        products.add(new Product("suburban", "S-Bahn", DefaultLogos.getSuburban()));
        products.add(new Product("bus", "Bus", DefaultLogos.getBus()));
        products.add(new Product("tram", "Tram", DefaultLogos.getTram()));
        products.add(new Product("ferry", "Fähre", DefaultLogos.getFerry()));
        products.add(new Product("subway", "U-Bahn", DefaultLogos.getSubway()));
        products.add(new Product("onCall", "Anruf-Taxi", DefaultLogos.getTaxi()));
        return products;
    }
}
