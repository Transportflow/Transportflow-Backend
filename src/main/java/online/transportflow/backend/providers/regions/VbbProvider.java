package online.transportflow.backend.providers.regions;

import online.transportflow.backend.objects.Product;
import online.transportflow.backend.providers.HafasProvider;

import java.util.ArrayList;
import java.util.List;

public class VbbProvider extends HafasProvider {
    public VbbProvider(List<Product> products) {
        super("https://vbb.transportflow.online",
                "Brandenburg",
                "de",
                "https://images.unsplash.com/photo-1530464684439-723262c0d16e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=700&q=80",
                "white",
                products);
    }

    public static List<Product> getProviderProducts() {
        return BvgProvider.getProviderProducts();
    }
}
