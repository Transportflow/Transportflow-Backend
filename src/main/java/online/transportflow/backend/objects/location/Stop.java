package online.transportflow.backend.objects.location;

import com.google.gson.annotations.Expose;
import online.transportflow.backend.objects.BaseObject;
import online.transportflow.backend.objects.Product;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * This may be a station, region or just a simple stop
 */
public class Stop extends BaseObject {
    @Expose
    private String name;
    @Expose
    private Location location;

    @Expose
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
