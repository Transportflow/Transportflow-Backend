package online.transportflow.backend.providers.regions;

import online.transportflow.backend.objects.Product;
import online.transportflow.backend.providers.HafasProvider;

import java.util.ArrayList;
import java.util.List;

public class BvgProvider extends HafasProvider {
    public BvgProvider(List<Product> products) {
        super("https://bvg.transportflow.online",
                "Berlin (BVG)",
                "de",
                "https://images.unsplash.com/photo-1552035496-08efc7baf40e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1566&q=80",
                "white",
                products);
    }

    public static List<Product> getProviderProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("suburban", "S-Bahn", "https://upload.wikimedia.org/wikipedia/commons/e/e7/S-Bahn-Logo.svg"));
        products.add(new Product("subway", "U-Bahn", "https://upload.wikimedia.org/wikipedia/commons/e/ee/U-Bahn_Berlin_logo.svg"));
        products.add(new Product("tram", "Straßenbahn", "https://upload.wikimedia.org/wikipedia/commons/a/a6/Tram-Logo.svg"));
        products.add(new Product("bus", "Bus", "https://upload.wikimedia.org/wikipedia/commons/8/83/BUS-Logo-BVG.svg"));
        products.add(new Product("ferry", "Fähre", "https://upload.wikimedia.org/wikipedia/commons/d/d6/F%C3%A4hre-Logo-BVG.svg"));
        products.add(new Product("express", "IC/ICE", "https://www.dvb.de/assets/img/trans-icon/transport-train.svg"));
        products.add(new Product("regional", "RE/RB", "https://upload.wikimedia.org/wikipedia/commons/d/d5/Deutsche_Bahn_AG-Logo.svg"));
        return products;
    }
}
