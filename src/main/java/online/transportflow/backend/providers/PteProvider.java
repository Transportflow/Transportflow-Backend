package online.transportflow.backend.providers;

import online.transportflow.backend.objects.Product;
import online.transportflow.backend.objects.location.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PteProvider extends GeneralProvider {
    public PteProvider(String regionName, String region, String provider, String language, String image, String textColor, List<Product> products) {
        super("", regionName, region, provider, language, image, textColor, products);
    }

    public static List<Product> PteProductsToTransportflow(Set<de.schildbach.pte.dto.Product> p) {
        List<Product> products = new ArrayList<>();
        p.forEach(prdct -> {
            products.add(new Product(prdct.name(), prdct.name(), ""));
        });
        return products;
    }

    public static Location PteLocationToTransportflow(de.schildbach.pte.dto.Location l) {
        double lon = 0;
        double lat = 0;
        try {
            lon = l.getLonAsDouble();
            lat = l.getLatAsDouble();
        } catch(NullPointerException ignore) {}
        return new Location(l.name, l.place, lon, lat, 0);
    }
}
