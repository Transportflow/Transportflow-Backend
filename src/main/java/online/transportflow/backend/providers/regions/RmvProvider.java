package online.transportflow.backend.providers.regions;

import online.transportflow.backend.objects.Product;
import online.transportflow.backend.providers.DefaultLogos;
import online.transportflow.backend.providers.HafasProvider;

import java.util.ArrayList;
import java.util.List;

// https://images.unsplash.com/photo-1542344807-157f76301e8a?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=3067&q=80
public class RmvProvider extends HafasProvider {
    public RmvProvider(List<Product> products) {
        super("https://rmv.transportflow.online",
                "Rhein-Main (RMV)",
                "de",
                "https://images.unsplash.com/photo-1542344807-157f76301e8a?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=3067&q=80",
                "white",
                products);
        super.beta = false;
    }

    public static List<Product> getProviderProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("express-train", "ICE", "https://www.rmv.de/auskunft/s/n/img/products/1_pic.png"));
        products.add(new Product("long-distance-train", "IC/EC", "https://www.rmv.de/auskunft/s/n/img/products/2_pic.png"));
        products.add(new Product("regiona-train", "RE/RB", "https://www.rmv.de/auskunft/s/n/img/products/4_pic.png"));
        products.add(new Product("s-bahn", "S-Bahn", "https://www.rmv.de/auskunft/s/n/img/products/8_pic.png"));
        products.add(new Product("u-bahn", "U-Bahn", "https://www.rmv.de/auskunft/s/n/img/products/16_pic.png"));
        products.add(new Product("tram", "Straßenbahn", "https://www.rmv.de/auskunft/s/n/img/products/32_pic.png"));
        products.add(new Product("watercraft", "Fähre", "https://www.rmv.de/auskunft/s/n/img/products/256_pic.png"));
        products.add(new Product("bus", "Bus", "https://www.rmv.de/auskunft/s/n/img/products/64_pic.png"));
        products.add(new Product("ast", "Anruf-Sammel-Taxi", "https://www.rmv.de/auskunft/s/n/img/products/512_pic.png"));
        products.add(new Product("cable-car", "Seilbahn", "https://www.dvb.de/assets/img/trans-icon/transport-lift.svg"));
        return products;
    }
}
