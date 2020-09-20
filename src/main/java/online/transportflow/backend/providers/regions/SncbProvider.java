package online.transportflow.backend.providers.regions;

import online.transportflow.backend.objects.Product;
import online.transportflow.backend.providers.DefaultLogos;
import online.transportflow.backend.providers.HafasProvider;

import java.util.ArrayList;
import java.util.List;

public class SncbProvider extends HafasProvider {
    public SncbProvider(List<Product> products) {
        super("https://sncb.transportflow.online",
                "Belgien (SNCB)",
                "Belgien",
                "SNCB",
                "de",
                "https://images.unsplash.com/photo-1592758079583-fcf6ede7360c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1262&q=80",
                "black",
                products);
        super.beta = false;
    }

    public static List<Product> getProviderProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("high-speed-train", "HighSpeed-Train", DefaultLogos.getTrain()));
        products.add(new Product("intercity-p", "IC/P", "https://trainmap.belgiantrain.be/images/train-ic.png"));
        products.add(new Product("s-train", "S-Train", "https://trainmap.belgiantrain.be/images/train-s.png"));
        products.add(new Product("local-train", "Local Train", "https://trainmap.belgiantrain.be/images/train-l.png"));
        products.add(new Product("metro", "Metro", "https://upload.wikimedia.org/wikipedia/commons/thumb/1/16/U-Bahn_Wien.svg/1024px-U-Bahn_Wien.svg.png"));
        products.add(new Product("bus", "Bus", "https://trainmap.belgiantrain.be/images/train-b.png"));
        products.add(new Product("tram", "Tram", DefaultLogos.getTram()));
        return products;
    }
}