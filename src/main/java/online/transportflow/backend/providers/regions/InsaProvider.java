package online.transportflow.backend.providers.regions;

import online.transportflow.backend.objects.Product;
import online.transportflow.backend.providers.DefaultLogos;
import online.transportflow.backend.providers.HafasProvider;

import java.util.ArrayList;
import java.util.List;

public class InsaProvider extends HafasProvider {
    public InsaProvider(List<Product> products) {
        super("https://insa.transportflow.online",
                "Sachsen-Anhalt (INSA)",
                "de",
                "https://images.unsplash.com/photo-1569663950244-5f2616a977a4?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80",
                "white",
                products);
        super.beta = true;
    }

    public static List<Product> getProviderProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("nationalExpress", "ICE", DefaultLogos.getInterCityExpress()));
        products.add(new Product("national", "IC/EC", DefaultLogos.getInterCity()));
        products.add(new Product("regional", "RE/RB", DefaultLogos.getRegional()));
        products.add(new Product("suburban", "S-Bahn", DefaultLogos.getSuburban()));
        products.add(new Product("tram", "Tram", DefaultLogos.getTram()));
        products.add(new Product("bus", "Bus", DefaultLogos.getBus()));
        products.add(new Product("tourismTrain", "TT", DefaultLogos.getTrain()));
        return products;
    }
}
