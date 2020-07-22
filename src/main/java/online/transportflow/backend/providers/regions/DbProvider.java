package online.transportflow.backend.providers.regions;

import online.transportflow.backend.objects.Product;
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
        products.add(new Product("nationalExpress", "ICE", "https://upload.wikimedia.org/wikipedia/commons/b/bc/ICE-Logo.svg"));
        products.add(new Product("national", "IC/EC", "https://upload.wikimedia.org/wikipedia/commons/6/6f/IC-Logo.svg"));
        products.add(new Product("regionalExp", "RE", "https://upload.wikimedia.org/wikipedia/commons/d/d5/Deutsche_Bahn_AG-Logo.svg"));
        products.add(new Product("regional", "RB", "https://www.dvb.de/assets/img/trans-icon/transport-train.svg"));
        products.add(new Product("suburban", "S-Bahn", "https://upload.wikimedia.org/wikipedia/commons/e/e7/S-Bahn-Logo.svg"));
        products.add(new Product("bus", "Bus", "https://upload.wikimedia.org/wikipedia/commons/8/83/BUS-Logo-BVG.svg"));
        products.add(new Product("ferry", "Fähre", "https://upload.wikimedia.org/wikipedia/commons/d/d6/F%C3%A4hre-Logo-BVG.svg"));
        products.add(new Product("subway", "U-Bahn", "https://upload.wikimedia.org/wikipedia/commons/a/a3/U-Bahn.svg"));
        products.add(new Product("tram", "Tram", "https://upload.wikimedia.org/wikipedia/commons/a/a6/Tram-Logo.svg"));
        products.add(new Product("taxi", "Taxi", "https://www.dvb.de/assets/img/trans-icon/transport-alita.svg"));
        return products;
    }
}