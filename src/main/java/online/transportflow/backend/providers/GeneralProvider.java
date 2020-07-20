package online.transportflow.backend.providers;

import com.google.gson.annotations.Expose;
import online.transportflow.backend.objects.Product;
import online.transportflow.backend.objects.location.Stop;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.List;

public class GeneralProvider implements Provider {
    public String baseUrl;
    @Expose(serialize = true)
    public String regionName;
    public String language;
    @Expose(serialize = true)
    public String image;
    @Expose(serialize = true)
    public String textColor;

    @Expose(serialize = true)
    public List<Product> products;
    @Expose(serialize = false)
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");

    public GeneralProvider(String baseUrl, String regionName, String language, String image, String textColor, List<Product> products) {
        this.baseUrl = baseUrl;
        this.regionName = regionName;
        this.textColor = textColor;
        this.products = products;
        this.image = image;
        this.language = language;
    }

    @Override
    public String getRegionName() {
        return regionName;
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public List<Stop> searchLocation(String query, int results, boolean stops, boolean addresses, boolean poi)  {
        return null;
    }

    @Override
    public List<Stop> searchLocation(double lat, double lng, int radius, int results, boolean stops, boolean poi)  {
        return null;
    }
}