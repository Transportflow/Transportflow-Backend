package online.transportflow.backend.objects.location;

import com.google.gson.annotations.Expose;
import online.transportflow.backend.objects.BaseObject;
import online.transportflow.backend.objects.Product;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * This may be a station, region or just a simple stop
 * https://howtodoinjava.com/gson/custom-serialization-deserialization/
 */
public class Stop extends BaseObject {
    @Expose
    private String name;
    @Expose
    private Location location;

    @Expose(deserialize = false)
    @Nullable
    private List<Product> products;

    @Expose
    @Nullable
    private Stop[] regions;

    @Expose
    @Nullable
    private Stop[] stations;
    @Expose
    private double distance;

    public Stop(String id, String name, Location location, @Nullable List<Product> products, @Nullable Stop[] regions, @Nullable Stop[] stations, double distance) {
        super.id = id;
        this.name = name;
        this.location = location;
        this.products = products;
        this.regions = regions;
        this.stations = stations;
        this.distance = distance;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    @Nullable
    public List<Product> getProducts() {
        return products;
    }

    @Nullable
    public Stop[] getRegions() {
        return regions;
    }

    @Nullable
    public Stop[] getStations() {
        return stations;
    }

    public double getDistance() {
        return distance;
    }
}
