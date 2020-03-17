package online.transportflow.backend.objects;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Location {
    private LocationType type;
    private String id;
    private String name;
    private String regionName;
    private List<Product> products;
    private Number distance;

    private Coordinates coordinates;

    public Location(LocationType type, @Nullable String id, String name, @Nullable String regionName, List<Product> products, Coordinates coordinates, Number distance) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.regionName = regionName;
        this.products = products;
        this.coordinates = coordinates;
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRegionName() {
        return regionName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocationType getType() {
        return type;
    }

    public Number getDistance() {
        return distance;
    }
}
