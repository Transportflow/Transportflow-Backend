package online.transportflow.backend.providers.regions;

import online.transportflow.backend.objects.Product;
import online.transportflow.backend.providers.DefaultLogos;
import online.transportflow.backend.providers.HafasProvider;

import java.util.ArrayList;
import java.util.List;

public class CflProvider extends HafasProvider {
    public CflProvider(List<Product> products) {
        super("https://cfl.transportflow.online",
                "Luxemburg (CFL)",
                "de",
                "https://images.unsplash.com/photo-1570450356884-f45a7075b17f?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80",
                "white",
                products);
        super.beta = false;
    }

    public static List<Product> getProviderProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("express-train", "TGV/ICE/EC", "https://cfl.hafas.de/hafas-res/img/products/new/1_pic.png"));
        products.add(new Product("local-train", "Zug", "https://cfl.hafas.de/hafas-res/img/products/new/16_pic.png"));
        products.add(new Product("tram", "Tram", "https://cfl.hafas.de/hafas-res/img/products/new/256_pic.png"));
        products.add(new Product("bus", "Bus", "https://cfl.hafas.de/hafas-res/img/products/new/32_pic.png"));
        products.add(new Product("gondola", "Seilbahn", "https://cfl.hafas.de/hafas-res/img/products/new/512_pic.png"));
        return products;
    }
}