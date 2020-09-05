package online.transportflow.backend.providers.regions;

import online.transportflow.backend.objects.Product;
import online.transportflow.backend.providers.DefaultLogos;
import online.transportflow.backend.providers.HafasProvider;

import java.util.ArrayList;
import java.util.List;

public class VmtProvider extends HafasProvider {
    public VmtProvider(List<Product> products) {
        super("https://vmt.transportflow.online",
                "Thüringen (VMT)",
                "de",
                "https://images.unsplash.com/photo-1565618958134-92787e5d51ea?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=967&q=80",
                "white",
                products);
        super.beta = false;
    }

    public static List<Product> getProviderProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("long-distance-train", "ICE/IC/EC", DefaultLogos.getTrain()));
        products.add(new Product("regional-train", "RE/RB", DefaultLogos.getRegional()));
        products.add(new Product("tram", "Tram", DefaultLogos.getTram()));
        products.add(new Product("bus", "Bus", DefaultLogos.getBus()));
        return products;
    }
}